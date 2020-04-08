package nl.rubend.pris.userinterface.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import nl.rubend.pris.model.Aanwezigheid;
import nl.rubend.pris.model.Cursus;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentPresentiePane implements IngelogdGebruiker, Initializable {
	private Student student;
	private Cursus cursus;
	private int aanwezig;
	private int ziek;
	private int afwezig;
	private int gepland;
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	@FXML
	private PieChart pieChart;
	@FXML
	private ChoiceBox<Cursus> cursusBox;

	@FXML
	private void cursusUpdate() {
		getAanwezigheid(cursusBox.getValue());
		pieChartData.removeAll(pieChartData);
		addNode("Aanwezig",aanwezig);
		addNode("Afwezig",afwezig);
		addNode("Ziek",ziek);
		addNode("Gepland",gepland);
	}
	private void addNode(String naam,int value) {
		pieChartData.add(new PieChart.Data(naam,value));
	}

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student = (Student) gebruiker;
		System.out.println(student);
        System.out.println(student.getKlassen());
        ObservableList cursusBoxList = FXCollections.observableArrayList(student.getCursussen());
        cursusBox.setItems(cursusBoxList);
        pieChart.setLabelLineLength(10);
        pieChart.setData(pieChartData);
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	public void getAanwezigheid(Cursus cursus) {
		ArrayList<Aanwezigheid> items = null;
		if (cursus != null) items = student.getAanwezigheidBijCursus(cursus);
		else items = student.getAanwezigheidList();
		aanwezig = 0;
		ziek = 0;
		afwezig = 0;
		gepland = 0;
		for (Aanwezigheid aanwezigheid : items) {
			if (aanwezigheid.getStatus() == Aanwezigheid.AANWEZIG) {
				aanwezig++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.ZIEK) {
				ziek++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.AFWEZIG) {
				afwezig++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.LANGDURIG) {
				afwezig++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.GEPLAND) {
				gepland++;
			}
		}
	}
}
