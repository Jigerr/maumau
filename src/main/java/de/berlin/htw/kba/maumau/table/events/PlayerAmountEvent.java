package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

public class PlayerAmountEvent extends ApplicationEvent {

    private String playerAmount;
    
    public PlayerAmountEvent(Object source, String playerAmount) {
        super(source);
        this.playerAmount = playerAmount;
    }

    public String getPlayerAmount() {
        return playerAmount;
    }

}
