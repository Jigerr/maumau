package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class AmountOfCardsEvent.
 */
public class AmountOfCardsEvent extends ApplicationEvent {
    
    /** The amount of cards. */
    private int amountOfCards;
    
    /** The game mode. */
    private String gameMode;

    /**
     * Instantiates a new amount of cards event.
     *
     * @param source the source
     * @param amountOfCards the amount of cards
     * @param gameMode the game mode
     */
    public AmountOfCardsEvent(Object source, int amountOfCards, String gameMode) {
        super(source);
        this.amountOfCards = amountOfCards;
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
