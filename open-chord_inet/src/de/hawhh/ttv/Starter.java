package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import de.hawhh.ttv.Strategy.Plan;

public class Starter {

	// Deklaration des Loggings fuer Console und File history.log
	private static Logger logger = Logger.getLogger(Starter.class);
	
	// Initialisierung des gesamten Spiels
	public static void main(String[] args) {
		
		logger.info("Starting ...");
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		
		// GUI Initialisierung  
		new Ui();
	}

	Map<String, ChordNode> nodes = new HashMap<String, ChordNode>();

	// Initialisierung der GUI Paramter
    // Server IP - serverHostname
	// Lokale IP - locAdr
	// Port - serverPort
	// Wieviele Gegner - nodesToStart
	// GUI Checkbox selbstdeklarierter Server - startServer
	
	public Starter(String serverHostname, String locAdr, int serverPort, int nodesToStart, Boolean startServer) {
		// Besonderheiten bei der Ausfuerhrung als Server
		if (startServer) {
			logger.info("starting server node");
			// Serverdeklaration bestehend aus Port und lokaler Adresse
			ChordNode server = new ChordNode(serverPort, locAdr);
			nodes.put(server.Id(), server);
		}
		logger.info("starting " + nodesToStart + " nodes");
		// Normale Ausfuehrung
		for (int i = 1; i <= nodesToStart; i++) {
			// Normale Nodedeklaration
			ChordNode c = new ChordNode((serverPort + i),locAdr, serverHostname, serverPort);
			nodes.put(c.Id(), c);
		}
	}

	public List<String> getNodeList() {
		List<String> ret = new ArrayList<String>();
		for (ChordNode chordNode : nodes.values()) {
			// Beitreten der jeweiligen Nodes und dessen ID im Chord
			ret.add(chordNode.Id());
		}

		return ret;
	}

	public void startGame() {
		logger.info("starting Game in");

		try {
			// 3 Sekunden Intervall zum starten des Spiels
			for (int i = 3; i > 0; i--) {
				logger.debug(i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			logger.fatal(e);
		}
		// Spielverhalten der Nodes (Strategiezuweisung)
		for (ChordNode node : nodes.values()) {
			node.startGame(Plan.WEAKEST);
		}
	}
}
