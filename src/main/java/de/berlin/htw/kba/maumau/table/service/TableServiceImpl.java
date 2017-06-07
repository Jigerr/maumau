package de.berlin.htw.kba.maumau.table.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.TableRepository;

@Service
public class TableServiceImpl implements TableService {

	private static final int PENALTY_DRAW = 2;

	private static final int DEFAULT_DRAW = 1;

	private RuleSetService ruleSetService;

	private CardMasterService cardMasterService;

	private List<GameTable> openTables = new ArrayList<GameTable>();

	@Autowired
	private TableRepository repository;

	public TableServiceImpl(RuleSetService ruleSetService, CardMasterService cardMasterService) {
		this.ruleSetService = ruleSetService;
		this.cardMasterService = cardMasterService;
	}

	@Override
	public void drawCards(Integer gameTableId, String accountId) {
		GameTable gameTable = getTable(gameTableId);
		Player player = getPlayer(accountId, gameTable);

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
	public boolean playCard(Integer gameTableId, String accountId, Card currentCard, Suits wishedSuit) {
		GameTable gameTable = getTable(gameTableId);
		Player player = getPlayer(accountId, gameTable);
		boolean cardPlayed = false;
		if (ruleSetService.turnAllowed(currentCard,
				gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1),
				gameTable.getCondition())) {
			gameTable.getPlayingStack().getCardList().add(currentCard);
			player.getHand().remove(currentCard);
			changeCondition(gameTable, currentCard, wishedSuit);
			checkAmountOfPlayerCards(player, gameTable);
			endTurn(gameTable, player);
			cardPlayed = true;
		}
		return cardPlayed;
	}

	@Override
	public void callMau(Integer gameTableId, String accountId) {
		GameTable gameTable = getTable(gameTableId);
		Player player = getPlayer(accountId, gameTable);
		player.setCalledMau(true);
	}

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

	private GameTable getTable(Integer gameTableId) {
		for (GameTable gameTable : openTables) {
			if (gameTable.getTableID().equals(gameTableId)) {
				return gameTable;
			}
		}
		return null;
	}

	private Player getPlayer(String accountId, GameTable gameTable) {
		for (Player player : gameTable.getPlayers()) {
			if (player.getCurrentAccount().equals(accountId)) {
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
	public GameTable initTable() {
		GameTable gameTable = new GameTable();
		Player player1 = new Player("1", "1");
		Player player2 = new Player("2", "2");
		cardMasterService.fillStack(gameTable.getDrawingStack());
		cardMasterService.shuffleStack(gameTable.getDrawingStack());
		cardMasterService.fillHands(player1, gameTable.getDrawingStack());
		cardMasterService.fillHands(player2, gameTable.getDrawingStack());
		gameTable.getPlayers().add(player1);
		gameTable.getPlayers().add(player2);
		gameTable.setCurrentPlayer(player1);
		gameTable.getPlayingStack().getCardList().add(
				gameTable.getDrawingStack().getCardList().get(gameTable.getDrawingStack().getCardList().size() - 1));
		gameTable.getDrawingStack().getCardList().remove(gameTable.getDrawingStack().getCardList().size() - 1);
		changeCondition(gameTable,
				gameTable.getPlayingStack().getCardList().get(gameTable.getPlayingStack().getCardList().size() - 1),
				null);	
		gameTable = repository.save(gameTable);
		openTables.add(gameTable);
		return gameTable;
	}

	@Override
	public void skipTurn(Integer gameTableId, String playerId) {
		GameTable gameTable = getTable(gameTableId);
		Player player = getPlayer(playerId, gameTable);
		endTurn(gameTable, player);
		gameTable.setCondition(Condition.NO_EFFECT);
	}

	@Override
	public List<GameTable> getOpenTables() {
		return openTables;
	}

	@Override
	public void setOpenTables(List<GameTable> openTables) {
		this.openTables = openTables;
	}

}
