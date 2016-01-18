package de.hawhh.ttv;

import de.uniba.wiai.lspi.chord.data.ID;

// Klasse fuer die Verwaltung der Gegner
// Enthaelt IDs fuer Start und Ende des Bereichs
// ein Shipmanager verwaltet die Treffer
public class Enemy implements Comparable<Enemy>{
	public final ID startId;
	public final ID endId;
	public final ShipManager shipManager;
	
	public Enemy(ID start, ID end) {
		startId = start;
		endId = end;
		shipManager = new ShipManager(start, end, true);
	}

	@Override
	public int compareTo(Enemy o) {
		return startId.compareTo(o.startId);
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    
	    final Enemy other = (Enemy) obj;
	    	    
	    return this.startId.equals(other.startId) && this.endId.equals(other.endId);
	}
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this.startId != null ? this.startId.hashCode() : 0);
	    hash = 53 * hash + (this.endId != null ? this.endId.hashCode() : 0);
	    return hash;
	}
}
