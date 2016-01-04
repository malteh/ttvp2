package de.hawhh.ttv;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import de.uniba.wiai.lspi.chord.data.ID;

public class Strategy {
	
	private Logger logger = Logger.getLogger(Strategy.class);
	private final GameHistory gameHistory;
	private final Plan plan;
	
	public Strategy(GameHistory g, Plan p) {
		gameHistory = g;
		plan = p;
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
		BigInteger b = BigInteger.valueOf(0);
		return ID.valueOf(b);
	}

	private ID centerPlan() {
		return null;
	}

	public enum Plan {
		RANDOM, CENTER
	}

}
