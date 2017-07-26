/*
 * 
 */
package de.berlin.htw.kba.maumau.table.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterServiceImpl;
import de.berlin.htw.kba.maumau.cardmaster.service.Ranks;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetServiceImpl;
import de.berlin.htw.kba.maumau.ruleset.service.WrongCardException;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.db.TableRepository;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class TableServiceTest2.
 */
@RunWith(MockitoJUnitRunner.class)
public class TableServiceTest2 {

    /** The Constant PLAYER_ONE_ACCOUNT_ID. */
    private static final String PLAYER_ONE_ACCOUNT_ID = "1";
    
    /** The Constant PLAYER_TWO_ACCOUNT_ID. */
    private static final String PLAYER_TWO_ACCOUNT_ID = "2";
    
    /** The Constant TABLE_ID. */
    private static final Integer TABLE_ID = 1;

    /** The card master service. */
    private CardMasterService cardMasterService;
    
    /** The table dummy service. */
    private TableDummyService tableDummyService;
    
    /** The table service. */
    private TableService tableService;
    
    /** The repository. */
    TableRepository repository;

    /** The game table. */
    private GameTable gameTable;
    
    /** The player 1. */
    private Player player1;
    
    /** The player 2. */
    private Player player2;

    /**
     * Inits the.
     */
    @Before
    public void init() {
        cardMasterService = new CardMasterServiceImpl();
        tableDummyService = new TableDummyServiceImpl();
        RuleSetService ruleSetService = new RuleSetServiceImpl();
        repository = Mockito.mock(TableRepository.class);
        tableService = new TableServiceImpl(ruleSetService, cardMasterService, repository, null);
        gameTable = tableDummyService.getNewTableDummy();
        player1 = tableDummyService.getPlayer1();
        player2 = tableDummyService.getPlayer2();
        Mockito.when(repository.save(gameTable)).thenReturn(null);
        Mockito.when(repository.findOne(gameTable.getGameTableID())).thenReturn(gameTable);
    }

    /**
     * Test if player played last card.
     */
    @Test
    public void testIfPlayerPlayedLastCard() {
        Card playCard = new Card(Suits.CLUBS.getSuit(), Ranks.EIGHT.getValue());
        player1.getHand().clear();
        player1.addCard(playCard);
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.CLUBS.getSuit(), Ranks.NINE.getValue()));

        try {
            tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard, null);
        } catch (WrongCardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue("Size not equals", player1.getHand().size() == 0);
        Assert.assertTrue("Played card not on top", gameTable.getPlayingStack().getCardList()
                .get(gameTable.getPlayingStack().getCardList().size() - 1).equals(playCard));
        Assert.assertTrue("Game is not over yet", gameTable.getGameOver());
    }

    /**
     * Test no mau call.
     */
    @Test
    public void testNoMauCall() {
        Card playCard = new Card(Suits.CLUBS.getSuit(), Ranks.EIGHT.getValue());
        player1.getHand().clear();
        player1.addCard(playCard);
        player1.addCard(new Card(Suits.HEARTS.getSuit(), Ranks.EIGHT.getValue()));
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.CLUBS.getSuit(), Ranks.NINE.getValue()));

        try {
            tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard, null);
        } catch (WrongCardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(player1.getHand().size() == 3);
    }

    /**
     * Test plus four condition.
     */
    @Test
    public void testPlusFourCondition() {
        Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.SEVEN.getValue());
        player1.addCard(playCard);
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.CLUBS.getSuit(), Ranks.SEVEN.getValue()));
        gameTable.setCondition(Condition.PLUS_TWO);
        Mockito.when(repository.save(gameTable)).thenReturn(null);

        try {
            tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard, null);
        } catch (WrongCardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(gameTable.getCondition().equals(Condition.PLUS_FOUR));
    }

    /**
     * Test wish condition.
     */
    @Test
    public void testWishCondition() {
        Card playCard = new Card(Suits.CLUBS.getSuit(), Ranks.SEVEN.getValue());
        player2.addCard(playCard);
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.HEARTS.getSuit(), Ranks.JACK.getValue()));
        gameTable.setCondition(Condition.WISH_CLUBS);

        try {
            tableService.playCard(TABLE_ID, PLAYER_TWO_ACCOUNT_ID, playCard, null);
        } catch (WrongCardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(gameTable.getCondition().equals(Condition.PLUS_TWO));
        Assert.assertTrue("Played card not on top",
                gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1).equals(playCard));
    }

    /**
     * Test skip effect.
     */
    @Test
    public void testSkipEffect() {
        Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.ACE.getValue());
        player1.addCard(playCard);
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.CLUBS.getSuit(), Ranks.ACE.getValue()));

        try {
            tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard, null);
        } catch (WrongCardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(gameTable.getCondition().equals(Condition.SKIP));
    }

    /**
     * Test wish effect.
     */
    @Test
    public void testWishEffect() {
        Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.JACK.getValue());
        player1.addCard(playCard);
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.CLUBS.getSuit(), Ranks.TEN.getValue()));

        try {
            tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard, Suits.CLUBS);
        } catch (WrongCardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(gameTable.getCondition().equals(Condition.WISH_CLUBS));
    }

    /**
     * Test if stack is refilled.
     */
    @Test
    public void testIfStackIsRefilled() {
        gameTable.getDrawingStack().getCardList().clear();
        cardMasterService.fillStack(gameTable.getPlayingStack());
        Card topCard = new Card(gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1));

        tableService.drawCards(TABLE_ID, PLAYER_ONE_ACCOUNT_ID);

        Assert.assertEquals("Rank doesnt match", gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1).getRank(),
                topCard.getRank());
        Assert.assertEquals("Suit doesnt match", gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1).getSuit(),
                topCard.getSuit());
        Assert.assertTrue(gameTable.getPlayingStack().getCardList().size() == 1);
        Assert.assertTrue(gameTable.getDrawingStack().getCardList().size() == 30);
    }

}
