package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.uniba.wiai.lspi.chord.data.ID;

public class GameHistory {

	private Logger logger = Logger.getLogger(GameHistory.class);

	private static Map<ID, GameHistory> gameHistoryCollection = new HashMap<>();

	public final ID id;
	
	private GameHistory(ID id) {
		this.id = id;
	}

	// GameHistory factory
	public static GameHistory getInstance(ID nodeID) {
		if (!gameHistoryCollection.containsKey(nodeID)) {
			gameHistoryCollection.put(nodeID, new GameHistory(nodeID));
		}

		return gameHistoryCollection.get(nodeID);
	}

	// GameEvent management
	private final List<GameEvent> gameEvents = new ArrayList<>();

	public void addEvent(ID source, ID target, Boolean hit, int transactionID) {
		GameEvent g = new GameEvent(source, target, hit, transactionID);
		gameEvents.add(g);
		addTransactionID(transactionID);
		addEnemy(source);
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
	
	// Transaction ID management
	private List<Integer> transactionIDs = new ArrayList<Integer>();

	public Integer nextTransactionID() {
		return transactionIDs.isEmpty() ? 0 : transactionIDs.get(transactionIDs.size() - 1) + 1;
	}

	private void addTransactionID(Integer transactionID) {
		transactionIDs.add(transactionID);
	}

	public Boolean isDuplicate(Integer transactionID) {
		return transactionIDs.contains(transactionID);
	}
	
	// Enemy management
	private final Set<ID> enemies = new HashSet<>();
	
	public void addEnemy(ID e) {
		if (id != e)
			enemies.add(e);
	}
	
	public List<ID> getEnemies() {
		List<ID> ret = new ArrayList<ID>();
		ret.addAll(enemies);
		Collections.sort(ret);
		return ret;
	}
}
