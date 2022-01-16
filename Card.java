/**
 * Playing card implementation
 * @author Jonah Rosenberg
 */
public class Card {
    /**
     * CLUBS = +0
     * SPADES = +20
     * HEARTS = +40
     * DIAMONDS = +60
     */
    public enum Suit {
        CLUBS("Clubs"),
        SPADES("Spades"),
        HEARTS("Hearts"),
        DIAMONDS("Diamonds");
        private String suitName;
        private Suit(String suitName) {
            this.suitName = suitName;
        }
        @Override
        public String toString() {
            return suitName;
        }
    }

    private int value;
    private Suit suit;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Value of card for blackjack
     * @return blackjack card value
     */
    public int getValue() {
        return Math.min(value, 10);
    }
 
    /**
     * Value for sorting by including suit enum
     * @return weighted value
     */
    public int getWeightedValue() {
        int weightedValue = value;

        if (suit == Suit.SPADES) weightedValue+=20;
        else if (suit == Suit.HEARTS) weightedValue+=40;
        else if (suit == Suit.DIAMONDS) weightedValue+=60;

        return weightedValue;
    }

    private String getNameValue() {
        if (value == 1) return "Ace";
        else if (value == 11) return "Jack";
        else if (value == 12) return "Queen";
        else if (value == 13) return "King";
        return String.valueOf(value);
    }
    
    @Override
    public String toString() {
        return getNameValue() + " of " + suit;
    }
}
