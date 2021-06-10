package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Zetoni;
import splosno.KdoIgra;
import splosno.Koordinati;

public class Inteligenca extends KdoIgra {

    private static final int ZMAGA = Integer.MAX_VALUE;
    private static final int PORAZ = -ZMAGA;
    private static final int NEODLOCENO = 0;
    private int globina;

    public Inteligenca(int g) {
        super("AlphaBeta (" + g + ")");
        globina = g;
    }

    /**
     * Vrne eno izmed najboljših potez - njeni koordinati
     * @param igra - igra, ki jo igramo
     * @return - koordinati ene izmed najboljših potez
     */
    public Koordinati izberiPotezo(Igra igra) {
        return alphaBeta(igra, Integer.MIN_VALUE, Integer.MAX_VALUE, igra.getNaPotezi()).getPoteza();
    }

    /**
     * Algoritem alphaBeta, ki vrne seznam najbolje ocenjenjih 
     * potez na podlagi tega, kako bi se igra odvila, če bi odigrali
     * možno potezo.
     * @param igra - igra, ki jo igramo
     * @param alpha
     * @param beta
     * @param zetoni - žetoni, ki so na vrsti
     * @return - vrne seznam ocenjenih potez
     */
    public OcenjenePoteze alphaBeta(Igra igra, int alpha, int beta, Zetoni zetoni) {
        int ocena;
        if (igra.getNaPotezi() == zetoni) {
            ocena = PORAZ;
        } else {
            ocena = ZMAGA;
        }
        
        // seznam možnih potez
        List<Koordinati> moznePoteze = igra.moznePoteze();
        
        // izmed možnih potez izberemo najboljše
        List<PotezaZOceno> najboljse = izberiNajboljse(igra, moznePoteze, igra.getNaPotezi(), 20);
        
        // seznam potez z oceno pretvorimo v seznam koordinat
        List<Koordinati> najboljseKoordinate = new LinkedList<Koordinati>();
        for (PotezaZOceno poteza : najboljse) {
            najboljseKoordinate.add(poteza.getPoteza());
        }

        Koordinati kandidat = najboljse.get(0).getPoteza();
        OcenjenePoteze najboljeOcenjenePoteze = new OcenjenePoteze();

        // sprehodimo se po najboljših koordinatah; alphaBeta algoritem oceni, kako bi
        // se igra odvila, če bi odigrali to potezo
        for (Koordinati k : najboljseKoordinate) {
            Igra kopijaIgre = igra.clone();
            kopijaIgre.odigraj(k);
            int ocenap;
            ocenap = (kopijaIgre.getNaPotezi() == zetoni ? PORAZ : ZMAGA);
            switch (kopijaIgre.getStanje()) {
                case ZMAGA_BELI:
                    ocenap = (Zetoni.BELI == zetoni ? ZMAGA : PORAZ);
                    break;
                case ZMAGA_CRNI:
                    ocenap = (Zetoni.BELI == zetoni ? PORAZ : ZMAGA);
                    break;
                case NEODLOCENO:
                    ocenap = NEODLOCENO;
                    break;
                default:
                    if (globina == 1) {
                        ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre.getPlosca(), zetoni);
                    } else {
                        Inteligenca nizje = new Inteligenca(globina - 1);
                        ocenap = nizje.alphaBeta(kopijaIgre, alpha, beta, zetoni).getOcena();
                    }
            }
            if (igra.getNaPotezi() == zetoni) {
                if (ocenap > ocena) {
                    ocena = ocenap;
                    kandidat = k;
                    alpha = Math.max(alpha, ocena);
                }
            } else {
                if (ocenap < ocena) {
                    ocena = ocenap;
                    kandidat = k;
                    beta = Math.min(beta, ocena);
                }
            }
            if (alpha >= beta) {
                OcenjenePoteze x = new OcenjenePoteze();
                x.addPoteza(kandidat, ocena);
                return x;
            }
        }
        najboljeOcenjenePoteze.addPoteza(kandidat, ocena);
        return najboljeOcenjenePoteze;

    }

    /**
     * Poišče najboljše poteze.
     * @param igra - igra, ki jo igramo
     * @param poteze - poteze, ki so na voljo
     * @param naPotezi - žeton, ki je na potezi
     * @param velikost - velikost bufferja
     * @return - seznam potez z najboljšimi ocenami
     */
    private static List<PotezaZOceno> izberiNajboljse(Igra igra, List<Koordinati> poteze, Zetoni naPotezi,
            int velikost) {
        OcenjenaPotezaBuffer buffer = new OcenjenaPotezaBuffer(velikost);
        for (Koordinati k : poteze) {
            Igra kopija = igra.clone();
            kopija.odigraj(k);
            int ocena = OceniPozicijo.oceniPozicijo(igra.getPlosca(), naPotezi);
            buffer.add(new PotezaZOceno(k, ocena));
        }
        return buffer.list();
    }

}

