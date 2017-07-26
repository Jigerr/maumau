/*
 * 
 */
package de.berlin.htw.kba.maumau.table.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.table.db.TableRepository;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class TableServiceTest.
 */
public class TableServiceTest {

    /** The Constant PLAYER_ONE_ACCOUNT_ID. */
    private static final String PLAYER_ONE_ACCOUNT_ID = "1";
    
    /** The Constant TABLE_ID. */
    private static final Integer TABLE_ID = 1;

    /** The card master service. */
    private CardMasterService cardMasterService;    
    
    /** The table dummy service. */
    private TableDummyService tableDummyService;
    
    /** The table service. */
    private TableService tableService;
    
    /** The repository. */
    private TableRepository repository;
    
    /** The game table. */
    private GameTable gameTable;
    
    /** The player 1. */
    private Player player1;
    
    /** The player 2. */
    private Player player2;

    /**
     * Inits the.
     */
    @Before
    public void init() {
        cardMasterService = new CardMasterServiceImpl();
        tableDummyService = new TableDummyServiceImpl();
        RuleSetService ruleSetService = Mockito.mock(RuleSetService.class);
        repository = Mockito.mock(TableRepository.class);
        tableService = new TableServiceImpl(ruleSetService, cardMasterService, repository, null);  
        gameTable = tableDummyService.getNewTableDummy();
        player1 = tableDummyService.getPlayer1();
        player2 = tableDummyService.getPlayer2();
        Mockito.when(repository.findOne(gameTable.getGameTableID())).thenReturn(gameTable);
    }


    /**
     * Test if player drew one card.
     */
    @Test
    public void testIfPlayerDrewOneCard() {
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.getHand().size() == 6 && gameTable.getDrawingStack().getCardList().size() == 20);
    }

    /**
     * Test if player drew two cards.
     */
    @Test
    public void testIfPlayerDrewTwoCards() {
        gameTable.setCondition(Condition.PLUS_TWO);
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.getHand().size() == 7 && gameTable.getDrawingStack().getCardList().size() == 19);
    }

    /**
     * Test if turn ended.
     */
    @Test
    public void testIfTurnEnded() {
        Assert.assertTrue(gameTable.getCurrentPlayer().equals(player1));
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(gameTable.getCurrentPlayer().equals(player2));
    }

    /**
     * Test if player called mau.
     */
    @Test
    public void testIfPlayerCalledMau() {
        tableService.callMau(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.hasCalledMau());
    }

}
