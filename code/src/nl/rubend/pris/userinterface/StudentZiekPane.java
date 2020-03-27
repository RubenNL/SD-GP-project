package nl.rubend.pris.userinterface;

import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;

public class StudentZiekPane implements IngelogdGebruiker {
	private Student student;

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
	}
}
