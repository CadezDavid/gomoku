package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igralec;
import logika.Vrsta;
import logika.Zetoni;
import vodja.Vodja;

public class Okno extends JFrame implements ActionListener {

    private Platno platno;

    private JMenuItem igraClovekClovek;
    private JMenuItem igraRacunalnikClovek;

    private JLabel status;

    public Okno(int sirina, int visina) {
        // Vse to sem vzel s predavanj ...
        this.setTitle("Gomoku");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        // platno
        platno = new Platno();
        add(platno);

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

        // statusna vrstica
        status = new JLabel();
        status.setFont(new Font("Source Code Pro", Font.BOLD, 18));
        GridBagConstraints status_layout = new GridBagConstraints();
        status_layout.gridx = 0;
        status_layout.gridy = 1;
        status_layout.anchor = GridBagConstraints.CENTER;
        getContentPane().add(status, status_layout);

        status.setText("Izberite igro.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EnumMap<Zetoni, Igralec> igralca = new EnumMap<Zetoni, Igralec>(Zetoni.class);
        if (e.getSource() == igraRacunalnikClovek) {
            igralca.put(Zetoni.CRNI, new Igralec(Vrsta.RACUNALNIK, "milan"));
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.CLOVEK, "janko"));
        } else if (e.getSource() == igraClovekClovek) {
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.CLOVEK, "marcel"));
            igralca.put(Zetoni.CRNI, new Igralec(Vrsta.CLOVEK, "ciril"));
        }
        Vodja.ustvariNovoIgro(igralca);
    }

    public void osveziGUI() {
        switch (Vodja.getIgra().getStanje()) {
            case V_TEKU:
                String x = Vodja.getIgra().getNapotezi().equals(Zetoni.BELI) ? "beli" : "črni";
                status.setText("Na potezi je " + x + ".");
                break;
            case ZMAGA_BELI:
                status.setText("Zmagal je beli.");
                break;
            case ZMAGA_CRNI:
                status.setText("Zmagal je črni.");
                break;
            case NEODLOCENO:
                status.setText("Neodločeno.");
                break;
        }
        platno.repaint();
    }

	public Platno getPlatno() {
		return platno;
	}

	public void setPlatno(Platno platno) {
		this.platno = platno;
	}

	public JMenuItem getIgraClovekClovek() {
		return igraClovekClovek;
	}

	public void setIgraClovekClovek(JMenuItem igraClovekClovek) {
		this.igraClovekClovek = igraClovekClovek;
	}

	public JMenuItem getIgraRacunalnikClovek() {
		return igraRacunalnikClovek;
	}

	public void setIgraRacunalnikClovek(JMenuItem igraRacunalnikClovek) {
		this.igraRacunalnikClovek = igraRacunalnikClovek;
	}

	public JLabel getStatus() {
		return status;
	}

	public void setStatus(JLabel status) {
		this.status = status;
	}
}
