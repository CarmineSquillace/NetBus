package it.nominasuntsubstantiarerum.netbus.boundary.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;

import it.nominasuntsubstantiarerum.netbus.boundary.BImpiegatoBiglietteria;
import it.nominasuntsubstantiarerum.netbus.entity.EntityImpiegato;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuImpiegatoBiglietteria extends JFrame {
	
	BImpiegatoBiglietteria bImpiegatoBiglietteria;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private EntityImpiegato sessione;
	
	/**
	 * Create the frame.
	 */
	public MenuImpiegatoBiglietteria(BImpiegatoBiglietteria boundary) {
		this.bImpiegatoBiglietteria = boundary;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setIcon(new ImageIcon(MenuImpiegatoBiglietteria.class.getResource("/it/nominasuntsubstantiarerum/netbus/res/logo.png")));
		lblNewLabel.setBounds(12, 12, 154, 54);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Emetti biglietti");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bImpiegatoBiglietteria.emettiBiglietti();
			}
		});
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(12, 112, 426, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Accedi");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sessione = bImpiegatoBiglietteria.autenticazione();
				if (sessione != null) {
					btnNewButton.setEnabled(true);
					btnNewButton_1.setEnabled(false);
					btnNewButton_1.setText("Accesso eseguito come " + sessione.getCodiceIdentificativo());
				}
			}
		});
		btnNewButton_1.setBounds(12, 78, 426, 25);
		contentPane.add(btnNewButton_1);

	}
}
