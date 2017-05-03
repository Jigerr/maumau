package de.berlin.htw.kba.maumau.ruleset.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.berlin.htw.kba.maumau.ruleset.service.Conditions;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Card;

@RunWith(MockitoJUnitRunner.class)
public class RuleSetServiceTurnAllowedMockTest {
	
	@Mock
	private RuleSetService ruleSet = new RuleSetServiceImpl();
	
	@Test
	public void test() {
		
		Card card1 = new Card("HEARTS", "8");
		Card card2 = new Card("HEARTS", "9");
		
		Mockito.when(ruleSet.turnAllowed(card1, card2, Conditions.NO_EFFECT)).thenReturn(true);
		
		Assert.assertTrue(ruleSet.turnAllowed(card1, card2, Conditions.NO_EFFECT));
		
	}

}
