package de.hawhh.ttv;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import de.uniba.wiai.lspi.chord.data.ID;

public class Strategy {

	private Logger logger = Logger.getLogger(Strategy.class);
	private final GameHistory gameHistory;
	private final Plan plan;
	private final ShipManager shipManager;

	public Strategy(GameHistory g, Plan p, ShipManager s) {
		gameHistory = g;
		plan = p;
		shipManager = s;
	}

	
	public ID getTarget() {
		ID target = null;
		switch (plan) {
		case WEAKEST:
			target = weakestPlan();
			break;
		default:
			target = randomPlan();
			break;
		}
		logger.info(target + " is the next target");
		return target;
	}

        // Strategie 1
        // Schwaechster Gegner
        // Dieser Plan wird gemsesen anhand der Anzahl der ürbrigen Schiffe die 
        // der Gegner noch besitzt.
	private ID weakestPlan() {
		List<Enemy> es = gameHistory.getEnemies();
		if (es.isEmpty())
			return randomPlan();

		Enemy weakest = es.get(0);

                /// Erfassungschleife für die Anzahl der verbliebenden Schiffe
		for (int i = 1; i < es.size(); i++) {
			Enemy current = es.get(i);
			if (weakest.shipManager.getShipCount() > current.shipManager
					.getShipCount())
				weakest = current;
		}

		ID target = weakest.shipManager.getSlotWithShip();

	
		if (target == null) { // target has no ships left
		
			// Ausgabe beim Sieg
			for (int i = 0; i < 10; i++)
				logger.info("WINNER!!!!!" + weakest.endId + " is dead!");
			target = weakest.endId;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return target;
	}

	// Strategie 2
	// Zufallsplan
	
	private ID randomPlan() {
		Random rnd = new Random();
		ID id;
		do {
			id = ID.valueOf(new BigInteger(160, rnd));
		} while (shipManager.containsID(id));
		return id;
	}

	public enum Plan {
		RANDOM, WEAKEST
	}

}
