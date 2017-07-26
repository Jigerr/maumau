package de.berlin.htw.kba.maumau.cardmaster.service;

// TODO: Auto-generated Javadoc
/**
 * The Enum Suits.
 */
public enum Suits {
	
	/** The hearts. */
	HEARTS("Hearts"),
	
	/** The diamonds. */
	DIAMONDS("Diamonds"),
	
	/** The spades. */
	SPADES("Spades"),
	
	/** The clubs. */
	CLUBS("Clubs");

	/** The suit. */
	private String suit;

	/**
	 * Instantiates a new suits.
	 *
	 * @param suit the suit
	 */
	Suits(String suit) {
		this.suit = suit;
	}

	/**
	 * Gets the suit.
	 *
	 * @return the suit
	 */
	public String getSuit() {
		return suit;
	}

}
