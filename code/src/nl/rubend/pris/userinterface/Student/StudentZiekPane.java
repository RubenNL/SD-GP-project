package nl.rubend.pris.userinterface.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import nl.rubend.pris.Utils;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentZiekPane implements IngelogdGebruiker,Initializable {
	private Student student;

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
	}

	@FXML
	private DatePicker datePickerStudent;

	@FXML
	private ToggleSwitch dagToggle;

	@FXML
	private Label toggleLabel;

	@FXML
	private Label datumMessage;

	@FXML
	private Button cancelZiekButton;

	@FXML
	private Button ziekAfmeldenButton;

	@FXML
	private Label ziekMessage;

	@FXML
	void resetAllFields(ActionEvent event) {
		datePickerStudent.setValue(null);
		Utils.disablePastDates(datePickerStudent);
		dagToggle.selectedProperty().setValue(false);
		toggleLabel.setText("Afwezig");
		datumMessage.setText(null);
		ziekMessage.setText(null);
	}

	@FXML
	void meldAf() throws NotFoundException {
		ArrayList<Les> targetLessen = student.getLessenOpDag(datePickerStudent.getValue());
		boolean toggle = dagToggle.selectedProperty().getValue();
		String status = null;

		if (toggle){
			status = Aanwezigheid.ZIEK;
		}
		else {
			status = Aanwezigheid.GEPLAND;
		}

		if(targetLessen.size() > 0){
			for (Les les : targetLessen){
				student.getAanwezigheidBijLes(les).setStatus(student, status);
			}

			ArrayList<Boolean> lesCheckList = new ArrayList();
			for (Les les : targetLessen){
				if(student.getAanwezigheidBijLes(les).getStatus().equals(status)){
					lesCheckList.add(true);
				}
				else{
					lesCheckList.add(false);
				}
			}
			if(lesCheckList.contains(false)){
				ziekMessage.setText("Afmelding niet gelukt");
				ziekMessage.getStyleClass().clear();
				ziekMessage.getStyleClass().add("red-text");
			}
			else{
				ziekMessage.setText("Afmelding is gelukt");
				ziekMessage.getStyleClass().clear();
				ziekMessage.getStyleClass().add("green-text");
			}
		}
		else if(datePickerStudent.getValue() == null){
			ziekMessage.setText("Geen dag geselecteerd");
		}
		else{
			ziekMessage.setText("Geen lessen gevonden op die dag");
			ziekMessage.getStyleClass().clear();
			ziekMessage.getStyleClass().add("black-text");
		}
	}

	@FXML
	private void toggleAfwezig(){
		boolean toggle = dagToggle.selectedProperty().getValue();
		if(toggle){
			toggleLabel.setText("Ziek");
			datePickerStudent.setValue(LocalDate.now());
			Utils.enableTodayOnly(datePickerStudent);
			datumMessage.setText("Ziekmelden kan alleen op huidige dag");
		}
		else {
			toggleLabel.setText("Afwezig");
			Utils.disablePastDates(datePickerStudent);
			datumMessage.setText(null);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Utils.disablePastDates(datePickerStudent);
	}
}
