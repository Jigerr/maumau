/*
 * 
 */
package de.berlin.htw.kba.maumau.ruleset.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.berlin.htw.kba.maumau.ruleset.service.CardEffect;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Card;

// TODO: Auto-generated Javadoc
/**
 * The Class RuleSetServiceCardEffectTest.
 */
@RunWith(Parameterized.class)
public class RuleSetServiceCardEffectTest {

	/** The rule set. */
	private RuleSetService ruleSet;

	/** The current card. */
	private Card currentCard;
	
	/** The expected result. */
	private CardEffect expectedResult;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
	    ruleSet = new RuleSetServiceImpl();
	}	

	/**
	 * Instantiates a new rule set service card effect test.
	 *
	 * @param currentCard the current card
	 * @param expectedResult the expected result
	 */
	public RuleSetServiceCardEffectTest(Card currentCard, CardEffect expectedResult) {
		this.currentCard = currentCard;
		this.expectedResult = expectedResult;
	}
	
	/**
	 * Data.
	 *
	 * @return the collection
	 */
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			{new Card("Hearts", "8"), CardEffect.NO_EFFECT},
			{new Card("Hearts", "Jack"), CardEffect.WISH},
			{new Card("Diamonds", "Ace"), CardEffect.SKIP},
			{new Card("Clubs", "7"), CardEffect.PLUS_TWO},
			{new Card("Hearts", "7"), CardEffect.PLUS_TWO},
			{new Card("Spades", "10"), CardEffect.NO_EFFECT},
			{new Card("Clubs", "Jack"), CardEffect.WISH},
		});
	}
	
	/**
	 * Test get card effect.
	 */
	@Test
	public void testGetCardEffect() {

		// get 100% coverage
		CardEffect.valueOf(CardEffect.NO_EFFECT.toString());

		Assert.assertEquals(expectedResult, ruleSet.getCardEffect(currentCard));
	}

}
