package de.berlin.htw.kba.maumau.table.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.ruleset.service.WrongCardException;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.events.AmountOfCardsEvent;
import de.berlin.htw.kba.maumau.table.events.CallMauEvent;
import de.berlin.htw.kba.maumau.table.events.DoTurnEvent;
import de.berlin.htw.kba.maumau.table.events.DrawCardEvent;
import de.berlin.htw.kba.maumau.table.events.GameModeEvent;
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

// TODO: Auto-generated Javadoc
/**
 * The Class TableViewController.
 */
@Controller
public class TableViewController {

    /** The application event publisher. */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /** The table service. */
    @Autowired
    private TableService tableService;

    /** The polling service. */
    @Autowired
    private DatabasePollingService pollingService;

    /** The table view. */
    private TableView tableView;

    /**
     * Sets the view.
     *
     * @param tableView the new view
     */
    public void setView(TableView tableView) {
        this.tableView = tableView;
        tableView.setEventPublisher(applicationEventPublisher);
    }

    /**
     * Inits the game lobby.
     */
    public void initGameLobby() {
        tableView.initGameLobby();
    }

    /**
     * Handle start game event.
     *
     * @param event the event
     */
    @EventListener
    private void handleStartGameEvent(StartGameEvent event) {
        tableView.printGameModeMessage();
    }
    
    /**
     * Handle game mode event.
     *
     * @param event the event
     */
    @EventListener
    private void handleGameModeEvent(GameModeEvent event) {
        tableView.printAmountOfCardsMessage(event.getGameMode());
    }
    
    /**
     * Handle amount of cards event.
     *
     * @param event the event
     */
    @EventListener
    private void handleAmountOfCardsEvent(AmountOfCardsEvent event) {
        tableView.printAmountOfPlayersMessage(event.getAmountOfCards(), event.getGameMode());;
    }
    
    /**
     * Handle player amount event.
     *
     * @param event the event
     */
    @EventListener
    private void handlePlayerAmountEvent(PlayerAmountEvent event) {
        GameTable gameTable = tableService.initTable(event.getPlayerAmount(), event.getGameMode(), event.getAmountOfCards());
        ClientUser clientUser = new ClientUser(gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId());
        pollingService.setClientUser(clientUser);
        pollingService.setStartPolling(true);
    }

    /**
     * Handle play jack card event.
     *
     * @param event the event
     */
    @EventListener
    private void handlePlayJackCardEvent(PlayJackCardEvent event) {
        tableView.printWishSuitMessage(event);
    }

    /**
     * Handle play card event.
     *
     * @param event the event
     */
    @EventListener
    private void handlePlayCardEvent(PlayCardEvent event) {
        try {
            tableService.playCard(event.getGameTableId(), event.getPlayerId(), event.getCard(),
                    event.getWishedSuit());
            pollingService.setStartPolling(true);
        } catch (WrongCardException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            GameTable gameTable = tableService.loadGame(event.getGameTableId());
            tableView.chooseAction(gameTable);
        }
    }

    /**
     * Handle draw card event.
     *
     * @param event the event
     */
    @EventListener
    private void handleDrawCardEvent(DrawCardEvent event) {
        tableService.drawCards(event.getGameTableId(), event.getPlayerId());
        pollingService.setStartPolling(true);
    }

    /**
     * Handle call mau event.
     *
     * @param event the event
     */
    @EventListener
    private void handleCallMauEvent(CallMauEvent event) {
        tableService.callMau(event.getGameTableId(), event.getPlayerId());
        tableView.chooseAction(tableService.loadGame(event.getGameTableId()));
    }

    /**
     * Handle skip turn event.
     *
     * @param event the event
     */
    @EventListener
    private void handleSkipTurnEvent(SkipTurnEvent event) {
        tableService.skipTurn(event.getGameTableId(), event.getPlayerId());
        pollingService.setStartPolling(true);
    }

    /**
     * Handle load game list event.
     *
     * @param event the event
     */
    @EventListener
    private void handleLoadGameListEvent(LoadGameListEvent event) {
        tableView.printGameListMessage(tableService.loadGameList());
    }

    /**
     * Handle load game event.
     *
     * @param event the event
     */
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

    /**
     * Handle leave game event.
     *
     * @param event the event
     */
    @EventListener
    private void handleLeaveGameEvent(LeaveGameEvent event) {
        String playerId = pollingService.getPlayerId();
        pollingService.removePolling();
        tableService.removeControlledByFromPlayer(playerId, event.getGameTable());
        initGameLobby();
    }

    /**
     * Do turn.
     *
     * @param event the event
     */
    @EventListener
    private void doTurn(DoTurnEvent event) {
        GameTable gameTable = tableService.loadGame(event.getGameTableId());

        if (gameTable.getGameOver()) {
            tableView.printGameOverMessage(gameTable);
            pollingService.removePolling();
            initGameLobby();
        } else if (gameTable.getCondition().equals(Condition.SKIP)) {
            tableView.printSkipMessage(gameTable);
        } else {
            tableView.printTableContent(gameTable);
            tableView.chooseAction(gameTable);
        }
    }

}
