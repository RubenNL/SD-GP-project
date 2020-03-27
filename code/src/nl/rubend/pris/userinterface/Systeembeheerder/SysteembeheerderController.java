package nl.rubend.pris.userinterface;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.rubend.pris.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SysteembeheerderController implements Initializable,IngelogdGebruiker {
	public TextField nieuwAccountName;
	public TextField nieuwAccountEmail;
	public TextField nieuwAccountWachtwoord;
	public TextField nieuwAccountNummer;
	public Label nummberLabel;
	public Label hoortBijLabel;

	@FXML private AnchorPane accountAanmakenPane;
	@FXML private AnchorPane accountWeergevenPane;


	School school = School.getSchool();


	public ComboBox<String> accountTypeComboBox;
	public ComboBox<String> comboBoxGroep;


	private Systeembeheerder systeembeheerder;


	ArrayList<AnchorPane> allPanes = new ArrayList<AnchorPane>();



	ArrayList<Klas> klassen = school.getKlassen();
	ArrayList<Cursus> cursussen = school.getCursussen();

	ArrayList<String> klassenNamen = new ArrayList<>();
	ArrayList<String> cursussCodes = new ArrayList<>();






	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		allPanes.add(accountAanmakenPane);
		allPanes.add(accountWeergevenPane);
		ArrayList<String>accountTypen = new ArrayList<>();
		accountTypen.add("Student");
		accountTypen.add("Docent");
		accountTypeComboBox.setItems(FXCollections.observableArrayList(accountTypen));
	}

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.systeembeheerder=(Systeembeheerder) gebruiker;
	}
	private void switchToPane(AnchorPane targetPane){
		for (AnchorPane pane : allPanes){
			if(pane.equals(targetPane)){
				pane.setVisible(true);
				pane.setDisable(false);
			}
			else{
				pane.setVisible(false);
				pane.setDisable(true);
			}
		}
	}
	@FXML
	void toonScherm(ActionEvent event) {
		Control control=(Control) event.getSource();
		String paneId=control.getId().split("MenuButton")[0]+"Pane";
		switchToPane((AnchorPane) control.getScene().lookup("#"+paneId));
	}
	@FXML
	void maakAccountAan(ActionEvent event) throws Exception {
		String gebruikersType = accountTypeComboBox.getSelectionModel().getSelectedItem();
		String groep = comboBoxGroep.getSelectionModel().getSelectedItem();

		String naam = nieuwAccountName.getText();
		String email = nieuwAccountEmail.getText();
		String wachtwoord = nieuwAccountWachtwoord.getText();
		int nummer = 0;
		try {
			nummer = Integer.valueOf(nieuwAccountNummer.getText());
		} catch (IllegalArgumentException iae) {
//			incorrect();
			iae.getMessage();
		}

		if (gebruikersType != null && groep != null) {
			if (gebruikersType.equals("Student")) {
				Klas klas = school.getKlasByName(groep);
				Student newStudent = new Student(email, wachtwoord, naam, nummer);
				school.addGebruiker(newStudent);
				newStudent.addKlas(klas);

			}
			if (gebruikersType.equals("Docent")) {
				Cursus cursus = school.getCursusByCode(groep);
				Docent newDocent = new Docent(email, wachtwoord, naam, nummer);
				school.addGebruiker(newDocent);
				newDocent.setCursus(cursus);
			}
		}
	}

	@FXML
	void handlecomboBoxGebruikersType(ActionEvent actionEvent) {
		String gebruikersType = accountTypeComboBox.getSelectionModel().getSelectedItem();
		if (gebruikersType != null) {
			comboBoxGroep.setDisable(false);

			if (gebruikersType.equals("Student")) {
				nummberLabel.setText("Studentnummer");
				hoortBijLabel.setText("Klas");
				if (klassen!=null) {
					for (int i = 0; i < klassen.size(); i++) {
						klassenNamen.add(klassen.get(i).getKlasNaam());
					}
					comboBoxGroep.setItems(FXCollections.observableArrayList(klassenNamen));
				}
			}
			if (gebruikersType.equals("Docent")) {
				nummberLabel.setText("Docentnummer");
				hoortBijLabel.setText("Cursus Code");
				if(cursussen!=null) {
					for (int i = 0; i < cursussen.size(); i++) {
						cursussCodes.add(cursussen.get(i).getCursusCode());
					}
					comboBoxGroep.setItems(FXCollections.observableArrayList(cursussCodes));
				}
			}
		}
	}



	@FXML
	void handleUitloggen(ActionEvent event) {
		((Stage)accountAanmakenPane.getScene().getWindow()).close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inloggen.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		stage.setTitle("Inloggen");
		scene.getStylesheets().add("nl/rubend/pris/stylesheet-pris.css");
		stage.setScene(scene);
		stage.show();

	}


	public void handleComboBoxGroep(ActionEvent actionEvent) {
	}
}
