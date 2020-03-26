package nl.rubend.pris.model;

public class Aanwezigheid {
	private boolean status;
	public Aanwezigheid(boolean status) {
		this.status=status;
	}
	public void setStatus(boolean status) {
		this.status=status;
	}
	public boolean getStatatus() {
		return this.status;
	}
}
