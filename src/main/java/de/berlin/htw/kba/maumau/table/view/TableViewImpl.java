package de.berlin.htw.kba.maumau.table.view;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import de.berlin.htw.kba.maumau.cardmaster.service.Ranks;
import de.berlin.htw.kba.maumau.cardmaster.service.Suits;
import de.berlin.htw.kba.maumau.ruleset.service.Condition;
import de.berlin.htw.kba.maumau.table.db.Card;
import de.berlin.htw.kba.maumau.table.db.GameTable;
import de.berlin.htw.kba.maumau.table.events.CallMauEvent;
import de.berlin.htw.kba.maumau.table.events.DrawCardEvent;
import de.berlin.htw.kba.maumau.table.events.LeaveGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameListEvent;
import de.berlin.htw.kba.maumau.table.events.PlayCardEvent;
import de.berlin.htw.kba.maumau.table.events.PlayJackCardEvent;
import de.berlin.htw.kba.maumau.table.events.PlayerAmountEvent;
import de.berlin.htw.kba.maumau.table.events.SkipTurnEvent;
import de.berlin.htw.kba.maumau.table.events.StartGameEvent;

public class TableViewImpl implements TableView {

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void initGame() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("----------------------------\n");
			System.out.println("Choose Option: (N)ew Game, (L)oad Game, (E)xit");
			String input = scanner.nextLine();
			if ("E".equals(input)) {
				System.out.println("Exit!");
				System.exit(0);
			} else if ("N".equals(input)) {
				System.out.println("Creating New Game! \n");
				applicationEventPublisher.publishEvent(new StartGameEvent(this));
				break;
			} else if ("L".equals(input)) {
				System.out.println("Start to load all games!");
				applicationEventPublisher.publishEvent(new LoadGameListEvent(this));
				break;
			}
		}
	}

	@Override
	public void printTableContent(GameTable gameTable) {
		System.out.println("----------------------------\n");
		System.out.println("Player " + gameTable.getCurrentPlayer().getPlayerId() + "'s turn \n");
		if (!gameTable.getCondition().equals(Condition.NO_EFFECT)) {
			System.out.println("Current condititon: " + gameTable.getCondition());
		}
		System.out.println("Cards remaining in drawing stack: " + gameTable.getDrawingStack().amountOfCards() + "\n");
		System.out.println("Top card: " + gameTable.getPlayingStack().getCardList()
				.get(gameTable.getPlayingStack().getCardList().size() - 1).printCard() + "\n");
		System.out.println("Your Hand: ");
		int counter = 1;
		for (Card c : gameTable.getCurrentPlayer().getHand()) {
			System.out.println("(" + counter + ") " + c.printCard());
			counter++;
		}
	}

	@Override
	public void printSkipMessage(GameTable gameTable) {
		System.out.println("----------------------------\n");
		System.out.println("Player " + gameTable.getCurrentPlayer().getPlayerId() + "'s turn \n");
		System.out.println("Top card: " + gameTable.getPlayingStack().getCardList()
				.get(gameTable.getPlayingStack().getCardList().size() - 1).printCard() + "\n");
		if (!gameTable.getCondition().equals(Condition.NO_EFFECT)) {
			System.out.println("Current condititon: " + gameTable.getCondition());
		}
		System.out.println("You have to skip this turn!");
		System.out.println("Press any key to continue");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine();
			applicationEventPublisher
					.publishEvent(new SkipTurnEvent(this, gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId()));
			break;
		}

	}

	@Override
	public void chooseAction(GameTable gameTable) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n");

		while (true) {
			System.out.println("Please choose an action: ");
			System.out.println("# Play a card by typing its number");
			System.out.println("# (D) Draw card");
			System.out.println("# (C) Call mau");
			System.out.println("# (L) Leave game and back to menu");
			String input = scanner.nextLine();

			if (isInteger(input)) {
				if (isBetween(Integer.parseInt(input), 1, gameTable.getCurrentPlayer().getHand().size())) {
					Card c = gameTable.getCurrentPlayer().getHand().get(Integer.parseInt(input) - 1);
					ApplicationEvent event;
					if (c.getRank().equals(Ranks.JACK.getValue())) {
						event = new PlayJackCardEvent(this, c, gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId());
					} else {
						event = new PlayCardEvent(this, c, gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId(), null);
					}
					System.out.println("Playing card: " + c.printCard());
					applicationEventPublisher.publishEvent(event);
					break;
				}
			} else {
				if ("D".equals(input)) {
					System.out.println("Drawing card!");
					applicationEventPublisher.publishEvent(
							new DrawCardEvent(this, gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId()));
					break;
				} else if ("C".equals(input)) {
					System.out.println("Calling mau!");
					applicationEventPublisher.publishEvent(
							new CallMauEvent(this, gameTable.getGameTableID(), gameTable.getCurrentPlayer().getPlayerId()));
				} else if ("L".equals(input)) {
					System.out.println("Leaving game!");
					applicationEventPublisher.publishEvent(new LeaveGameEvent(this));
				}
			}
		}
	}

	@Override
	public void printCardNotAllowedMessage() {
		System.out.println("\n");
		System.out.println("You cannot play this card! Please choose a different one.");
	}

	@Override
	public void setEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void printGameOverMessage(GameTable gameTable) {
		System.out.println("Game is over.");
	}

	@Override
	public void printGameListMessage(List<GameTable> gameTablelist) {
		int counter = 1;
		if (gameTablelist.isEmpty()) {
			System.out.println("\n");
			System.out.println("No games found. Returning to menu.");
			initGame();
		} else {
			System.out.println("\n Listing all found games: \n");
			for (GameTable table : gameTablelist) {
				System.out.println("(" + counter + ") " + "Gametable ID: " + table.getGameTableID() + ", Created on: "
						+ table.getCreated());
				counter++;
			}
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Please choose a game to load by typing its number.");
				String input = scanner.nextLine();
				if (isInteger(input)) {
					if (isBetween(Integer.parseInt(input), 1, gameTablelist.size())) {
						GameTable gameTable = gameTablelist.get(Integer.parseInt(input)-1);
						applicationEventPublisher.publishEvent(new LoadGameEvent(this, gameTable.getGameTableID()));
						break;
					}
				}
			}
		}
	}

	@Override
	public void printWishSuitMessage(PlayJackCardEvent jackEvent) {
		System.out.println("\n");
		System.out.println("A Jack was played, please choose a suit.");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Choose between: (C)lubs, (D)iamonds, (H)earts, (S)pades");
			Suits wishedSuit = null;
			String input = scanner.nextLine();
			switch (input) {
			case "C":
				wishedSuit = Suits.CLUBS;
				break;
			case "D":
				wishedSuit = Suits.DIAMONDS;
				break;
			case "H":
				wishedSuit = Suits.HEARTS;
				break;
			case "S":
				wishedSuit = Suits.SPADES;
				break;
			default:
				break;
			}
			if (wishedSuit != null) {
				PlayCardEvent event = new PlayCardEvent(this, jackEvent.getCard(), jackEvent.getGameTableId(),
						jackEvent.getPlayerId(), wishedSuit);
				applicationEventPublisher.publishEvent(event);
				break;
			}
		}

	}
	
    @Override
    public void printAmountOfPlayersMessage() {
        System.out.println("How many players should play this game?");
        
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Choose (2), (3) or (4) players.");
            String playerAmount = null;
            String input = scanner.nextLine();
            switch(input) {
                case "2":
                case "3":
                case "4":
                    playerAmount = input;
                    break;
                default:
                    break;                
            }
            if(playerAmount != null) {
                PlayerAmountEvent event = new PlayerAmountEvent(this, playerAmount);
                applicationEventPublisher.publishEvent(event);
                break;
            }
            
            
        }
        
    }

	public static boolean isInteger(String str) {
		return str != null && str.length() > 0 && IntStream.range(0, str.length()).allMatch(
				i -> i == 0 && (str.charAt(i) == '-' || str.charAt(i) == '+') || Character.isDigit(str.charAt(i)));
	}

	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}



}
