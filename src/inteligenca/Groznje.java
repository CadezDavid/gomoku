package inteligenca;

import logika.Polje;
import logika.Zetoni;

public class Groznje {
    public static int groznje(Polje[][] plosca, Zetoni naVrsti) {
        Zetoni nasprotnik = (naVrsti == Zetoni.BELI ? Zetoni.CRNI : Zetoni.BELI);
        int slabo = straightFour(plosca, naVrsti) + three(plosca, naVrsti)
                + brokenThree(plosca, naVrsti) + two(plosca, naVrsti) + five(plosca, naVrsti)
                + twoAndTwo(plosca, naVrsti) + threeAndOne(plosca, naVrsti);
        int dobro = straightFour(plosca, nasprotnik) + three(plosca, nasprotnik)
                + brokenThree(plosca, nasprotnik) + two(plosca, nasprotnik) + five(plosca, nasprotnik)
                + twoAndTwo(plosca, nasprotnik) + threeAndOne(plosca, nasprotnik);
        System.out.println("Dobro: " + dobro + " slabo: " + slabo);
        return dobro - slabo;
    }

    // Ocenujuje, kako ogržujoča je dana situacija (dano polje):

    // nevarnost:
    // 5 - ze izgubljeno,
    // 4 - ena poteza manjka,
    // 3 - dve potezi, ...

    // OGROŽUJOČE SITUACIJE:

    // five: XXXXX
    private static int five(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { p, p, p, p, p };
        return 100 * poisciVzorec(plosca, vzorec);
    }

    // straight four: _XXXX_
    private static int straightFour(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec1 = { Polje.PRAZNO, p, p, p, p, Polje.PRAZNO };
        Polje[] vzorec2 = { Polje.PRAZNO, p, p, p, p };
        Polje[] vzorec3 = { p, p, p, p, Polje.PRAZNO };
        return 80 * poisciVzorec(plosca, vzorec1) + 80 * poisciVzorec(plosca, vzorec2)
                + 80 * poisciVzorec(plosca, vzorec3);
    }

    // four: OXXXX_
    // private static int four(Polje[][] plosca, Zetoni naVrsti) {
    // Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
    // Polje[] vzorec1 = { p, p, p, p, Polje.PRAZNO };
    // Polje[] vzorec2 = { Polje.PRAZNO, p, p, p, p };
    // return 20 * poisciVzorec(plosca, vzorec1) + 20 * poisciVzorec(plosca,
    // vzorec2);
    // }

    // a je te trebaaa
    //
    // two and two: XX_XX
    private static int twoAndTwo(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { p, p, Polje.PRAZNO, p, p };
        return 30 * poisciVzorec(plosca, vzorec);
    }

    // thrre and one: XXX_X
    private static int threeAndOne(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec1 = { p, p, p, Polje.PRAZNO, p };
        Polje[] vzorec2 = { p, Polje.PRAZNO, p, p, p };
        return 20 * poisciVzorec(plosca, vzorec1) + 20 * poisciVzorec(plosca, vzorec2);
    }

    // a je te zgoraj treba

    // broken three: _X_XX_
    private static int brokenThree(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec1 = { Polje.PRAZNO, p, Polje.PRAZNO, p, p, Polje.PRAZNO };
        Polje[] vzorec2 = { Polje.PRAZNO, p, p, Polje.PRAZNO, p, Polje.PRAZNO };
        return 8 * poisciVzorec(plosca, vzorec1) + 8 * poisciVzorec(plosca, vzorec2);
    }

    // three: _XXX_
    private static int three(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, p, Polje.PRAZNO };
        return 4 * poisciVzorec(plosca, vzorec);
    }

    // two: _XX_
    private static int two(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, Polje.PRAZNO };
        return poisciVzorec(plosca, vzorec);
    }

    private static int poisciVzorec(Polje[][] plosca, Polje[] vzorec) {
        // Vrne stevilo pojavitev tega vzorca
        int stevec = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15 - vzorec.length + 1; j++) {
                boolean b = true;
                for (int k = 0; k < vzorec.length; k++) {
                    if (vzorec[k] != plosca[i][j + k]) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    stevec++;
                }
            }
        }
        for (int i = 0; i < 15 - vzorec.length + 1; i++) {
            for (int j = 0; j < 15; j++) {
                boolean b = true;
                for (int k = 0; k < vzorec.length; k++) {
                    if (vzorec[k] != plosca[i + k][j]) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    stevec++;
                }
            }
        }
        for (int i = 0; i < 15 - vzorec.length; i++) {
            for (int j = 0; j < 15 - vzorec.length; j++) {
                boolean b1 = true;
                for (int k = 0; k < vzorec.length; k++) {
                    if (vzorec[k] != plosca[i + k][j + k]) {
                        b1 = false;
                        break;
                    }
                }
                if (b1) {
                    stevec++;
                }
                boolean b2 = true;
                for (int k = 0; k < vzorec.length; k++) {
                    if (vzorec[k] != plosca[i + vzorec.length - k][j + k]) {
                        b2 = false;
                        break;
                    }
                }
                if (b2) {
                    stevec++;
                }
            }
        }
        return stevec;
    }
}
