package de.berlin.htw.kba.maumau.cardmaster.service;

import java.util.LinkedList;

import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class CardMasterServiceImpl.
 */
@Service
public class CardMasterServiceImpl implements CardMasterService {

	/**
	 * Instantiates a new card master service impl.
	 */
	public CardMasterServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService#fillStack(de.berlin.htw.kba.maumau.table.db.Stack)
	 */
	@Override
	public Stack fillStack(Stack stack) {

		if (!stack.getCardList().isEmpty()) {
			stack = new Stack();
		}

		for (Suits suit : Suits.values()) {
			for (Ranks rank : Ranks.values()) {
				Card card = new Card(suit.getSuit(), rank.getValue());
				stack.getCardList().add(card);
				// System.out.println("Card: " + card.getRank() + " of " +
				// card.getSuit() + " added to Stack...");
			}
		}
		return stack;
	}

	/* (non-Javadoc)
	 * @see de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService#shuffleStack(de.berlin.htw.kba.maumau.table.db.Stack)
	 */
	@Override
	public Stack shuffleStack(Stack stack) {

		LinkedList<Card> temp = new LinkedList<>();

		while (!stack.getCardList().isEmpty()) {
			int loc = (int) (Math.random() * stack.getCardList().size());
			temp.add(stack.getCardList().get(loc));
			stack.getCardList().remove(loc);
		}

		stack.setCardList(temp);

		return stack;
	}

	/* (non-Javadoc)
	 * @see de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService#fillHands(de.berlin.htw.kba.maumau.player.db.Player, de.berlin.htw.kba.maumau.table.db.Stack, int)
	 */
	@Override
	public void fillHands(Player player, Stack drawingStack, int amountOfCards) {
		for (int i = 0; i < amountOfCards ; i++) {
			player.addCard(drawingStack.getCardList().get(drawingStack.getCardList().size()-1));
			drawingStack.getCardList().remove(drawingStack.getCardList().size()-1);
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
