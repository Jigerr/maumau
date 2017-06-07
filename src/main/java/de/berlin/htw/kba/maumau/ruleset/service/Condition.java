package de.berlin.htw.kba.maumau.ruleset.service;

public enum Condition {

	NO_EFFECT("NO_EFFECT"),
	PLUS_TWO("2"),
	PLUS_FOUR("4"),
	PLUS_SIX("6"),
	PLUS_EIGHT("8"),
	SKIP("SKIP"),
	WISH_HEARTS("Hearts"),
	WISH_SPADES("Spades"),
	WISH_DIAMONDS("Diamonds"),
	WISH_CLUBS("Clubs");
	
	private String condition;

	Condition(String condition) {
		this.condition = condition;
	}

	public String getCondition() {
		return condition;
	}
}
