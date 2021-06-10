package inteligenca;

import logika.Polje;
import logika.Zetoni;

public class OceniPozicijo {
	
	/**
	 * S številom oceni razporeditev plošče z vidika igralca naPotezi.
	 * @param plosca - plošča, ki jo ocenjujemo
	 * @param naPotezi - igralec na potezi
	 * @return - številčna ocena
	 */
    public static int oceniPozicijo(Polje[][] plosca, Zetoni naPotezi) {
        return Groznje.groznje(plosca, naPotezi);
    }
    
}
