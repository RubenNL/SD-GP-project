package nl.rubend.pris.userinterface.Student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Callback;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentZiekPane implements IngelogdGebruiker,Initializable {
	public Label labelId;
	private Student student;

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
		labelId.setText(student.getNaam());
	}

	@FXML
	private DatePicker datePickerStudent;

	@FXML
	void valiDate(){
		ToggleSwitch mySwitch = new ToggleSwitch();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Callback<DatePicker, DateCell> dayCellFactory =
				(final DatePicker datePicker) -> new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if(item.isBefore(LocalDate.now())) {
							setDisable(true);
						}
					}
				};
		datePickerStudent.setDayCellFactory(dayCellFactory);
	}
}
