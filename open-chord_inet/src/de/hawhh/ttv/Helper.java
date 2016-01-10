package de.hawhh.ttv;

import de.uniba.wiai.lspi.chord.data.ID;

// Helperklasse dient der Verkleinerung der Hexadezimalausgabe 
// dient einer besseren Formatierung und Uebersicht in der Logausgabe im weitern Spielverlauf
public class Helper {
	public static String shortenID(ID id) {
		String idHex = id.toHexString().trim();
		String first = idHex.substring(0, 8);
		String last = idHex.substring(idHex.length() - 8);
		return String.format("[%s...%s]", first, last);
	}
}
