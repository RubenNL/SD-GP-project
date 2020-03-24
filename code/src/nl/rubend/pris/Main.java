package nl.rubend.pris;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nl.rubend.pris.model.Docent;
import nl.rubend.pris.model.School;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		School.getSchool().addGebruiker(new Docent("martijn.jansen@hu.nl","TestWachtwoord","Martijn Jansen", 1234));
		Parent root = FXMLLoader.load(getClass().getResource("userinterface/inloggen.fxml"));

		Scene scene = new Scene(root);

		stage.setTitle("PRIS Inloggen");
		stage.setScene(scene);
		stage.show();
	}
}
