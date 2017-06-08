package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.GameTable;

public class CallMauEvent extends ApplicationEvent {
	
	private Integer gameTableId;
	private String playerId;

	public CallMauEvent(Object source, Integer gameTableId, String playerId) {
		super(source);
		this.gameTableId = gameTableId;
		this.playerId = playerId;
	}

	public Integer getGameTableId() {
		return gameTableId;
	}

	public String getPlayerId() {
		return playerId;
	}
}
