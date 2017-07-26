package de.berlin.htw.kba.maumau.table.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import de.berlin.htw.kba.maumau.cardmaster.service.Ranks;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.WrongCardException;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.events.BotTurnEvent;
import de.berlin.htw.kba.maumau.table.service.TableService;

// TODO: Auto-generated Javadoc
/**
 * The Class BotController.
 */
@Controller
public class BotController {

    /** The table service. */
    @Autowired
    private TableService tableService;

    /**
     * Instantiates a new bot controller.
     */
    public BotController() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Do turn.
     *
     * @param event the event
     */
    @EventListener
    private void doTurn(BotTurnEvent event) {

        GameTable gameTable = tableService.loadGame(event.getGameTableId());

        if (gameTable.getCondition().equals(Condition.SKIP)) {
            tableService.skipTurn(gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId());
        } else {
            if (gameTable.getCurrentPlayer().getHand().size() == 2) {
                tableService.callMau(gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId());
            }

            Boolean cardPlayed = false;
            for (Card c : gameTable.getCurrentPlayer().getHand()) {
                Suits wishedSuit = null;
                if (c.getRank().equals(Ranks.JACK.getValue())) {
                    wishedSuit = Suits.CLUBS;
                }
                try {
                    cardPlayed = tableService.playCard(gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId(), c, wishedSuit);
                    break;
                } catch (WrongCardException ex) {
                    //nothing to do here
                }
            }
            if (cardPlayed == false) {
                tableService.drawCards(gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId());
            }
        }
    }

}
