package de.berlin.htw.kba.maumau.ruleset.service;

import de.berlin.htw.kba.maumau.table.db.Card;

/**
 * The Interface RuleSetService.
 */
public interface RuleSetService {

	/**
	 * Gets the card effect.
	 *
	 * @param currentCard
	 *            the current card
	 * @return the card effect
	 */
	CardEffect getCardEffect(Card currentCard);

	/**
	 * Turn allowed.
	 *
	 * @param currentCard
	 *            the current card
	 * @param lastPlayedCard
	 *            the last played card
	 * @param condition
	 *            the condition
	 * @return true, if successful
	 */
	boolean turnAllowed(Card currentCard, Card lastPlayedCard, Condition condition);

}
