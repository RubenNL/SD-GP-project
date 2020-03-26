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
import nl.rubend.pris.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		School school=School.getSchool();
		school.addGebruiker(new Docent("d","","Martijn Jansen", 1234));
		school.addGebruiker(new Student("s","","Abc Def", 4564));
		school.addKlas(new Klas("TICT-SD-V1E"));
		school.getKlasByName("TICT-SD-V1E").addStudent((Student)school.getGebruikerByEmail("s"));
		Les les=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,26),"HL15-1.203");
		les.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les.addDocent((Docent) school.getGebruikerByEmail("d"));
		((Student)school.getGebruikerByEmail("s")).addAanwezigheid(new Aanwezigheid(true,les));
		for(Les outles:((Docent)school.getSchool().getGebruikerByEmail("d")).getLessenByDag(LocalDate.of(2020,3,26))) {
			System.out.println(les.getAanwezigheid().get((Student) school.getGebruikerByEmail("s")).getStatus());
		};
		Parent root = FXMLLoader.load(getClass().getResource("userinterface/inloggen.fxml"));

		Scene scene = new Scene(root);

		stage.setTitle("PRIS Inloggen");
		stage.setScene(scene);
		stage.show();
	}
}
