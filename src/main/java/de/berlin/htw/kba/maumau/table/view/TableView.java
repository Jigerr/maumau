package de.berlin.htw.kba.maumau.table.view;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.events.PlayJackCardEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface TableView.
 */
public interface TableView {

	/**
	 * Inits the game lobby.
	 */
	void initGameLobby();

	/**
	 * Sets the event publisher.
	 *
	 * @param applicationEventPublisher the new event publisher
	 */
	void setEventPublisher(ApplicationEventPublisher applicationEventPublisher);

	/**
	 * Prints the table content.
	 *
	 * @param gameTable the game table
	 */
	void printTableContent(GameTable gameTable);

	/**
	 * Choose action.
	 *
	 * @param gameTable the game table
	 */
	void chooseAction(GameTable gameTable);

	/**
	 * Prints the skip message.
	 *
	 * @param gameTable the game table
	 */
	void printSkipMessage(GameTable gameTable);

	/**
	 * Prints the card not allowed message.
	 */
	void printCardNotAllowedMessage();

	/**
	 * Prints the game over message.
	 *
	 * @param gameTable the game table
	 */
	void printGameOverMessage(GameTable gameTable);

	/**
	 * Prints the wish suit message.
	 *
	 * @param event the event
	 */
	void printWishSuitMessage(PlayJackCardEvent event);

	/**
	 * Prints the game list message.
	 *
	 * @param gameTablelist the game tablelist
	 */
	void printGameListMessage(List<GameTable> gameTablelist);

    /**
     * Prints the amount of players message.
     *
     * @param amountOfCards the amount of cards
     * @param gameMode the game mode
     */
    void printAmountOfPlayersMessage(int amountOfCards, String gameMode);

    /**
     * Prints the game mode message.
     */
    void printGameModeMessage();

    /**
     * Prints the amount of cards message.
     *
     * @param gameMode the game mode
     */
    void printAmountOfCardsMessage(String gameMode);

}
