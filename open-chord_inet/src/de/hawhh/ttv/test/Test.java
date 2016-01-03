package de.hawhh.ttv.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.hawhh.ttv.ShipManager;
import de.uniba.wiai.lspi.chord.data.ID;

public class Test {
	final BigInteger CHORD_MIN = BigInteger.valueOf(0);
	final BigInteger CHORD_MAX = BigInteger.valueOf(2).pow(160).subtract(BigInteger.ONE);
	
	@org.junit.Test
	public void testInterval() {
		ID start = ID.valueOf(BigInteger.valueOf(0));
		ID end = ID.valueOf(BigInteger.valueOf(2));
		ID t1 = ID.valueOf(BigInteger.valueOf(1));
		ID t2 = ID.valueOf(BigInteger.valueOf(2));
		assertTrue(t1.isInInterval(start, end));
		assertFalse(t2.isInInterval(start, end));
	}

	@org.junit.Test
	public void testShipManager() {
		int size = 203;
		ID start = ID.valueOf(BigInteger.valueOf(0));
		ID end = ID.valueOf(BigInteger.valueOf(size));
		
		Set<Integer> set = new HashSet<Integer>(Arrays.asList(0, 10));
		
		ShipManager s = new ShipManager(start, end, set);
		
		int count = 0;
		for (int i = 0; i < size; i++) {
			ID id = ID.valueOf(BigInteger.valueOf(i));
			if (s.tryHit(id)) {
				count++; 
			}
		}
		
		assertEquals(2, count);
	}
	
	@org.junit.Test
	public void testMax() {		
		ShipManager s1 = new ShipManager(ID.valueOf(CHORD_MIN), ID.valueOf(CHORD_MAX));
		ShipManager s2 = new ShipManager(ID.valueOf(CHORD_MIN), ID.valueOf(CHORD_MAX.subtract(BigInteger.valueOf(1))));
		assertTrue(s1.hasMaxID());
		assertFalse(s2.hasMaxID());
	}
}
