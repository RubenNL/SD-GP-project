package nl.rubend.pris.model;

import java.io.Serializable;

public class Aanwezigheid implements Serializable {
	private boolean status;
	private Les les;
	private Gebruiker gebruiker;
	public Aanwezigheid(Les les) {
		this.status=true;
		this.les=les;
		try {
			gebruiker = School.getSchool().getGebruikerByEmail("onmogelijk");
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	public Aanwezigheid(Gebruiker gebruiker,boolean status,Les les) {
		this.les=les;
		setStatus(gebruiker,status);
	}
	public void setStatus(Gebruiker gebruiker,boolean status) {
		this.status=status;
		setGebruiker(gebruiker);
	}
	public void removeGebruiker(boolean removeDocent) {
		if(removeDocent && this.gebruiker!=null) if(this.gebruiker.getClass().getSimpleName().equals("Docent")) ((Docent) this.gebruiker).removeAanwezigheid(this);
		gebruiker=null;
	}
	public boolean getStatus() {
		return this.status;
	}
	public void setGebruiker(Gebruiker gebruiker) {
		if(this.gebruiker!=null) if(this.gebruiker.getClass().getSimpleName().equals("Docent")) ((Docent) this.gebruiker).removeAanwezigheid(this);
		if(gebruiker.getClass().getSimpleName().equals("Docent")) ((Docent) gebruiker).addAanwezigheid(this);
		this.gebruiker=gebruiker;
	}
	public Gebruiker getGebruiker() {
		return this.gebruiker;
	}
	public Les getLes() {return les;}
	public void removeLes() {les=null;}
}
