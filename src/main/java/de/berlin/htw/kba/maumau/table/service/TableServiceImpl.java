package de.berlin.htw.kba.maumau.table.service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.player.db.PlayerRepository;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.db.TableRepository;

@Service
public class TableServiceImpl implements TableService {

    private static final int PENALTY_DRAW = 2;

    private static final int DEFAULT_DRAW = 1;

    private static final String HUMAN = "human";

    private RuleSetService ruleSetService;

    private CardMasterService cardMasterService;

    //	private List<GameTable> openTables = new ArrayList<GameTable>();

    private TableRepository repository;

    private PlayerRepository playerRepository;

    public TableServiceImpl(RuleSetService ruleSetService, CardMasterService cardMasterService, TableRepository repository, PlayerRepository playerRepository) {
        this.ruleSetService = ruleSetService;
        this.cardMasterService = cardMasterService;
        this.repository = repository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void drawCards(Integer gameTableId, String playerId) {
        //		GameTable gameTable = getTable(gameTableId);
        GameTable gameTable = loadGame(gameTableId);
        Player player = getPlayer(playerId, gameTable);

        switch (gameTable.getCondition()) {
            case PLUS_TWO:
            case PLUS_FOUR:
            case PLUS_SIX:
            case PLUS_EIGHT:
                drawCardsFromDrawingStack(player, gameTable, Integer.parseInt(gameTable.getCondition().getCondition()));
                gameTable.setCondition(Condition.NO_EFFECT);
                break;
            default:
                drawCardsFromDrawingStack(player, gameTable, DEFAULT_DRAW);
                break;
        }
        endTurn(gameTable, player);
    }

    @Override
    public boolean playCard(Integer gameTableId, String playerId, Card currentCard, Suits wishedSuit) {
        //		GameTable gameTable = getTable(gameTableId);
        GameTable gameTable = loadGame(gameTableId);
        Player player = getPlayer(playerId, gameTable);
        Card card = getCard(currentCard, player);
        boolean cardPlayed = false;
        if (ruleSetService.turnAllowed(card,
                gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1),
                gameTable.getCondition())) {
            gameTable.getPlayingStack().getCardList().add(card);
            player.getHand().remove(card);
            changeCondition(gameTable, card, wishedSuit);
            checkAmountOfPlayerCards(player, gameTable);
            endTurn(gameTable, player);
            cardPlayed = true;
        }
        return cardPlayed;
    }

