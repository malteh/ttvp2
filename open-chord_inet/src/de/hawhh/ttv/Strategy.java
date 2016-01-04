package de.hawhh.ttv;

import org.apache.log4j.Logger;

import de.uniba.wiai.lspi.chord.data.ID;

public class Strategy {
	
	private Logger logger = Logger.getLogger(Strategy.class);
	private final GameHistory gameHistory;
	
	public Strategy(GameHistory g) {
		gameHistory = g;
	}

	public ID getTarget() {
		ID target = null;
		// TODO implement strategy
		logger.info(target + " is the next target");
		return target;
	}

}
