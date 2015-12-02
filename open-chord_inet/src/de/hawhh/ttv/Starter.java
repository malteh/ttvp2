package de.hawhh.ttv;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting ...");
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		
		new Server();
		
		new Client("8181");
		new Client("8282");
	}
}
