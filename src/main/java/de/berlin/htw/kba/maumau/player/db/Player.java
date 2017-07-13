package de.berlin.htw.kba.maumau.player.db;

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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import de.berlin.htw.kba.maumau.table.db.Card;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
@Entity
@Table(name = "PLAYER")
public class Player {
	
	/** The Id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Integer Id;

	/** The controlled by. */
	@Column(name = "CONTROLLED_BY")
	private String controlledBy;

	/** The Player id. */
	@Column(name = "TABLE_PLAYER_ID")
	private String PlayerId;

	/** The hand. */
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="PLAYER_ID")
	@OrderColumn
	private List<Card> hand = new ArrayList<>();

	/** The called mau. */
	private boolean calledMau = false;

	/**
	 * Instantiates a new player.
	 */
	protected Player() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new player.
	 *
	 * @param playerId            the player id
	 */
	public Player(String playerId) {
		this.PlayerId = playerId;
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
	 * Gets the controlled by.
	 *
	 * @return the controlled by
	 */
	public String getControlledBy() {
        return controlledBy;
    }

    /**
     * Sets the controlled by.
     *
     * @param controlledBy the new controlled by
     */
    public void setControlledBy(String controlledBy) {
        this.controlledBy = controlledBy;
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		Id = id;
	}

}
