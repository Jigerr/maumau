package de.berlin.htw.kba.maumau.cardmaster.service;

import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.table.db.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Interface CardMasterService.
 */
public interface CardMasterService {

	/**
	 * Fill stack.
	 *
	 * @param stack
	 *            the stack
	 * @return the stack
	 */
	Stack fillStack(Stack stack);

	/**
	 * Shuffle stack.
	 *
	 * @param stack
	 *            the stack
	 * @return the stack
	 */
	Stack shuffleStack(Stack stack);

	/**
	 * Show cards.
	 *
	 * @param player the player
	 * @param drawingStack the drawing stack
	 * @param amointOfCards the amoint of cards
	 */
	// void showCards(Stack stack);

	/**
	 * Fill hands.
	 *
	 * @param player
	 *            the player
	 * @param drawingStack
	 *            the drawing stack
	 */
	void fillHands(Player player, Stack drawingStack, int amointOfCards);

}
