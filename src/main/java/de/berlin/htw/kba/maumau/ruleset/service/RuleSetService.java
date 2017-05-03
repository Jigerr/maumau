package de.berlin.htw.kba.maumau.ruleset.service;

import de.berlin.htw.kba.maumau.table.db.Card;

public interface RuleSetService {

    CardEffects getCardEffect(Card currentCard);

	boolean turnAllowed(Card currentCard, Card lastPlayedCard, Conditions condition);

}
