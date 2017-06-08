package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.GameTable;

public class LoadGameEvent extends ApplicationEvent {

	private Integer gameTableId;

	public LoadGameEvent(Object source, Integer gameTableId) {
		super(source);
		this.gameTableId = gameTableId;
	}

	public Integer getGameTableId() {
		return gameTableId;
	}

}
