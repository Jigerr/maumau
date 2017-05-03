package de.berlin.htw.kba.maumau.ruleset.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.berlin.htw.kba.maumau.ruleset.service.Conditions;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Card;

@RunWith(Parameterized.class)
public class RuleSetServiceTurnAllowedTest {

	private RuleSetService ruleSet = new RuleSetServiceImpl();
	
	private Card currentCard;
	private Card lastPlayedCard;
	private Conditions condition;
	private Boolean expectedResult;
	
	public RuleSetServiceTurnAllowedTest(Card currentCard, Card lastPlayedCard, Conditions condition, boolean expectedResult) {
		this.currentCard = currentCard;
		this.lastPlayedCard = lastPlayedCard;
		this.condition = condition;
		this.expectedResult = expectedResult;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			{new Card("Hearts", "8"),new Card("Hearts", "9"),Conditions.NO_EFFECT, true},
			{new Card("Hearts", "10"),new Card("Spades", "10"),Conditions.NO_EFFECT, true},
			{new Card("Hearts", "Jack"),new Card("Spades", "9"),Conditions.NO_EFFECT, true}, 
			{new Card("Diamonds", "8"),new Card("Hearts", "10"),Conditions.NO_EFFECT, false}, 
			{new Card("Hearts", "7"),new Card("Spades", "7"),Conditions.PLUS_TWO, true}, 
			{new Card("Hearts", "7"),new Card("Spades", "7"),Conditions.PLUS_FOUR, true}, 
			{new Card("Spades", "8"),new Card("Spades", "7"),Conditions.PLUS_TWO, false}, 
			{new Card("Clubs", "8"),new Card("Hearts", "Jack"),Conditions.WISH_CLUBS, true}, 
			{new Card("Diamonds", "8"),new Card("Hearts", "Jack"),Conditions.WISH_CLUBS, false},
			{new Card("Clubs", "Jack"),new Card("Hearts", "Jack"),Conditions.WISH_CLUBS, false},
		});
	}

	@Test
	public void testTurnAllowed() {		
		Assert.assertEquals(expectedResult, ruleSet.turnAllowed(currentCard, lastPlayedCard, condition));
	}

}
