package de.berlin.htw.kba.maumau.ruleset.service;

import de.berlin.htw.kba.maumau.model.Card;

public interface RuleSet {

    String getCardEffect(Card card);

	boolean turnAllowed(Card currentCard, Card lastPlayedCard, Conditions condition);

}
