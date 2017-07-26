package de.berlin.htw.kba.maumau.table.service;

import java.util.List;

import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.ruleset.service.WrongCardException;
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
	 * @param playerId            the account id
	 * @param currentCard            the current card
	 * @param wishedSuit the wished suit
	 * @return true, if successful
	 * @throws WrongCardException the wrong card exception
	 */
	boolean playCard(Integer gameTableId, String playerId, Card currentCard, Suits wishedSuit) throws WrongCardException;

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
	 * @param playerAmount the player amount
	 * @param gameMode the game mode
	 * @param amountOfCards the amount of cards
	 * @return the game table
	 */
	GameTable initTable(String playerAmount, String gameMode, int amountOfCards);

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

    /**
     * Gets the free S lot.
     *
     * @param gameTableId the game table id
     * @return the free S lot
     */
    String getFreeSLot(Integer gameTableId);
    
    /**
     * Removes the controlled by from player.
     *
     * @param playerId the player id
     * @param gameTable the game table
     */
    void removeControlledByFromPlayer(String playerId, GameTable gameTable);

    /**
     * Save table.
     *
     * @param gameTable the game table
     */
    void saveTable(GameTable gameTable);

}
