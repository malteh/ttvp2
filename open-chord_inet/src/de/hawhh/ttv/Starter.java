package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.hawhh.ttv.Strategy.Plan;

public class Starter {

	private static Logger logger = Logger.getLogger(Starter.class);

	public static void main(String[] args) {
		logger.info("Starting ...");
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		// TODO set parameters via gui
		new Ui();
		//new Starter("141.22.27.30", 8080, 0, true);
	}

	Map<String, ChordNode> nodes = new HashMap<String, ChordNode>();

	public Starter(String serverHostname, String locAdr, int serverPort, int nodesToStart, Boolean startServer) {
		if (startServer) {
			logger.info("starting server node");
			ChordNode server = new ChordNode(serverPort);
			nodes.put(server.Id(), server);
		}
		logger.info("starting " + nodesToStart + " nodes");
		for (int i = 1; i <= nodesToStart; i++) {
			ChordNode c = new ChordNode((serverPort + i),locAdr, serverHostname, serverPort);
			nodes.put(c.Id(), c);
		}
	}

	public List<String> getNodeList() {
		List<String> ret = new ArrayList<String>();
		for (ChordNode chordNode : nodes.values()) {
			ret.add(chordNode.Id());
		}

		return ret;
	}

	public void startGame() {
		logger.info("starting Game in");

		try {
			for (int i = 3; i > 0; i--) {
				logger.debug(i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			logger.fatal(e);
		}
		
		for (ChordNode node : nodes.values()) {
			node.startGame(Plan.WEAKEST);
		}
	}
}
