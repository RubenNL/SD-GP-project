package nl.rubend.pris.userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Inloggen {

	@FXML
	private TextField emailBox;

	@FXML
	private PasswordField wachtwoordBox;

	@FXML
	void cancelButton(ActionEvent event) {
		((Stage) emailBox.getScene().getWindow()).close();
	}

	@FXML
	void okButton(ActionEvent event) {
		System.out.println(emailBox.getText());
		System.out.println(wachtwoordBox.getText());
	}
}
