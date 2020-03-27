package nl.rubend.pris.userinterface.Docent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
	@FXML private Button presentieMenuButton;
	@FXML private GridPane gridContainer;
	@FXML private AnchorPane welkomPane;
	@FXML private Label welkomLabel;
	@FXML private Parent docentPresentiePane;
	@FXML private DocentPresentiePane docentPresentiePaneController;
	private Docent docent;
	private ArrayList<Parent> allPanes = new ArrayList<Parent>();
	private ArrayList<IngelogdGebruiker> paneControllers = new ArrayList<IngelogdGebruiker>();
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.docent=(Docent) gebruiker;
		for(IngelogdGebruiker controller:paneControllers) {
			controller.setGebruiker(docent);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		allPanes.add(welkomPane);
		allPanes.add(docentPresentiePane);
		paneControllers.add(docentPresentiePaneController);
		switchToPane(welkomPane);
		welkomLabel.setText("Welkom ");
	}

	private void switchToPane(AnchorPane targetPane){
		allPanes.add(welkomPane);
		allPanes.add(docentPresentiePane);

		for (Parent pane : allPanes){
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
