package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import nl.rubend.pris.model.*;
import nl.rubend.pris.userinterface.IngelogdGebruiker;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KlassenBeherenPane implements Initializable, IngelogdGebruiker {
    @FXML
    private ListView alleKlassenList;
    @FXML
    private ListView alleCursussenList;
    @FXML
    private TextField klasNaamTextField;
    @FXML
    private TextField cursusNaamTextField;
    @FXML
    private TextField cursusCodeTextField;
    @FXML
    private Label klasLabel;
    @FXML
    private Label cursusLabel;

    private Systeembeheerder systeembeheerder;
    private School school = School.getSchool();
    private ArrayList<Klas> klassen = school.getKlassen();
    private ArrayList<Cursus> cursusen = school.getCursussen();
    private ObservableList<Klas> klassenList = FXCollections.observableArrayList();
    private ObservableList<Cursus> cursusenList = FXCollections.observableArrayList();


    @Override
    public void setGebruiker(Gebruiker gebruiker) {
        systeembeheerder = (Systeembeheerder) gebruiker;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alleKlassenList.setItems(klassenList);
        alleCursussenList.setItems(cursusenList);
    }

    @FXML
    public void handleAddKlas(ActionEvent actionEvent) {
        String klasNaam = klasNaamTextField.getText();
        if (klasNaam != null && (!klasNaam.equals(""))) {
            Klas newKlas = new Klas(klasNaam);
            school.addKlas(newKlas);
            klassenList.add(newKlas);
            alleKlassenList.setItems(klassenList);
        } else {
            melding(cursusLabel, "Ongeldige invoer!", false);        }

    }

    public void handleAddCursus(ActionEvent actionEvent) {
        String cursusNaam = cursusNaamTextField.getText();
        String cursusCode = cursusCodeTextField.getText();
        if (cursusNaam != null && !(cursusNaam.equals(""))
            || (cursusCode != null && !(cursusCode.equals(""))))  {
            Cursus newCursus = new Cursus(cursusNaam, cursusCode);
            school.addCursus(newCursus);
            cursusenList.add(newCursus);
            alleCursussenList.setItems(cursusenList);
            melding(cursusLabel, "Ongeldige invoer!", false);
        }
    }

    public void melding(Label label, String str, boolean isTrue) {
        if (isTrue) {
            label.setTextFill(Color.GREEN);
        } else {
            label.setTextFill(Color.RED);
        }
        label.setText(str);
    }


}
