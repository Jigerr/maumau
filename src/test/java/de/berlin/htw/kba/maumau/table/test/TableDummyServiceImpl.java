/*
 * 
 */
package de.berlin.htw.kba.maumau.table.test;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.table.db.GameTable;

// TODO: Auto-generated Javadoc
/**
 * The Class TableDummyServiceImpl.
 */
public class TableDummyServiceImpl implements TableDummyService {

    /** The table dummy. */
    private GameTable tableDummy;
    
    /** The player 1. */
    private Player player1;
    
    /** The player 2. */
    private Player player2;

    /** The card master service. */
    private CardMasterService cardMasterService = new CardMasterServiceImpl();

    /**
     * Instantiates a new table dummy service impl.
     */
    public TableDummyServiceImpl() {
        tableDummy = new GameTable();
        tableDummy.setGameTableID(1);
        player1 = new Player("1");
        player2 = new Player("2");
        cardMasterService.fillStack(tableDummy.getDrawingStack());
        cardMasterService.shuffleStack(tableDummy.getDrawingStack());
        cardMasterService.fillHands(player1, tableDummy.getDrawingStack(), 5);
        cardMasterService.fillHands(player2, tableDummy.getDrawingStack(), 5);
        tableDummy.getPlayers().add(player1);
        tableDummy.getPlayers().add(player2);
        tableDummy.setCurrentPlayer(player1);
        tableDummy.getDrawingStack().getCardList().remove(tableDummy.getDrawingStack().getCardList().size()-1);
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.test.TableDummyService#getNewTableDummy()
     */
    @Override
    public GameTable getNewTableDummy() {
        return tableDummy;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.test.TableDummyService#getPlayer1()
     */
    @Override
    public Player getPlayer1() {
        return player1;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.test.TableDummyService#getPlayer2()
     */
    @Override
    public Player getPlayer2() {
        return player2;
    }

}
