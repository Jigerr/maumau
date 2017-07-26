package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class GameModeEvent.
 */
public class GameModeEvent extends ApplicationEvent {

    /** The game mode. */
    private String gameMode;

    /**
     * Instantiates a new game mode event.
     *
     * @param source the source
     * @param gameMode the game mode
     */
    public GameModeEvent(Object source, String gameMode) {
        super(source);
        this.gameMode = gameMode;
    }

    /**
     * Gets the game mode.
     *
     * @return the game mode
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Sets the game mode.
     *
     * @param gameMode the new game mode
     */
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

}
