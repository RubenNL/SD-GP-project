package nl.rubend.pris;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

public interface Utils {

	static void disablePastDates(DatePicker dp){
		Callback<DatePicker, DateCell> dayCellFactory =
				(final DatePicker datePicker) -> new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if(item.isBefore(LocalDate.now())) {
							setDisable(true);
						}
					}
				};
		dp.setDayCellFactory(dayCellFactory);
	}

	static void enableTodayOnly(DatePicker dp){
		Callback<DatePicker, DateCell> dayCellFactory =
				(final DatePicker datePicker) -> new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if(item.isBefore(LocalDate.now())) {
							setDisable(true);
						}

						if(item.isAfter(LocalDate.now())){
							setDisable(true);
						}
					}
				};
		dp.setDayCellFactory(dayCellFactory);
	}
	static void melding(String tekstMessage) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setResizable(true);
		alert.onShownProperty().addListener(e -> {
			Platform.runLater(() -> alert.setResizable(false));
		});
		alert.setTitle("Nieuw Account!");
		alert.setContentText(tekstMessage);
		alert.showAndWait();
	}
	static boolean yesNo(String bericht) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bericht, ButtonType.YES, ButtonType.NO);
		alert.setResizable(true);
		alert.onShownProperty().addListener(e -> {//overgenomen van stackoverflow, popups werken niet goed in Linux zonder dit.
			Platform.runLater(() -> alert.setResizable(false));
		});
		alert.setTitle("Waarschuwing!");
		alert.showAndWait();
		return alert.getResult() == ButtonType.YES;
	}
}
