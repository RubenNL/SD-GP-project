package nl.rubend.pris.userinterface.Docent;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class DocentPresentiePane extends Application implements IngelogdGebruiker  {
	@FXML GridPane table;
	@FXML DatePicker dateBox;
	@FXML ComboBox lesBox;;
	private Docent docent;
	private ArrayList<Les> lessen;
	private Les les;
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.docent=(Docent) gebruiker;
	}
	@FXML void selectDate() {
		ArrayList<String> items=new ArrayList<String>();
		lessen=docent.getLessenByDag(dateBox.getValue());
		for(Les les:lessen) {
			items.add(les.getBeginTijd()+"-"+les.getEindTijd());
		};
		table.getChildren().removeAll(table.getChildren());
		lesBox.setItems(FXCollections.observableArrayList(items));
	}
	@FXML void selectLes() {
		les=lessen.get(lesBox.getItems().indexOf(lesBox.getValue()));
		table.getChildren().removeAll(table.getChildren());
		table.addRow(0,new Label("Naam student"),new Label("Aanwezig"),new Label("Laatst bewerkt door"), new Label("Te veel afwezig?"));
		for(Student student:les.getStudenten()) {
			ComboBox<String> comboBox=new ComboBox<String>();
			comboBox.getItems().addAll(Aanwezigheid.getOptions());
			Aanwezigheid aanwezigheid=student.getAanwezigheidBijLes(les);
			comboBox.setValue(aanwezigheid.getStatus());
			comboBox.setOnAction(actionEvent ->  {
				try {
					aanwezigheid.setStatus(docent,comboBox.getValue());
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
//				changedBy.setText(aanwezigheid.getGebruiker().getNaam());
			});
		}
	}
	private String encodeValue(String value) {
		try {
			return new URI(null, null, value, null).getRawPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return "";
		}
	}
	private void sendMail(String adres, String subject,String body) {
		getHostServices().showDocument("mailto:"+adres+"?subject="+encodeValue(subject)+"&body="+encodeValue(body));
	}
	public void contactOpnemen(ActionEvent actionEvent) {
		sendMail("john@example.com","Hello World","This is the body");
	}

	@Override
	public void start(Stage stage) throws Exception {

	}
}


