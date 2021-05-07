package inteligenca;

import logika.Igra;
import splosno.KdoIgra;
import splosno.Koordinati;

public abstract class Inteligenca extends KdoIgra {

    public Inteligenca(String ime) {
        super(ime);
    }

    public abstract Koordinati izberiPotezo(Igra igra);

}
