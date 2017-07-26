package de.berlin.htw.kba.maumau.ruleset.service;

import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.cardmaster.service.Ranks;
import de.berlin.htw.kba.maumau.table.db.Card;

// TODO: Auto-generated Javadoc
/**
 * The Class RuleSetServiceImpl.
 */
@Service
public class RuleSetServiceImpl implements RuleSetService {
    
    /** The Constant WRONG_CARD_MESSAGE. */
    private final static String WRONG_CARD_MESSAGE = "You cannot play this card. Please choose a different one.";

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.ruleset.service.RuleSetService#turnAllowed(de.berlin.htw.kba.maumau.table.db.Card, de.berlin.htw.kba.maumau.table.db.Card, de.berlin.htw.kba.maumau.ruleset.service.Condition)
     */
    @Override
    public boolean turnAllowed(Card currentCard, Card lastPlayedCard, Condition condition) throws WrongCardException {

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
                    throw new WrongCardException(WRONG_CARD_MESSAGE);
                } else {
                    return checkWishRule(currentCard, condition);
                }
            default:
                throw new WrongCardException(WRONG_CARD_MESSAGE);
        }
    }

    /**
     * Check wish rule.
     *
     * @param currentCard the current card
     * @param condition the condition
     * @return true, if successful
     * @throws WrongCardException the wrong card exception
     */
    private boolean checkWishRule(Card currentCard, Condition condition) throws WrongCardException {
        if(currentCard.getSuit().equals(condition.getCondition())) {
            return true;
        } else {
            throw new WrongCardException(WRONG_CARD_MESSAGE);
        }       
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.ruleset.service.RuleSetService#getCardEffect(de.berlin.htw.kba.maumau.table.db.Card)
     */
    @Override
    public CardEffect getCardEffect(Card currentCard) {
        if (currentCard.getRank().equals(Ranks.SEVEN.getValue())) {
            return CardEffect.PLUS_TWO;
        } else if (currentCard.getRank().equals(Ranks.JACK.getValue())) {
            return CardEffect.WISH;
        } else if (currentCard.getRank().equals(Ranks.ACE.getValue())) {
            return CardEffect.SKIP;
        } else {
            return CardEffect.NO_EFFECT;
        }
    }

    /**
     * Check basic rules.
     *
     * @param currentCard the current card
     * @param lastPlayedCard the last played card
     * @return true, if successful
     * @throws WrongCardException the wrong card exception
     */
    private boolean checkBasicRules(Card currentCard, Card lastPlayedCard) throws WrongCardException {
        if ((lastPlayedCard.getSuit().equals(currentCard.getSuit()))
                || (lastPlayedCard.getRank().equals(currentCard.getRank()))) {
            return true;
        } else {
            throw new WrongCardException(WRONG_CARD_MESSAGE);
        }
    }

    /**
     * Check if jack.
     *
     * @param currentCard the current card
     * @return true, if successful
     */
    private boolean checkIfJack(Card currentCard) {
        return (currentCard.getRank().equals("Jack"));
    }

    /**
     * Check jack on jack rule.
     *
     * @param currentCard the current card
     * @param lastPlayedCard the last played card
     * @return true, if successful
     */
    private boolean checkJackOnJackRule(Card currentCard, Card lastPlayedCard) {
        return (lastPlayedCard.getRank().equals("Jack") && currentCard.getRank().equals("Jack"));
    }

    /**
     * Check draw rule.
     *
     * @param currentCard the current card
     * @param lastPlayedCard the last played card
     * @return true, if successful
     * @throws WrongCardException the wrong card exception
     */
    private boolean checkDrawRule(Card currentCard, Card lastPlayedCard) throws WrongCardException {
        if ((lastPlayedCard.getRank().equals(currentCard.getRank()))) {
            return true;
        } else {
            throw new WrongCardException(WRONG_CARD_MESSAGE);
        }

    }

}
