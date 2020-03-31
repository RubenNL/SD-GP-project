package nl.rubend.pris.userinterface.Student;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

public class StudentLesPane implements IngelogdGebruiker {
	private Student student;
	@FXML DatePicker dateBox;
	@FXML VBox lessenMenu;
	@FXML private void dateSelect() {
		lessenMenu.getChildren().removeAll(lessenMenu.getChildren());
		for(Les les:student.getLessenOpDag(dateBox.getValue())) {
			Aanwezigheid aanwezigheid=student.getAanwezigheidBijLes(les);
			boolean checked=aanwezigheid.getStatus()==Aanwezigheid.GEPLAND;
			CheckBox checkbox=new CheckBox(les.getBeginTijd()+"-"+les.getCursus().getCursusNaam());
			checkbox.setSelected(checked);
			checkbox.setOnAction(actionEvent ->  {
				try {
					if(checkbox.isSelected()) aanwezigheid.setStatus(student,Aanwezigheid.GEPLAND);
					else aanwezigheid.setStatus(student,Aanwezigheid.AANWEZIG);
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			});
			lessenMenu.getChildren().add(checkbox);
		}
	}
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
	}
}
