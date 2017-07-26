package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayCardEvent.
 */
public class PlayCardEvent extends ApplicationEvent {

	/** The card. */
	private Card card;
	
	/** The game table id. */
	private Integer gameTableId;
	
	/** The player id. */
	private String playerId;
	
	/** The wished suit. */
	private Suits wishedSuit;

	/**
	 * Instantiates a new play card event.
	 *
	 * @param source the source
	 * @param card the card
	 * @param gameTableId the game table id
	 * @param playerId the player id
	 * @param wishedSuit the wished suit
	 */
	public PlayCardEvent(Object source, Card card, Integer gameTableId, String playerId, Suits wishedSuit) {
		super(source);
		this.card = card;
		this.gameTableId = gameTableId;
		this.playerId = playerId;
		this.wishedSuit = wishedSuit;
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

	/**
	 * Gets the wished suit.
	 *
	 * @return the wished suit
	 */
	public Suits getWishedSuit() {
		return wishedSuit;
	}

}
