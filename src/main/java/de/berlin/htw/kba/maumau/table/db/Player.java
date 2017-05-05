package de.berlin.htw.kba.maumau.table.db;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player {

    /** The current account. */
    private String currentAccount;
    
    /** The Player id. */
    private String PlayerId;
    
    /** The hand. */
    private LinkedList<Card> hand = new LinkedList<>();
    
    /** The called mau. */
    private boolean calledMau = false;

    /**
     * Instantiates a new player.
     *
     * @param playerId the player id
     * @param accountId the account id
     */
    public Player(String playerId, String accountId) {
        this.PlayerId = playerId;
        this.currentAccount = accountId;
    }

    /**
     * Gets the hand.
     *
     * @return the hand
     */
    public LinkedList<Card> getHand() {
        return hand;
    }

    /**
     * Sets the hand.
     *
     * @param hand the new hand
     */
    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

    /**
     * Adds the card.
     *
     * @param newCard the new card
     */
    public void addCard(Card newCard) {
        this.hand.add(newCard);
    }

    /**
     * Gets the current account.
     *
     * @return the current account
     */
    public String getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Sets the current account.
     *
     * @param accountId the new current account
     */
    public void setCurrentAccount(String accountId) {
        this.currentAccount = accountId;
    }

    /**
     * Gets the player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return PlayerId;
    }

    /**
     * Sets the player id.
     *
     * @param playerId the new player id
     */
    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

	/**
	 * Checks if player called mau.
	 *
	 * @return true, if has called mau
	 */
	public boolean hasCalledMau() {
		return calledMau;
	}

	/**
	 * Sets called mau.
	 *
	 * @param calledMau the new called mau
	 */
	public void setCalledMau(boolean calledMau) {
		this.calledMau = calledMau;
	}

}
