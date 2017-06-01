package de.berlin.htw.kba.maumau.table.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.berlin.htw.kba.maumau.cardmaster.service.Ranks;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.ruleset.service.CardEffects;
import de.berlin.htw.kba.maumau.ruleset.service.Conditions;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceMockTest {

	private static final String PLAYER_ONE_ACCOUNT_ID = "1";
	private static final String TABLE_ID = "1";

	private TableDummyService tableDummyService;

	@Mock
	private RuleSetService ruleSetService;

	@InjectMocks
	private TableServiceImpl tableService;

	private Table table;
	private Player player1;

	@Before
	public void init() {	
	    tableDummyService = new TableDummyServiceImpl();
	    table = tableDummyService.getNewTableDummy();
        player1 = tableDummyService.getPlayer1();
		tableService.getOpenTables().add(table);
	}

	@After
	public void tearDown() {
		tableService.getOpenTables().clear();
	}

	@Test
	public void testPlusSixCondition() {
		Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.SEVEN.getRank());
		player1.addCard(playCard);
		table.getPlayingStack().getStack().add(new Card(Suits.CLUBS.getSuit(), Ranks.SEVEN.getRank()));
		table.setCondition(Conditions.PLUS_FOUR);

		Mockito.when(ruleSetService.turnAllowed(playCard, table.getPlayingStack().getStack().getLast(),
				Conditions.PLUS_FOUR)).thenReturn(true);
		Mockito.when(ruleSetService.getCardEffect(playCard)).thenReturn(CardEffects.PLUS_TWO);

		tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard);

		Assert.assertTrue(table.getCondition().equals(Conditions.PLUS_SIX));
	}

}
