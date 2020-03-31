package nl.rubend.pris.userinterface.Student;

import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

public class StudentPresentiePane implements IngelogdGebruiker {
    private Student student;
    @Override
    public void setGebruiker(Gebruiker gebruiker) {
        this.student=(Student) gebruiker;
    }
}
