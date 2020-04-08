package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import nl.rubend.pris.Utils;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import org.controlsfx.control.SearchableComboBox;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class AccountAanmakenPane implements Initializable, IngelogdGebruiker {
	public Label errorMeldingLabel;
	private Systeembeheerder systeembeheerder;
	@FXML
	private TextField nieuwAccountName;
	@FXML
	private TextField nieuwAccountEmail;
	@FXML
	private TextField nieuwAccountWachtwoord;
	@FXML
	private Spinner nieuwAccountNummer;
	@FXML
	private ComboBox<String> accountTypeComboBox;
	@FXML
	private SearchableComboBox<String> comboBoxGroep;
	@FXML
	private Label nummberLabel;
	@FXML
	private Label hoortBijLabel;
	@FXML
	private SearchableComboBox<String> slber;
	@FXML
	private Label slbLabel;

	private School school = School.getSchool();
	private ArrayList<Klas> klassen = school.getKlassen();
	private ArrayList<Cursus> cursussen = school.getCursussen();
	private ArrayList<String> klassenNamen = new ArrayList<>();
	private ArrayList<String> cursussCodes = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nieuwAccountNummer.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999999999));
		NumberFormat format = NumberFormat.getIntegerInstance();
		UnaryOperator<TextFormatter.Change> filter = c -> {
			if (c.isContentChange()) {
				ParsePosition parsePosition = new ParsePosition(0);
				format.parse(c.getControlNewText(), parsePosition);
				if (parsePosition.getIndex() == 0 ||
						parsePosition.getIndex() < c.getControlNewText().length()) {
					return null;
				}
			}
			return c;
		};
		TextFormatter<Integer> nummerFormatter = new TextFormatter<Integer>(
				new IntegerStringConverter(), 0, filter);
		nieuwAccountNummer.getEditor().setTextFormatter(nummerFormatter);
		for (Class gebruikerType : Gebruiker.gebruikerTypes) {
			accountTypeComboBox.getItems().add(gebruikerType.getSimpleName());
		}
	}

	@FXML
	private void maakAccountAan() {
		try {
			maakAccountAanWerkelijk();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException | InvocationTargetException e) {
			melding(e.getCause().getMessage());
		} catch (IllegalArgumentException e) {
			melding(e.getMessage());
		}
	}

	private void maakAccountAanWerkelijk() throws NoSuchMethodException, NotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, IllegalArgumentException, ClassNotFoundException {
		//Deze functie is bijzonder ingewikkeld, met de hoop dat er nieuwe user-types kunnen worden toegevoegd zonder deze code aan te passen.
		if (accountTypeComboBox.getValue().equals("")) {
			melding("geen accounttype geselecteerd!");
			return;
		}

		Class<Gebruiker> gebruikerType = (Class<Gebruiker>) Class.forName("nl.rubend.pris.model." + accountTypeComboBox.getValue());
		String naam = nieuwAccountName.getText();
		String email = nieuwAccountEmail.getText();
		String wachtwoord = nieuwAccountWachtwoord.getText();
		String groep = comboBoxGroep.getValue();
		if (naam != null && email != null && wachtwoord != null) {
			Gebruiker gebruiker;
			if (gebruikerType.equals(Systeembeheerder.class))
				gebruiker = (Gebruiker) gebruikerType.getDeclaredConstructor(String.class, String.class, String.class).newInstance(email, wachtwoord, naam);
			else {
				int nummer = (Integer) nieuwAccountNummer.getValue();
				if (nummer != 0 && groep != null) {

					if (gebruikerType.equals(Student.class) && !slber.getValue().equals("")) {
						Docent slberClass = (Docent) school.getGebruikerByEmail(slber.getValue());
						gebruiker = gebruikerType.getDeclaredConstructor(String.class, String.class, String.class, int.class, Docent.class).newInstance(email, wachtwoord, naam, nummer, slberClass);
						school.getKlasByName(groep).addStudent((Student) gebruiker);
					} else if (gebruikerType.equals(Docent.class)) {
						gebruiker = gebruikerType.getDeclaredConstructor(String.class, String.class, String.class, int.class).newInstance(email, wachtwoord, naam, nummer);
						((Docent) gebruiker).setCursus(school.getCursusByCode(groep));
					} else {
						melding("Niet alle gegevens ingevoerd.");
						return;
					}
				} else {
					melding("Niet alle gegevens ingevoerd.");
					return;
				}
			}
			school.addGebruiker(gebruiker);
			Utils.melding("Account is geregistreerd!");
			nieuwAccountNummer.getValueFactory().setValue(0);
			nieuwAccountName.setText("");
			nieuwAccountEmail.setText("");
			nieuwAccountWachtwoord.setText("");
		}
	}

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		try {
			comboBoxGroep.getItems().removeAll(comboBoxGroep.getItems());
			slber.getItems().removeAll(slber.getItems());
		} catch (Exception e) {}
		systeembeheerder = (Systeembeheerder) gebruiker;
		accountTypeComboBox.setValue(Student.class.getSimpleName());
		accountTypeComboBox.getOnAction().handle(null);
	}

	@FXML
	void handlecomboBoxGebruikersType(ActionEvent actionEvent) {
		try {
			comboBoxGroep.getItems().removeAll(comboBoxGroep.getItems());
			slber.getItems().removeAll(slber.getItems());
		} catch (Exception e) {
			//dit geeft een foutmelding, maar kan gewoon genegeerd worden. Catch doet bij deze niet goed zijn werk.
		}
		//Deze functies hierboven geven foutmeldingen, maar een Try-Catch vangt ze niet af.
		String gebruikersType = accountTypeComboBox.getValue();
		nummberLabel.setVisible(true);
		hoortBijLabel.setVisible(true);
		nieuwAccountNummer.setVisible(true);
		comboBoxGroep.setVisible(true);
		slbLabel.setVisible(false);
		slber.setVisible(false);
		if (gebruikersType.equals("Student")) {
			nummberLabel.setText("Studentnummer");
			hoortBijLabel.setText("Klas");
			slbLabel.setVisible(true);
			slber.setVisible(true);
			for (Klas klas : school.getKlassen()) comboBoxGroep.getItems().add(klas.getKlasNaam());
			for (Gebruiker gebruiker : school.getGebruikers())
				if (gebruiker instanceof Docent) slber.getItems().add(gebruiker.getEmail());
		} else if (gebruikersType.equals("Docent")) {
			nummberLabel.setText("Docentnummer");
			hoortBijLabel.setText("Cursus Code");
			for (Cursus cursus : school.getCursussen())
				if (!cursus.getCursusCode().equals("deleted")) comboBoxGroep.getItems().add(cursus.getCursusCode());
		} else if (gebruikersType.equals("Systeembeheerder")) {
			nummberLabel.setVisible(false);
			hoortBijLabel.setVisible(false);
			nieuwAccountNummer.setVisible(false);
			comboBoxGroep.setVisible(false);
		}
	}

	public void melding(String str) {
//		Het zou misschien beter met CSS gestyled kunnen
		errorMeldingLabel.setTextFill(Color.RED);
		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(f -> errorMeldingLabel.setText(""));
		pause.play();
		errorMeldingLabel.setText(str);
	}
}
