package it.nominasuntsubstantiarerum.netbus.boundary.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import it.nominasuntsubstantiarerum.netbus.control.GestioneVenditeBigliettiNetBus;
import it.nominasuntsubstantiarerum.netbus.entity.EntityCorsa;
import it.nominasuntsubstantiarerum.netbus.exception.OperationException;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class RicercaCorse extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private EntityCorsa corsa;
	private JTextField part;
	private JTextField dest;
	private JButton okButton;
	private JTable table;
	
	private JTextField date;
	private List<EntityCorsa> corse; // Array per memorizzare le corse trovate

	
	private void submitSearch() {
		if (!(part.getText().isEmpty() || dest.getText().isEmpty() || date.getText().isEmpty() || !date.getText().matches("\\d{2}\\/\\d{2}\\/\\d{4}"))) {
			// Simulate a search operation
			LocalDate d = null;
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		        d = LocalDate.parse(date.getText(), formatter);
			} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(this, "Inserisci una data valida (dd/mm/yyyy).", "Errore", JOptionPane.ERROR_MESSAGE);
				date.setText("");
				return;
			}
			okButton.setEnabled(false); // Disable OK button
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			corse = List.of(); // Reset the array
			model.setRowCount(0); // Clear previous results
			try {
				corse = GestioneVenditeBigliettiNetBus.getInstance().ricercaCorse(part.getText(), dest.getText(), d);
				if (corse.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Nessuna corsa trovata.", "Attenzione", JOptionPane.WARNING_MESSAGE);
					return;
				}
				for (EntityCorsa corsa : corse)
					model.addRow(new Object[] {
						corsa.getIdCorsa(),
						corsa.getTratta(),
						corsa.getOrarioDiPartenza(),
						corsa.getOrarioDiArrivo()
					});
			} catch (OperationException e) {
				JOptionPane.showMessageDialog(this, "Errore durante la ricerca delle corse.", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public RicercaCorse() {
		setModal(true);
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
				RowSpec.decode("default:grow"),}));
		{
			JLabel lblNewLabel = new JLabel("Partenza");
			contentPanel.add(lblNewLabel, "2, 2");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Destinazione");
			contentPanel.add(lblNewLabel_1, "4, 2");
		}
		{
			part = new JTextField();
			part.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (part.getText().isEmpty()) {
						JOptionPane.showMessageDialog(RicercaCorse.this, "Inserisci una partenza.", "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (part.getText().length() > 100) {
						JOptionPane.showMessageDialog(RicercaCorse.this, "Nome troppo lungo.", "Errore", JOptionPane.ERROR_MESSAGE);
						part.setText("");
						return;
					}
					if (!part.getText().matches("[a-zA-ZàèìòùÀÈÌÒÙ\\s-]+")) {
						JOptionPane.showMessageDialog(RicercaCorse.this, "La partenza contiene simboli non validi.", "Errore", JOptionPane.ERROR_MESSAGE);
						part.setText("");
						return;
					}
					submitSearch();
				}
			});
			contentPanel.add(part, "2, 4, fill, default");
			part.setColumns(10);
		}
		{
			dest = new JTextField();
			dest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (dest.getText().isEmpty()) {
						JOptionPane.showMessageDialog(RicercaCorse.this, "Inserisci una destinazione.", "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (dest.getText().length() > 100) {
						JOptionPane.showMessageDialog(RicercaCorse.this, "Nome troppo lungo.", "Errore", JOptionPane.ERROR_MESSAGE);
						dest.setText("");
						return;
					}
					if (!dest.getText().matches("[a-zA-ZàèìòùÀÈÌÒÙ\\s-]+")) {
						JOptionPane.showMessageDialog(RicercaCorse.this, "La destinazione contiene simboli non validi.", "Errore", JOptionPane.ERROR_MESSAGE);
						dest.setText("");
						return;
					}
					submitSearch();
				}
			});
			contentPanel.add(dest, "4, 4, fill, default");
			dest.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Data");
			contentPanel.add(lblNewLabel_2, "2, 6, right, default");
		}
		{
			date = new JTextField();
			date.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (date.getText().isEmpty() || !date.getText().matches("\\d{2}\\/\\d{2}\\/\\d{4}")) {
						JOptionPane.showMessageDialog(RicercaCorse.this, "Inserisci una data valida (dd/mm/yyyy).", "Errore", JOptionPane.ERROR_MESSAGE);
						date.setText("");
					} else {
						submitSearch();
					}
				}
			});
			contentPanel.add(date, "4, 6, fill, default");
			date.setColumns(10);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "2, 8, 3, 1, fill, fill");
			{
				table = new JTable();
				scrollPane.setViewportView(table);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.getColumnModel().getSelectionModel().addListSelectionListener(e -> {
					okButton.setEnabled(e.getFirstIndex() >= 0);
				});
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"ID Corsa", "ID Tratta" ,"Orario di partenza", "Orario di arrivo"
					}
				) {
					Class[] columnTypes = new Class[] {
						Integer.class, Integer.class, String.class, String.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (table.getSelectedRow() == -1) {
							corsa = null;
						} else {
							corsa = corse.get(table.getSelectedRow());
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						corsa = null; // Set to -1 to indicate no selection
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public EntityCorsa getCorsa() {
		return corsa;
	}

}
