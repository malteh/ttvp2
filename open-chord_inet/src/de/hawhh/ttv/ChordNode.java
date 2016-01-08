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
	private final int port;
	private final int serverPort;
	private String server = "localhost";
	private final String locAdr;
	private boolean isClient = false;
	private boolean isStarted = false;

	private ShipManager shipManager;
	private GameHistory gameHistory;
	private Strategy strategy;

	public ChordNode(int port, String locAdr, String server, int serverPort) {
		isClient = true;
		this.port = port;
		this.serverPort = serverPort;
		this.server = server;
		this.locAdr = locAdr;
		init();
	}

	public ChordNode(int port) {
		this.port = port;
		locAdr = server;
		this.serverPort = port;
		init();
	}

	public void startGame(Plan p) {
		if (isStarted)
			return;
		isStarted = true;
		initShipManager();
		gameHistory = GameHistory.getInstance(chord.getID());
		strategy = new Strategy(gameHistory, p, shipManager);
		if (shipManager.hasMaxID()) {
			logger.info(chord.getID() + " has 2^160-1");
			retrieved(null);
		}
	}

	private void initShipManager() {
		ID start = ID.valueOf(chord.getPredecessorID().toBigInteger()
				.add(BigInteger.valueOf(1)));
		ID end = chord.getID();
		shipManager = new ShipManager(start, end);
	}

	private void init() {
		try {
			de.uniba.wiai.lspi.chord.service.PropertiesLoader
					.loadPropertyFile();
		} catch (IllegalStateException e) {
		}

		try {
			localURL = new URL(protocol + "://" + locAdr + ":" + port + "/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		chord.setCallback(this);

		if (isClient) {
			URL bootstrapURL = null;
			try {
				bootstrapURL = new URL(protocol + "://" + server + ":"
						+ serverPort + "/");
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
		logger.info("node " + Helper.shortenID(chord.getID()) + "init done");

	}

	@Override
	public void retrieved(ID target) {
		if (!isStarted)
			startGame(Plan.WEAKEST);
		Boolean hit = false;
		if (target != null) {
			logger.info(Helper.shortenID(chord.getID()) + " tries hit at "
					+ Helper.shortenID(target));
			hit = shipManager.tryHit(target);
		}
		asyncBroadcastAndFire(target, hit);
	}

	private void asyncBroadcastAndFire(ID target, Boolean hit) {
		new Thread(new AsyncBroadcast(chord, target, hit)).start();
	}

	@Override
	public void broadcast(ID source, ID target, Boolean hit, int transactionID) {
		gameHistory.addEvent(source, target, hit, transactionID);
		if (hit)
			logger.info(String.format("############### %s hit by %s",
					Helper.shortenID(target), Helper.shortenID(source)));
	}

	public String Id() {
		return chord.getID().toString();
	}

	private class AsyncBroadcast implements Runnable {

		Chord chord;
		Boolean hit;
		ID target;

		public AsyncBroadcast(Chord c, ID t, Boolean h) {
			chord = c;
			hit = h;
			target = t;
		}

		@Override
		public void run() {
			if (target != null)
				chord.broadcast(target, hit);
			ID t = strategy.getTarget();
			try {
				chord.retrieve(t);
			} catch (ServiceException e) {
				logger.fatal(e);
			}
		}
	}
}