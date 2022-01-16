import java.util.LinkedList;
import java.util.Scanner;

/**
 * Game implementation for starting game, playing, and ending.
 * @author Jonah Rosenberg
 */
public class Game {
    /**
     * Commands:
     *  help - show commands
     *  hand - show current hand
     *  score - show hand score
     *  dealer - show dealer hand
     *  hit - give player card
     *  stand - keep player hand
     *  bubble sort - bubble sort deck
     *  quick sort - quick sort deck
     */
    private Deck deck;

    public Game() {
        this.deck = new Deck();
        deck.shuffle();
        startGame();
    }

    private void startGame() {
        System.out.println("<<Starting game>>\n");

        Scanner s = new Scanner(System.in);
        
        LinkedList<Card> playerHand = deal();
        LinkedList<Card> dealerHand = deal();

        System.out.print(">> ");
        String req;
        
        if (getScore(playerHand) != 21) {
            req = s.nextLine();
            while (processRequest(req, playerHand, dealerHand)) {
                if (getScore(playerHand) > 21) break;

                System.out.print(">> ");
                req = s.nextLine();
            }
        }
        else {
            System.out.println("BlackJack!");
        }

        dealerPlay(dealerHand);

        if (determineWinner(playerHand, dealerHand)) System.out.println("You Won!\ntype \'p\' to play again.");
        else System.out.println("You lost.\ntype \'p\' to play again.");

        System.out.print(">> ");

        req = s.nextLine();
        if (req.equals("p")) {
            deck.returnCards(playerHand);
            deck.returnCards(dealerHand);
            startGame();
        }
        else s.close();
    }

    private boolean processRequest(String req, LinkedList<Card> player, LinkedList<Card> dealer) {
        if (req.equals("hand")) printHand(player);
        else if (req.equals("dealer")) printDealerHand(dealer);
        else if (req.equals("score")) System.out.println(getScore(player));
        else if (req.equals("hit")) hit(player);
        else if (req.equals("stand")) return false;
        else if (req.equals("bubble sort")) deck.bubbleSort();
        else if (req.equals("quick sort")) deck.performQuickSort();
        else if (req.equals("quit")) System.exit(1);
        else {
            System.out.println("help - show commands\n" +
                    "hand - show current hand\n" +
                    "score - show hand score\n" +
                    "dealer - show dealer hand\n" +
                    "hit - draw card\n" +
                    "stand - keep player hand\n" +
                    "bubble sort - bubble sort deck\n" +
                    "quick sort - quick sort deck\n" +
                    "quit - quit game");
        }

        return true;
    }

    private LinkedList<Card> deal() {
        LinkedList<Card> hand = new LinkedList<>();
        hand.add(deck.drawCard());
        hand.add(deck.drawCard());
        return hand;
    }

    private int getScore(LinkedList<Card> hand) {
        int score = 0;
        int aces = 0;
        for (Card card : hand) {
            if (card.getValue() == 1) {
                aces++;
                score+=10;
            }
            score+=card.getValue();
            if (score > 21 && aces > 0) {
                score -= 10 * aces;
                aces--;
            };
        }
        return score;
    }

    private boolean determineWinner(LinkedList<Card> player, LinkedList<Card> dealer) {
        if (getScore(player) > 21)
            return false;
        else if (getScore(dealer) > 21)
            return true;
        return getScore(player) >= getScore(dealer);
    }

    private LinkedList<Card> hit(LinkedList<Card> hand) {
        System.out.println("Hitting...");
        hand.add(deck.drawCard());
        printHand(hand);
        if (getScore(hand) > 21) System.out.println("Bust!");
        return hand;
    }

    private void printHand(LinkedList<Card> hand) {
        hand.forEach(card -> {
            System.out.println(card);
        });
        System.out.println("Score: " + getScore(hand));
        System.out.println();
    }

    private void printDealerHand(LinkedList<Card> hand) {
        System.out.println(hand.getFirst() + "\n Card");
        System.out.println();
    }

    private void dealerPlay(LinkedList<Card> hand) {
        System.out.println("Dealer Play: ");
        printHand(hand);
        while (getScore(hand) <= 16) {
            if (getScore(hand) > 21) break;
            hit(hand);
        }
    }
}
