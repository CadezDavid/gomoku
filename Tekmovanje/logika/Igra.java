package logika;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import splosno.Koordinati;

public class Igra {
    private Zetoni naPotezi;
    private EnumMap<Zetoni, Igralec> igralca;
    private Stanje stanje;
    private Polje[][] plosca;
    private List<Koordinati> poteze;

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
        poteze = new LinkedList<Koordinati>();
    }

    public boolean odigraj(Koordinati koordinati) {
        // Pogleda, če je polje, ki ga želi igralec igrati prazno
        // Če je, tja postavi žeton igralca na potezi in vrne true
        if (plosca[koordinati.getX()][koordinati.getY()] == Polje.PRAZNO) {
            plosca[koordinati.getX()][koordinati.getY()] = (naPotezi == Zetoni.BELI) ? Polje.BELI : Polje.CRNI;
            if (preveriZmago(koordinati)) {
                stanje = (naPotezi == Zetoni.BELI) ? Stanje.ZMAGA_BELI : Stanje.ZMAGA_CRNI;
                // System.out.println("Zmagal je " + stanje.toString() + ".");
            }
            if (preveriNeodloceno()) {
                stanje = Stanje.NEODLOCENO;
                // System.out.println("Neodločeno!");
            }
            setNaPotezi(naPotezi == Zetoni.BELI ? Zetoni.CRNI : Zetoni.BELI);
            poteze.add(koordinati);
            return true;
        } else
            return false;
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

    public Igra clone() {
        Igra kopija = new Igra(this.getIgralca());
        kopija.setNaPotezi(this.getNaPotezi());
        kopija.setStanje(this.getStanje());

        Polje[][] plosca = new Polje[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                plosca[i][j] = this.getPlosca()[i][j];
            }
        }
        kopija.setPlosca(plosca);

        return kopija;
    }

    // public List<Koordinati> moznePoteze() {
    // List<Koordinati> moznePoteze = new LinkedList<Koordinati>();
    // for (int i = 0; i < 15; i++) {
    // for (int j = 0; j < 15; j++) {
    // if (getPlosca()[i][j] == Polje.PRAZNO) {
    // moznePoteze.add(new Koordinati(i, j));
    // }
    // }
    // }
    // return moznePoteze;
    // }

    public List<Koordinati> moznePoteze() {
        Set<Koordinati> moznePoteze = new HashSet<Koordinati>();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (getPlosca()[i][j] != Polje.PRAZNO) {
                    for (int m = Math.max(0, i - 2); m < Math.min(i + 2, 15); m++) {
                        for (int n = Math.max(0, j - 2); n < Math.min(j + 2, 15); n++) {
                            if (m != 0 || n != 0) {
                                if (getPlosca()[m][n].equals(Polje.PRAZNO))
                                    moznePoteze.add(new Koordinati(m, n));
                            }
                        }
                    }
                }
            }
        }
        List<Koordinati> list = new LinkedList<Koordinati>();
        list.addAll(moznePoteze);
        return list;
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

    public boolean preveriNeodloceno() {
        Polje[][] polje = this.getPlosca();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (polje[i][j].equals(Polje.PRAZNO))
                    return false;
            }
        }
        return true;
    }

    public boolean preveriZmago(Koordinati k) {
        return (preveriVrsto(k) || preveriStolpec(k) || preveriDiagonaloDD(k) || preveriDiagonaloDG(k));
    }

    public boolean preveriVrsto(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int stevec = 0;
        for (int i = 1; i <= y; i++) {
            if (polje[x][y - i].equals(trenutno))
                stevec++;
            else
                break;
        }
        for (int i = 1; i <= 14 - y; i++) {
            if (polje[x][y + i].equals(trenutno))
                stevec++;
            else
                break;
        }
        return (stevec + 1 >= 5);
    }

    public boolean preveriStolpec(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int stevec = 0;
        for (int i = 1; i <= x; i++) {
            if (polje[x - i][y].equals(trenutno))
                stevec++;
            else
                break;
        }
        for (int i = 1; i <= 14 - x; i++) {
            if (polje[x + i][y].equals(trenutno))
                stevec++;
            else
                break;
        }
        return (stevec + 1 >= 5);
    }

    // preveri diagonalo "desno dol"
    public boolean preveriDiagonaloDD(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int stevec = 0;
        for (int i = 1; i <= Math.min(x, y); i++) {
            if (polje[x - i][y - i].equals(trenutno))
                stevec++;
            else
                break;
        }
        for (int i = 1; i <= Math.min(14 - x, 14 - y); i++) {
            if (polje[x + i][y + i].equals(trenutno))
                stevec++;
            else
                break;
        }
        return stevec + 1 >= 5;
    }

    // preveri diagonalo "desno gor"
    public boolean preveriDiagonaloDG(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int stevec = 0;
        for (int i = 1; i <= Math.min(14 - x, y); i++) {
            if (polje[x + i][y - i].equals(trenutno))
                stevec++;
            else
                break;
        }
        for (int i = 1; i <= Math.min(x, 14 - y); i++) {
            if (polje[x - i][y + i].equals(trenutno))
                stevec++;
            else
                break;
        }
        return stevec + 1 >= 5;

    }

    public Zetoni getNaPotezi() {
        return naPotezi;
    }

    public void setNaPotezi(Zetoni naPotezi) {
        this.naPotezi = naPotezi;
    }

    public List<Koordinati> getPoteze() {
        return poteze;
    }

    public void setPoteze(List<Koordinati> poteze) {
        this.poteze = poteze;
    }

}
