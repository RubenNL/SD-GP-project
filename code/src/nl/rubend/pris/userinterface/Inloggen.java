package nl.rubend.pris.userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Inloggen {

	@FXML
	private TextField emailBox;

	@FXML
	private PasswordField wachtwoordBox;

	@FXML
	void cancelButton(ActionEvent event) {

	}

	@FXML
	void okButton(ActionEvent event) {
		System.out.println(emailBox.getText());
		System.out.println(wachtwoordBox.getText());
	}

}
