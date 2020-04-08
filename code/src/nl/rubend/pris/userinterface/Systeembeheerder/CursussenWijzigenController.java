package nl.rubend.pris.userinterface.Systeembeheerder;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;


public class CursussenWijzigenController {

    public void handleSluit(ActionEvent actionEvent) {
        Button source = (Button)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void handleMainPaneClicked(MouseEvent mouseEvent) {
    }

    public void handleCursusListClicked(MouseEvent mouseEvent) {
    }

    public void handleVerwijderCursus(ActionEvent actionEvent) {
    }

    public void handleZetCursus(ActionEvent actionEvent) {
    }
}
