package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.rubend.pris.Utils;
import nl.rubend.pris.model.*;
import org.controlsfx.control.SearchableComboBox;
import java.util.ArrayList;
import java.util.Arrays;

public class KlassenWijzigenController {

    public ListView<Klas> klassenCodenListView;
    public ListView<Student> studentenNamenListView;
    public SearchableComboBox<Student> studentZoekComboBox;

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
        for(Student student: alleStudenten) studentZoekComboBox.getItems().add(student);
        showLosseStudents();
    }


    public void handleMainPaneClicked(MouseEvent mouseEvent) {
        if (klassenCodenListView.getSelectionModel().getSelectedItem() != null) {
            klassenCodenListView.getSelectionModel().clearSelection();
            studentenNamenListView.getSelectionModel().clearSelection();
            showLosseStudents();
        }
    }


    public void handleKlasListClicked(MouseEvent event) throws NotFoundException {
        studentenList.clear();
        Klas gesKlas = klassenCodenListView.getSelectionModel().getSelectedItem();
        if (gesKlas!=null && school.getKlassen().contains(gesKlas)) {
            showStudents(gesKlas.getStudenten());
            System.out.println("yay!");
        }
    }

    public void handleVoegStudentToeAanGeselecteerdeKlas(ActionEvent actionEvent) throws IllegalArgumentException {
        Student student = studentZoekComboBox.getValue();
        Klas gesKlas = klassenCodenListView.getSelectionModel().getSelectedItem();
        if (gesKlas != null && student != null) {
            try {
                gesKlas.addStudent(student);
                showStudents(gesKlas.getStudenten());

            } catch (IllegalArgumentException iae) {
                Utils.melding("Student: " + student + " is al toegevoegd!");
            }
        }

    }

    public void handleVerwijderStudentVanGeselecteerdeKlas(ActionEvent actionEvent) throws NotFoundException {
        Klas gesKlas = klassenCodenListView.getSelectionModel().getSelectedItem();
        Student gesStud = studentenNamenListView.getSelectionModel().getSelectedItem();
        Student gekStud = studentZoekComboBox.getValue();

        if (gesKlas != null && gesStud != null) {
            if (Utils.yesNo("Wilt u zeker student: (" + gesStud + ") uit klas: (" + gesKlas + ") halen?")) {
                gesStud.removeKlas(gesKlas);
                gesKlas.removeStudent(gesStud);
                showStudents(gesKlas.getStudenten());
            }
        } if (gekStud != null && gesKlas != null && gesKlas.getStudenten().contains(gekStud)) {
            if (Utils.yesNo("Wilt u zeker student: (" + gekStud + ") uit klas: (" + gesKlas + ") halen?")) {
                gekStud.removeKlas(gesKlas);
                gesKlas.removeStudent(gekStud);
                showStudents(gesKlas.getStudenten());
            }
        }
    }

    public void showStudents(ArrayList<Student> stds) {
        studentenList.clear();
        if (!stds.isEmpty()) {
            for (Student student : stds) {
                System.out.println(student);
                studentenList.add(student);
            }
            studentenNamenListView.setItems(studentenList);
        }
    }

    public void showLosseStudents() {
        ArrayList<Student> losseStd = new ArrayList<>();
        for(Student student: alleStudenten) if (student.getKlassen().isEmpty()) losseStd.add(student);
        showStudents(losseStd);
    }

    public void handleSuiten(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

}
