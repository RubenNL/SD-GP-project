package nl.rubend.pris.userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.School;

public class Inloggen {

	@FXML
	private TextField emailBox;

	@FXML
	private PasswordField wachtwoordBox;

	@FXML
	private Label errorField;

	@FXML
	void okButton(ActionEvent event) {
		try {
			Gebruiker gebruiker=School.getSchool().getGebruikerByEmail(emailBox.getText());
			if(gebruiker.checkWachtwoord(wachtwoordBox.getText())) {
				String gebruikerType=gebruiker.getClass().getSimpleName();
				errorField.setText("Ingelogd als "+gebruikerType);//TODO dit laten verwijzen!
			} else {
				errorField.setText("Email/wachtwoord incorrect!");
			}
		} catch (Exception e) {
			errorField.setText("Email/wachtwoord incorrect!");
		}
	}

	@FXML
	void cancelButton(ActionEvent event) {
		((Stage) emailBox.getScene().getWindow()).close();
	}
}
