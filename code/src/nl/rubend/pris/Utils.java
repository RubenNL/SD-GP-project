package nl.rubend.pris;

import javafx.application.Platform;
import javafx.scene.control.*;
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
		ButtonType ja = new ButtonType("Ja", ButtonBar.ButtonData.YES);
		ButtonType nee = new ButtonType("Nee", ButtonBar.ButtonData.NO);

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bericht, ja, nee);
		alert.setHeaderText("Bevestiging");
		alert.setResizable(true);
		alert.onShownProperty().addListener(e -> {//overgenomen van stackoverflow, popups werken niet goed in Linux zonder dit.
			Platform.runLater(() -> alert.setResizable(false));
			System.out.println();
		});
		alert.setTitle("Waarschuwing!");
		alert.showAndWait();
		return alert.getResult() == ja;
	}

	static boolean isAlpha(String name) {
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}


}
