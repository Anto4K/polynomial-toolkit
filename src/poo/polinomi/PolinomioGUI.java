package poo.polinomi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class FinestraGUI extends JFrame implements ActionListener{
	private String regPolinomio = "([\\-\\+]?[\\d]*[x]?([\\^][\\d]+)?)+";//regular expression per verficare input utente

	private ArrayList<Polinomio> polinomi = new ArrayList<>(); //lista per gestire i polinomi in back-end
	private String nomeGUI= "poo.polinomi.Polinomio GUI";// titolo interfaccia grafica
	private String impl = " LL ";//indica l'implementazione scelta dall'utente
	private JTextField input;//polinomio preso da input tastiera (attraverso JtextField)
	private JLabel desc, nomeCT;//1: scritta superiore 2:nome del campo di testo
	private JPanel background, layout;//1: pannello usato per gestire i textField e i button 2: pannello utilizzato nella gestione delle checkbox  
	private JButton OK; //Button che permette all'utente di completare l'operazione di inserimento del polinomio
	private String valore; //stringa passata dall'utente per calcolare in un punto un polinomio
	private JMenu file, operazioni;//voci del menu superiore
	private JMenuBar menu;//oggetto menu per la gestione dei sottomenu
	private JMenuItem salva, carica, calcolaIn, derivata, somma, moltiplicazione, rimuovi;//menu dedicati alle diverse operazioni
	private JFrame risultato;//finestra dedicata alla visualizzazione del risultato per il metodo valuta
	private double punto, ris; //punto in cui calcolare il polinomio
	private File fileDiSalvataggio;//file per salvare i polinomi
	
	public FinestraGUI() {
		setTitle(nomeGUI+impl);
		setSize(800,600);
		setLocation(50,200);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(exitOK()) System.exit(0);
			}
		});
		
		FinestraSceltaTipo fst = new FinestraSceltaTipo();
		fst.setVisible(true);
		
		menu = new JMenuBar();
		file = new JMenu("File");
		//sottomenu di file
			//voce salva
			salva = new JMenuItem("Salva su file");
			salva.addActionListener(this);
			file.add(salva);
			//voce carica
			carica = new JMenuItem("Carica da file");
			carica.addActionListener(this);
			file.add(carica);
		operazioni = new JMenu("Operazioni");
		//sottomenu di operazioni
			//voce calcolaIn
			calcolaIn = new JMenuItem("Calcola in un punto");
			calcolaIn.addActionListener(this);
			operazioni.add(calcolaIn);
			//voce derivata
			derivata = new JMenuItem("Calcola derivata");
			derivata.addActionListener(this);
			operazioni.add(derivata);
			//voce somma
			somma = new JMenuItem("Somma");
			somma.addActionListener(this);
			operazioni.add(somma);
			//voce moltiplicazione
			moltiplicazione = new JMenuItem("Moltiplicazione");
			moltiplicazione.addActionListener(this);
			operazioni.add(moltiplicazione);
			//voce rimuovi
			rimuovi = new JMenuItem("Rimuovi");
			rimuovi.addActionListener(this);
			operazioni.add(rimuovi);
			
		menu.add(file);
		menu.add(operazioni);
		this.setJMenuBar(menu);
		
		desc = new JLabel("POLINOMI");
		desc.setFont(new Font("Helvetica", Font.PLAIN,20));
		desc.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(desc, BorderLayout.NORTH);
		
		nomeCT = new JLabel("Inserire qui un polinomio>> ");
		input = new JTextField(25); input.addActionListener(this);
		OK = new JButton("INSERISCI"); OK.addActionListener(this);
		
		background = new JPanel();
		background.add(nomeCT); background.add(input); background.add(OK); background.setBackground(Color.WHITE);
		this.add(background, BorderLayout.SOUTH);
		
		layout = new JPanel(new GridLayout(7,7,2,2)); 
		this.add(layout);
		
		inizializza();	
	}//poo.polinomi.FinestraGUI (costruttore)
	
	private class FinestraSceltaTipo extends JFrame implements ActionListener{
		private JLabel etichetta;
		private ButtonGroup gruppo;
		private JPanel pannello;
		private JRadioButton bottoneAL, bottoneLL, bottoneSet, bottoneMap, bottoneLC;
		
		public FinestraSceltaTipo() {
			setTitle("poo.polinomi.Polinomio GUI");
			setLocation(100,200);
			setSize(500,175);
			setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );		
			
			 addWindowListener( new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					FinestraGUI.this.setTitle(nomeGUI+impl);
					FinestraGUI.this.setVisible(true);
	 				dispose();	
				}
			 }); //addWindowListener
	       
			etichetta = new JLabel("Scelta Tipo implementazione del poo.polinomi.Polinomio");
			etichetta.setFont(new Font("Helvetica", Font.PLAIN, 24));
			etichetta.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(etichetta, BorderLayout.CENTER);
			
			pannello=new JPanel();
			gruppo=new ButtonGroup();
			bottoneAL = new JRadioButton("ArrayList", false); bottoneAL.addActionListener(this);
			bottoneLL = new JRadioButton("LinkedList", true); bottoneLL.addActionListener(this);
			bottoneSet = new JRadioButton("Set", false); bottoneSet.addActionListener(this);
			bottoneMap = new JRadioButton("Map", false); bottoneMap.addActionListener(this);
			//bottoneLC = new JRadioButton("ListaConcatenata", false); bottoneLC.addActionListener(this);
			gruppo.add(bottoneAL); gruppo.add(bottoneLL); gruppo.add(bottoneSet);
			gruppo.add(bottoneMap); gruppo.add(bottoneLC);
			pannello.add(bottoneAL); pannello.add(bottoneLL); pannello.add(bottoneSet);
			pannello.add(bottoneMap);
			//pannello.add(bottoneLC);
			this.add(pannello, BorderLayout.SOUTH);
		}//FinestraSceltaTipo
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == bottoneAL) {
				impl = " AL ";
				FinestraGUI.this.setTitle(nomeGUI+impl);
			}
			else if(e.getSource() == bottoneLL) {
				impl = " LL ";
				FinestraGUI.this.setTitle(nomeGUI+impl);
			}
			else if(e.getSource() == bottoneSet) {
				impl = " Set ";
				FinestraGUI.this.setTitle(nomeGUI+impl);
			}
			else if(e.getSource() == bottoneMap) {
				impl = " Map ";
				FinestraGUI.this.setTitle(nomeGUI+impl);
			}
			else {
				impl = " LC ";
				FinestraGUI.this.setTitle(nomeGUI+impl);
			}
		}//actionPerformed	
		
	}//FinestraSceltaTipo CLASS
	
	@Override
	public void actionPerformed(ActionEvent e) {
		aggiorna();
		if(e.getSource() == OK) generaPol();
		if(e.getSource() == rimuovi) rimuovi();
		if(e.getSource() == moltiplicazione) moltiplica();
		if(e.getSource() == somma) somma();
		if(e.getSource() == derivata) derivata();
		if(e.getSource() == calcolaIn) new FrameValuta();
		if(e.getSource() == salva) frameSalva();
		if(e.getSource() == carica) {
			try {
				open();
			}catch(IOException IO) {
				JFrame erroreIO = new FrameErrore("Apertura file fallita !!");
				erroreIO.setVisible(true);
			}
		}
	}//actionPerformed
	
	private void frameSalva() {
		JFileChooser chooser = new JFileChooser();
		try {
			if(fileDiSalvataggio != null) {
				int ans = JOptionPane.showConfirmDialog(null, "Sovrascrivere "+fileDiSalvataggio.getAbsolutePath()+" ?");
				if(ans == JOptionPane.YES_OPTION)
					salva();
				else
					JOptionPane.showMessageDialog(null, "Nessun salvataggio!");
			}
			if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				fileDiSalvataggio = chooser.getSelectedFile();
			}
			if(fileDiSalvataggio != null) {
				salva();
			}
			else
				JOptionPane.showMessageDialog(null, "Nessun salvataggio!");
		}
		catch(Exception exc) {
			JFrame errore = new FrameErrore("File inesistente");
			errore.setVisible(true);
		}
	}//frameSalva
	
	private void salva() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(fileDiSalvataggio));
		for(Polinomio p : polinomi) {
			pw.println(p);
		}
		pw.close();
	}//salva
	
	private void open() throws IOException {
		File file = null;
		JFileChooser chooser = new JFileChooser();
		int ok = chooser.showOpenDialog(this);
		try {
			if(layout.getComponentCount() >= 49) throw new IndexOutOfBoundsException();
			if(ok == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				BufferedReader bf = new BufferedReader(new FileReader(file));
				for(;;) {
					String linea = bf.readLine();
					if(linea == null) break;
					if(!linea.matches(regPolinomio)) throw new IllegalArgumentException();
					Polinomio p = parsePolinomio(linea); polinomi.add(p);
					JCheckBox box = new JCheckBox(p.toString());
					box.addActionListener(this); layout.add(box);
					this.aggiorna();
				}
				bf.close();
			}
		}
		catch(FileNotFoundException e){
			JFrame errore = new FrameErrore("File inesistente");
			errore.setVisible(true);
		}
		catch(IllegalArgumentException e) {
			JFrame errore = new FrameErrore("Il file contiene oggetti diversi dai polinomi");
			errore.setVisible(true);
		}
		catch(IndexOutOfBoundsException e) {
			JFrame errore = new FrameErrore("Spazio esaurito. Rimuovere qualche polinomio");
			errore.setVisible(true);
		}
	}//open
	
	

	private void derivata() {
		Polinomio p = null;
		for(int i=0; i < layout.getComponentCount(); ++i) {
			JCheckBox box = (JCheckBox) layout.getComponent(i);
			if(box.isSelected()) {
				p = polinomi.get(i).derivata();
				if(p.size() == 0) {
					JFrame errore = new FrameErrore("Derivata pari a 0");
					errore.setVisible(true);
				}
				else {
					JCheckBox newBox = new JCheckBox(p.toString());
					newBox.addActionListener(this); layout.add(newBox);
					polinomi.add(p);
				}
				break;
			}
		}
		this.aggiorna();
	}//derivata

	private void somma() {
		Polinomio p1 = null;
		Polinomio p2 = null;
		boolean salvato = false;
		int i = 0;
		while(i < layout.getComponentCount()) {
			JCheckBox box = (JCheckBox) layout.getComponent(i);
			if(box.isSelected() && !salvato) {
				p1 = polinomi.get(i);
				salvato = true;
			}
			else if(box.isSelected()) {
				p2 = polinomi.get(i);
				break;
			}
			i++;
		}
		Polinomio res= p1.add(p2);
		JCheckBox newBox = new JCheckBox(res.toString());
		newBox.addActionListener(this); layout.add(newBox);
		polinomi.add(res);
		this.aggiorna();
	}//somma

	private void moltiplica() {
		Polinomio p1 = null;
		Polinomio p2 = null;
		boolean salvato = false;
		int i= 0;
		while(i < layout.getComponentCount()) {
			JCheckBox box =(JCheckBox) layout.getComponent(i);
			if(box.isSelected() && !salvato) {
				p1 = polinomi.get(i);
				salvato = true;
			}
			else if(box.isSelected()) {
				p2 = polinomi.get(i);
				break;
			}
			i++;
		}
		Polinomio res = p1.mul(p2);
		JCheckBox newBox = new JCheckBox(res.toString());
		newBox.addActionListener(this); layout.add(newBox);
		polinomi.add(res);
		this.aggiorna();
	}//moltiplica

	private void rimuovi() {
		int elem = layout.getComponentCount();
		int i = 0;
		while(i <elem) {
			JCheckBox box = (JCheckBox) layout.getComponent(i); 
			if(box.isSelected()) {
				polinomi.remove(i);
				layout.remove(box);
				elem--;
			}
			else i++;
		}
		this.aggiorna();
	}//rimuovi

	private void generaPol() {
		Polinomio po = null;
		JCheckBox contenitore = null;
		JFrame errore = null;
		String lettura = input.getText();
		try {
			if(layout.getComponentCount() >= 49) throw new IndexOutOfBoundsException();
			if(lettura.equals("")) throw new NoSuchElementException();
			if(!lettura.matches(regPolinomio) || lettura.equals("0")) throw new IllegalArgumentException();
			po = parsePolinomio(lettura); polinomi.add(po); 
			contenitore = new JCheckBox(po.toString()); contenitore.addActionListener(this);
			layout.add(contenitore); input.setText(""); this.aggiorna();
		}
		catch(IndexOutOfBoundsException e) {
			errore = new FrameErrore("Spazio esaurito. Rimuovere qualche polinomio");
			errore.setVisible(true);
		}
		catch(NoSuchElementException e) {
			errore = new FrameErrore("Non hai inserito nulla! Digitare un polinomio!");
			errore.setVisible(true);
		}
		catch(IllegalArgumentException e) {
			errore = new FrameErrore("poo.polinomi.Polinomio non corretto. Riscrivere il polinomio!");
			errore.setVisible(true);
		}
	}//generaPol
	
	private Polinomio parsePolinomio(String input) {
		Polinomio p = null;
		if(impl.equals(" AL "))  p = new PolinomioAL();
		if(impl.equals(" LL "))  p = new PolinomioLL(); 
		if(impl.equals(" Set ")) p = new PolinomioSet();
		if(impl.equals(" Map ")) p = new PolinomioMap();
		//if(impl.equals(" LC "))  p = new PolinomioLC();
		StringTokenizer st=new StringTokenizer(input,"+-",true);
		p.add(InputPolinomio.primoMonomio(st) );
		while( st.hasMoreTokens() ) {
			p.add( InputPolinomio.prossimoMonomio(st) );
		}
		return p;
	}//parsePolinomio;
	
	private class FrameValuta extends JFrame implements ActionListener{
		private JTextField cDiTesto;
		private JButton valuta;
		private JPanel sfondo;
		
		public FrameValuta() {
			this.setTitle("Valuta");
			this.setSize(400,100);
			this.setLocation(550,350);
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			sfondo = new JPanel(); sfondo.setBackground(Color.WHITE);
			JLabel m = new JLabel("Inserire un valore numerico per x");
			m.setFont(new Font("Helvetica", Font.PLAIN, 15));
			m.setForeground(Color.BLACK);
			m.setHorizontalAlignment(SwingConstants.CENTER);
			sfondo.add(m, BorderLayout.CENTER);
			cDiTesto = new JTextField(25); cDiTesto.addActionListener(this);
			sfondo.add(cDiTesto, BorderLayout.EAST);
			valuta = new JButton("VALUTA"); valuta.addActionListener(this);
			sfondo.add(valuta,BorderLayout.SOUTH);
			this.add(sfondo);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					setVisible(false);} 
				});
		}//FrameValuta

		private void calcolaIn() {
		for(int i= 0; i<layout.getComponentCount(); ++i) {
			JCheckBox box = (JCheckBox) layout.getComponent(i);
			if(box.isSelected()) {
				ris = polinomi.get(i).valore(punto);
			}
		}	
		risultato = new FrameRisultato(String.format("%1.2f", ris));
		risultato.setVisible(true);
		}//calcolaIn
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == valuta) {
				try{
					valore = cDiTesto.getText();
					punto = Double.parseDouble(valore);
					this.setVisible(false);
					calcolaIn();
				}
				catch (NumberFormatException exp) {
					this.setVisible(false);
					JFrame errore = new FrameErrore("Inserire un valore numerico!");
					errore.setVisible(true);
				}
			}
		}//actionPerfomed
		
	}//FrameValuta
	
	private class FrameRisultato extends JFrame{
		
		public FrameRisultato(String msg) {
			this.setTitle("Risultato");
			this.setSize(400,100);
			this.setLocationByPlatform(true);
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			JLabel ris = new JLabel("Il risultato �: " + msg);
			ris.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(ris, BorderLayout.CENTER);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					setVisible(false);} 
				});
		}//FrameRisultato
		
	}//FrameRisultato
	
	private class FrameErrore extends JFrame{
		public FrameErrore(String messaggio) {
			this.setTitle("ERRORE!!");
			this.setSize(400,100);
			this.setLocation(400,200);
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			JLabel message = new JLabel(messaggio);
			message.setFont(new Font("Helvetica", Font.PLAIN,18));
			message.setForeground(Color.RED);
			message.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(message, BorderLayout.CENTER);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}
			});
		}
	}//FrameErrore
	
	private boolean exitOK() {
		int val = JOptionPane.showConfirmDialog(null, "Vuoi davvere chiuedere?", "Perderai tutti i dati!",JOptionPane.YES_NO_OPTION);
		return val == JOptionPane.YES_NO_OPTION;
	}//exitOK
	
	private void menuMolteplici() {
		salva.setEnabled(true);
		calcolaIn.setEnabled(false);
		derivata.setEnabled(false);
		somma.setEnabled(false);
		moltiplicazione.setEnabled(false);
		rimuovi.setEnabled(true);
	}//menuMolteplici
	
	private void menuDoppio() {
		salva.setEnabled(true);
		calcolaIn.setEnabled(false);
		derivata.setEnabled(false);
		somma.setEnabled(true);
		moltiplicazione.setEnabled(true);
		rimuovi.setEnabled(true);
	}//menuDoppio
	
	private void menuSingolo() {
		salva.setEnabled(true);
		calcolaIn.setEnabled(true);
		derivata.setEnabled(true);
		somma.setEnabled(false);
		moltiplicazione.setEnabled(false);
		rimuovi.setEnabled(true);
	}//menuSingolo
	
	private void inizializza() {
		salva.setEnabled(true);
		calcolaIn.setEnabled(false);
		derivata.setEnabled(false);
		somma.setEnabled(false);
		moltiplicazione.setEnabled(false);
		rimuovi.setEnabled(false);
	}//iniziallizza
	
	private int contaElementi() {
		int c = 0;
		int i = 0;
		while(i < layout.getComponentCount()) {
			JCheckBox box = (JCheckBox) layout.getComponent(i);
			if(box.isSelected())
				c++;
			i++;
		}
		return c;
	}//contaElementi
	
	private void aggiorna() {
		if(contaElementi() == 0) 
				inizializza();
		else if(contaElementi() == 1) 
				menuSingolo();
		else if(contaElementi() == 2) 
				menuDoppio();
		else menuMolteplici();
		revalidate();
		repaint();
	}//aggiorna

}//poo.polinomi.FinestraGUI


public class PolinomioGUI {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run() {
				FinestraGUI window = new FinestraGUI();
			}
		});
	}//main
}//poo.polinomi.PolinomioGUI
