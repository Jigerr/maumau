package de.berlin.htw.kba.maumau.table.test;

import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.GameTable;

public interface TableDummyService {

    GameTable getNewTableDummy();

    Player getPlayer1();

    Player getPlayer2();

}
