package nl.rubend.pris.userinterface.Student;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class StudentLesPane implements IngelogdGebruiker, Initializable {
	private Student student;
	@FXML DatePicker dateBox;
	@FXML VBox lessenMenu;
	@FXML Button lesConfirmButton;

	ArrayList<ArrayList> checkboxData = new ArrayList();

	@FXML private void dateSelect() {
		lessenMenu.getChildren().removeAll(lessenMenu.getChildren());
		createCheckBoxes();
	}

	private void createCheckBoxes(){
		for(Les les:student.getLessenOpDag(dateBox.getValue())) {
			Aanwezigheid aanwezigheid=student.getAanwezigheidBijLes(les);
			boolean checked=aanwezigheid.getStatus()==Aanwezigheid.GEPLAND;
			CheckBox checkbox=new CheckBox(les.getBeginTijd()+" - "+les.getCursus().getCursusNaam());
			checkbox.setSelected(checked);
			checkbox.setPadding(new Insets(0,0,10,0));

			ArrayList lesData = new ArrayList();
			checkboxData.add(lesData);
			lesData.add(0, les);

			if(checked){
				lesData.add(1, "Checked");
			}
			else{
				lesData.add(1, "Unchecked");
			}

			checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (checkbox.isSelected()){
						lesData.set(1, "Checked");
						System.out.println(checkboxData);
					}
					else{
						lesData.set(1, "Unchecked");
						System.out.println(checkboxData);
					}
				}
			});
			lessenMenu.getChildren().add(checkbox);
		}
	}

	@FXML
	private void lesConfirm() throws NotFoundException {
		for (ArrayList lesData : checkboxData){
			Les les = (Les) lesData.get(0);
			try{
				if (lesData.get(1).equals("Checked")){
					student.getAanwezigheidBijLes(les).setStatus(student, Aanwezigheid.GEPLAND);
				}
			}
			catch (NotFoundException nietGevonden){
				System.out.println(nietGevonden.getMessage());
			}
		}
	}

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
	}
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if(item.isBefore(LocalDate.now().plusDays(1))) {
					setDisable(true);
				}
			}
		};
		dateBox.setDayCellFactory(dayCellFactory);
	}
}
