package de.berlin.htw.kba.maumau.table.service;

import java.util.ArrayList;
import java.util.List;

import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.Table;

public class TableServiceImpl implements TableService {
    
    private List<Table> openTables = new ArrayList<Table>();
    
	@Override
	public Card drawCard(String tableId, String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean playCard(String tableId, String accountId, Card currentCard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void callMau(String tableId, String accountId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTurn(String tableId, String accountId) {
		Table tempTable;
		for(Table table: openTables) {
		    if(table.getTableID().equals(tableId)) {
		        tempTable = table;
		    }
		}
		
		

	}

	@Override
    public List<Table> getOpenTables() {
        return openTables;
    }

	@Override
    public void setOpenTables(List<Table> openTables) {
        this.openTables = openTables;
    }

}
