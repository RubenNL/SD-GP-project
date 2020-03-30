package nl.rubend.pris.userinterface.Systeembeheerder;

import com.sun.glass.events.GestureEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import nl.rubend.pris.Main;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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


	Systeembeheerder systeembeheerder;
	private School school = School.getSchool();
	private ObservableList<OverzichtAccountDatamodel> dataList;
	ArrayList<Gebruiker> gebruikers = school.getGebruikers();



	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		systeembeheerder=(Systeembeheerder) gebruiker;
	}



	public void initialize(URL url, ResourceBundle resourceBundle) {
		tableView.setPlaceholder(new Label("Er zijn geen accounts om weer te geven."));

		ArrayList<String> keuze = new ArrayList<>();
		keuze.add("Alle Gebruikers");
		keuze.add("Studenten");
		keuze.add("Docenten");
		accountTypeComboBox.setItems(FXCollections.observableArrayList(keuze));
		accountTypeComboBox.setVisibleRowCount(3);
		accountTypeComboBox.setValue("Alle Gebruikers");


		fillDataListAll();
		typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
		numCol.setCellValueFactory(new PropertyValueFactory<>("Nummer"));
		naamCol.setCellValueFactory(new PropertyValueFactory<>("Naam"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tableView.getItems().setAll(dataList);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
	}



	public void handleComboBoxViewItemsInList(ActionEvent actionEvent) {
		fillDataListAll();
		tableView.getItems().setAll(dataList);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
	}





	public void handleNieuwWachtWoordOpstellen(ActionEvent actionEvent) throws Exception {
		TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
		if (pos != null) {
			int row = pos.getRow();
			OverzichtAccountDatamodel item = tableView.getItems().get(row);
			Gebruiker gebruiker = school.getGebruikerByEmail(item.getEmail());
			nieuwWachtwoordMelding(gebruiker);
		}

	}




	public void handleAccountVerwijderen(ActionEvent actionEvent) throws Exception {


		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wilt u zeker deze account verwijderen?", ButtonType.YES, ButtonType.NO);
		alert.setResizable(true);
		alert.onShownProperty().addListener(e -> {
			Platform.runLater(() -> alert.setResizable(false));
		});
		alert.setTitle("Waarschuwing!");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
			if (pos != null) {
				int row = pos.getRow();
				OverzichtAccountDatamodel item = tableView.getItems().get(row);
				Gebruiker gebruiker = school.getGebruikerByEmail(item.getEmail());
				gebruikers.remove(gebruiker);
				tableView.getItems().remove(item);
				gebruiker = null;
			}
		}
	}


	public OverzichtAccountDatamodel fillDataListStudenten(Gebruiker gebruiker) {
		if (gebruiker instanceof Student) {
			OverzichtAccountDatamodel datamodel;
			Student student = (Student) gebruiker;
			String type = "Student";
			String studentNummer = String.valueOf(student.getStudentNummer());
			String naam = student.getNaam();
			String email = student.getEmail();
			datamodel = new OverzichtAccountDatamodel(type, studentNummer, naam, email);
			return datamodel;
		}
		return null;
	}


	public OverzichtAccountDatamodel fillDataListDocenten(Gebruiker gebruiker) {
		OverzichtAccountDatamodel datamodel;
		if (gebruiker instanceof Docent) {
			Docent docent = (Docent) gebruiker;
			String type = "Docent";
			String docentNummer = String.valueOf(docent.getDocentNummer());
			String naam = docent.getNaam();
			String email = docent.getEmail();
			datamodel = new OverzichtAccountDatamodel(type, docentNummer, naam, email);
			return datamodel;
		}
		return null;
	}


	public void fillDataListAll() {
		String keuze = accountTypeComboBox.getSelectionModel().getSelectedItem().toString();
		dataList = FXCollections.observableArrayList();

		if (keuze.equals("Studenten")) {
			for (Gebruiker gebruiker : gebruikers) {
				OverzichtAccountDatamodel datamodel;
				datamodel = fillDataListStudenten(gebruiker);
				if (datamodel != null) {
					dataList.add(datamodel);
				}
			}
		} else if (keuze.equals("Docenten")) {
			for (Gebruiker gebruiker : gebruikers) {
				OverzichtAccountDatamodel datamodel;
				datamodel = fillDataListDocenten(gebruiker);
				if (datamodel != null) {
					dataList.add(datamodel);
				}
			}
		} else {
			for (Gebruiker gebruiker : gebruikers) {
				OverzichtAccountDatamodel datamodelStd, datamodelDoc;
				datamodelStd = fillDataListStudenten(gebruiker);
				datamodelDoc = fillDataListDocenten(gebruiker);
				if (datamodelStd != null) {
					dataList.add(datamodelStd);
				}
				if (datamodelDoc != null) {
					dataList.add(datamodelDoc);
				}
			}
		}
	}


	public void nieuwWachtwoordMelding(Gebruiker gebruiker) {
		TextInputDialog dialog = new TextInputDialog("");


		dialog.setTitle("Waarschuwing!");
		dialog.setHeaderText("Nieuw Wachtwoord Opstellen:");
		dialog.setContentText("wachtwoord:");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(psswd -> {
			gebruiker.setWachtwoord(psswd);
			melding("Het is gelukt!");
			dialog.close();
		});


	}

	private void melding(String tekstMessage) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setResizable(true);
		alert.onShownProperty().addListener(e -> {
			Platform.runLater(() -> alert.setResizable(false));
		});
		alert.setTitle("Nieuw Account!");
		alert.setContentText(tekstMessage);
		alert.showAndWait();
	}

}
