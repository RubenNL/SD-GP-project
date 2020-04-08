package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.rubend.pris.Utils;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KlassenEnCursussenBeherenPane implements Initializable, IngelogdGebruiker {
	@FXML private ListView alleKlassenList;
	@FXML private ListView alleCursussenList;
	@FXML private TextField klasNaamTextField;
	@FXML private TextField cursusNaamTextField;
	@FXML private TextField cursusCodeTextField;
	@FXML private Label klasLabel;
	@FXML private Label cursusLabel;

	private Systeembeheerder systeembeheerder;
	private School school = School.getSchool();
	private ObservableList<Klas> klassenList = FXCollections.observableArrayList();
	private ObservableList<Cursus> cursusenList = FXCollections.observableArrayList();
	private ArrayList<Klas> klassen;
	private ArrayList<Cursus> cursusen;

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		systeembeheerder = (Systeembeheerder) gebruiker;
		ArrayList<Klas> klassen = school.getKlassen();
		ArrayList<Cursus> cursusen = new ArrayList<>();
		for(Cursus cursus:school.getCursussen()) {
			if(!cursus.getCursusCode().equals("deleted")) cursusen.add(cursus);
		}
		klassenList.setAll(klassen);
		cursusenList.setAll(cursusen);
		alleKlassenList.setItems(klassenList);
		alleCursussenList.setItems(cursusenList);
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	@FXML
	public void handleAddKlas(ActionEvent actionEvent) {
		String klasNaam = klasNaamTextField.getText();
		if (klasNaam != null && (!klasNaam.equals(""))) {
			Klas newKlas = new Klas(klasNaam);
			school.addKlas(newKlas);
			klassenList.add(newKlas);
			alleKlassenList.setItems(klassenList);
			melding(klasLabel, "Klas maken gelukt!", true);
			klasNaamTextField.setText("");
		} else melding(klasLabel, "Ongeldige invoer!", false);
	}

	public void handleAddCursus(ActionEvent actionEvent) {
		String cursusNaam = cursusNaamTextField.getText();
		String cursusCode = cursusCodeTextField.getText();
		if (cursusNaam != null && !(cursusNaam.equals(""))
				|| (cursusCode != null && !(cursusCode.equals("")))) {
			Cursus newCursus = new Cursus(cursusCode, cursusNaam);
			school.addCursus(newCursus);
			cursusenList.add(newCursus);
			alleCursussenList.setItems(cursusenList);
			melding(cursusLabel, "Cursus maken gelukt!", true);
			cursusNaamTextField.setText("");
			cursusCodeTextField.setText("");
		} else melding(cursusLabel, "Ongeldige invoer!", false);
	}

	public void melding(Label label, String str, boolean isTrue) {
		if (isTrue) label.setTextFill(Color.GREEN);
		else label.setTextFill(Color.RED);
		label.setText(str);
		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(f -> label.setText(""));
		pause.play();
	}


	public void handleRemoveKlas(ActionEvent actionEvent) throws NotFoundException {
		final int selectedIdx = alleKlassenList.getSelectionModel().getSelectedIndex();
		if (selectedIdx != -1) {
			if (Utils.yesNo("Weet u zeker dat u deze klas wilt verwijderen?")) {
				Klas klas=klassenList.get(selectedIdx);
				klas.removeKlas();
				alleKlassenList.getItems().remove(selectedIdx);
			}
		} else melding(klasLabel, "Selecteer eerst een klas!", false);
	}

	public void handleRemoveCursus(ActionEvent actionEvent) throws NotFoundException {
		final int selectedIdx = alleCursussenList.getSelectionModel().getSelectedIndex();
		if (selectedIdx != -1) {
			if (Utils.yesNo("Wil je zeker deze cursus verwijderen?")) {
				Cursus cursus=cursusenList.get(selectedIdx);
				cursus.removeCursus();
				alleCursussenList.getItems().remove(selectedIdx);
			}
		} else melding(cursusLabel, "Selecteer eerst een cursus!", false);
	}

	public void handleWijzigKlas(ActionEvent actionEvent) throws IOException {
		FXMLLoader loader =
				new FXMLLoader(getClass().getResource("klassenWijzigen.fxml"));
		Parent root = loader.load();
		Stage newStage = new Stage();
		newStage.getIcons().add(new Image("file:icon.png"));
		newStage.setTitle("PRIS");
		newStage.setScene(new Scene(root));
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.showAndWait();
	}

	public void handleWijzigCursus(ActionEvent actionEvent) throws IOException {
		FXMLLoader loader =
				new FXMLLoader(getClass().getResource("cursussenWijzigen.fxml"));
		Parent root = loader.load();
		Stage newStage = new Stage();
		newStage.getIcons().add(new Image("file:icon.png"));
		newStage.setTitle("PRIS");
		newStage.setScene(new Scene(root));
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.showAndWait();
	}
}
