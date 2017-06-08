package de.berlin.htw.kba.maumau.table.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.events.CallMauEvent;
import de.berlin.htw.kba.maumau.table.events.DrawCardEvent;
import de.berlin.htw.kba.maumau.table.events.LeaveGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameListEvent;
import de.berlin.htw.kba.maumau.table.events.PlayCardEvent;
import de.berlin.htw.kba.maumau.table.events.PlayJackCardEvent;
import de.berlin.htw.kba.maumau.table.events.SkipTurnEvent;
import de.berlin.htw.kba.maumau.table.events.StartGameEvent;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.view.TableView;

@Controller
public class TableViewController {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private TableService tableService;

	private TableView tableView;

	public void setView(TableView tableView) {
		this.tableView = tableView;
		tableView.setEventPublisher(applicationEventPublisher);
	}

	public void initGame() {
		tableView.initGame();
	}

	@EventListener
	private void handleStartGameEvent(StartGameEvent event) {
		GameTable gameTable = tableService.initTable();
		doTurn(gameTable);
	}

	@EventListener
	private void handlePlayJackCardEvent(PlayJackCardEvent event) {
		tableView.printWishSuitMessage(event);
	}

	@EventListener
	private void handlePlayCardEvent(PlayCardEvent event) {
		if (!tableService.playCard(event.getGameTable().getTableID(), event.getPlayerId(), event.getCard(),
				event.getWishedSuit())) {
			tableView.printCardNotAllowedMessage();
		}
		doTurn(event.getGameTable());
	}

	@EventListener
	private void handleDrawCardEvent(DrawCardEvent event) {
		tableService.drawCards(event.getGameTable().getTableID(), event.getPlayerId());
		doTurn(event.getGameTable());
	}

	@EventListener
	private void handleCallMauEvent(CallMauEvent event) {
		tableService.callMau(event.getGameTable().getTableID(), event.getPlayerId());
		doTurn(event.getGameTable());
	}

	@EventListener
	private void handleSkipTurnEvent(SkipTurnEvent event) {
		tableService.skipTurn(event.getGameTable().getTableID(), event.getPlayerId());
		doTurn(event.getGameTable());
	}
	
	@EventListener
	private void handleLoadGameListEvent(LoadGameListEvent event) {
		tableView.printGameListMessage(tableService.loadGameList());
	}
	
	@EventListener
	private void handleLoadGameEvent(LoadGameEvent event) {
		doTurn(tableService.loadGame(event.getGameTable().getGameTableID()));
	}
	
	@EventListener
	private void handleLeaveGameEvent(LeaveGameEvent event) {
		initGame();
	}

	public void doTurn(GameTable gameTable) {
		if (gameTable.getGameOver()) {
			tableView.printGameOverMessage(gameTable);
			tableService.getOpenTables().remove(gameTable);
			tableService.removeGameTable(gameTable);
			initGame();
		} else if (gameTable.getCondition().equals(Condition.SKIP)) {
			tableView.printSkipMessage(gameTable);
		} else {
			tableView.printTableContent(gameTable);
			tableView.chooseAction(gameTable);
		}
	}

}
