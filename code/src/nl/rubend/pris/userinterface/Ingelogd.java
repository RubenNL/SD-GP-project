package nl.rubend.pris.userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.rubend.pris.Main;
import nl.rubend.pris.model.Gebruiker;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Ingelogd implements Initializable,IngelogdGebruiker {
	@FXML private GridPane menuPane;
	@FXML private GridPane gridContainer;
	@FXML private Parent welkomPane;
	private int rowCount=0;
	private ArrayList<Parent> allPanes = new ArrayList<Parent>();
	private Gebruiker gebruiker;
	private void switchToPane(Parent targetPane) {
		for (Parent pane : allPanes) {
			pane.setVisible(false);
			pane.setDisable(true);
		}
		targetPane.setVisible(true);
		targetPane.setDisable(false);
	}
	public void addButton(String text, String location) throws IOException {
		Button button=new Button(text);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(location));
		Parent pane=fxmlLoader.load();
		IngelogdGebruiker controller = fxmlLoader.<IngelogdGebruiker>getController();
		controller.setGebruiker(gebruiker);
		button.setDepthTest(DepthTest.DISABLE);
		button.setMnemonicParsing(false);
		button.setPrefHeight(48.0);
		button.setPrefWidth(140.0);
		button.getStyleClass().add("main-menu-button");
		GridPane.setRowIndex(button,rowCount++);
		button.setFont(new Font("Roboto Light",15.0));
		GridPane.setColumnIndex(pane,1);
		GridPane.setRowIndex(pane,1);
		gridContainer.getChildren().add(pane);
		allPanes.add(pane);
		button.setOnAction(actionEvent ->  {
			switchToPane(pane);
		});
		menuPane.getChildren().add(button);
		switchToPane(welkomPane);
	}

	@FXML
	void handleUitloggen(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wilt u zeker uit gaan loggen?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Waarschuwing!");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			Main.showInloggen((Stage) menuPane.getScene().getWindow());
		}
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		allPanes.add(welkomPane);
	}

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker=gebruiker;
	}
}
