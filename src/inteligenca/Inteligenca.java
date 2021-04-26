package inteligenca;

import logika.Igra;
import logika.Polje;
import splosno.Koordinati;

public class Inteligenca {
    public Inteligenca(Igra igra) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (igra.getPlosca()[i][j].equals(Polje.PRAZNO)) {
                    x = 1;
                    y = 1;
                }
            }
        }
        Koordinati koordinati = new Koordinati(x, y);
        igra.odigraj(koordinati);
    }
}
