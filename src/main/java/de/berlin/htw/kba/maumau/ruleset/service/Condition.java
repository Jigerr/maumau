package de.berlin.htw.kba.maumau.ruleset.service;

// TODO: Auto-generated Javadoc
/**
 * The Enum Condition.
 */
public enum Condition {

	/** The no effect. */
	NO_EFFECT("NO_EFFECT"),
	
	/** The plus two. */
	PLUS_TWO("2"),
	
	/** The plus four. */
	PLUS_FOUR("4"),
	
	/** The plus six. */
	PLUS_SIX("6"),
	
	/** The plus eight. */
	PLUS_EIGHT("8"),
	
	/** The skip. */
	SKIP("SKIP"),
	
	/** The wish hearts. */
	WISH_HEARTS("Hearts"),
	
	/** The wish spades. */
	WISH_SPADES("Spades"),
	
	/** The wish diamonds. */
	WISH_DIAMONDS("Diamonds"),
	
	/** The wish clubs. */
	WISH_CLUBS("Clubs");
	
	/** The condition. */
	private String condition;

	/**
	 * Instantiates a new condition.
	 *
	 * @param condition the condition
	 */
	Condition(String condition) {
		this.condition = condition;
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
}
