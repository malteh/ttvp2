package de.hawhh.ttv;

import de.uniba.wiai.lspi.chord.data.ID;

public class Helper {
	public static String shortenID(ID id) {
		String idHex = id.toHexString().trim();
		String first = idHex.substring(0, 8);
		String last = idHex.substring(idHex.length() - 8);
		return String.format("[%s...%s]", first, last);
	}
}
