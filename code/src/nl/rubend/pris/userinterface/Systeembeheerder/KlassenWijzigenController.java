package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.rubend.pris.model.*;
import org.controlsfx.control.SearchableComboBox;
import java.util.ArrayList;

public class KlassenWijzigenController {

    public ListView<Klas> klassenCodenListView;
    public ListView<Student> studentenNamenListView;
    public SearchableComboBox<String> studentZoekComboBox;

    private School school = School.getSchool();
    private ObservableList<Klas> klassenList = FXCollections.observableArrayList();
    private ArrayList<Klas> klassen;
    private ObservableList<Student> studentenList = FXCollections.observableArrayList();
    private ArrayList<Student> alleStudenten;


    public void initialize() throws Exception {
        klassen = school.getKlassen();
        klassenList.setAll(klassen);
        klassenCodenListView.setItems(klassenList);
        alleStudenten = new ArrayList<>();
        for (Gebruiker gebruiker: school.getGebruikers()) {
            if (gebruiker instanceof Student) {
                alleStudenten.add((Student)gebruiker);
            }
        }
        for(Student student: alleStudenten) studentZoekComboBox.getItems().add(student.getNaam());

    }


    public void handle(MouseEvent event) throws NotFoundException {
        Klas geselecteerdeKlas = school.getKlasByName(klassenCodenListView.getSelectionModel().getSelectedItem().getKlasNaam());
        ArrayList<Student> studenten = geselecteerdeKlas.getStudenten();
        if (studenten != null && !studenten.isEmpty()) {
            for (Student student : studenten) {
                if (!studentenList.contains(student)) {
                    studentenList.add(student);
                }
            }
        } else {
            studentenList.clear();
        }
        studentenNamenListView.setItems(studentenList);
    }


    public void handleVoegStudentToeAanGeselecteerdeKlas(ActionEvent actionEvent) {

    }

    public void handleVerwijderStudentVanGeselecteerdeKlas(ActionEvent actionEvent) {
        Student geselecteerdeStudent = studentenNamenListView.getSelectionModel().getSelectedItem();

    }

    public void handleSuiten(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
