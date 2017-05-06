package de.berlin.htw.kba.maumau.table.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.cardmaster.service.Ranks;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.ruleset.service.Conditions;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

public class TableServiceTest2 {

	private static final String PLAYER_ONE_ID = "1";
	private static final String PLAYER_TWO_ID = "2";
	private static final String PLAYER_ONE_ACCOUNT_ID = "1";
	private static final String PLAYER_TWO_ACCOUNT_ID = "2";
	private static final String TABLE_ID = "1";

	private CardMasterService cardMasterService = new CardMasterServiceImpl();

	private TableService tableService = new TableServiceImpl();

	private Table table;
	private Player player1;
	private Player player2;

	@Before
	public void init() {
		table = new Table();
		table.setTableID(TABLE_ID);
		cardMasterService.fillStack(table.getDrawingStack());
		player1 = new Player(PLAYER_ONE_ID, PLAYER_ONE_ACCOUNT_ID);
		player2 = new Player(PLAYER_TWO_ID, PLAYER_TWO_ACCOUNT_ID);
		table.getPlayers().add(player1);
		table.getPlayers().add(player2);
		table.setCurrentPlayer(player1.getPlayerId());
		tableService.getOpenTables().add(table);
	}

	@After
	public void tearDown() {
		tableService.getOpenTables().clear();
	}

	@Test
	public void testIfPlayerPlayedLastCard() {
		Card playCard = new Card(Suits.CLUBS.getSuit(), Ranks.EIGHT.getRank());
		player1.addCard(playCard);
		table.getPlayingStack().getStack().add(new Card(Suits.CLUBS.getSuit(), Ranks.NINE.getRank()));

		tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard);

		Assert.assertTrue("Size not equals", player1.getHand().size() == 0);
		Assert.assertTrue("Played card not on top", table.getPlayingStack().getStack().getLast().equals(playCard));
		Assert.assertTrue("Game is not over yet", table.getGameOver());
	}

	@Test
	public void testNoMauCall() {
		Card playCard = new Card(Suits.CLUBS.getSuit(), Ranks.EIGHT.getRank());
		player1.addCard(playCard);
		player1.addCard(new Card(Suits.HEARTS.getSuit(), Ranks.EIGHT.getRank()));
		table.getPlayingStack().getStack().add(new Card(Suits.CLUBS.getSuit(), Ranks.NINE.getRank()));

		tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard);

		Assert.assertTrue(player1.getHand().size() == 3);
	}

	@Test
	public void testPlusFourCondition() {
		Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.SEVEN.getRank());
		player1.addCard(playCard);
		table.getPlayingStack().getStack().add(new Card(Suits.CLUBS.getSuit(), Ranks.SEVEN.getRank()));
		table.setCondition(Conditions.PLUS_TWO);

		tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard);

		Assert.assertTrue(table.getCondition().equals(Conditions.PLUS_FOUR));
	}

	@Test
	public void testWishCondition() {
		Card playCard = new Card(Suits.CLUBS.getSuit(), Ranks.SEVEN.getRank());
		player2.addCard(playCard);
		table.getPlayingStack().getStack().add(new Card(Suits.HEARTS.getSuit(), Ranks.JACK.getRank()));
		table.setCondition(Conditions.WISH_CLUBS);

		tableService.playCard(TABLE_ID, PLAYER_TWO_ACCOUNT_ID, playCard);

		Assert.assertTrue(table.getCondition().equals(Conditions.PLUS_TWO));
		Assert.assertTrue("Played card not on top", table.getPlayingStack().getStack().getLast().equals(playCard));
	}

	@Test
	public void testSkipEffect() {
		Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.ACE.getRank());
		player1.addCard(playCard);
		table.getPlayingStack().getStack().add(new Card(Suits.CLUBS.getSuit(), Ranks.ACE.getRank()));

		tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard);

		Assert.assertTrue(table.getCondition().equals(Conditions.SKIP));
	}

	@Test
	public void testWishEffect() {
		Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.JACK.getRank());
		player1.addCard(playCard);
		table.getPlayingStack().getStack().add(new Card(Suits.CLUBS.getSuit(), Ranks.TEN.getRank()));

		tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard);

		Assert.assertTrue(table.getCondition().equals(Conditions.WISH_CLUBS));
	}

	@Test
	public void testIfStackIsRefilled() {
		table.getDrawingStack().getStack().clear();
		cardMasterService.fillStack(table.getPlayingStack());
		Card topCard = new Card(table.getPlayingStack().getStack().getLast());

		tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);

		Assert.assertEquals("Rank doesnt match", table.getPlayingStack().getStack().getLast().getRank(),
				topCard.getRank());
		Assert.assertEquals("Suit doesnt match", table.getPlayingStack().getStack().getLast().getSuit(),
				topCard.getSuit());
		Assert.assertTrue(table.getPlayingStack().getStack().size() == 1);
		Assert.assertTrue(table.getDrawingStack().getStack().size() == 30);
	}

}
