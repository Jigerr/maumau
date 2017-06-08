package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

public class PlayCardEvent extends ApplicationEvent {

	private Card card;
	private Integer gameTableId;
	private String playerId;
	private Suits wishedSuit;

	public PlayCardEvent(Object source, Card card, Integer gameTableId, String playerId, Suits wishedSuit) {
		super(source);
		this.card = card;
		this.gameTableId = gameTableId;
		this.playerId = playerId;
		this.wishedSuit = wishedSuit;
	}
	
	public Card getCard() {
		return card;
	}

	public Integer getGameTableId() {
		return gameTableId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public Suits getWishedSuit() {
		return wishedSuit;
	}

}
