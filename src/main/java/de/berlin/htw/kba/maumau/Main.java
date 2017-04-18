package de.berlin.htw.kba.maumau;

import de.berlin.htw.kba.maumau.engine.MauMaster;
import de.berlin.htw.kba.maumau.engine.MauMasterImpl;
import de.berlin.htw.kba.maumau.model.Stack;


public class Main {
	
	//private MauMaster mauMaster;

    public static void main(String[] args) {

        Stack stack = new Stack();
        System.out.println("Anzahl der Karten im Stack: " + stack.amountOfCards());

        MauMaster mauMaster = new MauMasterImpl();

        mauMaster.initDeck(stack);

        mauMaster.showCards(stack);
        System.out.println("Anzahl der Karten im Stack: " + stack.amountOfCards());

        mauMaster.shuffleDeck(stack);

        mauMaster.showCards(stack);
        System.out.println("Anzahl der Karten im Stack: " + stack.amountOfCards());

    }
}
