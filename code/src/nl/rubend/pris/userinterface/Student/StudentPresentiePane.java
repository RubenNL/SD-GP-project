package nl.rubend.pris.userinterface.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import nl.rubend.pris.model.*;
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
    @FXML
    private PieChart pieChart;
    @FXML
    private ChoiceBox<Cursus> cursusBox;
    @FXML
    private ChoiceBox<?> periodeBox;
    @FXML
    private void cursusUpdate() {
        System.out.println("cursus "+cursusBox.getValue()+" geselecteerd!");
        getAanwezigheid(cursusBox.getValue());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Aanwezig", aanwezig),
                new PieChart.Data("Ziek", ziek),
                new PieChart.Data("Afwezig", afwezig),
                new PieChart.Data("Langdurig", langdurig),
                new PieChart.Data("Gepland", gepland)
        );
        pieChart.setData(pieChartData);
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
    }

    public void getAanwezigheid(Cursus cursus) {
        ArrayList<Aanwezigheid> items=null;
        if(cursus!=null) items=student.getAanwezigheidBijCursus(cursus);
        else items=student.getAanwezigheidList();
        aanwezig=0;
        ziek=0;
        afwezig=0;
        langdurig=0;
        gepland=0;
        for(Aanwezigheid aanwezigheid:items) {
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
