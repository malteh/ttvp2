package de.hawhh.ttv;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;

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
	private String port;
	private String server;
	private boolean isClient = false;

	public ChordNode(String port, String server) {
		isClient = true;
		this.port = port;
		this.server = server;
		init();
	}

	public ChordNode(String port) {
		this.port = port;
		init();
	}

	private void init() {
		logger.info("Starting Server ...");

		// TODO: make pretty
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

		// TODO: make pretty, client/server separation
		if (isClient) {
			URL bootstrapURL = null;
			try {
				bootstrapURL = new URL(protocol + "://" + server + ":8080/");
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			try {
				chord.join(localURL, bootstrapURL);
				//System.out.println(chord.getID());
				//logger.info(chord.getPredecessorID());
			} catch (ServiceException e) {
				throw new RuntimeException(" Could not join DHT ! ", e);
			}
		} else {
			try {
				chord.create(localURL);
				//System.out.println(chord.getID());
			} catch (ServiceException e) {
				throw new RuntimeException(" Could not create DHT !", e);
			}
		}
		
		logger.info("ok");
	}

	@Override
	public void retrieved(ID target) {
		// TODO Auto-generated method stub
	}
	
	public void test() {
		// TODO: Thread oder AsyncChord
		chord.broadcast(chord.getID(), true);
	}

	@Override
	public void broadcast(ID source, ID target, Boolean hit) {
		// TODO Auto-generated method stub
		System.out.println(substr(chord.getID()) + ": broadcast s:" + substr(source) + " t:" + substr(target));
	}
	
	private String substr(Object s) {
		return s.toString().substring(0,2);
	}
}