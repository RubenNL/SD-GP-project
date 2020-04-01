package nl.rubend.pris.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Aanwezigheid implements Serializable {
	private int status;
	public static final String AANWEZIG="Aanwezig";
	public static final String AFWEZIG="Afwezig";
	public static final String ZIEK="Ziek";
	public static final String GEPLAND="Gepland afwezig";
	public static final String LANGDURIG="Langdurig afwezig";
	private static ArrayList<String> opties=new ArrayList<String>(){{
		add(AANWEZIG);
		add(AFWEZIG);
		add(ZIEK);
		add(GEPLAND);
		add(LANGDURIG);
	}};
	public static ArrayList<String> getOptions() {
		return opties;
	}
	private Les les;
	private Gebruiker gebruiker;
	public Aanwezigheid(Les les) {
		this.les=les;
		try {
			setStatus(School.getSchool().getGebruikerByEmail("onmogelijk"),AANWEZIG);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	public Aanwezigheid(Gebruiker gebruiker,String status,Les les) throws NotFoundException {
		this.les=les;
		setStatus(gebruiker,status);
	}
	public void setStatus(Gebruiker gebruiker,String status) throws NotFoundException {
		int intStatus=opties.indexOf(status);
		if(intStatus==-1) throw new NotFoundException("Type niet gevonden");
		this.status=intStatus;
		setGebruiker(gebruiker);
	}
	public void removeGebruiker(boolean removeDocent) {
		if(removeDocent && this.gebruiker!=null) if(this.gebruiker.getClass().getSimpleName().equals("Docent")) ((Docent) this.gebruiker).removeAanwezigheid(this);
		gebruiker=null;
	}
	public String getStatus() {
		return opties.get(this.status);
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
