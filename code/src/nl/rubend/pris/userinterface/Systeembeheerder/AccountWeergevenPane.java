package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountWeergevenPane implements Initializable, IngelogdGebruiker {
	public ComboBox accountTypeComboBox;
	@FXML
	public TableView<OverzichtAccountDatamodel> tableView;
	@FXML
	TableColumn<OverzichtAccountDatamodel, String> typeCol;
	@FXML
	TableColumn<OverzichtAccountDatamodel, String> numCol;
	@FXML
	TableColumn<OverzichtAccountDatamodel, String> naamCol;
	@FXML
	TableColumn<OverzichtAccountDatamodel, String> emailCol;
	@FXML
	TableColumn<OverzichtAccountDatamodel, String> psswdCol;


	Systeembeheerder systeembeheerder;
	private School school = School.getSchool();
	private ObservableList<OverzichtAccountDatamodel> dataList;
	ArrayList<Gebruiker> gebruikers = school.getGebruikers();



	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		systeembeheerder=(Systeembeheerder) gebruiker;
	}



	public void initialize(URL url, ResourceBundle resourceBundle) {
		ArrayList<String> keuze = new ArrayList<>();
		keuze.add("Alle Gebruikers");
		keuze.add("Studenten");
		keuze.add("Docenten");
		accountTypeComboBox.setItems(FXCollections.observableArrayList(keuze));
		accountTypeComboBox.setVisibleRowCount(3);
		accountTypeComboBox.setValue("Alle Gebruikers");
		tableView.setPlaceholder(new Label("Er zijn geen accounts om weer te geven."));


		fillDataList();
		typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
		numCol.setCellValueFactory(new PropertyValueFactory<>("Nummer"));
		naamCol.setCellValueFactory(new PropertyValueFactory<>("Naam"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		psswdCol.setCellValueFactory(new PropertyValueFactory<>("Wachtwoord"));
		tableView.getItems().setAll(dataList);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
	}



	public void handleComboBoxViewItemsInList(ActionEvent actionEvent) {

	}


	public void handleAccountVerwijderen(ActionEvent actionEvent) throws Exception {
		TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
		int row = pos.getRow();
		OverzichtAccountDatamodel item = tableView.getItems().get(row);
		Gebruiker gebruiker = school.getGebruikerByEmail(item.getEmail());
		gebruikers.remove(gebruiker);
		tableView.getItems().remove(item);
		}




	public void fillDataList() {
		dataList = FXCollections.observableArrayList();
		for (Gebruiker gebruiker: gebruikers) {
			OverzichtAccountDatamodel datamodel;
			if (gebruiker instanceof Student) {
				Student student = (Student) gebruiker;
				String type = "Student";
				String studentNummer = String.valueOf(student.getStudentNummer());
				String naam = student.getNaam();
				String email = student.getEmail();
				String wachtwoord = student.getWachtwoord();

				datamodel = new OverzichtAccountDatamodel(type, studentNummer, naam, email, wachtwoord);
				dataList.add(datamodel);
			} else if (gebruiker instanceof Docent) {
				Docent docent = (Docent) gebruiker;
				String type = "Docent";
				String docentNummer = String.valueOf(docent.getDocentNummer());
				String naam = docent.getNaam();
				String email = docent.getEmail();
				String wachtwoord = docent.getWachtwoord();
				datamodel = new OverzichtAccountDatamodel(type, docentNummer, naam, email, wachtwoord);
				dataList.add(datamodel);
			}
		}
	}


}
