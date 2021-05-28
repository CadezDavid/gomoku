package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import logika.Igralec;
import logika.Vrsta;
import logika.Zetoni;
import vodja.Vodja;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {

    private Platno platno;

    private JMenuItem igraClovekClovek;
    private JMenuItem igraRacunalnikClovek;
    private JMenuItem igraClovekRacunalnik;
    // private JMenuItem miniMax;
    // private JMenuItem alphaBeta;
    private JPanel orodjarna;
    private JPanel zacetni;
    private JButton gumbZaNovoIgro;
    private JButton gumbCC;
    private JButton gumbCR;
    private JButton gumbRC;
    private JLabel status;
 

    
    public Okno(int sirina, int visina) {
        // Vse to sem vzel s predavanj ...
        this.setTitle("Gomoku");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        //glavnaPlosca = new JPanel();
        //glavnaPlosca.setLayout(new BoxLayout(glavnaPlosca, BoxLayout.Y_AXIS));
        //this.add(glavnaPlosca);
        
    
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
        
        igraClovekRacunalnik = new JMenuItem("Človek - računalnik");
        igra_menu.add(igraClovekRacunalnik);
        igraClovekRacunalnik.addActionListener(this);
        
        
        // // tip racunalnika
        // JMenu inteligenca = new JMenu("Inteligenca");
        // menu_bar.add(inteligenca);

        // miniMax = new JMenuItem("MiniMax");
        // inteligenca.add(miniMax);
        // miniMax.addActionListener(this);

        // alphaBeta = new JMenuItem("AlphaBeta");
        // inteligenca.add(alphaBeta);
        // alphaBeta.addActionListener(this);
        
     // platno
        platno = new Platno();
        // glavnaPlosca.add(platno, BorderLayout.CENTER);
        platno.setVisible(true);
        
        GridBagConstraints platno_layout = new GridBagConstraints();
		platno_layout.gridx = 0;
		platno_layout.gridy = 0;
		platno_layout.fill = GridBagConstraints.BOTH;
		platno_layout.weightx = 1.0;
		platno_layout.weighty = 1.0;
		platno_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(platno, platno_layout);


        // statusna vrstica
        status = new JLabel();
        status.setFont(new Font("Fira Code", Font.BOLD, 18));
        GridBagConstraints status_layout = new GridBagConstraints();
        status_layout.gridx = 0;
        status_layout.gridy = 1;
        status_layout.anchor = GridBagConstraints.CENTER;
        status.setText("Izberite igro.");
        getContentPane().add(status, status_layout);
        status.setVisible(true);
        
        ///////////////////////////////////////////////////////////////////////////////////////////
        // vrstica za začeti novo igro
        orodjarna = new JPanel();
        gumbZaNovoIgro = new JButton("Začni znova.");
        gumbZaNovoIgro.addActionListener(this);
        orodjarna.add(gumbZaNovoIgro);
        GridBagConstraints orodjarna_layout = new GridBagConstraints();
        orodjarna_layout.gridx = 0;
        orodjarna_layout.gridy = 2;
        orodjarna_layout.anchor = GridBagConstraints.CENTER;
        orodjarna.setVisible(false);
        getContentPane().add(orodjarna, orodjarna_layout);
        
        ///////////////////////////////////////////////////////////////////////////////////////////
        
        zacetni = new JPanel();
        gumbCC = new JButton("Človek - človek");
        gumbCC.addActionListener(this);
        zacetni.add(gumbCC);
        gumbCR = new JButton("Človek - računalnik");
        gumbCR.addActionListener(this);
        zacetni.add(gumbCR);
        gumbRC = new JButton("Računalnik - človek");
        gumbRC.addActionListener(this);
        zacetni.add(gumbRC);
        GridBagConstraints zacetni_layout = new GridBagConstraints();
        zacetni_layout.gridx = 0;
        zacetni_layout.gridy = 3;
        zacetni_layout.anchor = GridBagConstraints.CENTER;
        zacetni.setVisible(true);
        getContentPane().add(zacetni, zacetni_layout);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EnumMap<Zetoni, Igralec> igralca = new EnumMap<Zetoni, Igralec>(Zetoni.class);
        if (e.getSource() == igraRacunalnikClovek) {
            igralca.put(Zetoni.CRNI, new Igralec(Vrsta.RACUNALNIK, "milan"));
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.CLOVEK, "janko")); 
        } else if (e.getSource() == igraClovekRacunalnik) {
        	igralca.put(Zetoni.CRNI, new Igralec(Vrsta.CLOVEK, "janko"));
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.RACUNALNIK, "milan")); 
        } else if (e.getSource() == igraClovekClovek) {
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.CLOVEK, "marcel"));
            igralca.put(Zetoni.CRNI, new Igralec(Vrsta.CLOVEK, "ciril"));
        } else if (e.getSource() == gumbZaNovoIgro) {
        	igralca = Vodja.getIgra().getIgralca();
        }
        Vodja.ustvariNovoIgro(igralca);
        
    }

    public void osveziGUI() {
        switch (Vodja.getIgra().getStanje()) {
            case V_TEKU:
                String x = Vodja.getIgra().getNaPotezi().equals(Zetoni.BELI) ? "beli" : "črni";
                status.setText("Na potezi je " + x + ".");
                orodjarna.setVisible(false);
                break;
            case ZMAGA_BELI:
                status.setText("Zmagal je beli.");
                orodjarna.setVisible(true);
                zacetni.setVisible(true);
                break;
            case ZMAGA_CRNI:
                status.setText("Zmagal je črni.");
                orodjarna.setVisible(true);
                zacetni.setVisible(true);
                break;
            case NEODLOCENO:
                status.setText("Neodločeno.");
                orodjarna.setVisible(true);
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