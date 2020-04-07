package nl.rubend.pris.model;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import org.w3c.dom.ls.LSOutput;

import java.io.Serializable;
import java.util.ArrayList;

public class Cursus implements Serializable {
	private String cursusCode;
	private String cursusNaam;
	private ArrayList<Klas> klassen=new ArrayList<Klas>();
	public Cursus(String cursusCode, String cursusNaam) {
		this.cursusCode = cursusCode;
		this.cursusNaam = cursusNaam;
	}
	protected void addKlas(Klas klas) {this.klassen.add(klas);}
	public ArrayList<Klas> getKlassen() {return this.klassen;}
	public String getCursusNaam() {
		return cursusNaam;
	}
	public String getCursusCode() {
		return cursusCode;
	}

	public ArrayList<Cursus> getCursussen() {
		ArrayList<Cursus> response=new ArrayList<Cursus>();
		for(Klas klas:klassen) {
			response.addAll(Klas.getCursussen());
		}
		return response;
	}
}
