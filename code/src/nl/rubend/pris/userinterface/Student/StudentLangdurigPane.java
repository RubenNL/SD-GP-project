package nl.rubend.pris.userinterface.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentLangdurigPane implements IngelogdGebruiker {
	public CheckBox controlleCheckBox;
	public ToggleButton toggleLDAfwezig;
	public Label uitslagLabel;
	public Button buttonBevestiging;
	public Button buttonAnnuleren;
	private Student student;
	@FXML private Label labelId;


	private ArrayList<Aanwezigheid> aanweziheidPerLes;

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
		labelId.setText(student.getNaam());
		start();
	}

	public void start() {
		if (student.isLangdurigAfwezig()) {
			controlleCheckBox.setSelected(true);
			controlleCheckBox.setDisable(true);
			toggleLDAfwezig.setSelected(true);
		}
	}

	public void handleBevestigen(ActionEvent actionEvent) {
		if (controlleCheckBox.isSelected() && toggleLDAfwezig.isSelected()) {
			if (student.isLangdurigAfwezig() == false) {
				zetLangdurigAfwezig();
				melding("Afmelding gelukt!", 2);
				controlleCheckBox.setDisable(true);
			}
		} else if (toggleLDAfwezig.isSelected()==false) {
			if (student.isLangdurigAfwezig()) {
				uitzetLangdurigAfwezig();
				melding("Uitzetten gelukt!", 2);
				controlleCheckBox.setDisable(false);
				controlleCheckBox.setSelected(false);
			}
		} else {
			melding("Afmelding niet gelukt!", 1);
		}
	}

	public void handleAnnuleren(ActionEvent actionEvent) {
		if (controlleCheckBox.isDisable()==false) {
			controlleCheckBox.setSelected(false);
		}
		toggleLDAfwezig.setSelected(false);
		melding("");

	}


	public void zetLangdurigAfwezig() {
		aanweziheidPerLes = student.getAanwezigheidList();
		student.setLangdurigAfwezig(true);
		for (Aanwezigheid anw: aanweziheidPerLes) {
			// Zet "Afwezigheid" voor elk les als false vanaf de dag dat er afgemeld wordt
			if (anw.getLes().getDatum().compareTo(LocalDate.now()) >= 0 && anw.getStatus()==Aanwezigheid.AANWEZIG) {
				try {
					anw.setStatus(student,Aanwezigheid.LANGDURIG);
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public void uitzetLangdurigAfwezig() {
		aanweziheidPerLes = student.getAanwezigheidList();
		student.setLangdurigAfwezig(false);
		for (Aanwezigheid anw: aanweziheidPerLes) {
			// Zet "Afwezigheid" voor elk les als true vanaf de dag dat er afgemeld wordt
			if (anw.getLes().getDatum().compareTo(LocalDate.now()) >= 0 && anw.getStatus()==Aanwezigheid.LANGDURIG) {
				try {
					anw.setStatus(student,Aanwezigheid.AANWEZIG);
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}





	public void melding(String str, int nr) {
		if (nr == 1) {
			uitslagLabel.setTextFill(Color.RED);
		} else if (nr == 2) {
			uitslagLabel.setTextFill(Color.GREEN);
		}
		uitslagLabel.setText(str);
	}

	public void melding(String str) {
		melding(str, 0);
	}

}
