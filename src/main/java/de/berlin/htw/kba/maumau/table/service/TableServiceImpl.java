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
import de.berlin.htw.kba.maumau.ruleset.service.WrongCardException;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.db.TableRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class TableServiceImpl.
 */
@Service
public class TableServiceImpl implements TableService {

    /** The Constant PENALTY_DRAW. */
    private static final int PENALTY_DRAW = 2;

    /** The Constant DEFAULT_DRAW. */
    private static final int DEFAULT_DRAW = 1;

    /** The Constant HUMAN. */
    private static final String HUMAN = "human";
    
    /** The Constant BOT. */
    private static final String BOT = "bot";

    /** The rule set service. */
    private RuleSetService ruleSetService;

    /** The card master service. */
    private CardMasterService cardMasterService;

    /** The repository. */
    private TableRepository repository;

    /** The player repository. */
    private PlayerRepository playerRepository;

    /**
     * Instantiates a new table service impl.
     *
     * @param ruleSetService the rule set service
     * @param cardMasterService the card master service
     * @param repository the repository
     * @param playerRepository the player repository
     */
    public TableServiceImpl(RuleSetService ruleSetService, CardMasterService cardMasterService, TableRepository repository, PlayerRepository playerRepository) {
        this.ruleSetService = ruleSetService;
        this.cardMasterService = cardMasterService;
        this.repository = repository;
        this.playerRepository = playerRepository;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#drawCards(java.lang.Integer, java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#playCard(java.lang.Integer, java.lang.String, de.berlin.htw.kba.maumau.table.db.Card, de.berlin.htw.kba.maumau.cardmaster.service.Suits)
     */
    @Override
    public boolean playCard(Integer gameTableId, String playerId, Card currentCard, Suits wishedSuit) throws WrongCardException {
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
            if (!gameTable.getGameOver()) {
                endTurn(gameTable, player);
            }
            cardPlayed = true;
        }
        return cardPlayed;
    }

    /**
     * Gets the card.
     *
     * @param currentCard the current card
     * @param player the player
     * @return the card
     */
    private Card getCard(Card currentCard, Player player) {
        for (Card card : player.getHand()) {
            if (card.getRank().equals(currentCard.getRank()) && card.getSuit().equals(currentCard.getSuit())) {
                return card;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#callMau(java.lang.Integer, java.lang.String)
     */
    @Override
    @Transactional
    public void callMau(Integer gameTableId, String playerId) {
        GameTable gameTable = loadGame(gameTableId);
        Player player = getPlayer(playerId, gameTable);
        player.setCalledMau(true);
        repository.save(gameTable);
    }

    /**
     * End turn.
     *
     * @param gameTable the game table
     * @param player the player
     */
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

    /**
     * Gets the player.
     *
     * @param playerId the player id
     * @param gameTable the game table
     * @return the player
     */
    private Player getPlayer(String playerId, GameTable gameTable) {
        for (Player player : gameTable.getPlayers()) {
            if (player.getPlayerId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Draw cards from drawing stack.
     *
     * @param player the player
     * @param gameTable the game table
     * @param amountOfCards the amount of cards
     */
    private void drawCardsFromDrawingStack(Player player, GameTable gameTable, int amountOfCards) {
        checkIfEnoughCardsToDraw(gameTable, amountOfCards);
        for (int i = 0; i < amountOfCards; i++) {
            player.getHand().add(gameTable.getDrawingStack().getCardList()
                    .get(gameTable.getDrawingStack().getCardList().size() - 1));
            gameTable.getDrawingStack().getCardList().remove(gameTable.getDrawingStack().getCardList().size() - 1);
        }
    }

    /**
     * Change condition.
     *
     * @param gameTable the game table
     * @param currentCard the current card
     * @param wishedSuit the wished suit
     */
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

    /**
     * Check amount of player cards.
     *
     * @param player the player
     * @param gameTable the game table
     */
    @Transactional
    private void checkAmountOfPlayerCards(Player player, GameTable gameTable) {
        if (player.getHand().size() == 1 && !player.hasCalledMau()) {
            drawCardsFromDrawingStack(player, gameTable, PENALTY_DRAW);
        } else if (player.getHand().size() == 0) {
            gameTable.setGameOver(true);
        }
        repository.save(gameTable);
    }

    /**
     * Check if enough cards to draw.
     *
     * @param gameTable the game table
     * @param amountOfCards the amount of cards
     */
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#initTable(java.lang.String, java.lang.String, int)
     */
    @Override
    public GameTable initTable(String playerAmount, String gameMode, int amountOfCards) {
        GameTable gameTable = new GameTable();
        gameTable.setCreated(new Date());
        cardMasterService.fillStack(gameTable.getDrawingStack());
        cardMasterService.shuffleStack(gameTable.getDrawingStack());

        for (int i = 1; i <= Integer.parseInt(playerAmount); i++) {
            Player player = new Player(String.valueOf(i));
            cardMasterService.fillHands(player, gameTable.getDrawingStack(), amountOfCards);
            gameTable.getPlayers().add(player);
            if (i == 1) {
                player.setControlledBy(HUMAN);
                gameTable.setCurrentPlayer(player);
            } else if ("B".equals(gameMode)) {
                player.setControlledBy(BOT);
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#skipTurn(java.lang.Integer, java.lang.String)
     */
    @Override
    public void skipTurn(Integer gameTableId, String playerId) {
        GameTable gameTable = loadGame(gameTableId);
        Player player = getPlayer(playerId, gameTable);
        gameTable.setCondition(Condition.NO_EFFECT);
        endTurn(gameTable, player);

    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#removeGameTable(de.berlin.htw.kba.maumau.table.db.GameTable)
     */
    @Override
    @Transactional
    public void removeGameTable(GameTable gameTable) {
        repository.delete(gameTable);
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#loadGameList()
     */
    @Override
    @Transactional
    public List<GameTable> loadGameList() {
        return repository.findAll();
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#loadGame(java.lang.Integer)
     */
    @Override
    @Transactional
    public GameTable loadGame(Integer gameTableId) {
        return repository.findOne(gameTableId);
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#getFreeSLot(java.lang.Integer)
     */
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#removeControlledByFromPlayer(java.lang.String, de.berlin.htw.kba.maumau.table.db.GameTable)
     */
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.TableService#saveTable(de.berlin.htw.kba.maumau.table.db.GameTable)
     */
    @Override
    @Transactional
    public void saveTable(GameTable gameTable) {
        repository.save(gameTable);
    }

}
