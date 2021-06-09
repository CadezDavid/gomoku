package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Polje;
import logika.Stanje;
import logika.Vrsta;
import logika.Zetoni;
import splosno.Koordinati;
import vodja.Vodja;

// @SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {

    private final static double LINE_WIDTH = 0.05;
    private final static double PADDING = 0.05;
    private static Color ozadje = new Color(255, 218, 179);
    private int velikost;

    public Platno(int v) {
        setPreferredSize(getPreferredSize());
        setBackground(ozadje);
        addMouseListener(this);
        setVelikost(v);
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    private double squareWidth() {
        return Math.min(getWidth(), getHeight()) * (1 - PADDING) / (double) velikost;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        double sirina = getWidth();
        double polovicaS = (sirina - (sirina * PADDING)) / 2;
        double visina = getHeight();
        double polovicaV = (visina - (visina * PADDING)) / 2;
        double polovica = Math.min(polovicaS, polovicaV);
        double s = (sirina / 2) - polovica;
        double v = (visina / 2) - polovica;
        double w = squareWidth();

        // narišemo črte
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        for (int i = 0; i < velikost + 1; i++) {
            g2.drawLine((int) ((i * w) + s), (int) (v), (int) (i * w + s), (int) (velikost * w + v));
            g2.drawLine((int) (s), (int) ((i * w) + v), (int) (velikost * w + s), (int) (i * w + v));
        }
        System.out.println(velikost);

        if (Vodja.getIgra() != null) {
            Polje[][] plosca = Vodja.getIgra().getPlosca();
            for (int i = 0; i < velikost; i++) {
                for (int j = 0; j < velikost; j++) {
                    Koordinati k = new Koordinati(i, j);
                    switch (plosca[i][j]) {
                        case BELI:
                            paintSquare(g2, Zetoni.BELI, k);
                            break;
                        case CRNI:
                            paintSquare(g2, Zetoni.CRNI, k);
                            break;
                        case PRAZNO:
                            break;
                    }
                }
            }
        }
    }

    public void paintSquare(Graphics2D g2, Zetoni zeton, Koordinati koordinati) {
        double sirina = getWidth();
        double polovicaS = sirina * (1 - PADDING) / 2;
        double visina = getHeight();
        double polovicaV = visina * (1 - PADDING) / 2;
        double polovica = Math.min(polovicaS, polovicaV);
        double s = (sirina / 2) - polovica;
        double v = (visina / 2) - polovica;
        double w = squareWidth();

        int x = koordinati.getX();
        int y = koordinati.getY();

        double i = x * w;
        double j = y * w;

        if (zeton.equals(Zetoni.BELI)) {
            g2.setColor(Color.WHITE);
        } else {
            g2.setColor(Color.BLACK);
        }

        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        g2.fillOval((int) ((i + w / 4) + s), (int) ((j + w / 4) + v), (int) w / 2, (int) w / 2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Vodja.getIgra() != null && Vodja.getIgra().getStanje() == Stanje.V_TEKU
                && Vodja.kdoJeNaVrsti().equals(Vrsta.CLOVEK)) {
            int x = e.getX();
            int y = e.getY();
            Vodja.getIgra().odigraj(getKoordinati(x, y));
            Vodja.cikel();
        }

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    public static double getLineWidth() {
        return LINE_WIDTH;
    }

    public static double getPadding() {
        return PADDING;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    // izračuna koordinati (i, j) v polju glede na klik
    private Koordinati getKoordinati(int x, int y) {
        double sirina = getWidth();
        double polovicaS = sirina * (1 - PADDING) / 2;
        double visina = getHeight();
        double polovicaV = visina * (1 - PADDING) / 2;
        double polovica = Math.min(polovicaS, polovicaV);
        double s = (sirina / 2) - polovica;
        double v = (visina / 2) - polovica;
        double w = squareWidth();
        System.out.println(w);

        int i = (int) (((double) x - s) / w);
        int j = (int) (((double) y - v) / w);
        System.out.println("Koordinati " + i + " " + j);
        return new Koordinati(i, j);
    }

    public int getVelikost() {
        return velikost;
    }

    public void setVelikost(int v) {
        velikost = v;
    }
}
