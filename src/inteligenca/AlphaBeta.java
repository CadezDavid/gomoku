package inteligenca;

import logika.Igra;
import splosno.Koordinati;

public class AlphaBeta extends Inteligenca {
    private int globina;

    public AlphaBeta(int g) {
        super("AlphaBeta " + g);
        globina = g;
    }

    public Koordinati izberiPotezo(Igra igra) {
        return new Koordinati(1, 2);
    }
}
