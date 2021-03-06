package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.beans.property.SimpleStringProperty;

public class OverzichtAccountDatamodel {
	public SimpleStringProperty type;
	public SimpleStringProperty nummer;
	public SimpleStringProperty naam;
	public SimpleStringProperty email;

	public OverzichtAccountDatamodel(String type, String nummer, String naam,  String email) {
		this.type = new SimpleStringProperty(type);
		this.nummer = new SimpleStringProperty(nummer);
		this.naam = new SimpleStringProperty(naam);
		this.email = new SimpleStringProperty(email);
	}

	public String getType() {
		return type.get();
	}

	public String getNummer() {
		return nummer.get();
	}

	public String getNaam() {
		return naam.get();
	}

	public String getEmail() {
		return email.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public void setNummer(String nummer) {
		this.nummer.set(nummer);
	}

	public void setNaam(String naam) {
		this.naam.set(naam);
	}

	public void setEmail(String email) {
		this.email.set(email);
	}
	
}
