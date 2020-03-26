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
import nl.rubend.pris.model.Student;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		School.getSchool().addGebruiker(new Docent("d","","Martijn Jansen", 1234));
		School.getSchool().addGebruiker(new Student("s","","Abc Def", 4564));
		Parent root = FXMLLoader.load(getClass().getResource("userinterface/inloggen.fxml"));

		Scene scene = new Scene(root);

		stage.setTitle("PRIS Inloggen");
		stage.setScene(scene);
		stage.show();
	}
}
