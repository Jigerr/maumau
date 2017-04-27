package de.berlin.htw.kba.maumau.model;

import java.util.LinkedList;

public class Player {

    private String currentAccount;
    private String PlayerId;
    private LinkedList<Card> hand = new LinkedList<>();

    public Player(String playerId, String accountId) {
        this.PlayerId = playerId;
        this.currentAccount = accountId;
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

    public String getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String accountId) {
        this.currentAccount = accountId;
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

}
