package de.berlin.htw.kba.maumau.table.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.events.CallMauEvent;
import de.berlin.htw.kba.maumau.table.events.DoTurnEvent;
import de.berlin.htw.kba.maumau.table.events.DrawCardEvent;
import de.berlin.htw.kba.maumau.table.events.LeaveGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameListEvent;
import de.berlin.htw.kba.maumau.table.events.PlayCardEvent;
import de.berlin.htw.kba.maumau.table.events.PlayJackCardEvent;
import de.berlin.htw.kba.maumau.table.events.PlayerAmountEvent;
import de.berlin.htw.kba.maumau.table.events.SkipTurnEvent;
import de.berlin.htw.kba.maumau.table.events.StartGameEvent;
import de.berlin.htw.kba.maumau.table.service.ClientUser;
import de.berlin.htw.kba.maumau.table.service.DatabasePollingService;
import de.berlin.htw.kba.maumau.table.service.TableService;
import de.berlin.htw.kba.maumau.table.view.TableView;

@Controller
public class TableViewController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private TableService tableService;

    @Autowired
    private DatabasePollingService pollingService;

    private TableView tableView;

    public void setView(TableView tableView) {
        this.tableView = tableView;
        tableView.setEventPublisher(applicationEventPublisher);
    }

    public void initGameLobby() {
        tableView.initGameLobby();
    }

    @EventListener
    private void handleStartGameEvent(StartGameEvent event) {
        tableView.printAmountOfPlayersMessage();
    }

    @EventListener
    private void handlePlayerAmountEvent(PlayerAmountEvent event) {
        GameTable gameTable = tableService.initTable(event.getPlayerAmount());
        ClientUser clientUser = new ClientUser(gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId());
        pollingService.setClientUser(clientUser);
        pollingService.setStartPolling(true);
    }

    @EventListener
    private void handlePlayJackCardEvent(PlayJackCardEvent event) {
        tableView.printWishSuitMessage(event);
    }

    @EventListener
    private void handlePlayCardEvent(PlayCardEvent event) {
        if (!tableService.playCard(event.getGameTableId(), event.getPlayerId(), event.getCard(),
                event.getWishedSuit())) {
            tableView.printCardNotAllowedMessage();
        } else {
            pollingService.setStartPolling(true);
        }
    }

    @EventListener
    private void handleDrawCardEvent(DrawCardEvent event) {
        tableService.drawCards(event.getGameTableId(), event.getPlayerId());
        pollingService.setStartPolling(true);
    }

    @EventListener
    private void handleCallMauEvent(CallMauEvent event) {
        tableService.callMau(event.getGameTableId(), event.getPlayerId());
        tableView.chooseAction(tableService.loadGame(event.getGameTableId()));
    }

    @EventListener
    private void handleSkipTurnEvent(SkipTurnEvent event) {
        tableService.skipTurn(event.getGameTableId(), event.getPlayerId());
        pollingService.setStartPolling(true);
    }

    @EventListener
    private void handleLoadGameListEvent(LoadGameListEvent event) {
        tableView.printGameListMessage(tableService.loadGameList());
    }

    @EventListener
    private void handleLoadGameEvent(LoadGameEvent event) {
        GameTable gameTable = tableService.loadGame(event.getGameTableId());
        String playerId = tableService.getFreeSLot(gameTable.getGameTableID());
        if (playerId == null) {
            System.out.println("Sorry, no free slot.");
            tableView.initGameLobby();
        } else {
            gameTable.setLeaver(false);
            for (Player p : gameTable.getPlayers()) {
                if (p.getPlayerId().equals(playerId)) {
                    p.setControlledBy("human");
                }
            }
            tableService.saveTable(gameTable);
            ClientUser clientUser = new ClientUser(gameTable.getGameTableID(), playerId);
            pollingService.setClientUser(clientUser);
            pollingService.setStartPolling(true);
        }

    }

    @EventListener
    private void handleLeaveGameEvent(LeaveGameEvent event) {
        String playerId = pollingService.getPlayerId();
        pollingService.removePolling();
        tableService.removeControlledByFromPlayer(playerId, event.getGameTable());
        initGameLobby();
    }

    @EventListener
    private void doTurn(DoTurnEvent event) {
        GameTable gameTable = tableService.loadGame(event.getGameTableId());

        if (gameTable.getGameOver()) {
            tableView.printGameOverMessage(gameTable);
            tableService.removeGameTable(gameTable);
            initGameLobby();
        } else if (gameTable.getCondition().equals(Condition.SKIP)) {
            tableView.printSkipMessage(gameTable);
        } else {
            tableView.printTableContent(gameTable);
            tableView.chooseAction(gameTable);
        }
    }

}
