package de.berlin.htw.kba.maumau.table.service;

import de.berlin.htw.kba.maumau.table.db.Card;

public interface TableService {
	
	Card drawCard(String tableId, String accountId);
	
	boolean playCard(String tableId, String accountId, Card currentCard);
	
	void callMau(String tableId, String accountId);
	
	void endTurn(String tableId, String accountId);

}
