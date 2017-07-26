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
import de.berlin.htw.kba.maumau.table.events.AmountOfCardsEvent;
import de.berlin.htw.kba.maumau.table.events.CallMauEvent;
import de.berlin.htw.kba.maumau.table.events.DrawCardEvent;
import de.berlin.htw.kba.maumau.table.events.GameModeEvent;
import de.berlin.htw.kba.maumau.table.events.LeaveGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameEvent;
import de.berlin.htw.kba.maumau.table.events.LoadGameListEvent;
import de.berlin.htw.kba.maumau.table.events.PlayCardEvent;
import de.berlin.htw.kba.maumau.table.events.PlayJackCardEvent;
import de.berlin.htw.kba.maumau.table.events.PlayerAmountEvent;
import de.berlin.htw.kba.maumau.table.events.SkipTurnEvent;
import de.berlin.htw.kba.maumau.table.events.StartGameEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class TableViewImpl.
 */
public class TableViewImpl implements TableView {

    /** The application event publisher. */
    private ApplicationEventPublisher applicationEventPublisher;

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#initGameLobby()
     */
    @Override
    public void initGameLobby() {
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printTableContent(de.berlin.htw.kba.maumau.table.db.GameTable)
     */
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printSkipMessage(de.berlin.htw.kba.maumau.table.db.GameTable)
     */
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

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#chooseAction(de.berlin.htw.kba.maumau.table.db.GameTable)
     */
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
                    applicationEventPublisher.publishEvent(new LeaveGameEvent(this, gameTable));
                    break;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printCardNotAllowedMessage()
     */
    @Override
    public void printCardNotAllowedMessage() {
        System.out.println("\n You cannot play this card! Please choose a different one.");
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#setEventPublisher(org.springframework.context.ApplicationEventPublisher)
     */
    @Override
    public void setEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printGameOverMessage(de.berlin.htw.kba.maumau.table.db.GameTable)
     */
    @Override
    public void printGameOverMessage(GameTable gameTable) {
        System.out.println("Game is over.");
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printGameListMessage(java.util.List)
     */
    @Override
    public void printGameListMessage(List<GameTable> gameTablelist) {
        int counter = 1;
        if (gameTablelist.isEmpty()) {
            System.out.println("\n");
            System.out.println("No games found. Returning to menu.");
            initGameLobby();
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
                        GameTable gameTable = gameTablelist.get(Integer.parseInt(input) - 1);
                        applicationEventPublisher.publishEvent(new LoadGameEvent(this, gameTable.getGameTableID()));
                        break;
                    }
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printWishSuitMessage(de.berlin.htw.kba.maumau.table.events.PlayJackCardEvent)
     */
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

    /**
     * Checks if is integer.
     *
     * @param str the str
     * @return true, if is integer
     */
    public static boolean isInteger(String str) {
        return str != null && str.length() > 0 && IntStream.range(0, str.length()).allMatch(
                i -> i == 0 && (str.charAt(i) == '-' || str.charAt(i) == '+') || Character.isDigit(str.charAt(i)));
    }

    /**
     * Checks if is between.
     *
     * @param x the x
     * @param lower the lower
     * @param upper the upper
     * @return true, if is between
     */
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printGameModeMessage()
     */
    @Override
    public void printGameModeMessage() {
        System.out.println("Do you want to play against bots or other players?");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please choose (B) for bots or (P) for other players.");

            String gameMode = null;
            String input = scanner.nextLine();
            switch (input) {
                case "B":
                case "P":
                    gameMode = input;
                    break;
                default:
                    break;
            }
            if (gameMode != null) {
                GameModeEvent event = new GameModeEvent(this, gameMode);
                applicationEventPublisher.publishEvent(event);
                break;
            }
        }

    }
    
    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printAmountOfCardsMessage(java.lang.String)
     */
    @Override
    public void printAmountOfCardsMessage(String gameMode) {
        System.out.println("With how many cards per hand you want to play?");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please choose (3), (4), (5) or (6).");

            int amountOfCards = 0;
            String input = scanner.nextLine();
            switch (input) {
                case "3":
                case "4":
                case "5":
                case "6":
                    amountOfCards = Integer.valueOf(input);
                    break;
                default:
                    break;
            }
            if (amountOfCards != 0) {
                AmountOfCardsEvent event = new AmountOfCardsEvent(this, amountOfCards, gameMode);
                applicationEventPublisher.publishEvent(event);
                break;
            }
        }

    }
    
    /* (non-Javadoc)
     * @see de.berlin.htw.kba.maumau.table.view.TableView#printAmountOfPlayersMessage(int, java.lang.String)
     */
    @Override
    public void printAmountOfPlayersMessage(int amountOfCards, String gameMode) {
        System.out.println("How many players should play this game?");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose (2), (3) or (4) players.");
            String playerAmount = null;
            String input = scanner.nextLine();
            switch (input) {
                case "2":
                case "3":
                case "4":
                    playerAmount = input;
                    break;
                default:
                    break;
            }
            if (playerAmount != null) {
                PlayerAmountEvent event = new PlayerAmountEvent(this, playerAmount, gameMode, amountOfCards);
                applicationEventPublisher.publishEvent(event);
                break;
            }
        }

    }

}
