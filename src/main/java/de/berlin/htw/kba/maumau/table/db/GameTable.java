package de.berlin.htw.kba.maumau.table.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.berlin.htw.kba.maumau.ruleset.service.Condition;

/**
 * The Class Table.
 */
@Entity
@Table(name = "GAME_TABLE")
public class GameTable {

	/** The table ID. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "GAME_TABLE_ID")
	private Integer gameTableID;

	/** The drawing stack. */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DRAWING_STACK_ID")
	private Stack drawingStack = new Stack();

	/** The playing stack. */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PLAYING_STACK_ID")
	private Stack playingStack = new Stack();

	/** The player. */
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="GAME_TABLE_ID")
	private List<Player> players = new ArrayList<Player>();

	/** The current player. */
	@OneToOne
	@JoinColumn(name="CURRENT_PLAYER_ID")
	private Player currentPlayer;

	/** The game direction clockwise. */
	@Column(name = "GAME_DIRECTION")
	private Boolean gameDirectionClockwise = true;

	/** The game over. */
	@Column(name = "GAME_OVER")
	private Boolean gameOver = false;

	/** The condition. */
	@Enumerated(EnumType.STRING)
	@Column(name = "CONDITIONS")
	private Condition condition = Condition.NO_EFFECT;

	/**
	 * Instantiates a new table.
	 */
	public GameTable() {
	}

	/**
	 * Gets the table ID.
	 *
	 * @return the table ID
	 */
	public Integer getTableID() {
		return gameTableID;
	}

	/**
	 * Sets the table ID.
	 *
	 * @param tableID
	 *            the new table ID
	 */
	public void setTableID(Integer tableID) {
		this.gameTableID = tableID;
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
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the player.
	 *
	 * @param player
	 *            the new player
	 */
	public void setPlayers(List<Player> player) {
		this.players = player;
	}

	/**
	 * Gets the current player.
	 *
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets the current player.
	 *
	 * @param currentPlayer
	 *            the new current player
	 */
	public void setCurrentPlayer(Player currentPlayer) {
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
	public Condition getCondition() {
		return condition;
	}

	/**
	 * Sets the condition.
	 *
	 * @param condition
	 *            the new condition
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
}
