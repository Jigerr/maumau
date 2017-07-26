package de.berlin.htw.kba.maumau.cardmaster.service;

// TODO: Auto-generated Javadoc
/**
 * The Enum Ranks.
 */
public enum Ranks {

	/** The seven. */
	SEVEN("7"),
	
	/** The eight. */
	EIGHT("8"),
	
	/** The nine. */
	NINE("9"),
	
	/** The ten. */
	TEN("10"),
	
	/** The jack. */
	JACK("Jack"),
	
	/** The queen. */
	QUEEN("Queen"),
	
	/** The king. */
	KING("King"),
	
	/** The ace. */
	ACE("Ace");
	
	/** The rank. */
	private String rank;

	/**
	 * Instantiates a new ranks.
	 *
	 * @param rank the rank
	 */
	Ranks(String rank) {
		this.rank = rank;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return rank;
	}
}
