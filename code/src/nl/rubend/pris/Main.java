package nl.rubend.pris;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.rubend.pris.model.Cursus;
import nl.rubend.pris.model.NotFoundException;
import nl.rubend.pris.model.School;
import nl.rubend.pris.model.Systeembeheerder;

import java.io.IOException;
import java.util.Locale;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
		Locale.setDefault(new Locale("nl", "NL"));
	}

	public static void initData() throws NotFoundException {
		School school=School.getSchool();
		school.addGebruiker(new Systeembeheerder("onmogelijk@example.com","joadsijfadsofnlasdfhahsdof","Systeem"));
		school.addCursus(new Cursus("deleted","Verwijderde cursus"));
		DemoData.importData();
		School.serialize();
	}
	public static void showInloggen(Stage stage) throws IOException {
		stage.close();
		Parent root = FXMLLoader.load(Main.class.getResource("userinterface/inloggen.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("PRIS Inloggen");
		stage.setScene(scene);
		stage.getIcons().add(new Image("file:icon.png"));
		stage.show();
	}
	@Override
	public void start(Stage stage) throws IOException, ClassNotFoundException, NotFoundException {
		try {
			School.deserialize();
		} catch (Exception e) {
			System.out.println("Data is afkomstig van oude versie van model. Default data wordt geimporteert.");
			initData();
			School.deserialize();
		}
		showInloggen(stage);
		Runtime.getRuntime().addShutdownHook(new Thread(School::serialize));
	}
}