package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Starter implements IGuiUpdater {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting ...");
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		
		new Starter();
	}
	
	Map<String, ChordNode> nodes = new HashMap<String, ChordNode>();
	
	public Starter() {
		ChordNode server = new ChordNode(8080);
		nodes.put(server.Id(), server);
		
		for (int i = 1; i <= 2; i++) {
			ChordNode c = new ChordNode((8080+i), "localhost");
			nodes.put(c.Id(), c);
		}
		
		new Ui(this);
		//System.exit(0);
	}
	
	public List<String> getNodeList() {
		List<String> ret = new ArrayList<String>();
		for (ChordNode chordNode : nodes.values()) {
			ret.add(chordNode.Id());
		}
		
		return ret;		
	}
	
	public List<String> getNeighborList(String id) {
		List<String> ret = new ArrayList<String>();
		ret.add("TODO");
		return ret;
	}

	@Override
	public void doBroadcast(String id) {
		nodes.get(id).test();
	}
}
