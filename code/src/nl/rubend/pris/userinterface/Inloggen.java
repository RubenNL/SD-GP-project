package nl.rubend.pris.userinterface;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.School;

import java.io.IOException;

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
				openGUI(gebruiker);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(f -> errorField.setText(""));
			pause.play();
			errorField.setText("Email/wachtwoord incorrect!");
		}
	}

	@FXML
	void cancelButton(ActionEvent event) {
		((Stage) emailBox.getScene().getWindow()).close();
	}
	private void openGUI(Gebruiker gebruiker) throws IOException {
		cancelButton(null);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gebruiker.getClass().getSimpleName().toLowerCase() + ".fxml"));
		Parent root= (Parent) fxmlLoader.load();

		Scene scene = new Scene(fxmlLoader.load());
		Stage stage = new Stage();
		stage.setTitle("PRIS");
		stage.setScene(scene);
		stage.show();
	}
}
