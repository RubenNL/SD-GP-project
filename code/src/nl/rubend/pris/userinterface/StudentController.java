package nl.rubend.pris.userinterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import nl.rubend.pris.model.Gebruiker;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import nl.rubend.pris.model.Student;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentController implements Initializable, IngelogdGebruiker {
	private Student student;
	ArrayList<AnchorPane> allPanes = new ArrayList<AnchorPane>();

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		allPanes.add(welkomPane);
		allPanes.add(ziekPane);
		allPanes.add(lesPane);
		allPanes.add(langdurigPane);
		switchToPane(welkomPane);
		welkomLabel.setText("Welkom ");
	}

	@FXML
	private Button ziekMenuButton;

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
	private AnchorPane ziekPane;

	@FXML
	private AnchorPane lesPane;

	@FXML
	private AnchorPane langdurigPane;

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
}
