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
    private int velikost;
    private List<Koordinati> zmagovalnaVrsta = null;

    
    /**
     * Konstruktor Igra, ki ustvari igro - kot igralca, ki je na 
     * potezi, nastavi črnega, saj črni začne. Stanje igre nastavi na
     * V_TEKU in zariše novo, prazno igralno polje. 
     * 
     * @param igralca - igralca, ki bosta igrala igro
     * @param velikost - velikost plosce
     */
    public Igra(EnumMap<Zetoni, Igralec> igralca, int v) {
    	setVelikost(v);
        // Zacne crni
        naPotezi = Zetoni.CRNI;
        // Stanje se spremeni v V_TEKU
        stanje = Stanje.V_TEKU;
        // Plosca se po angl. "initialize" in napolni
        // s praznimi polji
        plosca = new Polje[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                plosca[i][j] = Polje.PRAZNO;
            }
        }
        setIgralca(igralca);
        poteze = new LinkedList<Koordinati>();
    }

    /**
     * Funkicja, ki preveri, ali je sta koordinati poteze, ki jo želim odigrati,
     * validni (če je polje prazno), in če sta, to potezo tudi odigra, nato
     * pa preveri, ali je poteza privedla do zmage ali pa neodločenega izida. 
     * Če je privedla do zmage, še poišče seznam zmagovalnih koordinat in ga nastavi.
     * 
     * @param koordinati - koordinati poteze, ki jo želimo odigrati
     * @return - vrne true, če je bila poteza validna, in false, če je 
     * ne moremo odigrati
     */
    public boolean odigraj(Koordinati koordinati) {
        if (plosca[koordinati.getX()][koordinati.getY()] == Polje.PRAZNO) {
            plosca[koordinati.getX()][koordinati.getY()] = (naPotezi == Zetoni.BELI) ? Polje.BELI : Polje.CRNI;
            if (preveriZmago(koordinati)) {
            	zmagovalnaVrsta = zmagovalnaVrsta(koordinati);
                stanje = (naPotezi == Zetoni.BELI) ? Stanje.ZMAGA_BELI : Stanje.ZMAGA_CRNI;
                }
            if (preveriNeodloceno()) {
                stanje = Stanje.NEODLOCENO;
            }
            setNaPotezi(naPotezi == Zetoni.BELI ? Zetoni.CRNI : Zetoni.BELI);
            poteze.add(koordinati);
            return true;
        } else
            return false;
    }

    /**
     * Izbere prvo prosto polje in to potezo odigra.
     */
    public void nakljucna() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                if (this.getPlosca()[i][j].equals(Polje.PRAZNO)) {
                    x = i;
                    y = j;
                }
            }
        }
        Koordinati koordinati = new Koordinati(x, y);
        this.odigraj(koordinati);
    }

    /**
     * Ustvari kopijo igre.
     */
    public Igra clone() {
        Igra kopija = new Igra(this.getIgralca(), this.getVelikost());
        kopija.setNaPotezi(this.getNaPotezi());
        kopija.setStanje(this.getStanje());

        Polje[][] plosca = new Polje[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                plosca[i][j] = this.getPlosca()[i][j];
            }
        }
        kopija.setPlosca(plosca);

        return kopija;
    }


    /**
     * Funkcija se sprehodi po plošči in pregleda kvadratek 3x3 okoli
     * vsakega polnega polja. Prazna polja doda v seznam možnih potez.
     * 
     * @return - vrne seznam možnih potez
     */
    public List<Koordinati> moznePoteze() {
        Set<Koordinati> moznePoteze = new HashSet<Koordinati>();
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                if (getPlosca()[i][j] != Polje.PRAZNO) {
                    for (int m = Math.max(0, i - 2); m < Math.min(i + 2, velikost); m++) {
                        for (int n = Math.max(0, j - 2); n < Math.min(j + 2, velikost); n++) {
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
    
    /**
     * Preveri, ali se je stanje igre neodločeno.
     * @return - true, če so vsa polja zapolnjena in false sicer
     */
    public boolean preveriNeodloceno() {
        Polje[][] polje = this.getPlosca();
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                if (polje[i][j].equals(Polje.PRAZNO))
                    return false;
            }
        }
        return true;
    }

    /** 
     * Preveri, ali se je kje zmagovalna vrstica.
     * @param k - koordinati, ki smo ju odigrali
     * @return - true, če je bila k zmagovalna poteza in false sicer
     */
    public boolean preveriZmago(Koordinati k) {
        return (preveriVrsto(k) || preveriStolpec(k) || preveriDiagonaloDD(k) || preveriDiagonaloDG(k));
    }

    /**
     * Preveri, ali je v vrsti okoli koordinate k, ki smo jo vnesli, vsaj 5 polj iste barve
     * @param koordinati - koordinati, ki smo ju odigrali
     * @return - true, če je okoli k v vrsti vsaj 5 žetonov iste barve
     */
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
        for (int i = 1; i <= velikost - 1 - y; i++) {
            if (polje[x][y + i].equals(trenutno))
                stevec++;
            else
                break;
        }
        return (stevec + 1 >= 5);
    }

    /**
     * Preveri, ali je v stolpcu okoli koordinate k, ki smo jo vnesli, vsaj 5 polj iste barve
     * @param koordinati - koordinati, ki smo ju odigrali
     * @return - true, če je okoli k v stolpcu vsaj 5 žetonov iste barve
     */
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
        for (int i = 1; i <= velikost - 1 - x; i++) {
            if (polje[x + i][y].equals(trenutno))
                stevec++;
            else
                break;
        }
        return (stevec + 1 >= 5);
    }

    /**
     * Preveri, ali je v diagonali "desno dol" okoli koordinate k, ki smo jo vnesli, vsaj 
     * 5 polj iste barve
     * @param koordinati - koordinati, ki smo ju odigrali
     * @return - true, če je okoli k po diagonali "desno dol" vsaj 5 žetonov iste barve
     */
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
        for (int i = 1; i <= Math.min(velikost - 1 - x, velikost - 1 - y); i++) {
            if (polje[x + i][y + i].equals(trenutno))
                stevec++;
            else
                break;
        }
        return stevec + 1 >= 5;
    }

    /**
     * Preveri, ali je v diagonali "desno gor" okoli koordinate k, ki smo jo vnesli, 
     * vsaj 5 polj iste barve
     * @param koordinati - koordinati, ki smo ju odigrali
     * @return - true, če je okoli k po diagonali "desno gor" vsaj 5 žetonov iste barve
     */
    public boolean preveriDiagonaloDG(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();

        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];

        int stevec = 0;
        for (int i = 1; i <= Math.min(velikost - 1 - x, y); i++) {
            if (polje[x + i][y - i].equals(trenutno))
                stevec++;
            else
                break;
        }
        for (int i = 1; i <= Math.min(x, velikost - 1 - y); i++) {
            if (polje[x - i][y + i].equals(trenutno))
                stevec++;
            else
                break;
        }
        return stevec + 1 >= 5;

    }
    
    /**
     * Vrne seznam zmagovalnih koordinat.
     * @param k - koordinati, ki smo ju nazadnje odigrali
     * @return - seznam koordinat, ki tvorijo zmagovalno vrsto/stolpec/diagonalo
     */
    public List<Koordinati> zmagovalnaVrsta (Koordinati k){
    	if (preveriVrsto(k) == true) {
    		return najdiVrsto(k);
    	} else if (preveriStolpec(k) == true) {
    		return najdiStolpec(k);
    	} else if (preveriDiagonaloDD(k) == true) {
    		return najdiDiagonaloDD(k);
    	} else return najdiDiagonaloDG(k);
    }
    
    /**
     * Preveri vrsto.
     * @param koordinati - koordinati, ki smo ju nazadnje odigrali
     * @return - seznam zmagovalnih koordinat, če je
     */
    public List<Koordinati> najdiVrsto(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();
        List<Koordinati> vrsta = new LinkedList<Koordinati>();
        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];
        vrsta.add(new Koordinati(x, y));
        
        int stevec = 0;
        for (int i = 1; i <= y; i++) {
            if (polje[x][y - i].equals(trenutno))
            {
            	vrsta.add(new Koordinati(x, y - i));
                stevec++;
            } else
                break;
        }
        for (int i = 1; i <= 14 - y; i++) {
            if (polje[x][y + i].equals(trenutno)) {
            	vrsta.add(new Koordinati(x, y + i));
                stevec++;
            } else
                break;
        }
        if (stevec + 1 >= 5) return vrsta;
        else return null;
    }
    
    /**
     * Preveri stolpec.
     * @param koordinati - koordinati, ki smo ju nazadnje odigrali
     * @return - seznam zmagovalnih koordinat, če je
     */
    public List<Koordinati> najdiStolpec(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();
        List<Koordinati> stolpec = new LinkedList<Koordinati>();
        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];
        stolpec.add(new Koordinati(x, y));
        
        int stevec = 0;
        for (int i = 1; i <= x; i++) {
            if (polje[x - i][y].equals(trenutno)) {
            	stolpec.add(new Koordinati(x - i, y));
                stevec++;
            }
            else
                break;
        }
        for (int i = 1; i <= 14 - x; i++) {
            if (polje[x + i][y].equals(trenutno)){
            	stolpec.add(new Koordinati(x + i, y));
                stevec++;
            } else
                break;
        }
        if (stevec + 1 >= 5) return stolpec;
        else return null;
    }
    
    /**
     * Preveri diagonalo "desno dol"
     * @param koordinati - koordinati, ki smo ju nazadnje odigrali
     * @return - seznam zmagovalnih koordinat, če je
     */
    public List<Koordinati> najdiDiagonaloDD(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();
        List<Koordinati> diag = new LinkedList<Koordinati>();
        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];
        diag.add(new Koordinati(x, y));
        
        int stevec = 0;
        for (int i = 1; i <= Math.min(x, y); i++) {
            if (polje[x - i][y - i].equals(trenutno)){
            	diag.add(new Koordinati(x - i, y - i));
                stevec++;
            }
            else
                break;
        }
        for (int i = 1; i <= Math.min(14 - x, 14 - y); i++) {
            if (polje[x + i][y + i].equals(trenutno)){
            	diag.add(new Koordinati(x + i, y + i));
                stevec++;
            } else
                break;
        }
        if (stevec + 1 >= 5) return diag;
        else return null;
    }
    
    /**
     * Preveri diagonalo "desno gor"
     * @param koordinati - koordinati, ki smo ju nazadnje odigrali
     * @return - seznam zmagovalnih koordinat, če je
     */
    public List<Koordinati> najdiDiagonaloDG(Koordinati koordinati) {
        int x = koordinati.getX();
        int y = koordinati.getY();
        List<Koordinati> diag = new LinkedList<Koordinati>();
        Polje[][] polje = this.getPlosca();
        Polje trenutno = polje[x][y];
        diag.add(new Koordinati(x, y));
        
        int stevec = 0;
        for (int i = 1; i <= Math.min(14 - x, y); i++) {
            if (polje[x + i][y - i].equals(trenutno)){
            	diag.add(new Koordinati(x + i, y - i));
                stevec++;
            }
            else
                break;
        }
        for (int i = 1; i <= Math.min(x, 14 - y); i++) {
            if (polje[x - i][y + i].equals(trenutno)){
            	diag.add(new Koordinati(x - i, y + i));
                stevec++;
            } else
                break;
        }
        if (stevec + 1 >= 5) return diag;
        else return null;
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
    
    public int getVelikost() {
    	return velikost;
    }
    
    public void setVelikost(int v) {
    	velikost = v;
    }
    
    public List<Koordinati> getZmagovalne(){
    	return zmagovalnaVrsta;
    }

}
