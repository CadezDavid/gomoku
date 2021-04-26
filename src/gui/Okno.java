package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Okno extends JFrame implements ActionListener {

    private JMenuItem igraClovekClovek;
    private JMenuItem igraRacunalnikClovek;

    public Okno(int sirina, int visina) {
        // Vse to sem vzel s predavanj ...
        this.setTitle("Gomoku");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        // polje
        Platno polje = new Platno();
        add(polje);

        // menu
        JMenuBar menu_bar = new JMenuBar();
        this.setJMenuBar(menu_bar);
        JMenu igra_menu = new JMenu("Nova igra");
        menu_bar.add(igra_menu);

        igraClovekClovek = new JMenuItem("Človek – Človek");
        igra_menu.add(igraClovekClovek);
        igraClovekClovek.addActionListener(this);

        igraRacunalnikClovek = new JMenuItem("Računalnik – Človek");
        igra_menu.add(igraRacunalnikClovek);
        igraRacunalnikClovek.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == igraRacunalnikClovek) {
            // ustvari novo igro ...
        } else if (e.getSource() == igraClovekClovek) {
            // ustvari novo igro ...
        }
    }
}
