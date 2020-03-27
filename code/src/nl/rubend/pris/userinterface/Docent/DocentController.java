package nl.rubend.pris.userinterface.Docent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.rubend.pris.model.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import javax.swing.*;


public class DocentController implements Initializable, IngelogdGebruiker {
	@FXML
	private Button presentieMenuButton;

	@FXML
	private Button lesMenuButton;

	@FXML
	private Button langdurigMenuButton;

	@FXML
	private GridPane gridContainer;

	@FXML
	private AnchorPane welkomPane;

	@FXML
	private Label welkomLabel;

	@FXML
	private AnchorPane presentiePane;

	@FXML
	private AnchorPane lesPane;

	@FXML
	private AnchorPane langdurigPane;

	@FXML
	private ListView studentAanwezigheid;


	private Docent docent;

	ArrayList<AnchorPane> allPanes = new ArrayList();

	private static Klas klas;

	public static void setKlas(Klas kls) { klas = kls; }

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.docent=(Docent) gebruiker;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		switchToPane(welkomPane);
		welkomLabel.setText("Welkom ");

		ObservableList<String> opties =
				FXCollections.observableArrayList(
						"Aanwezig",
							 "Afwezig",
							 "Ziek"
				);
		ComboBox aanwezigheidsBox = new ComboBox(opties);
		ArrayList<Label> namen = new ArrayList<>();
		for (Student student : klas.getStudenten()) {
			Label label = new Label(student.getNaam());
			namen.add(label);
			label.setFont(new Font("Arial", 12));
			label.setPadding(new Insets(10,10,5,5));
		}

		studentAanwezigheid.setItems(FXCollections.observableList(namen));

		for (int i = 1; i < namen.size(); i++) {

		}

	}

	private void switchToPane(AnchorPane targetPane){
		allPanes.add(welkomPane);
		allPanes.add(presentiePane);
		allPanes.add(lesPane);
		allPanes.add(langdurigPane);

		for (AnchorPane pane : allPanes){
			if (pane.equals(targetPane)) {
				pane.setVisible(true);
				pane.setDisable(false);
			}
			else {
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
}
