package de.berlin.htw.kba.maumau.ruleset.service;

import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.model.Card;

@Service
public class RuleSetImpl implements RuleSet {

	@Override
	public boolean turnAllowed(Card currentCard, Card lastPlayedCard, Conditions condition) {

		switch (condition) {

		case NO_EFFECT:
			if (checkIfJack(currentCard)) {
				return true;
			} else {
				return checkBasicRules(currentCard, lastPlayedCard);
			}
		case PLUS_TWO:
		case PLUS_FOUR:
		case PLUS_SIX:
		case PLUS_EIGHT:
			return checkDrawRule(currentCard, lastPlayedCard);
		case WISH_CLUBS:
		case WISH_DIAMONDS:
		case WISH_HEARTS:
		case WISH_SPADES:
			if (checkJackOnJackRule(currentCard, lastPlayedCard)) {
				return false;
			} else {
				return checkWishRule(currentCard, condition);
			}
		default:
			return false;
		}
	}

	private boolean checkWishRule(Card currentCard, Conditions condition) {
		return (currentCard.getSuit().equals(condition.getValue()));
	}

	@Override
	public String getCardEffect(Card card) {

		return null;
	}

	private boolean checkBasicRules(Card currentCard, Card lastPlayedCard) {
		if ((lastPlayedCard.getSuit().equals(currentCard.getSuit()))
				|| (lastPlayedCard.getRank().equals(currentCard.getRank()))) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkIfJack(Card currentCard) {
		return (currentCard.getRank().equals("Jack"));
	}

	private boolean checkJackOnJackRule(Card currentCard, Card lastPlayedCard) {
		return (lastPlayedCard.getRank().equals("Jack") && currentCard.getRank().equals("Jack"));
	}

	private boolean checkDrawRule(Card currentCard, Card lastPlayedCard) {
		return (lastPlayedCard.getRank().equals(currentCard.getRank()));
	}

}
