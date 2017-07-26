/*
 * 
 */
package de.berlin.htw.kba.maumau.table.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.berlin.htw.kba.maumau.cardmaster.service.Ranks;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.ruleset.service.CardEffect;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.ruleset.service.WrongCardException;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.TableRepository;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class TableServiceMockTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class TableServiceMockTest {

    /** The Constant PLAYER_ONE_ACCOUNT_ID. */
    private static final String PLAYER_ONE_ACCOUNT_ID = "1";
    
    /** The Constant TABLE_ID. */
    private static final Integer TABLE_ID = 1;

    /** The table dummy service. */
    private TableDummyService tableDummyService;

    /** The rule set service. */
    @Mock
    private RuleSetService ruleSetService;

    /** The repository. */
    @Mock
    private TableRepository repository;

    /** The table service. */
    @InjectMocks
    private TableServiceImpl tableService;

    /** The game table. */
    private GameTable gameTable;
    
    /** The player 1. */
    private Player player1;

    /**
     * Inits the.
     */
    @Before
    public void init() {
        tableDummyService = new TableDummyServiceImpl();
        gameTable = tableDummyService.getNewTableDummy();
        player1 = tableDummyService.getPlayer1();
        Mockito.when(repository.save(gameTable)).thenReturn(null);
        Mockito.when(repository.findOne(gameTable.getGameTableID())).thenReturn(gameTable);
    }

    /**
     * Test plus six condition.
     *
     * @throws WrongCardException the wrong card exception
     */
    @Test
    public void testPlusSixCondition() throws WrongCardException {
        Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.SEVEN.getValue());
        player1.addCard(playCard);
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.CLUBS.getSuit(), Ranks.SEVEN.getValue()));
        gameTable.setCondition(Condition.PLUS_FOUR);

        Mockito.when(ruleSetService.turnAllowed(playCard,
                gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1),
                Condition.PLUS_FOUR)).thenReturn(true);
        Mockito.when(ruleSetService.getCardEffect(playCard)).thenReturn(CardEffect.PLUS_TWO);

        tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard, null);

        Assert.assertTrue(gameTable.getCondition().equals(Condition.PLUS_SIX));
    }

}
