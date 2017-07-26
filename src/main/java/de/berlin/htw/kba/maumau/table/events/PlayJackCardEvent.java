package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayJackCardEvent.
 */
public class PlayJackCardEvent extends ApplicationEvent {

	/** The card. */
	private Card card;
	
	/** The game table id. */
	private Integer gameTableId;
	
	/** The player id. */
	private String playerId;

	/**
	 * Instantiates a new play jack card event.
	 *
	 * @param source the source
	 * @param card the card
	 * @param gameTableId the game table id
	 * @param playerId the player id
	 */
	public PlayJackCardEvent(Object source, Card card, Integer gameTableId, String playerId) {
		super(source);
		this.card = card;
		this.gameTableId = gameTableId;
		this.playerId = playerId;
	}
	
	/**
	 * Gets the card.
	 *
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * Gets the game table id.
	 *
	 * @return the game table id
	 */
	public Integer getGameTableId() {
		return gameTableId;
	}

	/**
	 * Gets the player id.
	 *
	 * @return the player id
	 */
	public String getPlayerId() {
		return playerId;
	}
	
}
