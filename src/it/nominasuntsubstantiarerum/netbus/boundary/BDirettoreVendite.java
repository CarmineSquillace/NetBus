package it.nominasuntsubstantiarerum.netbus.boundary;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import it.nominasuntsubstantiarerum.netbus.boundary.gui.InserisciCorse;
import it.nominasuntsubstantiarerum.netbus.control.GestioneVenditeBigliettiNetBus;
import it.nominasuntsubstantiarerum.netbus.exception.OperationException;

public class BDirettoreVendite {
	public static void inserisciTratte() {
		JTextField partenzaField = new JTextField();
		JTextField arrivoField = new JTextField();
		Object[] fields = {
			"Partenza:", partenzaField,
			"Arrivo:", arrivoField
		};
		int option = JOptionPane.showConfirmDialog(null, fields, "Inserisci Tratta", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String partenza = partenzaField.getText();
			String arrivo = arrivoField.getText();
			if (partenza.isEmpty() || arrivo.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Entrambi i campi devono essere compilati.", "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				GestioneVenditeBigliettiNetBus.getInstance().inserisciTratta(partenza, arrivo);
				JOptionPane.showMessageDialog(null, "Tratta inserita con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
			} catch (OperationException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void inserisciCitta() {
		String citta = JOptionPane.showInputDialog(null, "Nome città");
		if (citta == null) return;
		try {
			GestioneVenditeBigliettiNetBus.getInstance().inserisciCitta(citta);
			JOptionPane.showMessageDialog(null, "Città inserita con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
		} catch (OperationException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void inserisciCorse() {
		InserisciCorse inserisciCorse = new InserisciCorse();
		inserisciCorse.setVisible(true);
	}
}
