package inteligenca;

import logika.Igra;
import logika.Polje;
import logika.Zetoni;

public class OceniPozicijo {
    public static int oceniPozicijo(Igra igra) {
        Zetoni naPotezi = igra.getNaPotezi();
        Polje[][] plosca = igra.getPlosca();
        return Groznje.groznje(plosca, naPotezi);
    }
}
