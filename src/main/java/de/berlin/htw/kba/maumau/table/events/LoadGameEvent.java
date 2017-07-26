package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.GameTable;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadGameEvent.
 */
public class LoadGameEvent extends ApplicationEvent {

	/** The game table id. */
	private Integer gameTableId;

	/**
	 * Instantiates a new load game event.
	 *
	 * @param source the source
	 * @param gameTableId the game table id
	 */
	public LoadGameEvent(Object source, Integer gameTableId) {
		super(source);
		this.gameTableId = gameTableId;
	}

	/**
	 * Gets the game table id.
	 *
	 * @return the game table id
	 */
	public Integer getGameTableId() {
		return gameTableId;
	}

}
