package nl.rubend.pris.userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;
import nl.rubend.pris.model.Systeembeheerder;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SysteembeheerderController implements Initializable,IngelogdGebruiker {
	@FXML private AnchorPane accountAanmakenPane;
	@FXML private AnchorPane accountWeergevenPane;
	private Systeembeheerder systeembeheerder;
	ArrayList<AnchorPane> allPanes = new ArrayList<AnchorPane>();
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		allPanes.add(accountAanmakenPane);
		allPanes.add(accountWeergevenPane);
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
	void maakAccountAan(ActionEvent event) {
	}
	@FXML
	void sluitScherm(ActionEvent event) {
		((Stage)accountAanmakenPane.getScene().getWindow()).close();
	}

}
