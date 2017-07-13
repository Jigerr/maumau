package de.berlin.htw.kba.maumau.table.service;

import java.util.List;

import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

// TODO: Auto-generated Javadoc
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
	 * @param gameTableId            the table id
	 * @param accountId            the account id
	 * @param currentCard            the current card
	 * @param wishedSuit the wished suit
	 * @return true, if successful
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
	 * Inits the table.
	 *
	 * @return the game table
	 */
	GameTable initTable(String playerAmount);

	/**
	 * Skip turn.
	 *
	 * @param gameTableId the game table id
	 * @param playerId the player id
	 */
	void skipTurn(Integer gameTableId, String playerId);

	/**
	 * Removes the game table.
	 *
	 * @param gameTable the game table
	 */
	void removeGameTable(GameTable gameTable);

	/**
	 * Load games.
	 *
	 * @return the list
	 */
	List<GameTable> loadGameList();

	/**
	 * Load game.
	 *
	 * @param gameTableId the game table id
	 * @return the game table
	 */
	GameTable loadGame(Integer gameTableId);

}
