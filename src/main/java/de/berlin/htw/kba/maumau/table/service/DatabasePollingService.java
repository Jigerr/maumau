package de.berlin.htw.kba.maumau.table.service;

public interface DatabasePollingService {

    void setClientUser(ClientUser clientUser);

    void setStartPolling(Boolean startPolling);

    void removePolling();
    
    String getPlayerId();
}
