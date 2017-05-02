package de.berlin.htw.kba.maumau.ruleset.service;

public enum Conditions {

	NO_EFFECT("NO_EFFECT"),
	PLUS_TWO("PLUS_TWO"),
	PLUS_FOUR("PLUS_FOUR"),
	PLUS_SIX("PLUS_SIX"),
	PLUS_EIGHT("PLUS_EIGHT"),
	SKIP("SKIP"),
	WISH_HEARTS("Hearts"),
	WISH_SPADES("Spades"),
	WISH_DIAMONDS("Diamonds"),
	WISH_CLUBS("Clubs");
	
	private String condition;
	
	Conditions(String condition) {
		this.condition = condition;
	}
	
	public String getValue() {
		return condition;
	}
}
