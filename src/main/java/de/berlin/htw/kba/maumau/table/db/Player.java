package de.berlin.htw.kba.maumau.table.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The Class Player.
 */
@Entity
@Table(name = "PLAYER")
public class Player {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Integer Id;

	/** The current account. */
	@Column(name = "ACCOUNT_ID")
	private String accountId;

	/** The Player id. */
	@Column(name = "TABLE_PLAYER_ID")
	private String PlayerId;

	/** The hand. */
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="PLAYER_ID")
	private List<Card> hand = new ArrayList<>();

	/** The called mau. */
	private boolean calledMau = false;

	protected Player() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Instantiates a new player.
	 *
	 * @param playerId
	 *            the player id
	 * @param accountId
	 *            the account id
	 */
	public Player(String playerId, String accountId) {
		this.PlayerId = playerId;
		this.accountId = accountId;
	}

	/**
	 * Gets the hand.
	 *
	 * @return the hand
	 */
	public List<Card> getHand() {
		return hand;
	}

	/**
	 * Sets the hand.
	 *
	 * @param hand
	 *            the new hand
	 */
	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	/**
	 * Adds the card.
	 *
	 * @param newCard
	 *            the new card
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
		return accountId;
	}

	/**
	 * Sets the current account.
	 *
	 * @param accountId
	 *            the new current account
	 */
	public void setCurrentAccount(String accountId) {
		this.accountId = accountId;
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
	 * @param playerId
	 *            the new player id
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
	 * @param calledMau
	 *            the new called mau
	 */
	public void setCalledMau(boolean calledMau) {
		this.calledMau = calledMau;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

}
