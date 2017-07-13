package de.berlin.htw.kba.maumau.table.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.db.TableRepository;
import de.berlin.htw.kba.maumau.table.events.DoTurnEvent;

@Service
public class DatabasePollingServiceImpl implements DatabasePollingService {
    
    @Autowired
    private TableRepository repository;
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    private Boolean startPolling = false;
    
    private ClientUser clientUser = null;
    
    
    @Scheduled(fixedDelay = 5*1000)
    private void doPolling() throws InterruptedException {
        
        if(startPolling == true) {
            
            System.out.println("waiting for oponent's turn ...");
            GameTable table = repository.findOne(clientUser.getCurrentTable());
            
            if(table.getCurrentPlayer().getPlayerId().equals(clientUser.getCurrentPlayer())) {
                setStartPolling(false);
                applicationEventPublisher.publishEvent(new DoTurnEvent(this, table.getGameTableID()));
            }
        }
        
    }
    
    public void stopPolling() {
        this.startPolling = false;
        this.clientUser = null;
    }
    
    public void setStartPolling(Boolean startPolling) {
        this.startPolling = startPolling;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

}
