package de.berlin.htw.kba.maumau.engine;

import de.berlin.htw.kba.maumau.model.Stack;

public interface CardMaster {

    Stack initDeck(Stack stack);

    Stack shuffleDeck(Stack stack);

    void showCards(Stack stack);

}
