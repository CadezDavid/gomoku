package inteligenca;

import logika.Polje;
import logika.Zetoni;


public class Groznje {
	
	/**
	 * Oceni, kako nevarna je dana postavitev plošče za igralca naVrsti
	 * 
	 * @param plosca - kako so trenutno razporejeni žetoni na plošči
	 * @param naVrsti - igralec (oz. žetoni) na vrsti
	 * @return - število, ki pove, kako ogrožujoča je taka postavitev plošče
	 */
    public static int groznje(Polje[][] plosca, Zetoni naVrsti) {
        int slabo = straightFour(plosca, naVrsti) + three(plosca, naVrsti) + brokenThree(plosca, naVrsti)
                + two(plosca, naVrsti) + five(plosca, naVrsti) + twoAndTwo(plosca, naVrsti)
                + threeAndOne(plosca, naVrsti);
        return -slabo;
    }

    // OGROŽUJOČE SITUACIJE

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
        return 800 * poisciVzorec(plosca, vzorec1) + 808 * poisciVzorec(plosca, vzorec2)
                + 800 * poisciVzorec(plosca, vzorec3);
    }

    // two and two: XX_XX
    private static int twoAndTwo(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { p, p, Polje.PRAZNO, p, p };
        return 300 * poisciVzorec(plosca, vzorec);
    }

    // thrre and one: XXX_X
    private static int threeAndOne(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec1 = { p, p, p, Polje.PRAZNO, p };
        Polje[] vzorec2 = { p, Polje.PRAZNO, p, p, p };
        return 200 * poisciVzorec(plosca, vzorec1) + 200 * poisciVzorec(plosca, vzorec2);
    }

    // broken three: _X_XX_
    private static int brokenThree(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec1 = { Polje.PRAZNO, p, Polje.PRAZNO, p, p, Polje.PRAZNO };
        Polje[] vzorec2 = { Polje.PRAZNO, p, p, Polje.PRAZNO, p, Polje.PRAZNO };
        return 80 * poisciVzorec(plosca, vzorec1) + 80 * poisciVzorec(plosca, vzorec2);
    }

    // three: _XXX_
    private static int three(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, p, Polje.PRAZNO };
        return 40 * poisciVzorec(plosca, vzorec);
    }

    // two: _XX_
    private static int two(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, Polje.PRAZNO };
        return poisciVzorec(plosca, vzorec);
    }
    
    /**
     * Prešteje, kolikokrat se vzorec pojavi na plošči.
     * Pregleda vrstice, stolpce in diagonale.
     * @param plosca - trenutna plošča
     * @param vzorec - vzorec, ki ga iščemo
     * @return - število pojavitev vzorca
     */
    private static int poisciVzorec(Polje[][] plosca, Polje[] vzorec) {
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
