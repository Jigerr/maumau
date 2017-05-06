package de.berlin.htw.kba.maumau.table.test;

import org.junit.Before;
import org.junit.Test;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.ruleset.service.Conditions;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

import org.junit.After;
import org.junit.Assert;

public class TableServiceTest {
	
	private static final String PLAYER_ONE_ID = "1";
	private static final String PLAYER_TWO_ID = "2";
	private static final String PLAYER_ONE_ACCOUNT_ID = "1";
	private static final String PLAYER_TWO_ACCOUNT_ID = "2";
	private static final String TABLE_ID = "1";
    
    private CardMasterService cardMasterService = new CardMasterServiceImpl();
    
    private TableService tableService = new TableServiceImpl();
    
    private Table table;
    private Player player1;
    private Player player2;
    
    @Before
    public void init() {    	
        table = new Table();
        table.setTableID(TABLE_ID);
        cardMasterService.fillStack(table.getDrawingStack());
        cardMasterService.shuffleStack(table.getDrawingStack());
        player1 = new Player(PLAYER_ONE_ID, PLAYER_ONE_ACCOUNT_ID);
        player2 = new Player(PLAYER_TWO_ID, PLAYER_TWO_ACCOUNT_ID);
        cardMasterService.fillHands(player1, table.getDrawingStack());
        cardMasterService.fillHands(player2, table.getDrawingStack());
        table.getPlayers().add(player1);
        table.getPlayers().add(player2);
        table.setCurrentPlayer(player1.getPlayerId());
        table.getPlayingStack().getStack().add(table.getDrawingStack().getStack().getLast());
        table.getDrawingStack().getStack().removeLast();
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
    	Assert.assertTrue(table.getCurrentPlayer().equals(PLAYER_ONE_ID));
    	tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
    	Assert.assertTrue(table.getCurrentPlayer().equals(PLAYER_TWO_ID));
    }    
    
    @Test
    public void testIfPlayerCalledMau() {
    	tableService.callMau(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);
    	Assert.assertTrue(player1.hasCalledMau());
    }


}
