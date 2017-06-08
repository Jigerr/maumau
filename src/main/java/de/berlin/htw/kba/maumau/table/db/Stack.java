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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The Class Stack.
 */
@Entity
@Table(name = "STACK")
public class Stack {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Integer Id;
	
	/** The stack. */
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="STACK_ID")
	@OrderColumn
	private List<Card> cardList = new ArrayList<>();

	/**
	 * Instantiates a new stack.
	 */
	public Stack() {

	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	/**
	 * Gets the stack.
	 *
	 * @return the stack
	 */
	public List<Card> getCardList() {
		return cardList;
	}

	/**
	 * Sets the stack.
	 *
	 * @param deck
	 *            the new stack
	 */
	public void setCardList(List<Card> deck) {
		this.cardList = deck;
	}

	/**
	 * Amount of cards.
	 *
	 * @return the int
	 */
	public int amountOfCards() {
		return cardList.size();
	}

}