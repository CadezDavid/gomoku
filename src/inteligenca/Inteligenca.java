package inteligenca;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    public Koordinati izberiPotezo(Igra igra) {
        return alphaBeta(igra, Integer.MIN_VALUE, Integer.MAX_VALUE, igra.getNaPotezi()).getRandom();
    }

    public OcenjenePoteze alphaBeta(Igra igra, int alpha, int beta, Zetoni zetoni) {
        Set<Koordinati> moznePoteze = igra.moznePoteze();

        int ocena;
        if (igra.getNaPotezi() == zetoni) {
            ocena = PORAZ;
        } else {
            ocena = ZMAGA;
        }

        Koordinati kandidat = null;

        for (Koordinati p : moznePoteze) {
            Igra kopijaIgre = igra.clone();
            kopijaIgre.odigraj(p);
            int ocenap;
            ocenap = (kopijaIgre.getNaPotezi() == zetoni ? PORAZ : ZMAGA);
            switch (kopijaIgre.getStanje()) {
                case ZMAGA_BELI:
                    ocenap = (Zetoni.BELI == zetoni ? ZMAGA : PORAZ);
                    break;
                case ZMAGA_CRNI:
                    ocenap = (Zetoni.CRNI == zetoni ? PORAZ : ZMAGA);
                    break;
                case NEODLOCENO:
                    ocenap = NEODLOCENO;
                    break;
                case V_TEKU:
                    if (globina == 1) {
                        int x = kopijaIgre.getNaPotezi() == zetoni ? 1 : -1;
                        ocenap = x * OceniPozicijo.oceniPozicijo(kopijaIgre);
                    } else {
                        Inteligenca nizje = new Inteligenca(globina - 1);
                        ocenap = nizje.alphaBeta(kopijaIgre, alpha, beta, zetoni).getOcena();
                    }
            }
            if (igra.getNaPotezi() == zetoni) {
                if (ocenap > ocena) {
                    ocena = ocenap;
                    kandidat = p;
                    alpha = Math.max(alpha, ocena);
                }
            } else {
                if (ocenap < ocena) {
                    ocena = ocenap;
                    kandidat = p;
                    beta = Math.min(beta, ocena);
                }
            }
            if (alpha >= beta) {
                return new OcenjenePoteze(ocena, kandidat);
            }
        }
        return new OcenjenePoteze(ocena, kandidat);
    }

}

class OcenjenePoteze {
    private int ocena;
    private Set<Koordinati> poteze;

    public OcenjenePoteze(int o, Koordinati k) {
        ocena = o;
        poteze = new HashSet<Koordinati>();
        addPoteza(k);
    }

    public void addPoteza(Koordinati k) {
        poteze.add(k);
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Set<Koordinati> getPoteze() {
        return poteze;
    }

    public void setPoteze(Set<Koordinati> poteze) {
        this.poteze = poteze;
    }

    public Koordinati getRandom() {
        int size = poteze.size();
        int x = new Random().nextInt(size);
        int i = 0;
        for (Koordinati k : poteze) {
            if (i == x) {
                return k;
            } else {
                i++;
            }
        }
        return getRandom();
    }

}
