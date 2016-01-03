package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {
	public List<Integer> tids = new ArrayList<Integer>();
	
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
