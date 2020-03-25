package nl.rubend.pris.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nl.rubend.pris.model.Gebruiker;

public class DocentController implements IngelogdGebruiker{
	Gebruiker gebruiker;
	@FXML
	private Label testLabel;
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker=gebruiker;
		testLabel.setText(gebruiker.getNaam());
	}
}
