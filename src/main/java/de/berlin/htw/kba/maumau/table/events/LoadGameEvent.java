package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.GameTable;

public class LoadGameEvent extends ApplicationEvent {

	private GameTable gameTable;

	public LoadGameEvent(Object source, GameTable gameTable) {
		super(source);
		this.gameTable = gameTable;
	}

	public GameTable getGameTable() {
		return gameTable;
	}

}
