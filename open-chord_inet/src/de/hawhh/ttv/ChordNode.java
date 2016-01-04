package de.hawhh.ttv;

import java.math.BigInteger;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import de.hawhh.ttv.Strategy.Plan;
import de.uniba.wiai.lspi.chord.data.ID;
import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.NotifyCallback;
import de.uniba.wiai.lspi.chord.service.ServiceException;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;

public class ChordNode implements NotifyCallback {
	
	private Logger logger = Logger.getLogger(ChordNode.class);
	String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
	URL localURL = null;
	Chord chord = new ChordImpl();
	private int port;
	private String server;
	private boolean isClient = false;
	
	private ShipManager shipManager;
	private GameHistory gameHistory = GameHistory.getInstance(chord.getID());
	private Strategy strategy;

	public ChordNode(int port, String server) {
		isClient = true;
		this.port = port;
		this.server = server;
		init();
	}

	public ChordNode(int port) {
		this.port = port;
		init();
	}

	public void startGame(Plan p) {
		initShipManager();
		strategy = new Strategy(gameHistory, p);
		if (shipManager.hasMaxID()) {
			logger.info(chord.getID() + " has 2^160-1");
			fire();
		}
	}

	private void fire() {
		ID target = strategy.getTarget();
		try {
			chord.retrieve(target);
		} catch (ServiceException e) {
			logger.fatal(e);
		}
	}

	private void initShipManager() {
		ID start = ID.valueOf(chord.getPredecessorID().toBigInteger().add(BigInteger.valueOf(1)));
		ID end = chord.getID();
		shipManager = new ShipManager(start, end);
	}


	private void init() {
		logger.info("Starting Server ...");

		try {
			de.uniba.wiai.lspi.chord.service.PropertiesLoader
					.loadPropertyFile();
		} catch (IllegalStateException e) {
		}

		try {
			localURL = new URL(protocol + "://localhost:" + port + "/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		chord.setCallback(this);

		if (isClient) {
			URL bootstrapURL = null;
			try {
				bootstrapURL = new URL(protocol + "://" + server + ":8080/");
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			try {
				chord.join(localURL, bootstrapURL);
			} catch (ServiceException e) {
				throw new RuntimeException(" Could not join DHT ! ", e);
			}
		} else {
			try {
				chord.create(localURL);
			} catch (ServiceException e) {
				throw new RuntimeException(" Could not create DHT !", e);
			}
		}
		logger.info("node init done");
		
	}

	@Override
	public void retrieved(ID target) {
		
		// TODO Auto-generated method stub
	}
	
	public void test() {
		new Thread(new AsyncBroadcast(chord, true)).start();
	}

	@Override
	public void broadcast(ID source, ID target, Boolean hit, int transactionID) {
		gameHistory.addTransactionID(transactionID);
		logger.assertLog(hit, String.format("%s hit by %s", target, source));
	}

	public String Id() {
		return chord.getID().toString();
	}
	

	private class AsyncBroadcast implements Runnable {

		Chord chord;
		Boolean hit;

		public AsyncBroadcast(Chord c, Boolean h) {
			chord = c;
			hit = h;
		}

		@Override
		public void run() {
			chord.broadcast(chord.getID(), hit);
		}
	}
}