package de.berlin.htw.kba.maumau.ruleset.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.model.Card;

@Service
public class RuleSetImpl implements RuleSet {

    @Override
    public boolean turnAllowed(Card currentCard, Card lastPlayedCard) {

        //remove
        System.out.println("Zug ungültig! Kartenwert oder Kartenfarbe stimmt nicht überein.");
        return false;
    }

    @Override
    public String getCardEffect(Card card) {

        return null;
    }

    private boolean checkBasicRules(Card currentCard, Card lastPlayedCard) {
        if ((lastPlayedCard.getSuit().equals(currentCard.getSuit())) || (lastPlayedCard.getRank().equals(currentCard.getRank()))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkAdvancedRules(Card currentCard, Card lastPlayedCard) {
        
        return false;
        
    }

}
