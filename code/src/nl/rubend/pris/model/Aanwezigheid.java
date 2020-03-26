package nl.rubend.pris.model;

public class Aanwezigheid {
	private boolean status;
	private Les les;
	public Aanwezigheid(Les les) {
		this.les=les;
	}
	public Aanwezigheid(boolean status,Les les) {
		this.status=status;
		this.les=les;
	}
	public void setStatus(boolean status) {
		this.status=status;
	}
	public boolean getStatus() {
		return this.status;
	}
	public Les getLes() {return les;}
}
