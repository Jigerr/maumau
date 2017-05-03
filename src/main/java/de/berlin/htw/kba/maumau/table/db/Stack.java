package de.berlin.htw.kba.maumau.table.db;

import java.util.LinkedList;

public class Stack {

	private LinkedList<Card> stack = new LinkedList<>();

	public Stack() {

	}

	public LinkedList<Card> getStack() {
		return stack;
	}

	public void setStack(LinkedList<Card> deck) {
		this.stack = deck;
	}

	public int amountOfCards() {
		return stack.size();
	}

}