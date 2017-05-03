package de.berlin.htw.kba.maumau.table.db;

import java.util.LinkedList;

import de.berlin.htw.kba.maumau.ruleset.service.CardEffects;

public class Round {

	private Float gameID;
	private Stack drawingStack;
	private Stack playingStack;
	private LinkedList<Player> player = new LinkedList<>();
	private String currentPlayer;
	private Boolean gameDirectionClockwise = true;
	private Boolean gameOver = false;
	private CardEffects cardEffect;

	public Round() {
	}

	public Float getGameID() {
		return gameID;
	}

	public void setGameID(Float gameID) {
		this.gameID = gameID;
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

	public LinkedList<Player> getPlayer() {
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

	public CardEffects getCardEffect() {
		return cardEffect;
	}

	public void setCardEffect(CardEffects cardEffect) {
		this.cardEffect = cardEffect;
	}

}
