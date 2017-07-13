package de.berlin.htw.kba.maumau.table.service;

public class ClientUser {

    private Integer currentTable = null;
    private String currentPlayer = null;

    public ClientUser() {
    }
    
    public ClientUser(Integer currentTable, String currentPlayer) {
        this.currentTable = currentTable;
        this.currentPlayer = currentPlayer;
    }

    public Integer getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(Integer currentTable) {
        this.currentTable = currentTable;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
