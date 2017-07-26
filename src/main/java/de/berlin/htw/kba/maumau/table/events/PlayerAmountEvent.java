package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerAmountEvent.
 */
public class PlayerAmountEvent extends ApplicationEvent {

    /** The player amount. */
    private String playerAmount;
    
    /** The game mode. */
    private String gameMode;
    
    /** The amount of cards. */
    private int amountOfCards;
    
    /**
     * Instantiates a new player amount event.
     *
     * @param source the source
     * @param playerAmount the player amount
     * @param gameMode the game mode
     * @param amountOfCards the amount of cards
     */
    public PlayerAmountEvent(Object source, String playerAmount, String gameMode, int amountOfCards) {
        super(source);
        this.playerAmount = playerAmount;
        this.gameMode = gameMode;
        this.setAmountOfCards(amountOfCards);
    }

    /**
     * Gets the player amount.
     *
     * @return the player amount
     */
    public String getPlayerAmount() {
        return playerAmount;
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

    /**
     * Gets the amount of cards.
     *
     * @return the amount of cards
     */
    public int getAmountOfCards() {
        return amountOfCards;
    }

    /**
     * Sets the amount of cards.
     *
     * @param amountOfCards the new amount of cards
     */
    public void setAmountOfCards(int amountOfCards) {
        this.amountOfCards = amountOfCards;
    }

}
