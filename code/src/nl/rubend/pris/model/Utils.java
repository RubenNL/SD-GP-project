package nl.rubend.pris.model;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

public final class Utils {

	public static void disablePastDates(DatePicker dp){
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

	public static void enableTodayOnly(DatePicker dp){
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

}
