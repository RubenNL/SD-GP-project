package nl.rubend.pris.userinterface.Systeembeheerder;

import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Systeembeheerder;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

public class AccountWeergevenPane implements IngelogdGebruiker {
	Systeembeheerder systeembeheerder;
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		systeembeheerder=(Systeembeheerder) gebruiker;
	}
}
