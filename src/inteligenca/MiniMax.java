package inteligenca;

import logika.Igra;
import splosno.Koordinati;

public class MiniMax extends Inteligenca {

    private static final int ZMAGA = Integer.MAX_VALUE;
    private static final int ZGUBA = -ZMAGA;
    private static final int NEODLOC = 0;
    private int globina;

    public MiniMax(int g) {
        super("MiniMax " + g);
        globina = g;
    }

    public Koordinati izberiPotezo(Igra igra) {

    }
}
