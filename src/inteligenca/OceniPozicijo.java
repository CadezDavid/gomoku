package inteligenca;

import logika.Igra;
import logika.Polje;
import logika.Zetoni;

public class OceniPozicijo {
	
    public static int oceniPozicijo(Polje[][] plosca, Zetoni naPotezi) {
        return Groznje.groznje(plosca, naPotezi);
    }
    
}
