package de.hawhh.ttv;

import java.math.BigInteger;
import java.util.Random;

import org.apache.log4j.Logger;

import de.uniba.wiai.lspi.chord.data.ID;

public class Strategy {
	
	private Logger logger = Logger.getLogger(Strategy.class);
	private final GameHistory gameHistory;
	private final Plan plan;
	private final ShipManager shipManager;
	
	public Strategy(GameHistory g, Plan p,ShipManager s) {
		gameHistory = g;
		plan = p;
		shipManager = s;
	}

	public ID getTarget() {
		ID target = null;
		switch (plan) {
		case CENTER:
			target = centerPlan();
			break;
		default:
			target = randomPlan();
			break;
		}
		logger.info(target + " is the next target");
		return target;
	}
	
	private ID randomPlan() {
		Random rnd = new Random();
		ID id;
		do {
			id = ID.valueOf(new BigInteger(160, rnd));
		} while (shipManager.containsID(id));
		return id;
	}
	
	private ID centerPlan() {
		return null;
	}

	public enum Plan {
		RANDOM, CENTER
	}

}
