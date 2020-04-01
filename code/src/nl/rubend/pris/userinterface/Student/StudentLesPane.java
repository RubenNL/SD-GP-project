package nl.rubend.pris.userinterface.Student;

import javafx.scene.control.Label;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

public class StudentLesPane implements IngelogdGebruiker {
	public Label labelId;
	private Student student;

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
		labelId.setText(student.getNaam());
	}
}
