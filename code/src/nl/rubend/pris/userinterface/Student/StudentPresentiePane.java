package nl.rubend.pris.userinterface.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
	private int langdurig;
	private int gepland;
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	@FXML
	private PieChart pieChart;
	@FXML
	private ChoiceBox<Cursus> cursusBox;

	@FXML
	private void cursusUpdate() {
		getAanwezigheid(cursusBox.getValue());
        pieChartData.get(0).setPieValue(aanwezig);
        pieChartData.get(1).setPieValue(ziek);
        pieChartData.get(2).setPieValue(afwezig);
        pieChartData.get(3).setPieValue(langdurig);
        pieChartData.get(4).setPieValue(gepland);
	}

	@Override
	public void setGebruiker(Gebruiker gebruiker) {
		this.student = (Student) gebruiker;
		System.out.println(student);
        System.out.println(student.getKlassen());
        ObservableList cursusBoxList = FXCollections.observableArrayList(student.getCursussen());
        cursusBox.setItems(cursusBoxList);
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
        pieChart.setLabelLineLength(10);
        pieChart.setData(pieChartData);
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()) + "%");
                    }
            });
        }
	    pieChartData.add(new PieChart.Data("Aanwezig",0));
        pieChartData.add(new PieChart.Data("Ziek",0));
        pieChartData.add(new PieChart.Data("Afwezig",0));
        pieChartData.add(new PieChart.Data("Langdurig",0));
        pieChartData.add(new PieChart.Data("Gepland",0));
	}

	public void getAanwezigheid(Cursus cursus) {
		ArrayList<Aanwezigheid> items = null;
		if (cursus != null) items = student.getAanwezigheidBijCursus(cursus);
		else items = student.getAanwezigheidList();
		aanwezig = 0;
		ziek = 0;
		afwezig = 0;
		langdurig = 0;
		gepland = 0;
		for (Aanwezigheid aanwezigheid : items) {
			if (aanwezigheid.getStatus() == Aanwezigheid.AANWEZIG) {
				aanwezig++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.ZIEK) {
				ziek++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.AFWEZIG) {
				afwezig++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.LANGDURIG) {
				langdurig++;
			} else if (aanwezigheid.getStatus() == Aanwezigheid.GEPLAND) {
				gepland++;
			}
		}
	}
}
