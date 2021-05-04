package flashcards;

public class Card {

    private final String term;
    private final String definition;
    private int mistakes;

    Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
        mistakes = 0;
    }
    Card(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }

    public String getDefinition() {
        return definition;
    }

    public String getTerm() {
        return term;
    }

    public void incrementMistakes() { mistakes++; }

    public int getMistakes() { return mistakes;   }

    public void resetMistakes() {
        mistakes = 0;
    }
}