package inteligenca;

import java.util.Map;

import logika.Igra;
import logika.Zetoni;
import splosno.KdoIgra;
import splosno.Koordinati;

public abstract class Inteligenca extends KdoIgra {

    public Inteligenca(String ime) {
        super(ime);
    }

    public abstract Map<Koordinati, Integer> izberiPotezo(Igra igra, int globina, Zetoni zetoni);

}
