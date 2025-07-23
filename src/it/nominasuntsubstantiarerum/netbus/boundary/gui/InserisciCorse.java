package it.nominasuntsubstantiarerum.netbus.boundary.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import it.nominasuntsubstantiarerum.netbus.control.GestioneVenditeBigliettiNetBus;
import it.nominasuntsubstantiarerum.netbus.exception.OperationException;
import it.nominasuntsubstantiarerum.netbus.util.Time;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;

public class InserisciCorse extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField partenza;
	private JTextField arrivo;
	private JTextField oraPartenza;
	private JTextField oraArrivo;
	private JTextField data;
	private JSpinner costo;
	private JSpinner autobus;

	/**
	 * Create the dialog.
	 */
	public InserisciCorse() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("Partenza");
			contentPanel.add(lblNewLabel, "2, 2");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Destinazione");
			contentPanel.add(lblNewLabel_1, "4, 2");
		}
		{
			partenza = new JTextField();
			contentPanel.add(partenza, "2, 4, fill, default");
			partenza.setColumns(10);
		}
		{
			arrivo = new JTextField();
			contentPanel.add(arrivo, "4, 4, fill, default");
			arrivo.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Orario di partenza");
			contentPanel.add(lblNewLabel_3, "2, 6");
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Orario di arrivo");
			contentPanel.add(lblNewLabel_4, "4, 6");
		}
		{
			oraPartenza = new JTextField();
			contentPanel.add(oraPartenza, "2, 8, fill, default");
			oraPartenza.setColumns(10);
		}
		{
			oraArrivo = new JTextField();
			contentPanel.add(oraArrivo, "4, 8, fill, default");
			oraArrivo.setColumns(10);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("Costo del biglietto");
			contentPanel.add(lblNewLabel_5, "2, 10");
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Data");
			contentPanel.add(lblNewLabel_2, "4, 10");
		}
		{
			costo = new JSpinner();
			costo.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 0.1f));
			contentPanel.add(costo, "2, 12");
		}
		{
			data = new JTextField();
			contentPanel.add(data, "4, 12, fill, default");
			data.setColumns(10);
		}
		{
			JLabel lblNewLabel_6 = new JLabel("ID Autobus assegnato:");
			contentPanel.add(lblNewLabel_6, "2, 14");
		}
		{
			autobus = new JSpinner();
			contentPanel.add(autobus, "4, 14");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aggiungi");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Logica per aggiungere la corsa
						String partenzaText = partenza.getText();
						String arrivoText = arrivo.getText();
						String dataText = data.getText();
						String oraText = oraPartenza.getText();
						String oraArrivoText = oraArrivo.getText();
						float costoValue = (float) costo.getValue();
						int autobusValue = (int) autobus.getValue();
						try {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							LocalDate dataCorsa = LocalDate.parse(dataText, formatter);
							Time orarioPartenza = new Time(DateFormat.getTimeInstance().parse(oraText).getTime());
							Time orarioArrivo = new Time(DateFormat.getTimeInstance().parse(oraArrivoText).getTime());
							// Chiamata al metodo per inserire la corsa
							GestioneVenditeBigliettiNetBus.getInstance().inserisciCorsa(partenzaText, arrivoText, dataCorsa, orarioPartenza, orarioArrivo, costoValue, autobusValue);
							JOptionPane.showMessageDialog(InserisciCorse.this, "Corsa inserita con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
						} catch (OperationException ex) {
							JOptionPane.showMessageDialog(InserisciCorse.this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						} catch (DateTimeParseException | ParseException e1) {
							JOptionPane.showMessageDialog(InserisciCorse.this, "Formato data/ora non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							JOptionPane.showMessageDialog(InserisciCorse.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Esci");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
