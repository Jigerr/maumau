package de.berlin.htw.kba.maumau.table.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.cardmaster.service.CardMasterService;
import de.berlin.htw.kba.maumau.ruleset.service.Conditions;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;

@Service
public class TableServiceImpl implements TableService {

	private static final int PENALTY_DRAW = 2;

	private static final int DEFAULT_DRAW = 1;

	private RuleSetService ruleSetService;

	private CardMasterService cardMasterService;

	private List<Table> openTables = new ArrayList<Table>();
	
	public TableServiceImpl(RuleSetService ruleSetService, CardMasterService cardMasterService) {
	    this.ruleSetService = ruleSetService;
	    this.cardMasterService = cardMasterService;
    }

	@Override
	public void drawCards(String tableId, String accountId) {
		Table table = getTable(tableId);
		Player player = getPlayer(accountId, table);

		switch (table.getCondition()) {
		case PLUS_TWO:
		case PLUS_FOUR:
		case PLUS_SIX:
		case PLUS_EIGHT:
			drawCardsFromDrawingStack(player, table, Integer.parseInt(table.getCondition().getValue()));
			table.setCondition(Conditions.NO_EFFECT);
			break;
		default:
			drawCardsFromDrawingStack(player, table, DEFAULT_DRAW);
			break;
		}
		endTurn(table, player);
	}

	@Override
	public void playCard(String tableId, String accountId, Card currentCard) {
		Table table = getTable(tableId);
		Player player = getPlayer(accountId, table);
		if (ruleSetService.turnAllowed(currentCard, table.getPlayingStack().getStack().getLast(),
				table.getCondition())) {
			table.getPlayingStack().getStack().add(currentCard);
			player.getHand().remove(currentCard);
			changeCondition(table, currentCard);
			checkAmountOfPlayerCards(player, table);
			endTurn(table, player);
		}
	}

	@Override
	public void callMau(String tableId, String accountId) {
		Table table = getTable(tableId);
		Player player = getPlayer(accountId, table);
		player.setCalledMau(true);
	}

	private void endTurn(Table table, Player player) {
		int startIndex = table.getPlayers().indexOf(player) + 1;
		ListIterator<Player> iter = table.getPlayers().listIterator(startIndex);
		if (iter.hasNext()) {
			table.setCurrentPlayer(iter.next());
		} else {
			table.setCurrentPlayer(table.getPlayers().getFirst());
		}
		player.setCalledMau(false);

		// temporär
		if (table.getGameOver()) {
			System.out.println("Spiel ist vorbei! " + "Spieler " + player.getPlayerId() + " hat gewonnen.");
		}
	}

	private Table getTable(String tableId) {
		for (Table table : openTables) {
			if (table.getTableID().equals(tableId)) {
				return table;
			}
		}
		return null;
	}

	private Player getPlayer(String accountId, Table table) {
		for (Player player : table.getPlayers()) {
			if (player.getCurrentAccount().equals(accountId)) {
				return player;
			}
		}
		return null;
	}

	private void drawCardsFromDrawingStack(Player player, Table table, int amountOfCards) {
		checkIfEnoughCardsToDraw(table, amountOfCards);
		for (int i = 0; i < amountOfCards; i++) {
			player.getHand().add(table.getDrawingStack().getStack().getLast());
			table.getDrawingStack().getStack().removeLast();
		}
	}

	private void changeCondition(Table table, Card currentCard) {
		switch (ruleSetService.getCardEffect(currentCard)) {
		case SKIP:
			table.setCondition(Conditions.SKIP);
			break;
		case PLUS_TWO:
			switch (table.getCondition()) {
			case PLUS_TWO:
				table.setCondition(Conditions.PLUS_FOUR);
				break;
			case PLUS_FOUR:
				table.setCondition(Conditions.PLUS_SIX);
				break;
			case PLUS_SIX:
				table.setCondition(Conditions.PLUS_EIGHT);
				break;
			default:
				table.setCondition(Conditions.PLUS_TWO);
				break;
			}
			break;
		case WISH:
			// FIXED FOR TESTING PURPOSE
			// NEED USER INPUT TO DECIDE WHICH SUIT
			table.setCondition(Conditions.WISH_CLUBS);
			break;
		case NO_EFFECT:
			table.setCondition(Conditions.NO_EFFECT);
			break;
		}
	}

	private void checkAmountOfPlayerCards(Player player, Table table) {
		if (player.getHand().size() == 1 && !player.hasCalledMau()) {
			drawCardsFromDrawingStack(player, table, PENALTY_DRAW);
		} else if (player.getHand().size() == 0) {
			table.setGameOver(true);
		}
	}

	private void checkIfEnoughCardsToDraw(Table table, int amountOfCards) {
		if (table.getDrawingStack().getStack().size() < amountOfCards) {
			Card topCard = new Card(table.getPlayingStack().getStack().getLast());
			table.getPlayingStack().getStack().remove(table.getPlayingStack().getStack().getLast());
			table.getDrawingStack().getStack().addAll(table.getPlayingStack().getStack());
			table.getPlayingStack().getStack().clear();
			table.getPlayingStack().getStack().add(topCard);
			cardMasterService.shuffleStack(table.getDrawingStack());
		}
	}

	@Override
	public List<Table> getOpenTables() {
		return openTables;
	}

	@Override
	public void setOpenTables(List<Table> openTables) {
		this.openTables = openTables;
	}

}
