package de.hawhh.ttv;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.uniba.wiai.lspi.chord.data.ID;

public class ShipManager {
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

	private void initSlots() {
		BigInteger totalSize = diff(end, start);
		BigInteger slotSize = totalSize.divide(BigInteger.valueOf(INTERVAL_COUNT));
		for (int i = 0; i < INTERVAL_COUNT; i++) {
			BigInteger delta = slotSize.multiply(BigInteger.valueOf(i));
			ID s = ID.valueOf(start.toBigInteger().add(delta));
			ID e = ID.valueOf(s.toBigInteger().add(slotSize));
			slots.add(new Slot(s.addPowerOfTwo(0), e));
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
	
	public Boolean tryHit(ID id) {
		for (Slot slot : slots) {
			ID s = slot.start;
			ID e = slot.end;
			if (id.isInInterval(s, e) && slot.hasShip)
				return true;
		}
		return false;
	}

	public BigInteger diff(ID end, ID start) {
		BigInteger chordMax = BigInteger.valueOf(2).pow(160).subtract(BigInteger.ONE);
		return end.toBigInteger().add(start.toBigInteger().negate()).mod(chordMax);
	}

	public class Slot {
		public ID start;
		public ID end;
		public Boolean hasShip = false;

		public Slot(ID start, ID end) {
			this.start = start;
			this.end = end;
		}
	}

}
