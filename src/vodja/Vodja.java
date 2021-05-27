package vodja;

import java.util.EnumMap;
import java.util.LinkedList;

import javax.swing.SwingWorker;

import gui.Okno;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;
import logika.Vrsta;
import logika.Zetoni;
import splosno.Koordinati;

public class Vodja {

    private static Okno okno;
    private static Igra igra;
    private static Inteligenca racunalnik = new Inteligenca(3);

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
                        if (igra.getPoteze().equals(new LinkedList<Koordinati>())) {
                            igra.odigraj(new Koordinati(7, 7));
                        } else if (igra.getPoteze().size() < 4) {
                            igra.odigraj(new Inteligenca(2).izberiPotezo(igra));
                        } else {
                            igrajRacunalnikovoPotezo();
                        }
                        break;
                    case CLOVEK:
                        break;
                }
            case ZMAGA_BELI:
            case ZMAGA_CRNI:
            case NEODLOCENO:
        }
    }

    public static void igrajRacunalnikovoPotezo() {
        SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void>() {
            @Override
            protected Koordinati doInBackground() {
                return racunalnik.izberiPotezo(igra);
            }

            @Override
            protected void done() {
                Koordinati poteza = null;
                try {
                    poteza = get();
                } catch (Exception e) {
                }
                ;
                if (poteza == null) {
                	System.out.println("Aaa tukej ripnem.");
                } else {
                igra.odigraj(poteza);
                }
                cikel();
            }
        };
        worker.execute();
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
        return igra.getIgralca().get(igra.getNaPotezi()).getVrsta();
    }
}
