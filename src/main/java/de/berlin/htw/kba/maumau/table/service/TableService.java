package de.berlin.htw.kba.maumau.table.service;

import java.util.List;

import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Table;

public interface TableService {
	
	Card drawCard(String tableId, String accountId);
	
	boolean playCard(String tableId, String accountId, Card currentCard);
	
	void callMau(String tableId, String accountId);
	
	void endTurn(String tableId, String accountId);

    List<Table> getOpenTables();

    void setOpenTables(List<Table> openTables);

}
