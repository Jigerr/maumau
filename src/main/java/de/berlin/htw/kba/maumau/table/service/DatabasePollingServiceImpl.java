package de.berlin.htw.kba.maumau.table.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.db.TableRepository;
import de.berlin.htw.kba.maumau.table.events.BotTurnEvent;
import de.berlin.htw.kba.maumau.table.events.DoTurnEvent;
import de.berlin.htw.kba.maumau.table.events.LeaveGameEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabasePollingServiceImpl.
 */
@Service
public class DatabasePollingServiceImpl implements DatabasePollingService {

    /** The Constant BOT. */
    private static final String BOT = "bot";

    /** The repository. */
    @Autowired
    private TableRepository repository;

    /** The application event publisher. */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /** The start polling. */
    private Boolean startPolling = false;

    /** The client user. */
    private ClientUser clientUser = null;

    /**
     * Do polling.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Scheduled(fixedDelay = 3 * 1000)
    private void doPolling() throws InterruptedException {

        if (startPolling == true) {
            GameTable table = repository.findOne(clientUser.getCurrentTable());

            if (table.getLeaver() == true) {
                System.out.println("Someone left the game...returning to game lobby.");
                setStartPolling(false);
                applicationEventPublisher.publishEvent(new LeaveGameEvent(clientUser.getCurrentPlayer(), table));
            } else if (table.getGameOver()) {
                System.out.println("Player " + table.getCurrentPlayer().getPlayerId() + " won the game...returning to game lobby.");
                setStartPolling(false);
                applicationEventPublisher.publishEvent(new LeaveGameEvent(clientUser.getCurrentPlayer(), table));
            } else if (table.getCurrentPlayer().getControlledBy() != null && table.getCurrentPlayer().getControlledBy().equals(BOT)) {
                applicationEventPublisher.publishEvent(new BotTurnEvent(this, table.getGameTableID()));
            } else {
                System.out.println("waiting for opponent's turn ...");

                if (table.getCurrentPlayer().getPlayerId().equals(clientUser.getCurrentPlayer())) {
                    setStartPolling(false);
                    applicationEventPublisher.publishEvent(new DoTurnEvent(this, table.getGameTableID()));
                }
            }

        }
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.DatabasePollingService#removePolling()
     */
    public void removePolling() {
        this.startPolling = false;
        this.clientUser = null;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.DatabasePollingService#setStartPolling(java.lang.Boolean)
     */
    public void setStartPolling(Boolean startPolling) {
        this.startPolling = startPolling;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.DatabasePollingService#setClientUser(de.berlin.htw.kba.maumau.table.service.ClientUser)
     */
    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.service.DatabasePollingService#getPlayerId()
     */
    public String getPlayerId() {
        return clientUser.getCurrentPlayer();
    }

}
