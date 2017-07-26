package de.berlin.htw.kba.maumau.table.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Card.
 */
@Entity
@Table(name = "CARD")
public class Card {
	
	/** The Id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Integer Id;
	
	/** The suit. */
	@Column(name = "SUIT")
	private String suit;

	/** The rank. */
	@Column(name = "RANK")
	private String rank;

	/**
	 * Instantiates a new card.
	 */
	public Card() {
	}
	/**
	 * Instantiates a new card.
	 *
	 * @param suit
	 *            the suit
	 * @param rank
	 *            the rank
	 */
	public Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
	}

	/**
	 * Instantiates a new card.
	 *
	 * @param card the card
	 */
	public Card(Card card) {
		this.suit = card.getSuit();
		this.rank = card.getRank();
	}
	
	/**
	 * Prints the card.
	 *
	 * @return the string
	 */
	public String printCard() {
		return rank + " of " + suit;
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

	/**
	 * Gets the suit.
	 *
	 * @return the suit
	 */
	public String getSuit() {
		return suit;
	}

	/**
	 * Sets the suit.
	 *
	 * @param suit
	 *            the new suit
	 */
	public void setSuit(String suit) {
		this.suit = suit;
	}

	/**
	 * Gets the rank.
	 *
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * Sets the rank.
	 *
	 * @param rank
	 *            the new rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
}
