package de.berlin.htw.kba.maumau.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.berlin.htw.kba.maumau.model.Card;
import de.berlin.htw.kba.maumau.model.Stack;

public class MauMasterImpl implements MauMaster {

	public MauMasterImpl() {
	}

	@Override
	public Stack initDeck(Stack stack) {

		// auslagern als enum
		List<String> suits = new ArrayList<>(Arrays.asList("Heart", "Diamond", "Club", "Spade"));

		// auslagern als enum
		List<String> ranks = new ArrayList<>(Arrays.asList("7", "8", "9", "10", "Jack", "Queen", "King", "Ace"));

		for (String suit : suits) {
			for (String rank : ranks) {
				Card card = new Card(suit, rank);
				stack.getDeck().add(card);
				System.out.println("Card " + card.getSuit() + "|" + card.getRank() + " added to Stack...");
			}
		}
		return stack;
	}

	@Override
    public Stack shuffleDeck(Stack stack) {

        LinkedList<Card> temp = new LinkedList<>();

        while ( !stack.getDeck().isEmpty() ) {
            int loc = (int) ( Math.random() * stack.getDeck().size() );
            temp.add(stack.getDeck().get(loc));
            stack.getDeck().remove(loc);
        }

        stack.setDeck(temp);

        return stack;
    }

	@Override
	public void showCards(Stack stack) {
		System.out.println("\n\n Showing Cards !!!");
		for(Card card : stack.getDeck()) {
			System.out.println("Card " + card.getSuit() + "|" + card.getRank());
		}
	}

	// public Card getCardFromDeck() {
	// Card temp = this.deck.getFirst();
	// this.deck.remove(this.deck.getFirst());
	// return temp;
	// }
}
