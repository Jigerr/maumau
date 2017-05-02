package de.berlin.htw.kba.maumau.ruleset.service;

import de.berlin.htw.kba.maumau.model.Card;

public interface RuleSet {

    boolean turnAllowed(Card currentCard, Card lastPlayedCard);

    String getCardEffect(Card card);

}
