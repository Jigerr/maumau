package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class DoTurnEvent.
 */
public class DoTurnEvent extends ApplicationEvent{
    
    /** The game table id. */
    private Integer gameTableId;

    /**
     * Instantiates a new do turn event.
     *
     * @param source the source
     * @param gameTableId the game table id
     */
    public DoTurnEvent(Object source, Integer gameTableId) {
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
