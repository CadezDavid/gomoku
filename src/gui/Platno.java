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
import logika.Vrsta;
import logika.Zetoni;
import splosno.Koordinati;
import vodja.Vodja;

public class Platno extends JPanel implements MouseListener {

    private final static double LINE_WIDTH = 0.05;
    private final static double PADDING = 0.05;

    public Platno() {
        setPreferredSize(getPreferredSize());
        setBackground(Color.PINK);
        addMouseListener(this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    private double squareWidth() {
        return Math.min(getWidth(), getHeight()) / 15;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        double w = squareWidth();

        // narišemo črte
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        for (int i = 0; i < 16; i++) {
            g2.drawLine((int) (i * w), (int) (0), (int) (i * w), (int) (15 * w));
            g2.drawLine((int) (0), (int) (i * w), (int) (15 * w), (int) (i * w));
        }

        if (Vodja.getIgra() != null) {
            Polje[][] plosca = Vodja.getIgra().getPlosca();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
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
        int x = koordinati.getX();
        int y = koordinati.getY();
        double w = squareWidth();
        double i = x * w;
        double j = y * w;

        if (zeton.equals(Zetoni.BELI)) {
            g2.setColor(Color.WHITE);
        } else {
            g2.setColor(Color.BLACK);
        }

        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        g2.fillOval((int) (i + w/4), (int) (j + w/4), (int) w / 2, (int) w / 2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Vodja.kdoJeNaVrsti().equals(Vrsta.CLOVEK)) {
            int x = e.getX();
            int y = e.getY();
            int w = (int) (squareWidth());
            int i = x / w;
            int j = y / w;
            Koordinati k = new Koordinati(i, j);
            Vodja.getIgra().odigraj(k);
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
}
