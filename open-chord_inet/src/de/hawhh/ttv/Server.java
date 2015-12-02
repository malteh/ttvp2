package de.hawhh.ttv;

import java.net.MalformedURLException;

import de.uniba.wiai.lspi.chord.data.ID;
import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.NotifyCallback;
import de.uniba.wiai.lspi.chord.service.ServiceException;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;

import org.apache.log4j.Logger;

public class Server implements NotifyCallback {
	
	private Logger logger = Logger.getLogger(Server.class);

	public Server() {
		logger.info("Starting Server ...");

		try {
			de.uniba.wiai.lspi.chord.service.PropertiesLoader
					.loadPropertyFile();
		} catch (IllegalStateException e) {
		}
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL localURL = null;
		try {
			localURL = new URL(protocol + "://localhost:8080/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		Chord chord = new ChordImpl();
		chord.setCallback(this);
		try {
			chord.create(localURL);
			logger.info(chord.getID());
		} catch (ServiceException e) {
			throw new RuntimeException(" Could not create DHT !", e);
		}
		logger.info("ok");
	}

	@Override
	public void retrieved(ID target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void broadcast(ID source, ID target, Boolean hit) {
		// TODO Auto-generated method stub
		logger.info("broadcast");
	}
}
