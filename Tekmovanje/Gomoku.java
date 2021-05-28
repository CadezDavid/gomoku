import java.io.IOException;

import gui.Okno;


public class Gomoku {

    public static void main(String[] args) throws IOException {
        Okno okno = new Okno(800, 800);
        okno.pack();
        okno.setVisible(true);
        Vodja.setOkno(okno);
    }
}
