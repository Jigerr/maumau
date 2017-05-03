package de.berlin.htw.kba.maumau.cardmaster.service;

public enum Suits {
	
	HEARTS("Hearts"),
	DIAMONDS("Diamonds"),
	SPADES("Spades"),
	CLUBS("Clubs");

	private String suit;

	Suits(String suit) {
		this.suit = suit;
	}

	public String getSuit() {
		return suit;
	}

}
