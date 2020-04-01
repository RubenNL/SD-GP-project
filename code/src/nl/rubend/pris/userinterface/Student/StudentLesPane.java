//package nl.rubend.pris.userinterface.Student;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.DateCell;
//import javafx.scene.control.DatePicker;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.util.Callback;
//import nl.rubend.pris.model.*;
//import nl.rubend.pris.userinterface.IngelogdGebruiker;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.ResourceBundle;
//
//public class StudentLesPane implements IngelogdGebruiker, Initializable {
//	private Student student;
//	@FXML DatePicker dateBox;
//	@FXML VBox lessenMenu;
//	@FXML private void dateSelect() {
//		lessenMenu.getChildren().removeAll(lessenMenu.getChildren());
//		for(Les les:student.getLessenOpDag(dateBox.getValue())) {
//			Aanwezigheid aanwezigheid=student.getAanwezigheidBijLes(les);
//			boolean checked=aanwezigheid.getStatus()==Aanwezigheid.GEPLAND;
//			CheckBox checkbox=new CheckBox(les.getBeginTijd()+"-"+les.getCursus().getCursusNaam());
//			checkbox.setSelected(checked);
//			checkbox.setOnAction(actionEvent ->  {
//				try {
//					if(checkbox.isSelected()) aanwezigheid.setStatus(student,Aanwezigheid.GEPLAND);
//					else aanwezigheid.setStatus(student,Aanwezigheid.AANWEZIG);
//				} catch (NotFoundException e) {
//					e.printStackTrace();
//				}
//			});
//			lessenMenu.getChildren().add(checkbox);
//		}
//	}
//	@Override
//	public void setGebruiker(Gebruiker gebruiker) {
//		this.student=(Student) gebruiker;
//	}
//	@Override
//	public void initialize(URL url, ResourceBundle resourceBundle) {
//		Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
//			@Override
//			public void updateItem(LocalDate item, boolean empty) {
//				super.updateItem(item, empty);
//				if(item.isBefore(LocalDate.now().plusDays(1))) {
//					setDisable(true);
//				}
//			}
//		};
//		dateBox.setDayCellFactory(dayCellFactory);
//	}
//}
