package de.berlin.htw.kba.maumau.table.service;

import java.util.List;

import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

/**
 * The Interface TableService.
 */
public interface TableService {

	/**
	 * Draw cards.
	 *
	 * @param gameTableId
	 *            the table id
	 * @param accountId
	 *            the account id
	 */
	void drawCards(Integer gameTableId, String accountId);

	/**
	 * Play card.
	 *
	 * @param gameTableId
	 *            the table id
	 * @param accountId
	 *            the account id
	 * @param currentCard
	 *            the current card
	 */
	boolean playCard(Integer gameTableId, String accountId, Card currentCard, Suits wishedSuit);

	/**
	 * Call mau.
	 *
	 * @param gameTableId
	 *            the table id
	 * @param accountId
	 *            the account id
	 */
	void callMau(Integer gameTableId, String accountId);

	/**
	 * Gets the open tables.
	 *
	 * @return the open tables
	 */
	// temopräre Methoden
	List<GameTable> getOpenTables();

	/**
	 * Sets the open tables.
	 *
	 * @param openTables
	 *            the new open tables
	 */
	void setOpenTables(List<GameTable> openTables);

	GameTable initTable();

	void skipTurn(Integer gameTableId, String playerId);

}
