package de.hawhh.ttv;

import java.util.ArrayList;
import java.util.List;

public class Starter implements IGuiUpdater {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting ...");
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		
		new Starter();
	}
	
	List<ChordNode> nodes = new ArrayList<ChordNode>();
	
	public Starter() {		
		nodes.add(new ChordNode("8080"));
		nodes.add(new ChordNode("8181", "localhost"));
		nodes.add(new ChordNode("8282", "localhost"));
		
		ChordNode n1 = nodes.get(1);
		n1.test();
		Ui u = new Ui(this);
		//System.exit(0);
	}
	
	public List<String> getNodeList() {
		List<String> ret = new ArrayList<String>();
		for (ChordNode chordNode : nodes) {
			ret.add(chordNode.Id());
		}
		
		return ret;		
	}
	
	public List<String> getNeighborList(String id) {
		List<String> ret = new ArrayList<String>();
		ret.add("TODO");
		return ret;
		
	}
}
