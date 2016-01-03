package de.hawhh.ttv.test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import de.hawhh.ttv.ShipManager;
import de.uniba.wiai.lspi.chord.data.ID;

public class Test {
	
	@org.junit.Test
	public void intervalTest() {
		ID start = ID.valueOf(BigInteger.valueOf(0));
		ID end = ID.valueOf(BigInteger.valueOf(2));
		ID t = ID.valueOf(BigInteger.valueOf(1));
		
		assertTrue(t.isInInterval(start, end));
	}

	@org.junit.Test
	public void test() {
		ID start = ID.valueOf(BigInteger.valueOf(0));
		ID end = ID.valueOf(BigInteger.valueOf(100));
		ShipManager s = new ShipManager(start, end);
		int count = 0;
		for (int i = 0; i < 100; i++) {
			ID id = ID.valueOf(BigInteger.valueOf(i));
			if (s.tryHit(id)) {
				count++; 
			}
		}
		assertEquals(10, count);
	}
}
