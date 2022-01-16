import java.util.Collections;
import java.util.LinkedList;

/**
 * Deck implementation with card class to create a deck, shuffle, and sort
 * @author Jonah Rosenberg
 */
public class Deck {
    private LinkedList<Card> cards;
    private int cardsDrawn;

    /**
     * Decks are 52 cards
     */
    public Deck() {
        this.cards = new LinkedList<>();
        this.cardsDrawn = 0;
        createDeck();
    }

    public int getDeckSize() {
        return cards.size();
    }

    private void createDeck() {
        System.out.println("Creating Deck...");

        for (int i = 1; i < 51; i++) {
            Card.Suit cardSuit;
            if (i < 13) cardSuit = Card.Suit.CLUBS;
            else if (i < 26) cardSuit = Card.Suit.SPADES;
            else if (i < 39) cardSuit = Card.Suit.DIAMONDS;
            else cardSuit = Card.Suit.HEARTS;

            int value = i % 13;
            if (value == 0) value = 13;

            cards.add(new Card(value, cardSuit));
        }

        System.out.println("Deck created.");
        printDeck();
    }
    
    private void printDeck() {
        cards.forEach(card -> System.out.println(card));
    }

    public Card drawCard() {
        System.out.println("Drawing card...");
        if (cardsDrawn == 52) {
            cardsDrawn = 0;
            shuffle();
        }
        else cardsDrawn++;
        System.out.println();
        return cards.remove();
    }

    public void returnCards(LinkedList<Card> hands) {
        System.out.println("Returning cards to deck...");
        hands.forEach(card -> {
            cards.add(card);
        });
        System.out.println("Cards returned to deck.");
    }

    public void shuffle() {
        System.out.println("Shuffling Deck...");
        Collections.shuffle(cards);
        System.out.println("Deck Shuffled.");
    }
    
    // SORTING

    private void swap(int i, int j) {
        Card temp = cards.get(j);
        cards.set(j, cards.get(i));
        cards.set(i, temp);
    }

    /**
     * Bubble Sort
     */
    public void bubbleSort() {
        System.out.println("Sorting current deck using Bubble sort...");
        System.out.println("Current deck:\n" + cards);

        long start = System.nanoTime();

        int n = cards.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (cards.get(j).getWeightedValue() > cards.get(j+1).getWeightedValue()) {
                    swap(j, j+1);
                }

        long end = System.nanoTime();
        long total = end - start;
        double seconds = (double) total / 1_000_000_000.0;
        System.out.println("Deck sorted using Bubble sort.");
        System.out.println("Deck is now: ");
        System.out.println(cards);
        System.out.println("Time taken to sort: " + seconds + " seconds.");
        System.out.print("Re-");
        shuffle();
    }
    
    // Quick Sort

    private int partition(int low, int high) {
        int pivot = cards.get(high).getWeightedValue();

        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            if (cards.get(j).getWeightedValue() < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return (i + 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    public void performQuickSort() {
        System.out.println("Sorting current deck using Quick Sort...");
        System.out.println("Current deck:\n" + cards);
        long start = System.nanoTime();
        quickSort(0, cards.size()-1);
        long end = System.nanoTime();
        long total = end - start;
        double seconds = (double) total / 1_000_000_000.0;
        System.out.println("Deck sorted using Quick sort.");
        System.out.println("Deck is now: ");
        System.out.println(cards);
        System.out.println("Time taken to sort: " + seconds + " seconds.");
        System.out.print("Re-");
        shuffle();
    }
    
}
