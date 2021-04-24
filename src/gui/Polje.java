package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Polje extends JPanel implements MouseListener {

    public Polje(int sirina, int visina) {
        setPreferredSize(new Dimension(sirina, visina));
        setBackground(Color.WHITE);
        addMouseListener(this);
    }

    private void addMouseListener(Polje polje) {
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }
}
