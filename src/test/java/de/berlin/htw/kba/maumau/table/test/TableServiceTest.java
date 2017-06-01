package de.berlin.htw.kba.maumau.table.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.ruleset.service.Conditions;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

public class TableServiceTest {

    private static final String PLAYER_ONE_ACCOUNT_ID = "1";
    private static final String TABLE_ID = "1";

    private CardMasterService cardMasterService;    
    private TableDummyService tableDummyService;
    private TableService tableService;
    
    private Table table;
    private Player player1;
    private Player player2;

    @Before
    public void init() {
        cardMasterService = new CardMasterServiceImpl();
        tableDummyService = new TableDummyServiceImpl();
        RuleSetService ruleSetService = Mockito.mock(RuleSetService.class);
        tableService = new TableServiceImpl(ruleSetService, cardMasterService);  
        table = tableDummyService.getNewTableDummy();
        player1 = tableDummyService.getPlayer1();
        player2 = tableDummyService.getPlayer2();
        tableService.getOpenTables().add(table);
    }

    @After
    public void tearDown() {
        tableService.getOpenTables().clear();
    }

    @Test
    public void testIfPlayerDrewOneCard() {
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.getHand().size() == 6 && table.getDrawingStack().getStack().size() == 20);
    }

    @Test
    public void testIfPlayerDrewTwoCards() {
        table.setCondition(Conditions.PLUS_TWO);
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.getHand().size() == 7 && table.getDrawingStack().getStack().size() == 19);
    }

    @Test
    public void testIfTurnEnded() {
        Assert.assertTrue(table.getCurrentPlayer().equals(player1));
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(table.getCurrentPlayer().equals(player2));
    }

    @Test
    public void testIfPlayerCalledMau() {
        tableService.callMau(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.hasCalledMau());
    }

}
