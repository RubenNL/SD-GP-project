package nl.rubend.pris;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.rubend.pris.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
		Locale.setDefault(new Locale("nl", "NL"));
	}

	private void serializeDemoData() throws NotFoundException {
		School school=School.getSchool();
		school.addGebruiker(new Systeembeheerder("onmogelijk@example.com","joadsijfadsofnlasdfhahsdof","Systeem"));
		school.addCursus(new Cursus("deleted","Verwijderde cursus"));
		school.addGebruiker(new Docent("martijn@hu.nl","martijn","Martijn Jansen", 1234));
		school.addGebruiker(new Student("eduward@student.hu.nl","eduward","Eduward", 4564, (Docent) school.getGebruikerByEmail("martijn@hu.nl")));
		school.addGebruiker(new Systeembeheerder("jos@hu.nl","jos","Jos"));

		school.addKlas(new Klas("TICT-SD-V1E"));

		school.addCursus(new Cursus("TCIF-V1GP-19_2019","SD-GroupProject"));
		school.addCursus(new Cursus("TCIF-V1OOP-19_2019","SD-OO Programming"));
		school.addCursus(new Cursus("TCIF-V1OOAD-19_2019","SD-Analysis & design"));
		school.addCursus(new Cursus("TCIF-V1CSN-19_2019","SD-CSN"));

		school.getKlasByName("TICT-SD-V1E").addCursus(school.getCursusByCode("TCIF-V1GP-19_2019"));
		school.getKlasByName("TICT-SD-V1E").addCursus(school.getCursusByCode("TCIF-V1OOP-19_2019"));
		school.getKlasByName("TICT-SD-V1E").addCursus(school.getCursusByCode("TCIF-V1OOAD-19_2019"));
		school.getKlasByName("TICT-SD-V1E").addCursus(school.getCursusByCode("TCIF-V1CSN-19_2019"));

		school.getKlasByName("TICT-SD-V1E").addStudent((Student)school.getGebruikerByEmail("eduward@student.hu.nl"));

		Les les=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,26),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.AFWEZIG,les));

		Les les2=new Les(LocalTime.of(12,30),LocalTime.of(14,15), LocalDate.of(2020,4,10),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les2.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les2.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("eduward@student.hu.nl"),Aanwezigheid.ZIEK,les2));
		Les les3 = new Les(LocalTime.of(14,30),LocalTime.of(15,30), LocalDate.of(2020,4,10),"HL15-1.203",school.getCursusByCode("TCIF-V1OOP-19_2019"));
		les3.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les3.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));

		Les les4 = new Les(LocalTime.of(15,45),LocalTime.of(17,00), LocalDate.of(2020,4,10),"HL15-1.203",school.getCursusByCode("TCIF-V1OOAD-19_2019"));
		les4.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les4.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));

		Les les5=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,26),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les5.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les5.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.AFWEZIG,les5));

		Les les6=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,26),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les6.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les6.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.AANWEZIG,les6));

		Les les7=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,26),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les7.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les7.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.GEPLAND,les7));

		Les les8=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,26),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les8.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les8.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.ZIEK,les8));

		Les les9=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,22),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les9.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les9.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.LANGDURIG,les9));

		Les les10=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,24),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les10.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les10.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.LANGDURIG,les10));

		Les les11=new Les(LocalTime.of(10,00),LocalTime.of(13,00), LocalDate.of(2020,3,25),"HL15-1.203",school.getCursusByCode("TCIF-V1GP-19_2019"));
		les11.addKlas(school.getKlasByName("TICT-SD-V1E"));
		les11.addDocent((Docent) school.getGebruikerByEmail("martijn@hu.nl"));
		((Student)school.getGebruikerByEmail("eduward@student.hu.nl")).addAanwezigheid(new Aanwezigheid(school.getGebruikerByEmail("martijn@hu.nl"),Aanwezigheid.LANGDURIG,les11));


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