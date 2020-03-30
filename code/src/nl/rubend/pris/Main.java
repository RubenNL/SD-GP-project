package nl.rubend.pris;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.rubend.pris.model.*;

import java.io.IOException;
import java.io.InvalidClassException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private void serializeDemoData() throws Exception {
		School school=School.getSchool();
		school.addGebruiker(new Docent("d","","Martijn Jansen", 1234));
		school.addGebruiker(new Student("s","","Abc Def", 4564));
		school.addGebruiker(new Systeembeheerder("b","","sysBeheerNaam"));
		school.addGebruiker(new Systeembeheerder("onmogelijk","joadsijfadsofnlasdfhahsdof","Systeem"));
		school.addKlas(new Klas("TICT-SD-V1E"));
		school.addCursus(new Cursus("TCIF-V1GP-19_2019","SD-GroupProject"));
		school.getKlasByName("TICT-SD-V1E").addCursus(school.getCursusByCode("TCIF-V1GP-19_2019"));
		school.getKlasByName("TICT-SD-V1E").addStudent((Student)school.getGebruikerByEmail("s"));
		Les les=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,26),"HL15-1.203");
		les.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les.addDocent((Docent) school.getGebruikerByEmail("d"));
		Les les2=new Les(LocalTime.of(12,30),LocalTime.of(15,30), LocalDate.of(2020,4,3),"HL15-1.203");
		les2.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les2.addDocent((Docent) school.getGebruikerByEmail("d"));
		School.serialize();
	}
	public static void showInloggen(Stage stage) throws IOException {
		stage.close();
		Parent root = FXMLLoader.load(Main.class.getResource("userinterface/inloggen.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("PRIS Inloggen");
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void start(Stage stage) throws Exception {
		try {
			School.deserialize();
		} catch (Exception e) {
			System.out.println("Data is afkomstig van oude versie van model. Default data wordt geimporteert.");
			serializeDemoData();
			School.deserialize();
		}
		showInloggen(stage);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				School.serialize();
			}
		}));
	}
}
