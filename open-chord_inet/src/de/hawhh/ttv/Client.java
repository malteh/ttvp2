package de.hawhh.ttv;

import java.net.MalformedURLException;
import de.uniba.wiai.lspi.chord.data.ID;
import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.NotifyCallback;
import de.uniba.wiai.lspi.chord.service.ServiceException;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;
import org.apache.log4j.Logger;

public class Client implements NotifyCallback {
	private Logger logger = Logger.getLogger(Client.class);
	
	public Client(String Port) {
		logger.info("Starting Client ...");
		
		try {
			de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		} catch (IllegalStateException e) {
		}
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL localURL = null;
		try {
			localURL = new URL(protocol + "://localhost:" + Port + "/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		URL bootstrapURL = null;
		try {
			bootstrapURL = new URL(protocol + "://localhost:8080/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		ChordImpl chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		chord.setCallback(this);
		try {
			chord.join(localURL, bootstrapURL);
			logger.info(chord.getID());
			logger.info(chord.getPredecessorID());
		} catch (ServiceException e) {
			throw new RuntimeException(" Could not join DHT ! ", e);
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
		logger.debug("broadcast");
	}
}
