package de.berlin.htw.kba.maumau.model;

import java.util.LinkedList;

//test comment
public class Round {

    private Float gameID;
    private Stack drawingStack;
    private Stack playingStack;
    private LinkedList<Player> player = new LinkedList<>();
    private String currentPlayer;
    private Boolean gameDirectionClockwise = true;
    private Boolean gameOver = false;
    private String wishedSuit;

    public Round() {
    }

/*
    public Round(Stack stack, LinkedList<Player> player, String currentPlayer) {

        this.stack = new Stack();

        this.player.add(new Player("Player 1"));
        this.player.add(new Player("Player 2"));

        //5 Karten pro Player
        for (int i = 1; i <= 5; i++) {

//            Card neueCard = this.stack.
//            this.player.getFirst().karteHinzufuegen();
        }

        // 1 Card in der Mitte


        this.currentPlayer = new String(this.player.getFirst().getName());
    }
*/

}
