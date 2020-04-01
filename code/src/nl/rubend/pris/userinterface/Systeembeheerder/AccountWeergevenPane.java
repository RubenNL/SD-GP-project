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

	private void updateTable() {
		String newValue=filterField.getText();
		filteredData.setPredicate(t -> {
			String lowerCaseFilter = newValue.toLowerCase();
			String currentType= (String) accountTypeComboBox.getValue();
			if(!currentType.equals(t.getType()) && !currentType.equals(ALL_USERS)) return false;
			String objectvalues = t.getNaam() + t.getNummer();
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

		fillDataList();
		typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
		numCol.setCellValueFactory(new PropertyValueFactory<>("Nummer"));
		naamCol.setCellValueFactory(new PropertyValueFactory<>("Naam"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tableView.getItems().setAll(dataList);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			updateTable();
		});
		accountTypeComboBox.setOnAction((update) -> {
			updateTable();
		});
		Refresh();
	}

	public void Refresh() {
		try {
			filteredData = new FilteredList<>(FXCollections.observableArrayList(dataList),	p -> true);
			SortedList<OverzichtAccountDatamodel> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(tableView.comparatorProperty());
			tableView.setItems(sortedData);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void handleNieuwWachtWoordOpstellen(ActionEvent actionEvent) throws Exception {
		try {
			TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
			if (pos != null) {
				int row = pos.getRow();
				OverzichtAccountDatamodel item = tableView.getItems().get(row);
				Gebruiker gebruiker = school.getGebruikerByEmail(item.getEmail());
				nieuwWachtwoordMelding(gebruiker);
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}


	public void handleAccountVerwijderen(ActionEvent actionEvent) throws Exception {
		TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
		if (pos != null) {
			if (Utils.yesNo("Wilt u zeker dit account verwijderen?")) {
				int row = pos.getRow();
				OverzichtAccountDatamodel item = tableView.getItems().get(row);
				Gebruiker gebruiker = school.getGebruikerByEmail(item.getEmail());
				((RemovableAccount) gebruiker).removeAccount();
				gebruikers.remove(gebruiker);
				tableView.getItems().remove(row);
				fillDataList();
			}
		}
	}

	public OverzichtAccountDatamodel getDataModel(Gebruiker gebruiker) {
		int nummer = 0;
		if (gebruiker instanceof Student) nummer = ((Student) gebruiker).getStudentNummer();
		else if (gebruiker instanceof Docent) nummer = ((Docent) gebruiker).getDocentNummer();
		OverzichtAccountDatamodel datamodel;
		String type = gebruiker.getClass().getSimpleName();
		String selection = (String) accountTypeComboBox.getValue();
		if (selection.equals(type) | selection.equals(ALL_USERS)) {
			String naam = gebruiker.getNaam();
			String email = gebruiker.getEmail();
			datamodel = new OverzichtAccountDatamodel(type, String.valueOf(nummer), naam, email);
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
