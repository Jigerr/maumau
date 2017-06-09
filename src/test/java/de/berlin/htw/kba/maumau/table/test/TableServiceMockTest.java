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
import de.berlin.htw.kba.maumau.ruleset.service.CardEffect;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.TableRepository;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.service.TableServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceMockTest {

    private static final String PLAYER_ONE_ACCOUNT_ID = "1";
    private static final Integer TABLE_ID = 1;

    private TableDummyService tableDummyService;

    @Mock
    private RuleSetService ruleSetService;

    @Mock
    private TableRepository repository;

    @InjectMocks
    private TableServiceImpl tableService;

    private GameTable gameTable;
    private Player player1;

    @Before
    public void init() {
        tableDummyService = new TableDummyServiceImpl();
        gameTable = tableDummyService.getNewTableDummy();
        player1 = tableDummyService.getPlayer1();
        Mockito.when(repository.save(gameTable)).thenReturn(null);
        Mockito.when(repository.findOne(gameTable.getGameTableID())).thenReturn(gameTable);
    }

    @Test
    public void testPlusSixCondition() {
        Card playCard = new Card(Suits.HEARTS.getSuit(), Ranks.SEVEN.getValue());
        player1.addCard(playCard);
        gameTable.getPlayingStack().getCardList().add(new Card(Suits.CLUBS.getSuit(), Ranks.SEVEN.getValue()));
        gameTable.setCondition(Condition.PLUS_FOUR);

        Mockito.when(ruleSetService.turnAllowed(playCard,
                gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1),
                Condition.PLUS_FOUR)).thenReturn(true);
        Mockito.when(ruleSetService.getCardEffect(playCard)).thenReturn(CardEffect.PLUS_TWO);

        tableService.playCard(TABLE_ID, PLAYER_ONE_ACCOUNT_ID, playCard, null);

        Assert.assertTrue(gameTable.getCondition().equals(Condition.PLUS_SIX));
    }

}
