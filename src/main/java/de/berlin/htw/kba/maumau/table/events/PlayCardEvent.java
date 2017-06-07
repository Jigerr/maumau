package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

public class PlayCardEvent extends ApplicationEvent {

	private Card card;
	private GameTable gameTable;
	private String playerId;
	private Suits wishedSuit;

	public PlayCardEvent(Object source, Card card, GameTable gameTable, String playerId, Suits wishedSuit) {
		super(source);
		this.card = card;
		this.gameTable = gameTable;
		this.playerId = playerId;
		this.wishedSuit = wishedSuit;
	}
	
	public Card getCard() {
		return card;
	}

	public GameTable getGameTable() {
		return gameTable;
	}

	public String getPlayerId() {
		return playerId;
	}

	public Suits getWishedSuit() {
		return wishedSuit;
	}

}
