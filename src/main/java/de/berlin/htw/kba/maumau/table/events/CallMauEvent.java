package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.GameTable;

// TODO: Auto-generated Javadoc
/**
 * The Class CallMauEvent.
 */
public class CallMauEvent extends ApplicationEvent {
	
	/** The game table id. */
	private Integer gameTableId;
	
	/** The player id. */
	private String playerId;

	/**
	 * Instantiates a new call mau event.
	 *
	 * @param source the source
	 * @param gameTableId the game table id
	 * @param playerId the player id
	 */
	public CallMauEvent(Object source, Integer gameTableId, String playerId) {
		super(source);
		this.gameTableId = gameTableId;
		this.playerId = playerId;
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
}
