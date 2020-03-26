package nl.rubend.pris.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nl.rubend.pris.model.Docent;
import nl.rubend.pris.model.Gebruiker;

public class DocentController implements IngelogdGebruiker {
	Docent docent;
	@FXML
	private Label testLabel;
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.docent=(Docent) gebruiker;
		testLabel.setText(Integer.toString(docent.getDocentNummer()));
	}
}
