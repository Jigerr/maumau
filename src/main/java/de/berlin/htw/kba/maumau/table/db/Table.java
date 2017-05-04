package de.berlin.htw.kba.maumau.table.db;

import java.util.LinkedList;

import de.berlin.htw.kba.maumau.ruleset.service.Conditions;

public class Table {

	private String tableID;
	private Stack drawingStack = new Stack();
	private Stack playingStack = new Stack();
	private LinkedList<Player> player = new LinkedList<>();
	private String currentPlayer;
	private Boolean gameDirectionClockwise = true;
	private Boolean gameOver = false;
	private Conditions conditions = Conditions.NO_EFFECT;

	public Table() {
	}

	public String getTableID() {
		return tableID;
	}

	public void setTableID(String tableID) {
		this.tableID = tableID;
	}

	public Stack getDrawingStack() {
		return drawingStack;
	}

	public void setDrawingStack(Stack drawingStack) {
		this.drawingStack = drawingStack;
	}

	public Stack getPlayingStack() {
		return playingStack;
	}

	public void setPlayingStack(Stack playingStack) {
		this.playingStack = playingStack;
	}

	public LinkedList<Player> getPlayers() {
		return player;
	}

	public void setPlayer(LinkedList<Player> player) {
		this.player = player;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Boolean getGameDirectionClockwise() {
		return gameDirectionClockwise;
	}

	public void setGameDirectionClockwise(Boolean gameDirectionClockwise) {
		this.gameDirectionClockwise = gameDirectionClockwise;
	}

	public Boolean getGameOver() {
		return gameOver;
	}

	public void setGameOver(Boolean gameOver) {
		this.gameOver = gameOver;
	}

    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }
}
