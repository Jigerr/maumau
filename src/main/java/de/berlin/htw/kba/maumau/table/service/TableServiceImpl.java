package de.berlin.htw.kba.maumau.table.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.berlin.htw.kba.maumau.ruleset.service.RuleSetService;
import de.berlin.htw.kba.maumau.ruleset.service.RuleSetServiceImpl;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Player;
import de.berlin.htw.kba.maumau.table.db.Table;

public class TableServiceImpl implements TableService {

	private static final int defaultDraw = 1;
	
	private RuleSetService ruleSetService = new RuleSetServiceImpl();

	private List<Table> openTables = new ArrayList<Table>();

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
			break;
		default:
			drawCardsFromDrawingStack(player, table, defaultDraw);
			break;
		}
		endTurn(table, player);
	}

	@Override
	public boolean playCard(String tableId, String accountId, Card currentCard) {
		// TODO Auto-generated method stub
		return false;
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
			table.setCurrentPlayer(iter.next().getPlayerId());
		} else {
			table.setCurrentPlayer(table.getPlayers().getFirst().getPlayerId());
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
		for (int i = 0; i < amountOfCards; i++) {
			player.getHand().add(table.getDrawingStack().getStack().getLast());
			table.getDrawingStack().getStack().removeLast();
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
