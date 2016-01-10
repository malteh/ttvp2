package de.hawhh.ttv;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// UI template from http://java.about.com/od/creatinguserinterfaces/ss/Example-Java-Code-For-Building-A-Simple-Gui-Application.htm
// GUI Initialisierung

public class Ui {
	
	private Logger logger = Logger.getLogger(Ui.class);

	final JPanel comboPanel = new JPanel();
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	Starter s;
	public Ui() {
		
		
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Game GUI");
		// Hoehen und Breitenangaben der GUI
		guiFrame.setSize(500, 100);
	        //  Frameausrichtung
		guiFrame.setLayout(new GridLayout(2, 4));
		guiFrame.setLocationRelativeTo(null);
		
		// GUI Feldelemente
		final TextField locAdr = new TextField("192.168.178.30");
		final TextField adr = new TextField("192.168.178.30");
		final TextField port = new TextField("5002");
		final TextField nodes = new TextField("1");
		final Checkbox isServer = new Checkbox("is Server");
		
		// GUI Knoepfe
		JButton switchViewBut = new JButton("Start game");
		JButton broadcastBut = new JButton("Init game");

		// Startknopflogik inklusive Debugfunktionalitaet
		
		switchViewBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					s.startGame();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					System.exit(1);
				}
			}
		});

		// Broadcast Initialisierung mit dem Log "Init Game"
		broadcastBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				logger.info("init game");
				try {
					s = new Starter(adr.getText(), locAdr.getText(), Integer.parseInt(port.getText()), Integer.parseInt(nodes.getText()), isServer.getState());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					System.exit(1);
				}
			}
		});
		
		// GUI Framedefinition
		guiFrame.add(adr);
		guiFrame.add(locAdr);
		guiFrame.add(port);
		guiFrame.add(nodes);
		guiFrame.add(isServer);
		guiFrame.add(broadcastBut);
		guiFrame.add(switchViewBut);

		guiFrame.setVisible(true);
	}

}
