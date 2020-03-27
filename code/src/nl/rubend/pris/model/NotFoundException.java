package nl.rubend.pris.model;

public class NotFoundException extends Exception {
	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
}