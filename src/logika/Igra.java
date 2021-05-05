package logika;

import java.util.Arrays;
import java.util.EnumMap;

import splosno.Koordinati;

public class Igra {

    private Polje[][] plosca;
    private Zetoni naPotezi;
    private EnumMap<Zetoni, Igralec> igralca;

    // Stanje igre; V_TEKU, NEODLOCENO, ZMAGA_BELI, ZMAGA_CRNI
    private Stanje stanje;

    public Igra(EnumMap<Zetoni, Igralec> igralca) {
        // Zacne crni
        naPotezi = Zetoni.CRNI;
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
            plosca[koordinati.getX()][koordinati.getY()] = (naPotezi == Zetoni.BELI) ? Polje.BELI : Polje.CRNI;
            setNapotezi(naPotezi == Zetoni.BELI ? Zetoni.CRNI : Zetoni.BELI);
            return true;
        } else
            return false;
    }

    public Zetoni getNapotezi() {
        return naPotezi;
    }

    public void setNapotezi(Zetoni napotezi) {
        this.naPotezi = napotezi;
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
     * public boolean preveriZmago(Polje plosca) { for (int i = 0; i < 15; i++) {
     * for (int j = 0; j < 15; j++) { Polje zacetno = this.getPlosca()[i][j]; if
     * (zacetno.equals(Polje.PRAZNO)) { break; } else if (zacetno.equals(Polje.CRNI)
     * {
     * 
     * } else if (zacetno.equals(Polje.BELI)) {
     * 
     * } } } }
     */

    /*
     * public boolean preveriZmago(Polje plosca, Koordinati koordinati) {
     * 
     * }
     */

    public boolean preveriVrsto(Polje plosca, Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];
        Polje[] iskani = new Polje[5];
        for (int i = 0; i < 5; i++) {
            iskani[i] = trenutno;
        }

        int s = 0;
        for (int i = 0; i < 11; i++) {
            if (Arrays.copyOfRange(polje[x], i, i + 5).equals(iskani)) {
                s++;
            }
        }
        // Če je s večji, bo bodisi daljši od 5 bodisi jih bo več
        // Če pa je s manjši, jih pa ne bo
        return s == 1;
    }

    // ne najbolj elegantno
    public boolean preveriStolpec(Polje plosca, Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int stevec = 0;
        // štetje damo na čakanje, ko je 6 ali več istih barv po vrsti, 
        // in ga spet začnemo, ko to vrsto prekine druga barva.
        boolean cakanje = false;
        for (int i = 0; i < 11; i++) {
            if (polje[i][y].equals(trenutno)) {
            	if (cakanje) continue;
            	else {
            		stevec ++;
            		if (stevec == 5) {
            			if (i == 14 || (!polje[i + 1][y].equals(trenutno))) return true;
            		}
            		if (stevec == 6) {
            			cakanje = true;
            			stevec = 0;
            		}
            	}
            } else {
            	if (cakanje) {
            		cakanje = false;
            	}
            }
        }
        return false;
    }

    // preveri, ali je po diagonali "desno dol" kakšna peterica
    // (on pregleda celo plosco, ne samo tistega dela, kamor smo vnesli zadnjo
    // potezo)
    public boolean preveriDiagonaloDD(Polje plosca, Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int s = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                Polje prvi = polje[i][j];
                Polje drugi = polje[i + 1][j + 1];
                Polje tretji = polje[i + 2][j + 2];
                Polje cetrti = polje[i + 3][j + 3];
                Polje peti = polje[i + 4][j + 4];
                if (prvi.equals(trenutno) && drugi.equals(trenutno) && tretji.equals(trenutno)
                        && cetrti.equals(trenutno) && peti.equals(trenutno)) {
                    s++;
                }
            }
        }
        return s == 1;
    }

    // preveri, ali je po diagonali "desno gor" kakšna peterica
    // (on pregleda celo plosco, ne samo tistega dela, kamor smo vnesli zadnjo
    // potezo)
    public boolean preveriDiagonaloDG(Polje plosca, Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int s = 0;
        for (int i = 4; i < 15; i++) {
            for (int j = 4; j < 15; j++) {
                Polje prvi = polje[i][j];
                Polje drugi = polje[i - 1][j + 1];
                Polje tretji = polje[i - 2][j + 2];
                Polje cetrti = polje[i - 3][j + 3];
                Polje peti = polje[i - 4][j + 4];
                if (prvi.equals(trenutno) && drugi.equals(trenutno) && tretji.equals(trenutno)
                        && cetrti.equals(trenutno) && peti.equals(trenutno)) {
                    s++;
                }
            }
        }
        return s == 1;
    }
}
