package de.hawhh.ttv;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

// UI template from http://java.about.com/od/creatinguserinterfaces/ss/Example-Java-Code-For-Building-A-Simple-Gui-Application.htm
public class Ui {

	private IGuiUpdater g;

	public void updateNodes() {
		nodesBox.removeAll();
		for (String n : g.getNodeList()) {
			nodesBox.addItem(n);
		}
	}

	public void updateNeighbors() {
		listModel.clear();
		for (String n : g.getNeighborList((String) nodesBox.getSelectedItem())) {
			listModel.addElement(n);
		}
	}

	String[] nodes = {};

	final JPanel comboPanel = new JPanel();
	JLabel comboLbl = new JLabel("Nodes:");
	JComboBox<String> nodesBox = new JComboBox<String>(nodes);
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList<String> neighborsList = new JList<String>(listModel);
	JLabel listLbl = new JLabel("Neighbors:");

	public Ui(IGuiUpdater g) {
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

		neighborsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		listPanel.add(listLbl);
		listPanel.add(neighborsList);

		JButton switchViewBut = new JButton("Switch view");

		switchViewBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//listLbl.setText("Neighbors: " + nodesBox.getSelectedItem());
				updateNeighbors();
				listPanel.setVisible(!listPanel.isVisible());
				comboPanel.setVisible(!comboPanel.isVisible());

			}
		});

		guiFrame.add(comboPanel, BorderLayout.NORTH);
		guiFrame.add(listPanel, BorderLayout.CENTER);
		guiFrame.add(switchViewBut, BorderLayout.SOUTH);

		guiFrame.setVisible(true);
		updateNodes();
	}

}