package de.berlin.htw.kba.maumau.cardmaster.service;

import java.util.LinkedList;

import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Stack;

public class CardMasterServiceImpl implements CardMasterService {

	public CardMasterServiceImpl() {
	}

	@Override
	public Stack fillStack(Stack stack) {

		if (!stack.getStack().isEmpty()) {
			stack = new Stack();
		}

		for (Suits suit : Suits.values()) {
			for (Ranks rank : Ranks.values()) {
				Card card = new Card(suit.getSuit(), rank.getRank());
				stack.getStack().add(card);
				// System.out.println("Card: " + card.getRank() + " of " +
				// card.getSuit() + " added to Stack...");
			}
		}
		return stack;
	}

	@Override
	public Stack shuffleStack(Stack stack) {

		LinkedList<Card> temp = new LinkedList<>();

		while (!stack.getStack().isEmpty()) {
			int loc = (int) (Math.random() * stack.getStack().size());
			temp.add(stack.getStack().get(loc));
			stack.getStack().remove(loc);
		}

		stack.setStack(temp);

		return stack;
	}

	@Override
	public void fillHands(Player player, Stack drawingStack) {
		for (int i = 0; i < 5; i++) {
			player.addCard(drawingStack.getStack().getLast());
			drawingStack.getStack().removeLast();
		}
	}

	// @Override
	// public void showCards(Stack stack) {
	// System.out.println("\n\n Showing Cards !!!");
	// for (Card card : stack.getStack()) {
	// System.out.println("Card: " + card.getRank() + " of " + card.getSuit());
	// }
	// }
}
