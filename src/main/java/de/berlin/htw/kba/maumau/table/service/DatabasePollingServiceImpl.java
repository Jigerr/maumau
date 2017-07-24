package de.berlin.htw.kba.maumau.table.service;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.player.db.Player;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.db.TableRepository;
import de.berlin.htw.kba.maumau.table.events.DoTurnEvent;
import de.berlin.htw.kba.maumau.table.events.LeaveGameEvent;
import de.berlin.htw.kba.maumau.table.events.SkipTurnEvent;

@Service
public class DatabasePollingServiceImpl implements DatabasePollingService {

    @Autowired
    private TableRepository repository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private Boolean startPolling = false;

    private ClientUser clientUser = null;

    @Scheduled(fixedDelay = 5 * 1000)
    private void doPolling() throws InterruptedException {
        
        mainloop: if (startPolling == true) {
            GameTable table = repository.findOne(clientUser.getCurrentTable());
            
            if(table.getLeaver() == true) {
                System.out.println("Someone left the game...returning to game lobby.");
                applicationEventPublisher.publishEvent(new LeaveGameEvent(clientUser.getCurrentPlayer(), table));
                setStartPolling(false);
                break mainloop;
            }

            System.out.println("waiting for opponent's turn ...");

            if (table.getCurrentPlayer().getPlayerId().equals(clientUser.getCurrentPlayer())) {
                setStartPolling(false);
                applicationEventPublisher.publishEvent(new DoTurnEvent(this, table.getGameTableID()));
            }
        }
    }

    public void removePolling() {
        this.startPolling = false;
        this.clientUser = null;
    }

    public void setStartPolling(Boolean startPolling) {
        this.startPolling = startPolling;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public String getPlayerId() {
        return clientUser.getCurrentPlayer();
    }

}
