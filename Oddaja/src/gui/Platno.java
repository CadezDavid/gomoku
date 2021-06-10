package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import logika.Polje;
import logika.Stanje;
import logika.Vrsta;
import logika.Zetoni;
import splosno.Koordinati;
import vodja.Vodja;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {

    private final static double LINE_WIDTH = 0.05;
    private final static double PADDING = 0.05;
    private int velikost;
    private Color bel;
    private Color crn;
    private Color ozadje;
    private Color zmagovalna = Color.RED;

    public Platno(int v, Color crn, Color bel, Color ozadje) {
        setPreferredSize(getPreferredSize());
        setOzadje(ozadje);
        setBackground(getOzadje());
        addMouseListener(this);
        setVelikost(v);
        setBel(bel);
        setCrn(crn);
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    /**
     * Izračuna, kakšna naj bo širina polja glede na velikost okna.
     * @return - širina polja
     */
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

        // narišemo črte - igralno polje
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        for (int i = 0; i < velikost + 1; i++) {
            g2.drawLine((int) ((i * w) + s), (int) (v), (int) (i * w + s), (int) (velikost * w + v));
            g2.drawLine((int) (s), (int) ((i * w) + v), (int) (velikost * w + s), (int) (i * w + v));
        }
        // če je igra v teku ali pa že končana, 
        // narišemo na ploščo še vse žetone
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

    /**
     * Ustrezno pobarva kvadratek
     * @param g2
     * @param zeton - žeton, ki je odigral koordinati
     * @param koordinati - koordinati, ki ju igramo
     */
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
        
 
        if (zeton.equals(Zetoni.BELI)){
            g2.setColor(bel);
        } else {
            g2.setColor(crn);
        }
        g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        g2.fillOval((int) ((i + w / 4) + s), (int) ((j + w / 4) + v), (int) w / 2, (int) w / 2);
        
        // preverimo, ali obstajajo zmagovalne koordinate,in če ja, obkrožimo zmagovalne
        // žetone z zmagovalno barvo
        if (Vodja.getIgra().getZmagovalne() != null) {
        	List<Koordinati> z = Vodja.getIgra().getZmagovalne() ;
        	if (z.contains(koordinati)) {
        		g2.setColor(zmagovalna);
        		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
        		g2.drawOval((int) ((i + w / 4) + s), (int) ((j + w / 4) + v), (int) w / 2, (int) w / 2);
        	}
        }
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

    /**
     * Izračuna koordinati (i, j) v polju glede na klik
     * @param x - x koordinata klika
     * @param y - y koordinata klika
     * @return - koordinati, ki ju želimo odigrati
     */
    private Koordinati getKoordinati(int x, int y) {
        double sirina = getWidth();
        double polovicaS = sirina * (1 - PADDING) / 2;
        double visina = getHeight();
        double polovicaV = visina * (1 - PADDING) / 2;
        double polovica = Math.min(polovicaS, polovicaV);
        double s = (sirina / 2) - polovica;
        double v = (visina / 2) - polovica;
        double w = squareWidth();

        int i = (int) (((double) x - s) / w);
        int j = (int) (((double) y - v) / w);
        return new Koordinati(i, j);
    }

    public int getVelikost() {
        return velikost;
    }

    public void setVelikost(int v) {
        velikost = v;
    }
    
    public void setBel(Color b) {
    	bel = b;
    }
    
    public Color getBel() {
    	return bel;
    }
    
    public void setCrn(Color c) {
    	crn = c;
    }
    
    public Color getCrn() {
    	return crn;
    }
    
    public void setOzadje(Color o) {
    	ozadje = o;
    }
    
    public Color getOzadje() {
    	return ozadje;
    }
    
}
