package de.hawhh.ttv;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// UI template from http://java.about.com/od/creatinguserinterfaces/ss/Example-Java-Code-For-Building-A-Simple-Gui-Application.htm
public class Ui {
	
	private Logger logger = Logger.getLogger(Ui.class);

	private IGuiUpdater g;

	public void updateNodes() {
		nodesBox.removeAll();
		for (String n : g.getNodeList()) {
			nodesBox.addItem(n);
		}
	}

	String[] nodes = {};

	final JPanel comboPanel = new JPanel();
	JLabel comboLbl = new JLabel("Nodes:");
	JComboBox<String> nodesBox = new JComboBox<String>(nodes);
	DefaultListModel<String> listModel = new DefaultListModel<String>();

	public Ui(final IGuiUpdater g) {
		this.g = g;

		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Game GUI");
		guiFrame.setSize(600, 400);
		guiFrame.setLocationRelativeTo(null);

		comboPanel.add(comboLbl);
		comboPanel.add(nodesBox);

		final JPanel listPanel = new JPanel();
		listPanel.setVisible(false);

		JButton switchViewBut = new JButton("Switch view");
		JButton broadcastBut = new JButton("broadcast");

		switchViewBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				listPanel.setVisible(!listPanel.isVisible());
				comboPanel.setVisible(!comboPanel.isVisible());

			}
		});

		broadcastBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				logger.info("start game");
				g.startGame();
			}
		});
		
		guiFrame.add(comboPanel, BorderLayout.NORTH);
		guiFrame.add(listPanel, BorderLayout.CENTER);
		guiFrame.add(broadcastBut, BorderLayout.EAST);
		guiFrame.add(switchViewBut, BorderLayout.SOUTH);

		guiFrame.setVisible(true);
		updateNodes();
	}

}