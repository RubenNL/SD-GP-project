package nl.rubend.pris.userinterface.Docent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.layout.GridPane;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DocentPresentiePane implements IngelogdGebruiker, Initializable {
	@FXML GridPane table;
	@FXML DatePicker dateBox;
	@FXML ComboBox lesBox;;
	private Docent docent;
	private ArrayList<Les> lessen;
	private Les les;
	private ArrayList<String> options=new ArrayList<String>();
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
		for(Student student:les.getStudenten()) {
			ComboBox<String> comboBox=new ComboBox<String>();
			comboBox.getItems().addAll(options);
			Aanwezigheid aanwezigheid=student.getAanwezigheidBijLes(les);
			comboBox.setValue(aanwezigheid.getStatus()?options.get(1):options.get(0));
			Label changedBy=new Label(aanwezigheid.getGebruiker().getNaam());
			table.addRow(table.getRowCount()+1,new Label(student.getNaam()),comboBox,changedBy);
			comboBox.setOnAction(actionEvent ->  {
				aanwezigheid.setStatus(docent,options.indexOf(comboBox.getValue())==0?false:true);
				changedBy.setText(aanwezigheid.getGebruiker().getNaam());
			});
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		options.add("Nee");
		options.add( "Ja");
	}
}

