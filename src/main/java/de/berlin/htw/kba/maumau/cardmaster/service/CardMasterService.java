package de.berlin.htw.kba.maumau.cardmaster.service;

import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Stack;

public interface CardMasterService {

    Stack fillStack(Stack stack);

    Stack shuffleStack(Stack stack);

    void showCards(Stack stack);

    void fillHands(Player player, Stack drawingStack);

}
