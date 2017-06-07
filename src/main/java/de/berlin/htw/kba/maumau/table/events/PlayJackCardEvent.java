package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

public class PlayJackCardEvent extends ApplicationEvent {

	private Card card;
	private GameTable gameTable;
	private String playerId;

	public PlayJackCardEvent(Object source, Card card, GameTable gameTable, String playerId) {
		super(source);
		this.card = card;
		this.gameTable = gameTable;
		this.playerId = playerId;
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
	
}