    private Card getCard(Card currentCard, Player player) {
        for (Card card : player.getHand()) {
            if (card.getRank().equals(currentCard.getRank()) && card.getSuit().equals(currentCard.getSuit())) {
                return card;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void callMau(Integer gameTableId, String playerId) {
        GameTable gameTable = loadGame(gameTableId);
        Player player = getPlayer(playerId, gameTable);
        player.setCalledMau(true);
        repository.save(gameTable);
    }

    @Transactional
    private void endTurn(GameTable gameTable, Player player) {
        int startIndex = gameTable.getPlayers().indexOf(player) + 1;
        ListIterator<Player> iter = gameTable.getPlayers().listIterator(startIndex);
        if (iter.hasNext()) {
            gameTable.setCurrentPlayer(iter.next());
        } else {
            gameTable.setCurrentPlayer(gameTable.getPlayers().get(0));
        }
        player.setCalledMau(false);
        repository.save(gameTable);
    }

    private Player getPlayer(String playerId, GameTable gameTable) {
        for (Player player : gameTable.getPlayers()) {
            if (player.getPlayerId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    private void drawCardsFromDrawingStack(Player player, GameTable gameTable, int amountOfCards) {
        checkIfEnoughCardsToDraw(gameTable, amountOfCards);
        for (int i = 0; i < amountOfCards; i++) {
            player.getHand().add(gameTable.getDrawingStack().getCardList()
                    .get(gameTable.getDrawingStack().getCardList().size() - 1));
            gameTable.getDrawingStack().getCardList().remove(gameTable.getDrawingStack().getCardList().size() - 1);
        }
    }

    private void changeCondition(GameTable gameTable, Card currentCard, Suits wishedSuit) {
        switch (ruleSetService.getCardEffect(currentCard)) {
            case SKIP:
                gameTable.setCondition(Condition.SKIP);
                break;
            case PLUS_TWO:
                switch (gameTable.getCondition()) {
                    case PLUS_TWO:
                        gameTable.setCondition(Condition.PLUS_FOUR);
                        break;
                    case PLUS_FOUR:
                        gameTable.setCondition(Condition.PLUS_SIX);
                        break;
                    case PLUS_SIX:
                        gameTable.setCondition(Condition.PLUS_EIGHT);
                        break;
                    default:
                        gameTable.setCondition(Condition.PLUS_TWO);
                        break;
                }
                break;
            case WISH:
                if (wishedSuit != null) {
                    switch (wishedSuit) {
                        case CLUBS:
                            gameTable.setCondition(Condition.WISH_CLUBS);
                            break;
                        case DIAMONDS:
                            gameTable.setCondition(Condition.WISH_DIAMONDS);
                            break;
                        case HEARTS:
                            gameTable.setCondition(Condition.WISH_HEARTS);
                            break;
                        case SPADES:
                            gameTable.setCondition(Condition.WISH_SPADES);
                            break;
                    }
                }
                break;
            case NO_EFFECT:
                gameTable.setCondition(Condition.NO_EFFECT);
                break;
        }
    }

    private void checkAmountOfPlayerCards(Player player, GameTable gameTable) {
        if (player.getHand().size() == 1 && !player.hasCalledMau()) {
            drawCardsFromDrawingStack(player, gameTable, PENALTY_DRAW);
        } else if (player.getHand().size() == 0) {
            gameTable.setGameOver(true);
        }
    }

    private void checkIfEnoughCardsToDraw(GameTable gameTable, int amountOfCards) {
        if (gameTable.getDrawingStack().getCardList().size() < amountOfCards) {
            Card topCard = new Card(gameTable.getPlayingStack().getCardList()
                    .get(gameTable.getPlayingStack().getCardList().size() - 1));
            gameTable.getPlayingStack().getCardList().remove(gameTable.getPlayingStack().getCardList()
                    .get(gameTable.getPlayingStack().getCardList().size() - 1));
            gameTable.getDrawingStack().getCardList().addAll(gameTable.getPlayingStack().getCardList());
            gameTable.getPlayingStack().getCardList().clear();
            gameTable.getPlayingStack().getCardList().add(topCard);
            cardMasterService.shuffleStack(gameTable.getDrawingStack());
        }
    }

    @Override
    public GameTable initTable(String playerAmount) {
        GameTable gameTable = new GameTable();
        gameTable.setCreated(new Date());
        cardMasterService.fillStack(gameTable.getDrawingStack());
        cardMasterService.shuffleStack(gameTable.getDrawingStack());

        for (int i = 1; i <= Integer.parseInt(playerAmount); i++) {
            Player player = new Player(String.valueOf(i));
            cardMasterService.fillHands(player, gameTable.getDrawingStack());
            gameTable.getPlayers().add(player);
            if (i == 1) {
                player.setControlledBy(HUMAN);
                gameTable.setCurrentPlayer(player);
            }
        }
        gameTable.getPlayingStack().getCardList().add(
                gameTable.getDrawingStack().getCardList().get(gameTable.getDrawingStack().getCardList().size() - 1));
        gameTable.getDrawingStack().getCardList().remove(gameTable.getDrawingStack().getCardList().size() - 1);
        changeCondition(gameTable,
                gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1),
                null);
        gameTable = repository.save(gameTable);
        return gameTable;
    }

    @Override
    public void skipTurn(Integer gameTableId, String playerId) {
        GameTable gameTable = loadGame(gameTableId);
        Player player = getPlayer(playerId, gameTable);
        gameTable.setCondition(Condition.NO_EFFECT);
        endTurn(gameTable, player);

    }

    @Override
    @Transactional
    public void removeGameTable(GameTable gameTable) {
        repository.delete(gameTable);
    }

    @Override
    @Transactional
    public List<GameTable> loadGameList() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public GameTable loadGame(Integer gameTableId) {
        return repository.findOne(gameTableId);
    }

    @Override
    @Transactional
    public String getFreeSLot(Integer gameTableId) {
        List<Player> playerList = playerRepository.findByGameTableId(gameTableId);
        String playerId = null;
        for (Player player : playerList) {
            if (player.getControlledBy() == null) {
                playerId = player.getPlayerId();
                break;
            }
        }
        return playerId;
    }

    @Override
    @Transactional
    public void removeControlledByFromPlayer(String playerId, GameTable gameTable) {

        for (Player p : gameTable.getPlayers()) {
            if (p.getPlayerId().equals(playerId)) {
                p.setControlledBy(null);
            }
        }
        gameTable.setLeaver(true);
        repository.save(gameTable);
    }
    
    @Override
    @Transactional
    public void saveTable(GameTable gameTable) {
        repository.save(gameTable);
    }

}
