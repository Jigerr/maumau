package de.berlin.htw.kba.maumau.cardmaster.service;

public enum Ranks {

	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("10"),
	JACK("Jack"),
	QUEEN("Queen"),
	KING("King"),
	ACE("Ace");
	
	private String rank;

	Ranks(String rank) {
		this.rank = rank;
	}

	public String getRank() {
		return rank;
	}
}
