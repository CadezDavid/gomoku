package inteligenca;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

    // Vrne koordinati ene od potez z največjo vrednostjo. 
    public Koordinati izberiPotezo(Igra igra) {
        return alphaBeta(igra, Integer.MIN_VALUE, Integer.MAX_VALUE, igra.getNaPotezi()).getRandom();
    }

    /**
     * Algoritem alphaBeta, preveri in 
     * oceni poteze in 
     * vrne seznam (razred NajboljeOcenjenePoteze) najbolje ocenjenih potez
     */
    public NajboljeOcenjenePoteze alphaBeta(Igra igra, int alpha, int beta, Zetoni zetoni) {
        Set<Koordinati> moznePoteze = igra.moznePoteze();

        int ocena;
        if (igra.getNaPotezi() == zetoni) {
            ocena = PORAZ;
        } else {
            ocena = ZMAGA;
        }

        Koordinati kandidat = null;
        NajboljeOcenjenePoteze najboljeOcenjenePoteze = new NajboljeOcenjenePoteze();

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
                        ocenap = nizje.alphaBeta(kopijaIgre, alpha, beta, zetoni).getFirst().getOcena();
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
            	OcenjenaPoteza ocenjenaPoteza = new OcenjenaPoteza(ocena, kandidat);
                najboljeOcenjenePoteze.addIfBest(ocenjenaPoteza);
            }
        }
        OcenjenaPoteza ocenjenaPoteza = new OcenjenaPoteza(ocena, kandidat);
        najboljeOcenjenePoteze.addIfBest(ocenjenaPoteza);
        return najboljeOcenjenePoteze;

}

class OcenjenaPoteza {
	
	 	private int ocena;
	    private Koordinati poteza;

	    public OcenjenaPoteza(int o, Koordinati k) {
	        ocena = o;
	        poteza = k;
	    }


	    public int getOcena() {
	        return ocena;
	    }

	    public void setOcena(int ocena) {
	        this.ocena = ocena;
	    }

	    public Koordinati getPoteza() {
	        return poteza;
	    }

	    public void setPoteza(Koordinati p) {
	        this.poteza = p;
	    }

//	Random random = new Random();
//    private int ocena;
//    private List<Koordinati> poteze;
//
//    public OcenjenePoteze() {
//        ocena = Integer.MIN_VALUE;
//        poteze = new LinkedList<Koordinati>();
//    }
//
//    public void addPoteza(int o, Koordinati k) {
//    	if (o > ocena) {
//    		ocena = o;
//    		poteze = new LinkedList<Koordinati>();
//    		poteze.add(k);
//    	} else if (o == ocena) {
//    		poteze.add(k);
//    	}
//    }
//
//    public int getOcena() {
//        return ocena;
//    }
//
//    public void setOcena(int ocena) {
//        this.ocena = ocena;
//    }
//
//    public List<Koordinati> getPoteze() {
//        return poteze;
//    }
//
//    public void setPoteze(List<Koordinati> poteze) {
//        this.poteze = poteze;
//    }
//
//    public Koordinati getRandom() {
//         int size = poteze.size();
//         int x = new Random().nextInt(size + 1);
//         return poteze.get(x);
//    }

}

class NajboljeOcenjenePoteze {
	private LinkedList<OcenjenaPoteza> buffer;
	
	public NajboljeOcenjenePoteze() {
		this.buffer = new LinkedList<OcenjenaPoteza>();
	}
	
	public void addIfBest(OcenjenaPoteza ocenjenaPoteza) {
		if (buffer.isEmpty()) buffer.add(ocenjenaPoteza);
		else {
			OcenjenaPoteza op = buffer.getFirst();
			int ocena = op.getOcena();
			int o = ocenjenaPoteza.getOcena();
			if (o > ocena) {
	    		ocena = o;
	    		buffer = new LinkedList<OcenjenaPoteza>();
	    		buffer.add(ocenjenaPoteza);
	    	} else if (o == ocena) {
	    		buffer.add(ocenjenaPoteza);
	    	}
	    }
	}
	public OcenjenaPoteza getFirst() {
		return buffer.get(0);
	}
	
	public List<OcenjenaPoteza> list() {
		return (List<OcenjenaPoteza>) buffer;
	}
	
	public Koordinati getRandom() {
		if (buffer.isEmpty()) return null;
		   int size = buffer.size();
           int x = new Random().nextInt(size);
           OcenjenaPoteza p = buffer.get(x);
           return p.getPoteza();
	}
}
}

	
