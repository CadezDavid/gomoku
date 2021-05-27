package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import logika.Igra;
import logika.Polje;
import logika.Zetoni;
import splosno.KdoIgra;
import splosno.Koordinati;

public class Inteligenca extends KdoIgra {

    private static final int ZMAGA = Integer.MAX_VALUE;
    private static final int PORAZ = -ZMAGA;
    private static final int NEODLOCENO = 0;
    private int globina;
    private int bufferVelikost = 100;

    public Inteligenca(int g) {
        super("AlphaBeta (" + g + ")");
        globina = g;
    }

    // Vrne koordinati ene od potez z največjo vrednostjo. 
    public Koordinati izberiPotezo(Igra igra) {
        return alphaBeta(igra, Integer.MIN_VALUE, Integer.MAX_VALUE, igra.getNaPotezi(), bufferVelikost).getPoteza(); //.getRandom();
    }

    /**
     * Algoritem alphaBeta, preveri in 
     * oceni poteze in 
     * vrne seznam (razred NajboljeOcenjenePoteze) najbolje ocenjenih potez
     */
    public OcenjenePoteze alphaBeta(Igra igra, int alpha, int beta, Zetoni zetoni, int velikost) {
        int ocena;
        if (igra.getNaPotezi() == zetoni) {
            ocena = PORAZ;
        } else {
            ocena = ZMAGA;
        }
        List<Koordinati> moznePoteze = igra.moznePoteze();
        List<Koordinati> smiselnePoteze = new LinkedList<Koordinati>();
        Polje[][] p = igra.getPlosca();
        for (Koordinati k : moznePoteze) {
        	if (jeVSrediscu(p, k)) {
        		smiselnePoteze.add(k);
        	}
        }
        
        List<PotezaZOceno> najboljse = izberiNajboljse(igra, smiselnePoteze, igra.getNaPotezi(), velikost);
        List<Koordinati> najboljseKoordinate = new LinkedList<Koordinati>();
        for (PotezaZOceno poteza : najboljse) {
        	najboljseKoordinate.add(poteza.getOcenjenaPoteza());
        }
        Koordinati kandidat = smiselnePoteze.get(0); // hm hm
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
                    ocenap = (Zetoni.CRNI == zetoni ? PORAZ : ZMAGA);
                    break;
                case NEODLOCENO:
                    ocenap = NEODLOCENO;
                    break;
                default:
                    if (globina == 1) {
                        int x = kopijaIgre.getNaPotezi() == zetoni ? 1 : -1;
                        ocenap = x *OceniPozicijo.oceniPozicijo(kopijaIgre.getPlosca(), zetoni);
                    } else {
                        Inteligenca nizje = new Inteligenca(globina - 1);
                        ocenap = nizje.alphaBeta(kopijaIgre, alpha, beta, zetoni, (int) (velikost / 2)).getOcena();
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
            	najboljeOcenjenePoteze.addPoteza(kandidat, ocena);
//            	return najboljeOcenjenePoteze; // kle da pol kr prvo vedno, 
            	//to se ne zdi ok. mogoče bi moral bit un seznam mal premešan
            	// k če da človek to stran, dost ok dela (glede na prej)
            //	sam zlo počas
            	// pa ne napada lih kej dost
            // PA ON SE IZOGIBA 5 V VRSTO!!!!
            }
        }
        najboljeOcenjenePoteze.addPoteza(kandidat, ocena);
        return najboljeOcenjenePoteze;

}
   
    private static boolean jeVSrediscu(Polje[][] p, Koordinati k) {
    	int x = k.getX();
    	int y = k.getY();
    	for (int i = Math.max(0,  x - 2); i < Math.min(x + 3, 15); i++) {
    		for (int j = Math.max(0,  y - 2); j < Math.min(y + 3, 15); j++) {
    			if (i != x || j!= y) {
    				if (p[i][j] != Polje.PRAZNO) return true;
    			}
    		}
    	}
    	return false;
    }
    
    private static List<PotezaZOceno> izberiNajboljse(Igra igra, List<Koordinati> smiselnePoteze, Zetoni naPotezi, int velikost) {
    	OcenjenaPotezaBuffer buffer = new OcenjenaPotezaBuffer(velikost);
    	for (Koordinati k : smiselnePoteze) {
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
	Random r = new Random();
	
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
	// Tukaj bi imel človek tist razred Poteza z oceno, da bi lahko to lepo dodajal, zdaj ga bom naredila, da vidim,kako bi to bilo
	public void add(PotezaZOceno p) {
		int i = 0;
		for (PotezaZOceno po : buffer) {
			if (p.compareTo(po) != 1) i++; //če je ocena poteze p <= oceni poteze po, povečamo i
			else break; // izstopimo iz zanke	   - torej če najdemo vdčjo, jo bomo zamenjal
		}
		if (i < velikost) buffer.add(i,p); // doda koordinati p na i-to mesto
		if (buffer.size() > velikost) buffer.removeLast();
	}
	
	
	public List<PotezaZOceno> list() {
		return (List<PotezaZOceno>) buffer;
	}
	

}

class PotezaZOceno {
	Koordinati poteza;
	int ocena;
	
	public PotezaZOceno(Koordinati k, int o){
		this.poteza = k;
		this.ocena = o;
	}
	
	public int getOcenaPoteze() {
		return this.ocena;
	}
	
	public Koordinati getOcenjenaPoteza() {
		return this.poteza;
	}
	
	public void setOcenaPoteze(int o) {
		this.ocena = o;
	}
	
	public int compareTo(PotezaZOceno p) {
		if (this.ocena < p.ocena) return -1;
		else if (this.ocena > p.ocena) return 1;
		else return 0;
	}
	
}



	
