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
		Gebruiker gebruiker;
		try {
			gebruiker = School.getSchool().getGebruikerByEmail(emailBox.getText());
		} catch (Exception e) {
			incorrect();
			return;
		}
		if(gebruiker.checkWachtwoord(wachtwoordBox.getText())) {
			openGUI(gebruiker);
		} else {
			incorrect();
		}
	}
	private void incorrect() {
		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(f -> errorField.setText(""));
		pause.play();
		errorField.setText("Email/wachtwoord incorrect!");
	}
	@FXML
	void cancelButton(ActionEvent event) {
		((Stage) emailBox.getScene().getWindow()).close();
	}
	private void openGUI(Gebruiker gebruiker) {
		String className=gebruiker.getClass().getSimpleName();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(className+"/"+className.toLowerCase() + ".fxml"));
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());

		} catch (IOException e) {
			e.printStackTrace();
		}
		IngelogdGebruiker controller = fxmlLoader.<IngelogdGebruiker>getController();
		controller.setGebruiker(gebruiker);
		Stage stage = new Stage();
		stage.setTitle("PRIS");
		scene.getStylesheets().add("nl/rubend/pris/stylesheet-pris.css");
		stage.setScene(scene);
		stage.show();
		cancelButton(null);
	}
}
