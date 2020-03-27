package nl.rubend.pris.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;

public class StudentLangdurigPane implements IngelogdGebruiker {
	private Student student;
	@FXML private Label labelId;
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
		labelId.setText(student.getNaam());
	}
}
