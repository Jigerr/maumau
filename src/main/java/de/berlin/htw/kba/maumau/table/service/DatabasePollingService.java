package de.berlin.htw.kba.maumau.table.service;

// TODO: Auto-generated Javadoc
/**
 * The Interface DatabasePollingService.
 */
public interface DatabasePollingService {

    /**
     * Sets the client user.
     *
     * @param clientUser the new client user
     */
    void setClientUser(ClientUser clientUser);

    /**
     * Sets the start polling.
     *
     * @param startPolling the new start polling
     */
    void setStartPolling(Boolean startPolling);

    /**
     * Removes the polling.
     */
    void removePolling();
    
    /**
     * Gets the player id.
     *
     * @return the player id
     */
    String getPlayerId();
}
