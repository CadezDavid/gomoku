package inteligenca;

import logika.Polje;
import logika.Zetoni;

public class Groznje {
    public static int groznje(Polje[][] plosca, Zetoni naVrsti) {
    	int dobro = (four(plosca, naVrsti) + 
        		fourObrnjeno(plosca, naVrsti) +
        		straightFour(plosca, naVrsti) +
        		three(plosca, naVrsti) + 
        		brokenThree(plosca, naVrsti) + 
        		brokenThreeObrnjeno(plosca, naVrsti) +
        		//two(plosca, naVrsti) + 
        		five(plosca, naVrsti)  +
        		twoAndTwo(plosca, naVrsti)+
        		threeAndOne(plosca, naVrsti) +
        		threeAndOneObrnjeno(plosca, naVrsti)
        		);
   		return dobro;
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
        return (int) Math.pow(6, poisciVzorec(plosca, vzorec));
    }
    
    // straight four: _XXXX_
    private static int straightFour(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, p, p, Polje.PRAZNO };
        return (int) Math.pow(5, poisciVzorec(plosca, vzorec));
    }

    // four: OXXXX_
    private static int four(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = {p, p, p, p, Polje.PRAZNO };  // a bi blo tukej bolj smiselno spustit to začetno? ker loh je stena tm pa je tko: lXXXX_
        return (int) Math.pow(5, poisciVzorec(plosca, vzorec));
    }
    
    //(p == Polje.BELI ? Polje.CRNI : Polje.BELI),
    
    
  ////////////////////////////////// a je te trebaaa /////////////////////////////////////////////
    // two and two: XX_XX
    private static int twoAndTwo(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = {p, p, Polje.PRAZNO, p, p};
        return (int) Math.pow(5, poisciVzorec(plosca, vzorec));
    }
    
    // thrre and one: XXX_X
    private static int threeAndOne(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = {p, p, p, Polje.PRAZNO, p};
        return (int) Math.pow(5, poisciVzorec(plosca, vzorec));
    }
    
    // thrre and one: XXX_X
    private static int threeAndOneObrnjeno(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = {p, Polje.PRAZNO, p, p, p};
        return (int) Math.pow(5, poisciVzorec(plosca, vzorec));
    }
 
    ///////////////////////////////////////////a je te zgoraj treba //////////////////////////
    
    // four: _XXXXO
    private static int fourObrnjeno(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = {Polje.PRAZNO , p, p, p, p};
        return (int) Math.pow(5, poisciVzorec(plosca, vzorec));
    }
    
  
    
    // broken three: _X_XX_
    private static int brokenThree(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, Polje.PRAZNO, p, p, Polje.PRAZNO };
        return (int) Math.pow(4, poisciVzorec(plosca, vzorec));
    }
    
    // broken three: _XX_X_
    private static int brokenThreeObrnjeno(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, Polje.PRAZNO, p, Polje.PRAZNO };
        return (int) Math.pow(4, poisciVzorec(plosca, vzorec));
    }
    
    // three: _XXX_
    private static int three(Polje[][] plosca, Zetoni naVrsti) {
        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
        Polje[] vzorec = { Polje.PRAZNO, p, p, p, Polje.PRAZNO };
        return (int) Math.pow(3, poisciVzorec(plosca, vzorec));
    }
    
    // two: _XX_
//    private static int two(Polje[][] plosca, Zetoni naVrsti) {
//        Polje p = (naVrsti == Zetoni.CRNI ? Polje.BELI : Polje.CRNI);
//        Polje[] vzorec = { Polje.PRAZNO, p, p, Polje.PRAZNO };
//        return (int) Math.pow(2, poisciVzorec(plosca, vzorec));
//    }
    


    

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
