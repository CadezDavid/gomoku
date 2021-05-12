package vodja;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.SwingWorker;

import gui.Okno;
import inteligenca.AlphaBeta;
import logika.Igra;
import logika.Igralec;
import logika.Vrsta;
import logika.Zetoni;
import splosno.Koordinati;

public class Vodja {

    private static Okno okno;
    private static Igra igra;
    private static AlphaBeta racunalnik = new AlphaBeta(3);

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
                        igrajRacunalnikovoPotezo();
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
                Map<Koordinati, Integer> poteze = racunalnik.izberiPotezo(igra, 1, igra.getNapotezi());
                Koordinati kandidat = new Koordinati(1, 2);
                int ocenap = Integer.MIN_VALUE;
                for (Koordinati p : poteze.keySet()) {
                    if (ocenap < poteze.get(p)) {
                        kandidat = p;
                        ocenap = poteze.get(p);
                    }
                }
                return kandidat;
            }

            @Override
            protected void done() {
                Koordinati poteza = null;
                try {
                    poteza = get();
                } catch (Exception e) {
                }
                ;
                igra.odigraj(poteza);
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
        return igra.getIgralca().get(igra.getNapotezi()).getVrsta();
    }
}
