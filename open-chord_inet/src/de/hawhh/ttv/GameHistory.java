package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.uniba.wiai.lspi.chord.data.ID;

public class GameHistory {

	private Logger logger = Logger.getLogger(GameHistory.class);

	private static Map<ID, GameHistory> gameHistoryCollection = new HashMap<>();

	private GameHistory() {
	}

	// GameHistory factory
	public static GameHistory getInstance(ID nodeID) {
		if (!gameHistoryCollection.containsKey(nodeID)) {
			gameHistoryCollection.put(nodeID, new GameHistory());
		}

		return gameHistoryCollection.get(nodeID);
	}

	// Transaction ID management
	private List<Integer> transactionIDs = new ArrayList<Integer>();

	public Integer nextTransactionID() {
		return transactionIDs.isEmpty() ? 0 : transactionIDs.get(transactionIDs.size() - 1) + 1;
	}

	public void addTransactionID(Integer transactionID) {
		transactionIDs.add(transactionID);
	}

	public Boolean isDuplicate(Integer transactionID) {
		return transactionIDs.contains(transactionID);
	}

	// Game Event management
	private final List<GameEvent> gameEvents = new ArrayList<>();

	public void addEvent(ID source, ID target, Boolean hit, int transactionID) {
		GameEvent g = new GameEvent(source, target, hit, transactionID);
		gameEvents.add(g);
		logger.debug("New Event:" + g);
	}

	public class GameEvent {
		public final ID source, target;
		public final Boolean hit;
		public final int transactionID;

		public GameEvent(ID source, ID target, Boolean hit, int transactionID) {
			this.source = source;
			this.target = target;
			this.hit = hit;
			this.transactionID = transactionID;
		}
	}
}
