package de.berlin.htw.kba.maumau.table.db;

// TODO: Auto-generated Javadoc
/**
 * The Class Card.
 */
public class Card {
    
    /** The suit. */
    private String suit;
    
    /** The rank. */
    private String rank;
    
    /** The active. */
    private Boolean active = false;

    /**
     * Instantiates a new card.
     *
     * @param suit the suit
     * @param rank the rank
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Gets the suit.
     *
     * @return the suit
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Sets the suit.
     *
     * @param suit the new suit
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     * Gets the rank.
     *
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Sets the rank.
     *
     * @param rank the new rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }
}
