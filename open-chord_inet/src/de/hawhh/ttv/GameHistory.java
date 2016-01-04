package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uniba.wiai.lspi.chord.data.ID;

public class GameHistory {
	
	private static Map<ID, GameHistory> hs = new HashMap<>();
	
	private GameHistory() {}
	
	public static GameHistory getInstance(ID id) {		
		if (!hs.containsKey(id)) {
			hs.put(id, new GameHistory());
		}
		
		return hs.get(id);
	}
	
	private List<Integer> tids = new ArrayList<Integer>();
	
	public Integer nextTid() {
		return tids.isEmpty() ? 0 : tids.get(tids.size()-1)+1;
	}
	
	public void addTid(Integer tid) {
		tids.add(tid);
	}
	
	public Boolean isDuplicate(Integer transactionID) {
		return tids.contains(transactionID);
	}
}
