package it.nominasuntsubstantiarerum.netbus.boundary.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import it.nominasuntsubstantiarerum.netbus.boundary.BImpiegatoBiglietteria;
import it.nominasuntsubstantiarerum.netbus.control.GestioneVenditeBigliettiNetBus;
import it.nominasuntsubstantiarerum.netbus.entity.EntityBiglietto;
import it.nominasuntsubstantiarerum.netbus.entity.EntityCorsa;
import it.nominasuntsubstantiarerum.netbus.exception.OperationException;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class EmissioneBiglietti extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField corsaField;
	private EntityBiglietto biglietto;
	private EntityCorsa corsa;
	private JSpinner quantita;
	private JLabel preventivo;

	private void aggiornaPreventivo() {
		if (corsa != null) {
			int numeroBiglietti = (int) quantita.getValue();
			String preventivoText = String.format("€ %.2f", corsa.getCosto() * numeroBiglietti);
			preventivo.setText(preventivoText);
		} else {
			preventivo.setText("-- seleziona una corsa --");
		}
	}
	
	/**
	 * Create the frame.
	 */
	public EmissioneBiglietti(String idImpiegato) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("Corsa:");
		contentPane.add(lblNewLabel, "2, 2, right, default");
		
		corsaField = new JTextField();
		corsaField.setEditable(false);
		contentPane.add(corsaField, "4, 2, fill, default");
		corsaField.setColumns(10);
		
		JButton btnNewButton = new JButton("🔍");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				corsa = BImpiegatoBiglietteria.ricercaCorse();
				if (corsa != null) {
					corsaField.setText(String.valueOf(corsa.getIdCorsa()));
					aggiornaPreventivo();
				}
				// Se l'ID della corsa è -1, significa che non è stata selezionata alcuna corsa e non ha senso modificare il campo di testo.
			}
		});
		contentPane.add(btnNewButton, "6, 2");
		
		JLabel lblNewLabel_1 = new JLabel("Numero biglietti:");
		contentPane.add(lblNewLabel_1, "2, 4, right, default");
		
		quantita = new JSpinner();
		quantita.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				aggiornaPreventivo();
			}
		});
		quantita.setModel(new SpinnerNumberModel(1, 1, null, 1));
		contentPane.add(quantita, "4, 4, 3, 1, fill, top");
		
		JLabel lblNewLabel_2 = new JLabel("Codice identificativo:");
		contentPane.add(lblNewLabel_2, "2, 6");
		
		JLabel fieldIdImpiegato = new JLabel(idImpiegato);
		contentPane.add(fieldIdImpiegato, "4, 6, 3, 1, fill, default");
		
		JLabel lblNewLabel_4 = new JLabel("Data emissione:");
		contentPane.add(lblNewLabel_4, "2, 8, right, default");
		
		JLabel data = new JLabel("-- non generato --");
		contentPane.add(data, "4, 8, 3, 1");
		
		JButton btnNewButton_2 = new JButton("Reimposta");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				corsaField.setText("");
				quantita.setValue(1);
				data.setText("-- non generato --");
				preventivo.setText("-- seleziona una corsa --");
				biglietto = null; // Resetta il biglietto
				corsa = null; // Resetta la corsa
			}
		});
		
		JLabel lblNewLabel_3 = new JLabel("Preventivo:");
		contentPane.add(lblNewLabel_3, "2, 10, right, default");
		
		preventivo = new JLabel("-- seleziona una corsa --");
		contentPane.add(preventivo, "4, 10");
		contentPane.add(btnNewButton_2, "2, 20");
		
		JButton btnNewButton_1 = new JButton("Genera e stampa");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (corsa == null) {
					JOptionPane.showMessageDialog(EmissioneBiglietti.this, "Devi prima selezionare una corsa.", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				biglietto = new EntityBiglietto(-1, corsa.getIdCorsa(), corsa.getCosto(), new Date(), idImpiegato, null);
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Generazione e stampa.");
				int userSelection = fileChooser.showSaveDialog(EmissioneBiglietti.this);
				if (userSelection != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(EmissioneBiglietti.this, "Generazione e stampa annullata dall'utente.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				FileOutputStream outputStream;
				try {
					outputStream = new FileOutputStream(filePath);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(EmissioneBiglietti.this, "Il file non può essere creato.", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {	
					GestioneVenditeBigliettiNetBus.getInstance().emettiBiglietti(biglietto, (int) quantita.getValue(), outputStream);
					JOptionPane.showMessageDialog(EmissioneBiglietti.this, "Biglietti emessi con successo! Usa il pulsante in basso a destra per stamparli o il pulsante 'Reimposta' per generare un nuovo biglietto..", "Successo", JOptionPane.INFORMATION_MESSAGE);
					data.setText(biglietto.getDataEmissione().toString());
				} catch (OperationException e1) {
					JOptionPane.showMessageDialog(EmissioneBiglietti.this, "Errore durante l'emissione dei biglietti: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(EmissioneBiglietti.this, e1.getMessage(), "Errore parametri", JOptionPane.ERROR_MESSAGE);
				} finally {
					try {
						outputStream.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(EmissioneBiglietti.this, "Errore durante la chiusura del file.", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		contentPane.add(btnNewButton_1, "4, 20, 3, 1");

	}

}
