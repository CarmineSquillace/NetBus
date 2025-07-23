package it.nominasuntsubstantiarerum.netbus.boundary.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.nominasuntsubstantiarerum.netbus.boundary.BDirettoreVendite;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuDirettoreVendite extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuDirettoreVendite frame = new MenuDirettoreVendite();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuDirettoreVendite() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MenuDirettoreVendite.class.getResource("/it/nominasuntsubstantiarerum/netbus/res/logo.png")));
		lblNewLabel.setBounds(6, 6, 154, 53);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Inserisci città");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BDirettoreVendite.inserisciCitta();
			}
		});
		btnNewButton.setBounds(6, 87, 438, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Benvenuto nel Pannello Amministrativo");
		lblNewLabel_1.setBounds(191, 20, 253, 16);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Inserisci tratta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BDirettoreVendite.inserisciTratte();
			}
		});
		btnNewButton_1.setBounds(6, 118, 438, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Inserisci corsa");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BDirettoreVendite.inserisciCorse();
			}
		});
		btnNewButton_2.setBounds(6, 148, 438, 29);
		contentPane.add(btnNewButton_2);

	}
}
