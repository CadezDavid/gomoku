import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;

import gui.Okno;
import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Stanje;
import logika.Zetoni;
import splosno.Koordinati;

public class Gomoku {

    protected static Igra igra;

    private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        // Ustvari okno
        Okno okno = new Okno(800, 800);
        okno.pack();
        // okno.setVisible(true);

        Igra igra;
        igra = ustvariNovoIgro(okno);
        while (igra.getStanje() == Stanje.V_TEKU) {
            EnumMap<Zetoni, Igralec> igralca = igra.getIgralca();
            Igralec igralec = igralca.get(igra.getNapotezi());
            String t = igralec.getVrsta();
            if (t.equals("racunalnik")) {
                igra.nakljucna();
            } else {
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        String s = "_";
                        Polje polje = igra.getPlosca()[i][j];
                        if (polje.equals(Polje.BELI)) {
                            s = "b";
                        } else if (polje.equals(Polje.CRNI)) {
                            s = "c";
                        } else if (polje.equals(Polje.PRAZNO)) {
                            s = "_";
                        }
                        System.out.print(s);
                    }
                    System.out.print("\n");
                }
                System.out.println("Izberi potezo (oblike \"x y\")");
                String poteza = r.readLine();

                int i = poteza.indexOf(" ");
                if (i == -1 || i == poteza.length()) {
                    System.out.println("Napacen format.");
                    continue;
                }
                String xString = poteza.substring(0, i);
                String yString = poteza.substring(i + 1);

                int x, y;

                try {
                    x = Integer.parseInt(xString);
                    y = Integer.parseInt(yString);
                } catch (NumberFormatException e) {
                    System.out.println("Napačen format.");
                    continue;
                }

                if (x < 0 || 14 < x || y < 0 || 14 < y) {
                    System.out.println("Napačen format.");
                    continue;
                }

                Koordinati poteza1 = new Koordinati(x, y);
                if (igra.odigraj(poteza1)) {
                    continue;
                }
                System.out.println(poteza1.toString() + " ni možna.");
            }
        }
    }

    public static Igra ustvariNovoIgro(Okno okno) throws IOException {
        EnumMap<Zetoni, Igralec> igralca = new EnumMap<Zetoni, Igralec>(Zetoni.class);

        System.out.println("Kako vam je ime:");
        String ime = r.readLine();

        System.out.println("Izberi proti čemu želis igrati:");
        System.out.println("1 - računalniku");
        System.out.println("2 - cloveku");
        String s1 = r.readLine();

        System.out.println("Izberi žetone:");
        System.out.println("1 - beli");
        System.out.println("2 - crni");
        String s2 = r.readLine();

        if (s1.equals("1") && s2.equals("1")) {
            igralca.put(Zetoni.BELI, new Igralec("clovek", ime));
            igralca.put(Zetoni.CRNI, new Igralec("racunalnik", "milan"));
            return new Igra(igralca);
        } else if (s1.equals("2") && s2.equals("1")) {
            igralca.put(Zetoni.BELI, new Igralec("clovek", ime));
            igralca.put(Zetoni.CRNI, new Igralec("clovek", "milan"));
            return new Igra(igralca);
        } else if (s1.equals("2") && s2.equals("2")) {
            igralca.put(Zetoni.CRNI, new Igralec("clovek", ime));
            igralca.put(Zetoni.BELI, new Igralec("clovek", "milan"));
            return new Igra(igralca);
        } else if (s1.equals("1") && s2.equals("2")) {
            igralca.put(Zetoni.CRNI, new Igralec("clovek", ime));
            igralca.put(Zetoni.BELI, new Igralec("racunalnik", "milan"));
            return new Igra(igralca);
        }
        return ustvariNovoIgro(okno);

    }
}
