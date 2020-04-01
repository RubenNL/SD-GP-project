package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import nl.rubend.pris.Utils;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountAanmakenPane implements Initializable, IngelogdGebruiker {
	public Label errorMeldingLabel;
	private Systeembeheerder systeembeheerder;
	@FXML private TextField nieuwAccountName;
	@FXML private TextField nieuwAccountEmail;
	@FXML private TextField nieuwAccountWachtwoord;
	@FXML private TextField nieuwAccountNummer;
	@FXML private ComboBox<String> accountTypeComboBox;
	@FXML private ComboBox<String> comboBoxGroep;
	@FXML private Label nummberLabel;
	@FXML private Label hoortBijLabel;

	private School school=School.getSchool();
	private ArrayList<Klas> klassen = school.getKlassen();
	private ArrayList<Cursus> cursussen = school.getCursussen();
	private ArrayList<String> klassenNamen = new ArrayList<>();
	private ArrayList<String> cursussCodes = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ArrayList<String> accountTypen = new ArrayList<>();
		accountTypen.add("Student");
		accountTypen.add("Docent");
		accountTypeComboBox.setItems(FXCollections.observableArrayList(accountTypen));
	}

	@FXML
	void maakAccountAan(ActionEvent event) throws Exception {

		String gebruikersType = accountTypeComboBox.getSelectionModel().getSelectedItem();
		String groep = comboBoxGroep.getSelectionModel().getSelectedItem();
		if (gebruikersType != null) {

			String naam = nieuwAccountName.getText();
			String email = nieuwAccountEmail.getText();
			String wachtwoord = nieuwAccountWachtwoord.getText();
			try {
				int nummer = Integer.valueOf(nieuwAccountNummer.getText());
				if (naam != null && email != null && wachtwoord != null && groep != null) {
					if (gebruikersType.equals("Student")) {
						Klas klas = school.getKlasByName(groep);
						Student newStudent = new Student(email, wachtwoord, naam, nummer);
						try {
							errorMeldingLabel.setText("");
							school.addGebruiker(newStudent);
							klas.addStudent(newStudent);
							Utils.melding("Student account geregestreerd!\n"
									+ "Naam: " + newStudent.getNaam() + "\n"
									+ "Email: " + newStudent.getEmail() + "\n"
									+ "Studentnummer: " + newStudent.getStudentNummer() + "\n"
									+ "En hij zit in : " + newStudent.getKlassen().toString()
							);
						} catch (Exception e) {
							melding(e.getMessage());
						}
					}
					if (gebruikersType.equals("Docent")) {
						Cursus cursus = school.getCursusByCode(groep);
						Docent newDocent = new Docent(email, wachtwoord, naam, nummer);
						try {
							school.addGebruiker(newDocent);
							newDocent.setCursus(cursus);
							errorMeldingLabel.setText("");
							Utils.melding("Docent account geregestreerd!\n"
									+ "Naam: " + newDocent.getNaam() + "\n"
									+ "Email: " + newDocent.getEmail() + "\n"
									+ "Studentnummer: " + newDocent.getDocentNummer() + "\n"
									+ "En hij geeft lessen bij de cursus: " + newDocent.getCursus().getCursusNaam()
							);
						} catch (Exception e) {
							melding(e.getMessage());
						}
					}
				} else {
				melding("Ongeldige invoer!");
				}
			} catch (IllegalArgumentException iae) {
				melding("Voer een geldige student/docent nummer in!");
			}
		} else {
			melding("Kies account type!");
		}
	}



	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		systeembeheerder=(Systeembeheerder) gebruiker;
	}
	@FXML
	void handlecomboBoxGebruikersType(ActionEvent actionEvent) {
		String gebruikersType = accountTypeComboBox.getSelectionModel().getSelectedItem();
		if (gebruikersType != null) {
			comboBoxGroep.setDisable(false);

			if (gebruikersType.equals("Student")) {
				nummberLabel.setText("Studentnummer");
				hoortBijLabel.setText("Klas");
				if (klassen!=null) {
					for (int i = 0; i < klassen.size(); i++) {
						klassenNamen.add(klassen.get(i).getKlasNaam());
					}
					comboBoxGroep.setItems(FXCollections.observableArrayList(klassenNamen));
				}
			}
			if (gebruikersType.equals("Docent")) {
				nummberLabel.setText("Docentnummer");
				hoortBijLabel.setText("Cursus Code");
				if(cursussen!=null) {
					for (int i = 0; i < cursussen.size(); i++) {
						cursussCodes.add(cursussen.get(i).getCursusCode());
					}
					comboBoxGroep.setItems(FXCollections.observableArrayList(cursussCodes));
				}
			}
		}
	}


	public void melding(String str) {
//		Het zou misschien beter met CSS gestyled kunnen
		errorMeldingLabel.setTextFill(Color.RED);
		errorMeldingLabel.setText(str);
	}
}
