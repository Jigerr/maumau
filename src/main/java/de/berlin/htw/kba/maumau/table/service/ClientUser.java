package de.berlin.htw.kba.maumau.table.service;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientUser.
 */
public class ClientUser {

    /** The current table. */
    private Integer currentTable = null;
    
    /** The current player. */
    private String currentPlayer = null;

    /**
     * Instantiates a new client user.
     */
    public ClientUser() {
    }
    
    /**
     * Instantiates a new client user.
     *
     * @param currentTable the current table
     * @param currentPlayer the current player
     */
    public ClientUser(Integer currentTable, String currentPlayer) {
        this.currentTable = currentTable;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Gets the current table.
     *
     * @return the current table
     */
    public Integer getCurrentTable() {
        return currentTable;
    }

    /**
     * Sets the current table.
     *
     * @param currentTable the new current table
     */
    public void setCurrentTable(Integer currentTable) {
        this.currentTable = currentTable;
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player.
     *
     * @param currentPlayer the new current player
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
