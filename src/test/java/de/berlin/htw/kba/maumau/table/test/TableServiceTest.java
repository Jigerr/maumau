package de.berlin.htw.kba.maumau.table.test;

import org.junit.Before;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

public class TableServiceTest {
    
    private CardMasterService cardMasterService = new CardMasterServiceImpl();
    
    private TableService tableService = new TableServiceImpl();
    
    @Before
    public void init() {
        Table table = new Table();
        table.setTableID("1");
        cardMasterService.fillStack(table.getDrawingStack());
        cardMasterService.shuffleStack(table.getDrawingStack());
        Player player1 = new Player("1", "1");
        Player player2 = new Player("2", "2");
        cardMasterService.fillHands(player1, table.getDrawingStack());
        cardMasterService.fillHands(player2, table.getDrawingStack());
        table.getPlayers().add(player1);
        table.getPlayers().add(player2);
        table.setCurrentPlayer(player1.getPlayerId());
        table.getPlayingStack().getStack().add(table.getDrawingStack().getStack().getLast());
        table.getDrawingStack().getStack().removeLast();
        tableService.getOpenTables().add(table);
    }

}
