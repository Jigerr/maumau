package de.berlin.htw.kba.maumau.table.view;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.events.PlayJackCardEvent;

public interface TableView {

	void initGame();

	void setEventPublisher(ApplicationEventPublisher applicationEventPublisher);

	void printTableContent(GameTable gameTable);

	void chooseAction(GameTable gameTable);

	void printSkipMessage(GameTable gameTable);

	void printCardNotAllowedMessage();

	void printGameOverMessage(GameTable gameTable);

	void printWishSuitMessage(PlayJackCardEvent event);

	void printGameListMessage(List<GameTable> gameTablelist);

    void printAmountOfPlayersMessage();

}
