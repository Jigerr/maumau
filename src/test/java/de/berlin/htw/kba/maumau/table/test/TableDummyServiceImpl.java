package de.berlin.htw.kba.maumau.table.test;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;

public class TableDummyServiceImpl implements TableDummyService {

    private Table tableDummy;
    private Player player1;
    private Player player2;

    private CardMasterService cardMasterService = new CardMasterServiceImpl();

    public TableDummyServiceImpl() {
        tableDummy = new Table();
        tableDummy.setTableID("1");
        player1 = new Player("1", "1");
        player2 = new Player("2", "2");
        cardMasterService.fillStack(tableDummy.getDrawingStack());
        cardMasterService.shuffleStack(tableDummy.getDrawingStack());
        cardMasterService.fillHands(player1, tableDummy.getDrawingStack());
        cardMasterService.fillHands(player2, tableDummy.getDrawingStack());
        tableDummy.getPlayers().add(player1);
        tableDummy.getPlayers().add(player2);
        tableDummy.setCurrentPlayer(player1);
        tableDummy.getDrawingStack().getStack().removeLast();
    }

    @Override
    public Table getNewTableDummy() {
        return tableDummy;
    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }

}
