package de.hawhh.ttv;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import de.uniba.wiai.lspi.chord.data.ID;

public class ShipManager {
	
	private Logger logger = Logger.getLogger(ShipManager.class);
	
	public final int INTERVAL_COUNT = 100;
	public final int SHIP_COUNT = 10;

	private List<Slot> slots = new ArrayList<>();

	private final ID start;
	private final ID end;

	public ShipManager(ID start, ID end) {
		this.start = start;
		this.end = end;
		initSlots();
		selectShipPositions();
	}
	
	public ShipManager(ID start, ID end, Set<Integer> ships) {
		this.start = start;
		this.end = end;
		initSlots();
		selectShipPositions(ships);
		logSlots();
	}

	private void initSlots() {
		BigInteger totalSize = diff(end, start);
		BigInteger slotSize = totalSize.divide(BigInteger.valueOf(INTERVAL_COUNT));
		for (int i = 0; i < INTERVAL_COUNT; i++) {
			BigInteger delta = slotSize.multiply(BigInteger.valueOf(i));
			ID s = ID.valueOf(start.toBigInteger().add(delta));
			ID e = ID.valueOf(s.toBigInteger().add(slotSize).subtract(BigInteger.valueOf(1)));
			slots.add(new Slot(s, e));
		}
		// numerisch überzählige Adressen werden dem numerisch letzten Intervall zugeschlagen
		slots.get(slots.size() - 1).end = end;
	}

	private void selectShipPositions() {
		Set<Integer> ships = new HashSet<>();

		Random r = new Random();

		while (ships.size() < SHIP_COUNT)
			ships.add(r.nextInt(INTERVAL_COUNT));

		for (Integer i : ships) {
			slots.get(i).hasShip = true;
		}
	}
	
	private void selectShipPositions(Set<Integer> ships) {
		for (Integer i : ships) {
			slots.get(i).hasShip = true;
		}
	}
	
	public Boolean tryHit(ID id) {
		for (Slot slot : slots) {
			ID s = ID.valueOf(slot.start.toBigInteger().subtract(BigInteger.valueOf(1)));
			ID e = ID.valueOf(slot.end.toBigInteger().add(BigInteger.valueOf(1)));
			if (slot.hasShip && id.isInInterval(s, e)) {
				slot.hasShip = false;
				return true;
			}
		}
		return false;
	}

	public BigInteger diff(ID end, ID start) {
		BigInteger chordMax = BigInteger.valueOf(2).pow(160).subtract(BigInteger.ONE);
		return end.toBigInteger().add(start.toBigInteger().negate()).mod(chordMax);
	}
	
	public void logSlots() {
		for (int i = 0; i < INTERVAL_COUNT; i++) {
			logger.debug(String.format("Slot %2d: %s", i, slots.get(i)));
		}
	}

	public class Slot {
		public ID start;
		public ID end;
		public Boolean hasShip = false;

		public Slot(ID start, ID end) {
			if (start.equals(end)) throw new IllegalArgumentException();
			this.start = start;
			this.end = end;
		}
		
		public String toString() {
			return String.format("Start: %s, End: %s, Has Ship: %s", start, end, hasShip);
		}
	}

}
