package inteligenca;

import logika.Polje;
import logika.Zetoni;

public class Groznje {
    public static int groznje(Polje[][] plosca, Zetoni naVrsti) {
    	Zetoni niNaVrsti = (naVrsti == Zetoni.CRNI ? Zetoni.BELI : Zetoni.CRNI);
    	int dobro = (stiri(plosca, naVrsti) + 
        		stiriObrnjeno(plosca, naVrsti) +
        		straightFour(plosca, naVrsti) +
        		three(plosca, naVrsti) + 
        		threeObrnjeno(plosca, naVrsti) +
        		brokenThree(plosca, naVrsti) + 
        		brokenThreeObrnjeno(plosca, naVrsti) +
        		pet(plosca, naVrsti));
    	int slabo = (stiri(plosca, niNaVrsti) + 
        		stiriObrnjeno(plosca, niNaVrsti) +
        		straightFour(plosca, niNaVrsti) +
        		three(plosca, niNaVrsti) + 
        		threeObrnjeno(plosca, niNaVrsti) +
        		brokenThree(plosca, niNaVrsti) + 
        		brokenThreeObrnjeno(plosca, niNaVrsti) +
        		pet(plosca, niNaVrsti));   	
        return dobro - slabo;
    }

    
    // Ocenujuje, kako ogržujoča je dana situacija (dano polje):
    
    // nevarnost: 
    // 5 - ze izgubljeno, 
    // 4 - ena poteza manjka, 
    // 3 - dve potezi, ...

    // straight four: _XXXX_
    private static int straightFour(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, p, p, Polje.PRAZNO };
        return (int) Math.pow(6, poisciVzorec(plosca, vzorec));
    }

    // zakaj ta vzorec?
    private static int pet(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { p, p, p, p, p }; //zakaj še teli "prazno"
        return (int) Math.pow(6, poisciVzorec(plosca, vzorec));
    }

    private static int stiri(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { (p == Polje.BELI ? Polje.CRNI : Polje.BELI), p, p, p, p, Polje.PRAZNO };
        return (int) Math.pow(5, poisciVzorec(plosca, vzorec));
    }

    // a je to smiselno
    private static int stiriObrnjeno(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = {Polje.PRAZNO, p, p, p, p, (p == Polje.BELI ? Polje.CRNI : Polje.BELI)};
        return (int) Math.pow(4, poisciVzorec(plosca, vzorec));
    }
    
    private static int brokenThree(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, Polje.PRAZNO, p, p, Polje.PRAZNO };
        return (int) Math.pow(4, poisciVzorec(plosca, vzorec));
    }
    
    private static int brokenThreeObrnjeno(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, Polje.PRAZNO, p, Polje.PRAZNO };
        return (int) Math.pow(4, poisciVzorec(plosca, vzorec));
    }
    

    private static int three(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, p, Polje.PRAZNO };
        return (int) Math.pow(3, poisciVzorec(plosca, vzorec));
    }

    private static int threeObrnjeno(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, p, Polje.PRAZNO };
        return (int) Math.pow(3, poisciVzorec(plosca, vzorec));
    }
    

    private static int poisciVzorec(Polje[][] plosca, Polje[] vzorec) {
        // Vrne stevilo pojavitev tega vzorca
        int stevec = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15 - vzorec.length; j++) {
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
        for (int i = 0; i < 15 - vzorec.length; i++) {
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
