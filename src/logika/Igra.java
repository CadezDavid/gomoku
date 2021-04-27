package logika;

import java.util.EnumMap;

import splosno.Koordinati;

public class Igra {

    // Plosca je 15x15 array z elementi iz razreda Polje
    private Polje[][] plosca;
    // napotezi oznacuje kdo je na potezi, beli ali crni
    private Zetoni napotezi;
    // igralca bosta predstavljena kot slovar, kjer je
    // ključ zeton in vrednost igralec (z atributoma vrsta in ime)
    private EnumMap<Zetoni, Igralec> igralca;

    // Stanje igre; V_TEKU, NEODLOCENO, ZMAGA_BELI, ZMAGA_CRNI
    private Stanje stanje;

    public Igra(EnumMap<Zetoni, Igralec> igralca) {
        // Zacne crni
        napotezi = Zetoni.CRNI;
        // Stanje se spremeni v V_TEKU
        stanje = Stanje.V_TEKU;
        // Plosca se po angl. "initialize" in napolni
        // s praznimi polji
        plosca = new Polje[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                plosca[i][j] = Polje.PRAZNO;
            }
        }
        setIgralca(igralca);
    }

    public boolean odigraj(Koordinati koordinati) {
        // Pogleda, če je polje, ki ga želi igralec igrati prazno
        // Če je, tja postavi žeton igralca na potezi in vrne true
        if (plosca[koordinati.getX()][koordinati.getY()] == Polje.PRAZNO) {
            plosca[koordinati.getX()][koordinati.getY()] = (napotezi == Zetoni.BELI) ? Polje.BELI : Polje.CRNI;
            setNapotezi(napotezi == Zetoni.BELI ? Zetoni.CRNI : Zetoni.BELI);
            return true;
        } else
            return false;
    }

    public Zetoni getNapotezi() {
        return napotezi;
    }

    public void setNapotezi(Zetoni napotezi) {
        this.napotezi = napotezi;
    }

    public void nakljucna() {
        // Izvede pac neko potezo, to je zaenkrat namesto računalnik
        int x = 0;
        int y = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (this.getPlosca()[i][j].equals(Polje.PRAZNO)) {
                    x = i;
                    y = j;
                }
            }
        }
        Koordinati koordinati = new Koordinati(x, y);
        this.odigraj(koordinati);
    }

    public Polje[][] getPlosca() {
        return plosca;
    }

    public void setPlosca(Polje[][] plosca) {
        this.plosca = plosca;
    }

    private void setIgralca(EnumMap<Zetoni, Igralec> igralca) {
        this.igralca = igralca;
    }

    public Stanje getStanje() {
        return stanje;
    }

    public EnumMap<Zetoni, Igralec> getIgralca() {
        return igralca;
    }

    public void setStanje(Stanje stanje) {
        this.stanje = stanje;
    }
    
    /*
    public boolean preveriZmago(Polje plosca) {
    	for (int i = 0; i < 15; i++) {
    		for (int j = 0; j < 15; j++) {
    			Polje zacetno = this.getPlosca()[i][j];
    			if (zacetno.equals(Polje.PRAZNO)) {
    				break;
    			} else if (zacetno.equals(Polje.CRNI) {
    				
    			} else if (zacetno.equals(Polje.BELI)) {
    				
    			}
    		}
    	}
    }
    */
    
    /* 
   public boolean preveriZmago(Polje plosca, Koordinati koordinati) {
    	
    }
    */
    
    //funkcija preveri, ali je v vrstici pet v vrsto
    //dopolniti jo bo treba, da 6 v vrsto ne šteje
    public boolean preveriVrsto(Polje plosca, Koordinati koordinati) {
    	int x = koordinati.getX();
    	Polje[][] polje = this.getPlosca();
    	int j = 0;
    	while (j < 11) {
    		Polje zacetno = polje[x][j];
    		for (int i = j + 1; i < j + 5; i++) {
    			if (zacetno != (polje[x][i])) {
    				j++;
    				break;
    			}
    		}
    		return true;
    	}
    	return false;
    	}
    	

}
