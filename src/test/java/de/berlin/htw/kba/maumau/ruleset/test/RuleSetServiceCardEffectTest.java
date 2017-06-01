package de.berlin.htw.kba.maumau.ruleset.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.berlin.htw.kba.maumau.ruleset.service.CardEffects;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Card;

@RunWith(Parameterized.class)
public class RuleSetServiceCardEffectTest {

	private RuleSetService ruleSet;

	private Card currentCard;
	private CardEffects expectedResult;
	
	@Before
	public void setUp() {
	    ruleSet = new RuleSetServiceImpl();
	}	

	public RuleSetServiceCardEffectTest(Card currentCard, CardEffects expectedResult) {
		this.currentCard = currentCard;
		this.expectedResult = expectedResult;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			{new Card("Hearts", "8"), CardEffects.NO_EFFECT},
			{new Card("Hearts", "Jack"), CardEffects.WISH},
			{new Card("Diamonds", "Ace"), CardEffects.SKIP},
			{new Card("Clubs", "7"), CardEffects.PLUS_TWO},
			{new Card("Hearts", "7"), CardEffects.PLUS_TWO},
			{new Card("Spades", "10"), CardEffects.NO_EFFECT},
			{new Card("Clubs", "Jack"), CardEffects.WISH},
		});
	}
	
	@Test
	public void testGetCardEffect() {

		// get 100% coverage
		CardEffects.valueOf(CardEffects.NO_EFFECT.toString());

		Assert.assertEquals(expectedResult, ruleSet.getCardEffect(currentCard));
	}

}
