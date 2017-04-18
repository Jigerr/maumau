package de.berlin.htw.kba.maumau.engine;

import de.berlin.htw.kba.maumau.model.Stack;


public interface MauMaster {

    Stack initDeck(Stack stack);

    Stack shuffleDeck(Stack stack);
    
    void showCards(Stack stack);

}
