package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import nl.rubend.pris.Utils;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import org.controlsfx.control.SearchableComboBox;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountAanmakenPane implements Initializable, IngelogdGebruiker {
	public Label errorMeldingLabel;
	private Systeembeheerder systeembeheerder;
	@FXML private TextField nieuwAccountName;
	@FXML private TextField nieuwAccountEmail;
	@FXML private TextField nieuwAccountWachtwoord;
	@FXML private Spinner nieuwAccountNummer;
	@FXML private ComboBox<String> accountTypeComboBox;
	@FXML private SearchableComboBox<String> comboBoxGroep;
	@FXML private Label nummberLabel;
	@FXML private Label hoortBijLabel;
	@FXML private SearchableComboBox<String> slber;
	@FXML private Label slbLabel;

	private School school=School.getSchool();
	private ArrayList<Klas> klassen = school.getKlassen();
	private ArrayList<Cursus> cursussen = school.getCursussen();
	private ArrayList<String> klassenNamen = new ArrayList<>();
	private ArrayList<String> cursussCodes = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nieuwAccountNummer.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999999999));
		for(Class gebruikerType:Gebruiker.gebruikerTypes) {
			accountTypeComboBox.getItems().add(gebruikerType.getSimpleName());
		}
	}

	@FXML
	void maakAccountAan(ActionEvent event) throws NotFoundException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		if (accountTypeComboBox.getValue().equals("")) {
			melding("geen accounttype geselecteerd!");
			return;
		}
		Class<Gebruiker> gebruikerType = (Class<Gebruiker>) Class.forName("nl.rubend.pris.model."+accountTypeComboBox.getValue());
		String naam = nieuwAccountName.getText();
		String email = nieuwAccountEmail.getText();
		String wachtwoord = nieuwAccountWachtwoord.getText();
		String groep = comboBoxGroep.getValue();
		if (naam != null && email != null && wachtwoord != null) {
			Gebruiker gebruiker;
			if (gebruikerType.equals(Systeembeheerder.class)) gebruiker = (Gebruiker) gebruikerType.getDeclaredConstructor(String.class,String.class,String.class).newInstance(email, wachtwoord, naam);
			else {
				int nummer = (Integer) nieuwAccountNummer.getValue();
				if (nummer != 0 && !groep.equals("")) {

					if (gebruikerType.equals(Student.class) && !slber.getValue().equals("")) {
						Docent slberClass= (Docent) school.getGebruikerByEmail(slber.getValue());
						gebruiker = gebruikerType.getDeclaredConstructor(String.class,String.class,String.class,int.class,Docent.class).newInstance(email, wachtwoord, naam, nummer,slberClass);
						((Student) gebruiker).addKlas(school.getKlasByName(groep));
					} else if (gebruikerType.equals(Docent.class)) {
						gebruiker = gebruikerType.getDeclaredConstructor(String.class,String.class,String.class,int.class).newInstance(email, wachtwoord, naam, nummer);
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
		systeembeheerder=(Systeembeheerder) gebruiker;
		accountTypeComboBox.setValue(Student.class.getSimpleName());
		accountTypeComboBox.getOnAction().handle(null);
	}
	@FXML
	void handlecomboBoxGebruikersType(ActionEvent actionEvent) {
		String gebruikersType = accountTypeComboBox.getValue();
		comboBoxGroep.getItems().removeAll(comboBoxGroep.getItems());
		nummberLabel.setVisible(true);
		hoortBijLabel.setVisible(true);
		nieuwAccountNummer.setVisible(true);
		comboBoxGroep.setVisible(true);
		slbLabel.setVisible(false);
		slber.setVisible(false);
		if(gebruikersType.equals("Student")) {
			nummberLabel.setText("Studentnummer");
			hoortBijLabel.setText("Klas");
			slbLabel.setVisible(true);
			slber.setVisible(true);
			for(Klas klas:school.getKlassen()) comboBoxGroep.getItems().add(klas.getKlasNaam());
			slber.getItems().removeAll(slber.getItems());
			for(Gebruiker gebruiker:school.getGebruikers()) if(gebruiker instanceof Docent) slber.getItems().add(gebruiker.getEmail());
		} else if (gebruikersType.equals("Docent")) {
			nummberLabel.setText("Docentnummer");
			hoortBijLabel.setText("Cursus Code");
			for(Cursus cursus:school.getCursussen()) comboBoxGroep.getItems().add(cursus.getCursusCode());
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
		errorMeldingLabel.setText(str);
	}
}
