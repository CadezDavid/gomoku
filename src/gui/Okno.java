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
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

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
    
    private JMenuItem velikostIgre;
    
    private JPanel orodjarna;
    private JPanel zacetni;
    private JButton gumbZaNovoIgro;
    private JLabel status;
	private JMenu velikost;
 

    
    public Okno(int sirina, int visina) {
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
        
       
        JMenu igra_nastavitve = new JMenu("Nastavitve");
        menu_bar.add(igra_nastavitve);
        
        velikostIgre = new JMenuItem("Velikost igralnega polja");
        igra_nastavitve.add(velikostIgre);
        velikostIgre.addActionListener(this);
        
        velikost = new JMenu("Velikost");
		igra_menu.add(velikost);		
		
		JSlider izberiVelikost = new JSlider(5, 20, 15);
		velikost.add(izberiVelikost);
		izberiVelikost.addChangeListener((ChangeListener) this);
		
		izberiVelikost.setPaintTicks(true);
		izberiVelikost.setMajorTickSpacing(1);
		izberiVelikost.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> labelTableVelikost = new Hashtable<Integer, JLabel>();
		labelTableVelikost.put(7, new JLabel("7"));
		labelTableVelikost.put(10, new JLabel("10"));
		labelTableVelikost.put(12, new JLabel("12"));
		labelTableVelikost.put(15, new JLabel("15"));
		labelTableVelikost.put(17, new JLabel("17"));
		labelTableVelikost.put(20, new JLabel("20"));
		labelTableVelikost.put(25, new JLabel("25"));
		izberiVelikost.setLabelTable(labelTableVelikost);
		izberiVelikost.setPaintLabels(true);
        menu_bar.add(velikost);
		
		// platno
        platno = new Platno(12);
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
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EnumMap<Zetoni, Igralec> igralca = new EnumMap<Zetoni, Igralec>(Zetoni.class);
        if (e.getSource() == velikostIgre) {
        	
        }
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
        } else if (e.getSource() == izberiVelikost) {
        	///////
        }
        Vodja.ustvariNovoIgro(igralca, velikost.);
        
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
