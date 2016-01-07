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
	public final BigInteger chordMax = BigInteger.valueOf(2).pow(160).subtract(BigInteger.ONE);

	private List<Slot> slots = new ArrayList<>();

	private final ID start;
	private final ID end;
	private int shipCount = SHIP_COUNT;

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
	
	public ShipManager(ID start, ID end, Boolean value) {
		this.start = start;
		this.end = end;
		initSlots();
		setAllShipPositionsValue(value);
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
		// numerisch überzählige Adressen werden dem numerisch letzten Intervall
		// zugeschlagen
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
	
	private void setAllShipPositionsValue(Boolean value) {
		for (Slot s : slots) {
			s.hasShip = value;
		}
	}
	
	public Boolean hasShips() {
		return shipCount == 0;
	}
	
	public int getShipCount() {
		return shipCount;
	}
	
	public ID getSlotWithShip() {
		for (Slot slot : slots) {
			if (slot.hasShip)
				return slot.end;
		}
		return null;
	}

	public Boolean tryHit(ID id) {
		Slot s = getContainingSlot(id);
		if (s == null)
			return false;

		if (s.hasShip) {
			s.hasShip = false;
			shipCount--;
			return true;
		}

		return false;
	}
	
	public Boolean containsID(ID id) {
		ID s = ID.valueOf(start.toBigInteger().subtract(BigInteger.valueOf(1)));
		ID e = ID.valueOf(end.toBigInteger().add(BigInteger.valueOf(1)));
		return id.isInInterval(s, e);
	}

	private Slot getContainingSlot(ID id) {
		for (Slot slot : slots) {
			ID s = ID.valueOf(slot.start.toBigInteger().subtract(BigInteger.valueOf(1)));
			ID e = ID.valueOf(slot.end.toBigInteger().add(BigInteger.valueOf(1)));
			if (id.isInInterval(s, e)) {
				return slot;
			}
		}
		return null;
	}

	public boolean hasMaxID() {
		return getContainingSlot(ID.valueOf(chordMax)) != null;
	}

	public BigInteger diff(ID end, ID start) {
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
			if (start.equals(end))
				throw new IllegalArgumentException();
			this.start = start;
			this.end = end;
		}

		public String toString() {
			return String.format("Start: %s, End: %s, Has Ship: %s", start, end, hasShip);
		}
	}

}
