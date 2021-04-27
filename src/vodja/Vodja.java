package vodja;

import java.util.EnumMap;

import gui.Okno;
import logika.Igra;
import logika.Igralec;
import logika.Vrsta;
import logika.Zetoni;

public class Vodja {

    private static Okno okno;
    private static Igra igra;

    public static void ustvariNovoIgro(EnumMap<Zetoni, Igralec> igralca) {
        igra = new Igra(igralca);
        cikel();
    }

    public static void cikel() {
        okno.osveziGUI();
        switch (igra.getStanje()) {
            case V_TEKU:
                switch (kdoJeNaVrsti()) {
                    case RACUNALNIK:
                        igra.nakljucna();
                        okno.osveziGUI();
                        break;
                    case CLOVEK:
                        break;
                }
            case ZMAGA_BELI:
            case ZMAGA_CRNI:
            case NEODLOCENO:
        }
    }

    public static Igra getIgra() {
        return igra;
    }

    public Okno getOkno() {
        return okno;
    }

    public static void setOkno(Okno o) {
        okno = o;
    }

    public static void setIgra(Igra x) {
        igra = x;
    }

    public static Vrsta kdoJeNaVrsti() {
        return igra.getIgralca().get(igra.getNapotezi()).getVrsta();
    }
}
