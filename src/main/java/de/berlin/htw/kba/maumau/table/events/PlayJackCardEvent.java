package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;

public class PlayJackCardEvent extends ApplicationEvent {

	private Card card;
	private Integer gameTableId;
	private String playerId;

	public PlayJackCardEvent(Object source, Card card, Integer gameTableId, String playerId) {
		super(source);
		this.card = card;
		this.gameTableId = gameTableId;
		this.playerId = playerId;
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
	
}
