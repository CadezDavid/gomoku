package logika;

public class Igralec {

    private String ime;
    private Vrsta vrsta;

	public Igralec(Vrsta vrsta, String ime) {
        setIme(ime);
        setVrsta(vrsta);
    }

    public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public Vrsta getVrsta() {
		return vrsta;
	}

	public void setVrsta(Vrsta vrsta) {
		this.vrsta = vrsta;
	}
}
