package de.berlin.htw.kba.maumau.table.events;

import org.springframework.context.ApplicationEvent;

public class DoTurnEvent extends ApplicationEvent{
    
    private Integer gameTableId;

    public DoTurnEvent(Object source, Integer gameTableId) {
        super(source);
        this.gameTableId = gameTableId;
    }

    public Integer getGameTableId() {
        return gameTableId;
    }

}
