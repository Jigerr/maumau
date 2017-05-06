package de.berlin.htw.kba.maumau.table.db;

import java.util.LinkedList;

import de.berlin.htw.kba.maumau.ruleset.service.Conditions;

/**
 * The Class Table.
 */
public class Table {

	/** The table ID. */
	private String tableID;

	/** The drawing stack. */
	private Stack drawingStack = new Stack();

	/** The playing stack. */
	private Stack playingStack = new Stack();

	/** The player. */
	private LinkedList<Player> player = new LinkedList<>();

	/** The current player. */
	private String currentPlayer;

	/** The game direction clockwise. */
	private Boolean gameDirectionClockwise = true;

	/** The game over. */
	private Boolean gameOver = false;

	/** The condition. */
	private Conditions condition = Conditions.NO_EFFECT;

	/**
	 * Instantiates a new table.
	 */
	public Table() {
	}

	/**
	 * Gets the table ID.
	 *
	 * @return the table ID
	 */
	public String getTableID() {
		return tableID;
	}

	/**
	 * Sets the table ID.
	 *
	 * @param tableID
	 *            the new table ID
	 */
	public void setTableID(String tableID) {
		this.tableID = tableID;
	}

	/**
	 * Gets the drawing stack.
	 *
	 * @return the drawing stack
	 */
	public Stack getDrawingStack() {
		return drawingStack;
	}

	/**
	 * Sets the drawing stack.
	 *
	 * @param drawingStack
	 *            the new drawing stack
	 */
	public void setDrawingStack(Stack drawingStack) {
		this.drawingStack = drawingStack;
	}

	/**
	 * Gets the playing stack.
	 *
	 * @return the playing stack
	 */
	public Stack getPlayingStack() {
		return playingStack;
	}

	/**
	 * Sets the playing stack.
	 *
	 * @param playingStack
	 *            the new playing stack
	 */
	public void setPlayingStack(Stack playingStack) {
		this.playingStack = playingStack;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public LinkedList<Player> getPlayers() {
		return player;
	}

	/**
	 * Sets the player.
	 *
	 * @param player
	 *            the new player
	 */
	public void setPlayer(LinkedList<Player> player) {
		this.player = player;
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
	 * @param currentPlayer
	 *            the new current player
	 */
	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Gets the game direction clockwise.
	 *
	 * @return the game direction clockwise
	 */
	public Boolean getGameDirectionClockwise() {
		return gameDirectionClockwise;
	}

	/**
	 * Sets the game direction clockwise.
	 *
	 * @param gameDirectionClockwise
	 *            the new game direction clockwise
	 */
	public void setGameDirectionClockwise(Boolean gameDirectionClockwise) {
		this.gameDirectionClockwise = gameDirectionClockwise;
	}

	/**
	 * Gets the game over.
	 *
	 * @return the game over
	 */
	public Boolean getGameOver() {
		return gameOver;
	}

	/**
	 * Sets the game over.
	 *
	 * @param gameOver
	 *            the new game over
	 */
	public void setGameOver(Boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public Conditions getCondition() {
		return condition;
	}

	/**
	 * Sets the condition.
	 *
	 * @param condition
	 *            the new condition
	 */
	public void setCondition(Conditions condition) {
		this.condition = condition;
	}
}
