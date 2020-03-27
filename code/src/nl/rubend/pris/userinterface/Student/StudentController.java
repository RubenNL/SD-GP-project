package nl.rubend.pris.userinterface.Student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.rubend.pris.Main;
import nl.rubend.pris.model.Gebruiker;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import nl.rubend.pris.model.Student;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentController implements Initializable, IngelogdGebruiker {
	private Student student;
	private ArrayList<Parent> allPanes = new ArrayList<Parent>();
	private ArrayList<IngelogdGebruiker> paneControllers = new ArrayList<IngelogdGebruiker>();
	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student=(Student) gebruiker;
		for(IngelogdGebruiker controller:paneControllers) {
			controller.setGebruiker(student);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		allPanes.add(welkomPane);
		allPanes.add(ziekPane);
		allPanes.add(lesPane);
		allPanes.add(langdurigPane);
		paneControllers.add(langdurigPaneController);
		paneControllers.add(lesPaneController);
		paneControllers.add(ziekPaneController);
		switchToPane(welkomPane);
		welkomLabel.setText("Welkom ");
	}

	@FXML private Button ziekMenuButton;
	@FXML private Button lesMenuButton;
	@FXML private Button langdurigMenuButton;

	@FXML private GridPane gridContainer;

	@FXML private AnchorPane welkomPane;
	@FXML private Label welkomLabel;

	@FXML private Parent ziekPane;
	@FXML private StudentZiekPane ziekPaneController;
	@FXML private Parent lesPane;
	@FXML private StudentLesPane lesPaneController;
	@FXML private Parent langdurigPane;
	@FXML private StudentLangdurigPane langdurigPaneController;

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
	void handleUitloggen(ActionEvent event) throws IOException {
		Main.showInloggen((Stage) ziekMenuButton.getScene().getWindow());
	}
}
