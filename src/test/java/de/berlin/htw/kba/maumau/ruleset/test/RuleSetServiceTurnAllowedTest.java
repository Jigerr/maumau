//package de.berlin.htw.kba.maumau.ruleset.test;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameters;
//
//import de.berlin.htw.kba.maumau.ruleset.service.Condition;
//import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
//import de.berlin.htw.kba.maumau.ruleset.service.RuleSetServiceImpl;
//import de.berlin.htw.kba.maumau.ruleset.service.WrongCardException;
//import de.berlin.htw.kba.maumau.table.db.Card;
//
//@RunWith(Parameterized.class)
//public class RuleSetServiceTurnAllowedTest {
//
//	private RuleSetService ruleSet;
//	
//	private Card currentCard;
//	private Card lastPlayedCard;
//	private Condition condition;
//	private Boolean expectedResult;
//	
//	   @Before
//	    public void setUp() {
//	        ruleSet = new RuleSetServiceImpl();
//	    }   
//	
//	public RuleSetServiceTurnAllowedTest(Card currentCard, Card lastPlayedCard, Condition condition, boolean expectedResult) {
//		this.currentCard = currentCard;
//		this.lastPlayedCard = lastPlayedCard;
//		this.condition = condition;
//		this.expectedResult = expectedResult;
//	}
//
//	@Parameters
//	public static Collection<Object[]> data() {
//		return Arrays.asList(new Object[][] { 
//			{new Card("Hearts", "8"),new Card("Hearts", "9"),Condition.NO_EFFECT, true},
//			{new Card("Hearts", "10"),new Card("Spades", "10"),Condition.NO_EFFECT, true},
//			{new Card("Hearts", "Jack"),new Card("Spades", "9"),Condition.NO_EFFECT, true}, 
//			{new Card("Diamonds", "8"),new Card("Hearts", "10"),Condition.NO_EFFECT, false}, 
//			{new Card("Hearts", "7"),new Card("Spades", "7"),Condition.PLUS_TWO, true}, 
//			{new Card("Hearts", "7"),new Card("Spades", "7"),Condition.PLUS_FOUR, true}, 
//			{new Card("Spades", "8"),new Card("Spades", "7"),Condition.PLUS_TWO, false}, 
//			{new Card("Clubs", "8"),new Card("Hearts", "Jack"),Condition.WISH_CLUBS, true}, 
//			{new Card("Diamonds", "8"),new Card("Hearts", "Jack"),Condition.WISH_CLUBS, false},
//			{new Card("Clubs", "Jack"),new Card("Hearts", "Jack"),Condition.WISH_CLUBS, false},
//		});
//	}
//
//	@Test(expected = WrongCardException.class)
//	public void testTurnAllowed() throws WrongCardException {		
//            ruleSet.turnAllowed(currentCard, lastPlayedCard, condition);
//	}
//
//}
