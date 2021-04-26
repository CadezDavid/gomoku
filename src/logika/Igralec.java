package logika;

public class Igralec {

    private String ime;
    private String vrsta;

	public Igralec(String vrsta, String ime) {
        setIme(ime);
        setVrsta(vrsta);
    }

    public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getVrsta() {
		return vrsta;
	}

	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}
}
