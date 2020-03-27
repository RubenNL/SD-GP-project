package nl.rubend.pris.userinterface.Docent;

import nl.rubend.pris.model.Docent;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

public class DocentPresentiePane implements IngelogdGebruiker {
	private Docent docent;
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.docent=(Docent) gebruiker;
	}
}

