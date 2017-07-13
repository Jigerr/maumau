package de.htw.berlin.kba.maumau.cardmaster.test;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Stack;

public class CardMasterServiceTest {

	private CardMasterService cardMaster = new CardMasterServiceImpl();
	private Stack stack;
	private static final String PLAYER_ONE_ID = "1";
	private static final int DECK_SIZE = 32;

	@Before
	public void initStack() {
		stack = new Stack();
		cardMaster.fillStack(stack);
	}

	@Test
	public void testIfStackIsFilled() {
		Assert.assertFalse(stack.getCardList().isEmpty());
	}

	@Test
	public void testIfFilledWith32Cards() {
		Assert.assertTrue(stack.getCardList().size() == DECK_SIZE);
	}

	@Test
	public void testIfFilledWith32Cards2() {
		cardMaster.fillStack(stack);
		Assert.assertTrue(stack.getCardList().size() == DECK_SIZE);
	}

	@Test
	public void testIfStackIsShuffeld() {
		LinkedList<Card> originalStack = new LinkedList<Card>(stack.getCardList());
		// Assert.assertEquals(originalStack, stack.getStack());
		cardMaster.shuffleStack(stack);
		Assert.assertNotEquals(originalStack, stack.getCardList());
	}

	@Test
	public void testIfPlayerHandIsFilled() {
		Player player = new Player(PLAYER_ONE_ID);
		cardMaster.fillHands(player, stack);
		Assert.assertTrue(player.getHand().size() == 5);
	}

}
