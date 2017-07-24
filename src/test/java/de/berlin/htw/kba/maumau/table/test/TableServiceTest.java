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

public class TableServiceTest {

    private static final String PLAYER_ONE_ACCOUNT_ID = "1";
    private static final Integer TABLE_ID = 1;

    private CardMasterService cardMasterService;    
    private TableDummyService tableDummyService;
    private TableService tableService;
    private TableRepository repository;
    
    private GameTable gameTable;
    private Player player1;
    private Player player2;

    @Before
    public void init() {
        cardMasterService = new CardMasterServiceImpl();
        tableDummyService = new TableDummyServiceImpl();
        RuleSetService ruleSetService = Mockito.mock(RuleSetService.class);
        repository = Mockito.mock(TableRepository.class);
//        tableService = new TableServiceImpl(ruleSetService, cardMasterService, repository);  
        gameTable = tableDummyService.getNewTableDummy();
        player1 = tableDummyService.getPlayer1();
        player2 = tableDummyService.getPlayer2();
        Mockito.when(repository.findOne(gameTable.getGameTableID())).thenReturn(gameTable);
    }


    @Test
    public void testIfPlayerDrewOneCard() {
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.getHand().size() == 6 && gameTable.getDrawingStack().getCardList().size() == 20);
    }

    @Test
    public void testIfPlayerDrewTwoCards() {
        gameTable.setCondition(Condition.PLUS_TWO);
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.getHand().size() == 7 && gameTable.getDrawingStack().getCardList().size() == 19);
    }

    @Test
    public void testIfTurnEnded() {
        Assert.assertTrue(gameTable.getCurrentPlayer().equals(player1));
        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(gameTable.getCurrentPlayer().equals(player2));
    }

    @Test
    public void testIfPlayerCalledMau() {
        tableService.callMau(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
        Assert.assertTrue(player1.hasCalledMau());
    }

}
