package nl.rubend.pris.userinterface.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import nl.rubend.pris.model.Gebruiker;
import nl.rubend.pris.model.Student;
import nl.rubend.pris.userinterface.IngelogdGebruiker;
import nl.rubend.pris.model.Aanwezigheid;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentPresentiePane implements IngelogdGebruiker, Initializable {
    private Student student;
    private int aanwezig;
    private int ziek;
    private int afwezig;
    private int langdurig;
    private int gepland;
    @FXML
    private PieChart pieChart;
    @FXML
    private ChoiceBox<?> cursusBox;
    @FXML
    private ChoiceBox<?> periodeBox;

    @Override
    public void setGebruiker(Gebruiker gebruiker) {
        this.student = (Student) gebruiker;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Aanwezig", aanwezig),
                new PieChart.Data("Ziek", ziek),
                new PieChart.Data("Afwezig", afwezig),
                new PieChart.Data("Langdurig", langdurig),
                new PieChart.Data("Gepland", gepland)
        );
        pieChart.setData(pieChartData);
    }

    public void getAanwezigheid() {
        for(Aanwezigheid aanwezigheid:student.getAanwezigheidList()) {
            if (aanwezigheid.getStatus()==Aanwezigheid.AANWEZIG) {
                aanwezig++;
            }
            else if (aanwezigheid.getStatus()==Aanwezigheid.ZIEK){
                ziek ++;
            }
        else if (aanwezigheid.getStatus()==Aanwezigheid.AFWEZIG){
                afwezig ++;
            }
        else if (aanwezigheid.getStatus()==Aanwezigheid.LANGDURIG){
                langdurig ++;
            }
        else if (aanwezigheid.getStatus()==Aanwezigheid.GEPLAND){
                gepland ++;
            }
        }
    }
}
