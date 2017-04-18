package de.berlin.htw.kba.maumau.model;

import java.util.LinkedList;

public class Stack {

    private LinkedList<Card> stack = new LinkedList<>();

    public Stack() {

    }

    public LinkedList<Card> getDeck() {
        return stack;
    }

    public void setDeck(LinkedList<Card> deck) {
        this.stack = deck;
    }

    public int amountOfCards() {
        return stack.size();
    }

}