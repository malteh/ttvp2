package de.hawhh.ttv;

import java.util.List;

public interface IGuiUpdater {
	List<String> getNodeList();
	List<String> getNeighborList(String id);
	void doBroadcast(String id);
}
