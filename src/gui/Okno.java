package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Okno extends JFrame implements ActionListener, ChangeListener {

    public Okno(int sirina, int visina) {
        this.setTitle("Gomoku");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());

        // polje
        polje = new Polje();
    }

	@Override
	public void stateChanged(ChangeEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