/*
 * Objekt razreda OcenjenePoteze nosi dva podatka -
 * oceno in pa seznam potez, ki jo imajo.
 *
 */
class OcenjenePoteze {
    int ocena;
    List<Koordinati> poteze;
    Random r = new Random(System.currentTimeMillis());

    public OcenjenePoteze() {
        ocena = Integer.MIN_VALUE;
        poteze = new LinkedList<Koordinati>();
    }

    public Koordinati getPoteza() {
        return poteze.get(r.nextInt(poteze.size()));
    }

    /**
     * Doda potezo objektu OcenjenePoteze.
     * @param k -koordinati poteze, ki jo dodajamo
     * @param o - ocena poteze, ki jo dodajamo
     */
    public void addPoteza(Koordinati k, int o) {
    	// če je ocena poteze k manjša od trenutne ocene objekta,
    	// poteze ne dodamo
        if (o < ocena) {
            return;
        // če je ocena poteze k enaka trenutni oceni objekta,
        // to potezo dodamo seznamu
        } else if (o == ocena) {
            poteze.add(k);
            return;
        // če je ocena poteze k večja od trenunte ocene objekta,
        // izpraznimo seznam potez in dodamo le potezo k, prav
        // tako oceno nastavimo na o
        } else {
            setOcena(o);
            poteze = new LinkedList<Koordinati>();
            poteze.add(k);
            return;
        }
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int o) {
        ocena = o;
    }

}

class OcenjenaPotezaBuffer {

    private int velikost;

    private LinkedList<PotezaZOceno> buffer;

    public OcenjenaPotezaBuffer(int velikost) {
        this.velikost = velikost;
        this.buffer = new LinkedList<PotezaZOceno>();
    }

   /**
    * Doda ocenjeno potezo v buffer.
    * @param p - poteza z oceno, ki jo dodajamo
    */
    public void add(PotezaZOceno p) {
        int i = 0;
        for (PotezaZOceno po : buffer) {
            if (p.compareTo(po) != 1)
                i++; // če je ocena poteze p <= oceni poteze po, povečamo i
            else
                break; // izstopimo iz zanke 
        }
        if (i < velikost)
            buffer.add(i, p);
        if (buffer.size() > velikost)
            buffer.removeLast();
    }

    public List<PotezaZOceno> list() {
        return (List<PotezaZOceno>) buffer;
    }

}

/**
 * 
 * Objekt razreda PotezaZOceno vsebuje
 * dva podatka - koordinati in oceno poteze.
 *
 */
class PotezaZOceno {
    Koordinati poteza;
    int ocena;

    public PotezaZOceno(Koordinati k, int o) {
        this.poteza = k;
        this.ocena = o;
    }

    public int getOcenaPoteze() {
        return this.ocena;
    }

    public Koordinati getPoteza() {
        return this.poteza;
    }

    public void setOcenaPoteze(int o) {
        this.ocena = o;
    }

    public int compareTo(PotezaZOceno p) {
        if (this.ocena < p.ocena)
            return -1;
        else if (this.ocena > p.ocena)
            return 1;
        else
            return 0;
    }

}
