package de.hawhh.ttv;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting ...");
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		
		new ChordNode("8080");
		
		ChordNode n1 = new ChordNode("8181", "localhost");
		new ChordNode("8282", "localhost");
		
		n1.test();
		System.exit(0);
	}
}
