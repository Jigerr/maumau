package de.berlin.htw.kba.maumau.table.db;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class Stack.
 */
public class Stack {

	/** The stack. */
	private LinkedList<Card> stack = new LinkedList<>();

	/**
	 * Instantiates a new stack.
	 */
	public Stack() {

	}

	/**
	 * Gets the stack.
	 *
	 * @return the stack
	 */
	public LinkedList<Card> getStack() {
		return stack;
	}

	/**
	 * Sets the stack.
	 *
	 * @param deck the new stack
	 */
	public void setStack(LinkedList<Card> deck) {
		this.stack = deck;
	}

	/**
	 * Amount of cards.
	 *
	 * @return the int
	 */
	public int amountOfCards() {
		return stack.size();
	}

}