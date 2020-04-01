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
import java.util.List;

import static java.util.Arrays.asList;

public class Inloggen {

	@FXML
	private TextField emailBox;

	@FXML
	private PasswordField wachtwoordBox;

	@FXML
	private Label errorField;



	private void logIn() throws IOException {
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

	@FXML
	public void enterInloggen(ActionEvent actionEvent) throws IOException {
		logIn();
	}

	@FXML
	void okButton(ActionEvent event) throws IOException {
		logIn();
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
	private void openGUI(Gebruiker gebruiker) throws IOException {
		String className=gebruiker.getClass().getSimpleName();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingelogd.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());

		} catch (IOException e) {
			e.printStackTrace();
		}
		Ingelogd controller = fxmlLoader.<Ingelogd>getController();
		controller.setGebruiker(gebruiker);
		if(className.equals("Student")) {
			controller.addButton("Ziek","Student/studentZiekPane.fxml");
			controller.addButton("Les","Student/studentLesPane.fxml");
			controller.addButton("Langdurig","Student/studentLangdurigPane.fxml");
		} else if(className.equals("Docent")) {
			controller.addButton("Presentie","Docent/docentPresentiePane.fxml");
		} else if(className.equals("Systeembeheerder")) {
			controller.addButton("Account maken","Systeembeheerder/accountAanmakenPane.fxml");
			controller.addButton("Account overzicht","Systeembeheerder/accountWeergevenPane.fxml");
		}
		Stage stage = new Stage();
		stage.setTitle("PRIS");
		scene.getStylesheets().add("nl/rubend/pris/stylesheet-pris.css");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		cancelButton(null);
	}


}
