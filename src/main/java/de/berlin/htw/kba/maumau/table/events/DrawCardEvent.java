package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.GameTable;

public class DrawCardEvent extends ApplicationEvent {
	
	private GameTable gameTable;
	private String playerId;

	public DrawCardEvent(Object source, GameTable gameTable, String playerId) {
		super(source);
		this.gameTable = gameTable;
		this.playerId = playerId;
	}

	public GameTable getGameTable() {
		return gameTable;
	}

	public String getPlayerId() {
		return playerId;
	}
	
}
