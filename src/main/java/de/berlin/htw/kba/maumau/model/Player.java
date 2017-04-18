package de.berlin.htw.kba.maumau.model;

import java.util.LinkedList;

public class Player {

    private String name;
    private LinkedList<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Card> getHand() {
        return hand;
    }

    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

    public void addCard(Card newCard) {
        this.hand.add(newCard);
    }


}
