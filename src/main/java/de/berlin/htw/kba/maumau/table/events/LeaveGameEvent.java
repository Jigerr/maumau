package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

import de.berlin.htw.kba.maumau.table.db.GameTable;

// TODO: Auto-generated Javadoc
/**
 * The Class LeaveGameEvent.
 */
public class LeaveGameEvent extends ApplicationEvent {

    /** The game table. */
    private GameTable gameTable;

    /**
     * Instantiates a new leave game event.
     *
     * @param source the source
     * @param gameTable the game table
     */
    public LeaveGameEvent(Object source, GameTable gameTable) {
        super(source);
        this.gameTable = gameTable;
    }

    /**
     * Gets the game table.
     *
     * @return the game table
     */
    public GameTable getGameTable() {
        return gameTable;
    }

}
