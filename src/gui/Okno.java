package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logika.Igralec;
import logika.Vrsta;
import logika.Zetoni;
import vodja.Vodja;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener, ChangeListener {

    private Platno platno;

    private JMenuBar menu_bar;
    
    private JMenu igra_menu;
    private JMenuItem igraClovekClovek;
    private JMenuItem igraRacunalnikClovek;
    private JMenuItem igraClovekRacunalnik;
    
    private JMenu barve_menu;
    private JMenuItem barva_crnega;
    private JMenuItem barva_belega;
    private JMenuItem barva_ozadja;
    
    private JMenu imena;
    private JMenuItem imeC;
    private JMenuItem imeB;
    
    private JPanel orodjarna;
    private JButton gumbZaNovoIgro;
    private JLabel status;
    private JMenu velikost;
    private JSlider izberiVelikost;
    private int v = 15;
    
    private Color crn = Color.BLACK;
    private Color bel = Color.WHITE;
    private Color ozadje = new Color(255, 218, 179);
    
    private String imeCrnega = "črni";
    private String imeBelega = "beli"; 

    public Okno(int sirina, int visina) {
        this.setTitle("Gomoku");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        // menu
        menu_bar = new JMenuBar();
        this.setJMenuBar(menu_bar);
        igra_menu = new JMenu("Nova igra");
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
       

        velikost = new JMenu("Velikost");
        igra_menu.add(velikost);

        izberiVelikost = new JSlider(5, 20, 15);
        velikost.add(izberiVelikost);
        izberiVelikost.addChangeListener(this);

        izberiVelikost.setPaintTicks(true);
        izberiVelikost.setMajorTickSpacing(1);
        izberiVelikost.setPaintTicks(true);

        Hashtable<Integer, JLabel> labelTableVelikost = new Hashtable<Integer, JLabel>();
        labelTableVelikost.put(6, new JLabel("6"));
        labelTableVelikost.put(9, new JLabel("9"));
        labelTableVelikost.put(12, new JLabel("12"));
        labelTableVelikost.put(15, new JLabel("15"));
        labelTableVelikost.put(18, new JLabel("18"));
        labelTableVelikost.put(21, new JLabel("21"));
        labelTableVelikost.put(24, new JLabel("24"));
        izberiVelikost.setLabelTable(labelTableVelikost);
        izberiVelikost.setPaintLabels(true);
        menu_bar.add(velikost);
        
        barve_menu = new JMenu("Barve žetonov in ozadja");
        menu_bar.add(barve_menu);
        
        barva_crnega = new JMenuItem("Barva črnih žetonov");
        barve_menu.add(barva_crnega);
        barva_crnega.addActionListener(this);
        
        barva_belega = new JMenuItem("Barva belih žetonov");
        barve_menu.add(barva_belega);
        barva_belega.addActionListener(this);
        
        barva_ozadja = new JMenuItem("Barva ozadja");
        barve_menu.add(barva_ozadja);
        barva_ozadja.addActionListener(this);

        imena = new JMenu("Imena igralcev");
        menu_bar.add(imena);
        
        imeC = new JMenuItem ("Ime črnega igracla");
        imena.add(imeC);
        imeC.addActionListener(this);
        
        imeB = new JMenuItem("Ime belega igralca");
        imena.add(imeB);
        imeB.addActionListener(this);
        
        // platno
        setPlatno(new Platno(v, crn, bel, ozadje));


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

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EnumMap<Zetoni, Igralec> igralca = new EnumMap<Zetoni, Igralec>(Zetoni.class);
        if (e.getSource() == igraRacunalnikClovek) {
            igralca.put(Zetoni.CRNI, new Igralec(Vrsta.RACUNALNIK, "milan"));
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.CLOVEK, "janko"));
            menu_bar.remove(velikost);
            Vodja.ustvariNovoIgro(igralca, v);
            platno.setVelikost(v);
        } else if (e.getSource() == igraClovekRacunalnik) {
            igralca.put(Zetoni.CRNI, new Igralec(Vrsta.CLOVEK, "janko"));
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.RACUNALNIK, "milan"));
            menu_bar.remove(velikost);
            Vodja.ustvariNovoIgro(igralca, v);
            platno.setVelikost(v);
        } else if (e.getSource() == igraClovekClovek) {
            igralca.put(Zetoni.BELI, new Igralec(Vrsta.CLOVEK, "marcel"));
            igralca.put(Zetoni.CRNI, new Igralec(Vrsta.CLOVEK, "ciril"));
            menu_bar.remove(velikost);
            Vodja.ustvariNovoIgro(igralca, v);
            platno.setVelikost(v);
        } else if (e.getSource() == gumbZaNovoIgro) {
            menu_bar.remove(velikost);
            Vodja.ustvariNovoIgro(Vodja.getIgra().getIgralca(), v);
            platno.setVelikost(v);
        } else if (e.getSource() == barva_crnega) {
        	Color barva = JColorChooser.showDialog(this, "Izberite barvo črnih žetonov", crn);
			if (barva != null)  {
				crn = barva;
				platno.setCrn(crn);
			}
        } else if (e.getSource() == barva_belega) {
        	Color barva = JColorChooser.showDialog(this, "Izberite barvo belih žetonov", bel);
			if (barva != null)  {
				bel = barva;
				platno.setBel(bel);
			} 
        } else if (e.getSource() == barva_ozadja) {
        	Color barva = JColorChooser.showDialog(this, "Izberite barvo ozadja", ozadje);
			if (barva != null)  {
				ozadje = barva;
				platno.setBackground(barva);
			}
        } else if (e.getSource() == imeC) {
        	String imeC = JOptionPane.showInputDialog("Vnesite ime črnega igralca:");
        	if (imeC != null) imeCrnega = imeC;
		} else if (e.getSource() == imeB) {
			String imeB = JOptionPane.showInputDialog("Vnesite ime belega igralca");
			if (imeB != null) imeBelega = imeB;
		}
     
        }

    

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        setV((int) source.getValue());
    }

    public void osveziGUI() {
        switch (Vodja.getIgra().getStanje()) {
            case V_TEKU:
                String x = Vodja.getIgra().getNaPotezi().equals(Zetoni.BELI) ? imeBelega : imeCrnega;
                status.setText("Na potezi je " + x + ".");
                orodjarna.setVisible(false);
                break;
            case ZMAGA_BELI:
                status.setText("Zmagal je " + imeBelega + ".");
                orodjarna.setVisible(true);
                menu_bar.add(velikost);
                break;
            case ZMAGA_CRNI:
                status.setText("Zmagal je " + imeCrnega + ".");
                orodjarna.setVisible(true);
                menu_bar.add(velikost);
                break;
            case NEODLOCENO:
                status.setText("Neodločeno.");
                orodjarna.setVisible(true);
                menu_bar.add(velikost);
                break;
        }
        platno.repaint();
    }

    public Platno getPlatno() {
        return platno;
    }

    public void setPlatno(Platno p) {
        platno = p;
        platno.setVisible(true);
        platno.repaint();
        GridBagConstraints platno_layout = new GridBagConstraints();
        platno_layout.gridx = 0;
        platno_layout.gridy = 0;
        platno_layout.fill = GridBagConstraints.BOTH;
        platno_layout.weightx = 1.0;
        platno_layout.weighty = 1.0;
        platno_layout.anchor = GridBagConstraints.CENTER;
        getContentPane().add(platno, platno_layout);
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

    public JMenuItem getIgraClovekRacunalnik() {
        return igraClovekRacunalnik;
    }

    public void setIgraClovekRacunalnik(JMenuItem igraClovekRacunalnik) {
        this.igraClovekRacunalnik = igraClovekRacunalnik;
    }

    public JMenuBar getMenu_bar() {
        return menu_bar;
    }

    public void setMenu_bar(JMenuBar menu_bar) {
        this.menu_bar = menu_bar;
    }

    public JMenu getIgra_menu() {
        return igra_menu;
    }

    public void setIgra_menu(JMenu igra_menu) {
        this.igra_menu = igra_menu;
    }

    public JPanel getOrodjarna() {
        return orodjarna;
    }

    public void setOrodjarna(JPanel orodjarna) {
        this.orodjarna = orodjarna;
    }

    public JButton getGumbZaNovoIgro() {
        return gumbZaNovoIgro;
    }

    public void setGumbZaNovoIgro(JButton gumbZaNovoIgro) {
        this.gumbZaNovoIgro = gumbZaNovoIgro;
    }

    public JMenu getVelikost() {
        return velikost;
    }

    public void setVelikost(JMenu velikost) {
        this.velikost = velikost;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

}
