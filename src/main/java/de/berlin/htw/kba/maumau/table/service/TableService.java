package de.berlin.htw.kba.maumau.table.service;

import java.util.List;

import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Table;

/**
 * The Interface TableService.
 */
public interface TableService {

	/**
	 * Draw cards.
	 *
	 * @param tableId
	 *            the table id
	 * @param accountId
	 *            the account id
	 */
	void drawCards(String tableId, String accountId);

	/**
	 * Play card.
	 *
	 * @param tableId
	 *            the table id
	 * @param accountId
	 *            the account id
	 * @param currentCard
	 *            the current card
	 */
	void playCard(String tableId, String accountId, Card currentCard);

	/**
	 * Call mau.
	 *
	 * @param tableId
	 *            the table id
	 * @param accountId
	 *            the account id
	 */
	void callMau(String tableId, String accountId);

	/**
	 * Gets the open tables.
	 *
	 * @return the open tables
	 */
	// temopräre Methoden
	List<Table> getOpenTables();

	/**
	 * Sets the open tables.
	 *
	 * @param openTables
	 *            the new open tables
	 */
	void setOpenTables(List<Table> openTables);

}
