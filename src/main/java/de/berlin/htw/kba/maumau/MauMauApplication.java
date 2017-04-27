package de.berlin.htw.kba.maumau;

import de.berlin.htw.kba.maumau.engine.CardMaster;
import de.berlin.htw.kba.maumau.engine.CardMasterImpl;
import de.berlin.htw.kba.maumau.model.Stack;

public class MauMauApplication {

    //private MauMaster mauMaster;

    public static void main(String[] args) {

        Stack stack = new Stack();
        System.out.println("Anzahl der Karten im Stack: " + stack.amountOfCards());

        CardMaster mauMaster = new CardMasterImpl();

        mauMaster.initDeck(stack);

        mauMaster.showCards(stack);
        System.out.println("Anzahl der Karten im Stack: " + stack.amountOfCards());

        mauMaster.shuffleDeck(stack);

        mauMaster.showCards(stack);
        System.out.println("Anzahl der Karten im Stack: " + stack.amountOfCards());

    }
}
