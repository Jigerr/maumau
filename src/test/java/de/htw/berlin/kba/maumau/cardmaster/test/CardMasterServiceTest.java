/*
 * 
 */
package de.htw.berlin.kba.maumau.cardmaster.test;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class CardMasterServiceTest.
 */
public class CardMasterServiceTest {

	/** The card master. */
	private CardMasterService cardMaster = new CardMasterServiceImpl();
	
	/** The stack. */
	private Stack stack;
	
	/** The Constant PLAYER_ONE_ID. */
	private static final String PLAYER_ONE_ID = "1";
	
	/** The Constant DECK_SIZE. */
	private static final int DECK_SIZE = 32;

	/**
	 * Inits the stack.
	 */
	@Before
	public void initStack() {
		stack = new Stack();
		cardMaster.fillStack(stack);
	}

	/**
	 * Test if stack is filled.
	 */
	@Test
	public void testIfStackIsFilled() {
		Assert.assertFalse(stack.getCardList().isEmpty());
	}

	/**
	 * Test if filled with 32 cards.
	 */
	@Test
	public void testIfFilledWith32Cards() {
		Assert.assertTrue(stack.getCardList().size() == DECK_SIZE);
	}

	/**
	 * Test if filled with 32 cards 2.
	 */
	@Test
	public void testIfFilledWith32Cards2() {
		cardMaster.fillStack(stack);
		Assert.assertTrue(stack.getCardList().size() == DECK_SIZE);
	}

	/**
	 * Test if stack is shuffeld.
	 */
	@Test
	public void testIfStackIsShuffeld() {
		LinkedList<Card> originalStack = new LinkedList<Card>(stack.getCardList());
		// Assert.assertEquals(originalStack, stack.getStack());
		cardMaster.shuffleStack(stack);
		Assert.assertNotEquals(originalStack, stack.getCardList());
	}

	/**
	 * Test if player hand is filled.
	 */
	@Test
	public void testIfPlayerHandIsFilled() {
		Player player = new Player(PLAYER_ONE_ID);
		cardMaster.fillHands(player, stack, 5);
		Assert.assertTrue(player.getHand().size() == 5);
	}

}
