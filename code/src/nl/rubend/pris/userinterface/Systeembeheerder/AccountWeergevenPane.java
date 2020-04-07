package nl.rubend.pris.userinterface.Systeembeheerder;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.rubend.pris.model.*;
import nl.rubend.pris.Utils;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountWeergevenPane implements Initializable, IngelogdGebruiker {
	private static final String ALL_USERS="Alle Gebruikers";
	public ComboBox accountTypeComboBox;
	public TextField filterField;
	@FXML public TableView<OverzichtAccountDatamodel> tableView;
	@FXML TableColumn<OverzichtAccountDatamodel, String> typeCol;
	@FXML TableColumn<OverzichtAccountDatamodel, String> numCol;
	@FXML TableColumn<OverzichtAccountDatamodel, String> naamCol;
	@FXML TableColumn<OverzichtAccountDatamodel, String> emailCol;

	private Systeembeheerder systeembeheerder;
	private School school = School.getSchool();
	private ObservableList<OverzichtAccountDatamodel> dataList=FXCollections.observableArrayList();;
	private ArrayList<Gebruiker> gebruikers = school.getGebruikers();
	private FilteredList<OverzichtAccountDatamodel> filteredData = new FilteredList<>(FXCollections.observableArrayList(),	p -> true);

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		systeembeheerder=(Systeembeheerder) gebruiker;
		fillDataList();
	}

	@FXML private void updateTable() {
		String newValue=filterField.getText();
		filteredData.setPredicate(t -> {
			String lowerCaseFilter = newValue.toLowerCase();
			String currentType= (String) accountTypeComboBox.getValue();
			if(!currentType.equals(t.getType()) && !currentType.equals(ALL_USERS)) return false;
			String objectvalues = t.getNaam() + t.getNummer() + t.getEmail();
			if (objectvalues.toLowerCase().indexOf(lowerCaseFilter) == -1 && newValue.length()>0) return false;
			return true;
		});
	}

	public void initialize(URL url, ResourceBundle resourceBundle) {
		tableView.setPlaceholder(new Label("Er zijn geen accounts om weer te geven."));

		ArrayList<String> keuze = new ArrayList<>();
		keuze.add(ALL_USERS);
		for(Class gebruikerClass:Gebruiker.gebruikerTypes) {
			keuze.add(gebruikerClass.getSimpleName());
		}
		accountTypeComboBox.setItems(FXCollections.observableArrayList(keuze));
		accountTypeComboBox.setVisibleRowCount(keuze.size());
		accountTypeComboBox.setValue(ALL_USERS);

		typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
		numCol.setCellValueFactory(new PropertyValueFactory<>("Nummer"));
		naamCol.setCellValueFactory(new PropertyValueFactory<>("Naam"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tableView.getItems().setAll(dataList);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		filteredData = new FilteredList(dataList);
		SortedList<OverzichtAccountDatamodel> sortedData = new SortedList<OverzichtAccountDatamodel>(filteredData);
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedData);
	}

	public void handleNieuwWachtWoordOpstellen(ActionEvent actionEvent) {

		TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
		if (pos != null) {
			int row = pos.getRow();
			OverzichtAccountDatamodel item = tableView.getItems().get(row);
			Gebruiker gebruiker = null;
			try {
				gebruiker = school.getGebruikerByEmail(item.getEmail());
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			nieuwWachtwoordMelding(gebruiker);
		}
	}


	public void handleAccountVerwijderen(ActionEvent actionEvent) {
		TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
		if (pos != null) {
			if (Utils.yesNo("Wilt u zeker dit account verwijderen?")) {
				int row = pos.getRow();
				OverzichtAccountDatamodel item = tableView.getItems().get(row);
				try {
					Gebruiker gebruiker = school.getGebruikerByEmail(item.getEmail());
					((RemovableAccount) gebruiker).removeAccount();
					gebruikers.remove(gebruiker);
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
				dataList.remove(item);
			}
		}
	}

	public OverzichtAccountDatamodel getDataModel(Gebruiker gebruiker) {
		String nummer = "";
		if (gebruiker instanceof Student) nummer = String.valueOf(((Student) gebruiker).getStudentNummer());
		else if (gebruiker instanceof Docent) nummer = String.valueOf(((Docent) gebruiker).getDocentNummer());
		else if (gebruiker instanceof Systeembeheerder && gebruiker.getNaam().equals("Systeem")) return null;
		OverzichtAccountDatamodel datamodel;
		String type = gebruiker.getClass().getSimpleName();
		String selection = (String) accountTypeComboBox.getValue();
		if (selection.equals(type) | selection.equals(ALL_USERS)) {
			String naam = gebruiker.getNaam();
			String email = gebruiker.getEmail();
			datamodel = new OverzichtAccountDatamodel(type, nummer, naam, email);
			return datamodel;
		} else return null;
	}

	public void fillDataList() {
		dataList.removeAll(dataList);
		for (Gebruiker gebruiker : gebruikers) {
			OverzichtAccountDatamodel dataModel=getDataModel(gebruiker);
			if (dataModel != null) {
				dataList.add(dataModel);
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
			Utils.melding("Het is gelukt!");
			dialog.close();
		});
	}
}
