package nl.rubend.pris.userinterface.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import org.controlsfx.control.ToggleSwitch;
import java.time.LocalDate;
import java.util.ArrayList;

public class StudentLangdurigPane implements IngelogdGebruiker {
	@FXML
	public Label uitslagLabel;

	@FXML
	private Student student;

	@FXML
	public ToggleSwitch toggleLDAfwezig;

	@FXML
	private ArrayList<Aanwezigheid> aanweziheidPerLes;

	@FXML
	private CheckBox controlleCheckBox;


	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
		start();
	}

	public void start() {
		if (student.isLangdurigAfwezig()) {
			controlleCheckBox.setSelected(true);
			controlleCheckBox.setDisable(true);
			toggleLDAfwezig.selectedProperty().setValue(true);
		}
	}

	public void handleBevestigen(ActionEvent actionEvent) {
		if (controlleCheckBox.isSelected() && (toggleLDAfwezig.selectedProperty().getValue()==true)) {
			if (!student.isLangdurigAfwezig()) {
				zetLangdurigAfwezig();
				melding("Afmelding gelukt!", 2);
				controlleCheckBox.setDisable(true);
			}
		} else if (!toggleLDAfwezig.selectedProperty().getValue()) {
			if (student.isLangdurigAfwezig()) {
				uitzetLangdurigAfwezig();
				melding("Uitzetten gelukt!", 2);
				controlleCheckBox.setSelected(false);
				controlleCheckBox.setDisable(false);
			}
		} else {
			melding("Afmelding niet gelukt, geef akkoord op de gevolgen", 1);
		}
	}

	public void handleAnnuleren(ActionEvent actionEvent) {
		if (controlleCheckBox.isDisable()) {
			controlleCheckBox.setSelected(false);
		}
		controlleCheckBox.setSelected(false);
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
		}

		if (nr == 2) {
			uitslagLabel.setTextFill(Color.GREEN);
		}

		uitslagLabel.setText(str);
	}

	public void melding(String str) {
		melding(str, 0);
	}

}
