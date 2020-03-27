package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import nl.rubend.pris.userinterface.Student.StudentLangdurigPane;
import nl.rubend.pris.userinterface.Student.StudentLesPane;
import nl.rubend.pris.userinterface.Student.StudentZiekPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SysteembeheerderController implements Initializable, IngelogdGebruiker {
	@FXML private Parent accountAanmakenPane;
	@FXML private AccountAanmakenPane accountAanmakenPaneController;
	@FXML private Parent accountWeergevenPane;
	@FXML private AccountWeergevenPane accountWeergevenPaneController;
	private School school = School.getSchool();
	private Systeembeheerder systeembeheerder;
	private ArrayList<Parent> allPanes = new ArrayList<Parent>();
	private ArrayList<IngelogdGebruiker> paneControllers = new ArrayList<IngelogdGebruiker>();
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.systeembeheerder=(Systeembeheerder) gebruiker;
		for(IngelogdGebruiker controller:paneControllers) {
			controller.setGebruiker(systeembeheerder);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		allPanes.add(accountAanmakenPane);
		allPanes.add(accountWeergevenPane);
		paneControllers.add(accountAanmakenPaneController);
		paneControllers.add(accountWeergevenPaneController);
		switchToPane(accountAanmakenPane);
	}

	private void switchToPane(Parent targetPane){
		for (Parent pane : allPanes){
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
	void handleUitloggen(ActionEvent event) {
		((Stage) accountAanmakenPane.getScene().getWindow()).close();
	}
}
