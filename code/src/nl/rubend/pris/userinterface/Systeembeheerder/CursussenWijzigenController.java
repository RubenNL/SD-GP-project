package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.rubend.pris.Utils;
import nl.rubend.pris.model.*;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;


public class CursussenWijzigenController {

    public SearchableComboBox<Cursus> CursusZoekComboBox;
    public ListView listView;


    private School school = School.getSchool();

    private ArrayList<Docent> alleDocenten;
    private ObservableList<String> dataList = FXCollections.observableArrayList();

    public void initialize() throws Exception {
        alleDocenten = new ArrayList<>();
        for (Gebruiker gebruiker: school.getGebruikers()) {
            if (gebruiker instanceof Docent) {
                alleDocenten.add((Docent) gebruiker);
            }
        }
        fillList();

        for(Cursus cursus: school.getCursussen()) if(!cursus.getCursusCode().equals("deleted")) CursusZoekComboBox.getItems().add(cursus);

    }

    public void fillList() {
        dataList.clear();
        listView.getItems().clear();

        for (Docent docent: alleDocenten) {
            Cursus cursus = docent.getCursus();
            if (cursus != null) {
                dataList.add(docent.getNaam() + " : " + cursus.getCursusCode());
            } else {
                dataList.add(docent.getNaam());
            }
        }
        listView.setItems(dataList);
    }


    public void handleMainPaneClicked(MouseEvent mouseEvent) {
        listView.getSelectionModel().clearSelection();
    }


    public void handleVerwijderCursus(ActionEvent actionEvent) throws NotFoundException {
        Object item = listView.getSelectionModel().getSelectedItem();
        if (item != null && item.toString().contains(":")) {
            if (Utils.yesNo("Wilt u zeker de cursus verwijderen?")) {
                String[] parts = item.toString().split(" : ");
                Cursus gesCursus = school.getCursusByCode(parts[1]);
                for (Docent docent : alleDocenten) {
                    if (docent.getNaam().equals(parts[0])) {
                        docent.setCursus(null);
                        System.out.println(docent.getCursus());
                        fillList();
                    }
                }
            }
        }
    }

    public void handleZetCursus(ActionEvent actionEvent) throws NotFoundException {
        Cursus gekCursus = CursusZoekComboBox.getValue();
        Object item = listView.getSelectionModel().getSelectedItem();
        if (gekCursus != null && item != null) {
            if (!item.toString().contains(" : ")) {
                for (Docent docent : alleDocenten) {
                    if (docent.getNaam().equals(item)) {
                        docent.setCursus(gekCursus);
                        fillList();
                    }
                }
            }
        }
    }

    public void handleSluit(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }



}
