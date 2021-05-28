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

    // Vrne koordinati ene od potez z največjo vrednostjo.
    public Koordinati izberiPotezo(Igra igra) {
        return alphaBeta(igra, Integer.MIN_VALUE, Integer.MAX_VALUE, igra.getNaPotezi()).getPoteza();
    }

    /**
     * Algoritem alphaBeta, preveri in oceni poteze in vrne seznam (razred
     * NajboljeOcenjenePoteze) najbolje ocenjenih potez
     */
    public OcenjenePoteze alphaBeta(Igra igra, int alpha, int beta, Zetoni zetoni) {
        int ocena;
        if (igra.getNaPotezi() == zetoni) {
            ocena = PORAZ;
        } else {
            ocena = ZMAGA;
        }
        List<Koordinati> moznePoteze = igra.moznePoteze();

        List<PotezaZOceno> najboljse = izberiNajboljse(igra, moznePoteze, igra.getNaPotezi(), 20);
        List<Koordinati> najboljseKoordinate = new LinkedList<Koordinati>();
        for (PotezaZOceno poteza : najboljse) {
            najboljseKoordinate.add(poteza.getPoteza());
        }

        Koordinati kandidat = najboljse.get(0).getPoteza();
        OcenjenePoteze najboljeOcenjenePoteze = new OcenjenePoteze();

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
                // najboljeOcenjenePoteze.addPoteza(kandidat, ocena);
                OcenjenePoteze x = new OcenjenePoteze();
                x.addPoteza(kandidat, ocena);
                return x;
            }
        }
        najboljeOcenjenePoteze.addPoteza(kandidat, ocena);
        return najboljeOcenjenePoteze;

    }

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

    public void addPoteza(Koordinati k, int o) {
        if (o < ocena) {
            return;
        } else if (o == ocena) {
            poteze.add(k);
            return;
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

    // Tukaj bi imel človek tist razred Poteza z oceno, da bi lahko to lepo dodajal,
    // zdaj ga bom naredila, da vidim,kako bi to bilo
    public void add(PotezaZOceno p) {
        int i = 0;
        for (PotezaZOceno po : buffer) {
            if (p.compareTo(po) != 1)
                i++; // če je ocena poteze p <= oceni poteze po, povečamo i
            else
                break; // izstopimo iz zanke - torej če najdemo vdčjo, jo bomo zamenjal
        }
        if (i < velikost)
            buffer.add(i, p); // doda koordinati p na i-to mesto
        if (buffer.size() > velikost)
            buffer.removeLast();
    }

    public List<PotezaZOceno> list() {
        return (List<PotezaZOceno>) buffer;
    }

}

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
