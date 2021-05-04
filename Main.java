package flashcards;

import java.io.*;
import java.util.*;

public class Main {
    static List<Card> listCards;
    static Scanner input;
    static List<String> log;
    static boolean isExportingCommandLine;
    static String exportFileName;
    static boolean isImportingCommandLine;
    static String importFileName;

    static final String MENU_TEXT = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
    static final String ADD_CARD_PROMPT = "The card:";
    static final String ADD_DEFINITION_PROMPT = "The definition of the card:";
    static final String CORRECT_ANSWER = "Correct!";
    static final String QUERY_CARD_TO_REMOVE = "Which card?:";
    static final String CARD_REMOVED = "The card has been removed.";
    static final String FILE_NAME_PROMPT = "File name:";
    static final String LOG_FILE_SAVED = "The log has been saved.";
    static final String FILE_NOT_FOUND = "File not found.";
    static final String HOW_MANY_TO_QUIZ = "How many times to ask?";
    static final String NO_ERROR_CARDS = "There are no cards with errors.";
    static final String STATS_RESET = "Card statistics have been reset";
    static final String BYE_BYE = "Bye bye!";


    public static void main(String[] args) {
        input = new Scanner(System.in);
        listCards = new ArrayList<>();
        log = new ArrayList<>();
        boolean keepGoing = true;
    
        isExportingCommandLine = false;
        exportFileName = "";
        isImportingCommandLine = false;
        importFileName = "";


        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-import")) {
                    isImportingCommandLine = true;
                    importFileName = args[i + 1];
                    importCards();
                }
                if (args[i].equals("-export")) {
                    isExportingCommandLine = true;
                    exportFileName = args[i + 1];
                }
            }

        }


        while (keepGoing) {
            System.out.println(MENU_TEXT);
            log.add(MENU_TEXT);
            String choice = input.nextLine();
            log.add(choice);

            switch (choice) {
                case "add": {
                    addCard();
                    break;
                }
                case "remove": {
                    removeCard();
                    break;
                }
                case "import": {
                    importCards();
                    break;
                }
                case "export": {
                    exportCards();
                    break;
                }
                case "ask": {
                    quizOnCards();
                    break;
                }
                case "exit": {
                    System.out.println(BYE_BYE);
                    log.add(BYE_BYE);
                    if (isExportingCommandLine) {
                        exportCards();
                    }
                    keepGoing = false;
                    break;
                }
                case "log": {
                    saveLogFile();
                    break;
                }
                case "hardest card": {
                    findHardestCard();
                    break;
                }
                case "reset stats": {
                    resetStats();
                    break;
                }
            }
        }
               
    }
    static void findHardestCard() {
        int mostMistakes = 0;
        ArrayList<Card> hardestCards = new ArrayList<>();

        for (Card card : listCards) {
            if (card.getMistakes() > mostMistakes) {
                mostMistakes = card.getMistakes();
                hardestCards.clear();
                hardestCards.add(card);
                continue;
            }
            if (mostMistakes > 0 && card.getMistakes() == mostMistakes) {
                hardestCards.add(card);
            }
        }
        if (mostMistakes == 0) {
            System.out.println(NO_ERROR_CARDS);
            log.add(NO_ERROR_CARDS);
        } else if (hardestCards.size() == 1) {
            Card hardCard = hardestCards.get(0);
            String hardCardMessage = String.format("The hardest card is \"%s\". You have %d errors answering it.", hardCard.getTerm(), mostMistakes);
            System.out.println(hardCardMessage);
            log.add(hardCardMessage);
        } else {
            StringBuilder hardCards = new StringBuilder("The hardest cards are ");
            for (int i = 0; i < hardestCards.size(); i++) {
                Card currentCard = hardestCards.get(i);
                hardCards.append("\"");
                hardCards.append(currentCard.getTerm());
                hardCards.append("\"");
                if (i < hardestCards.size() - 1) {
                    hardCards.append(", ");
                }
            }
            hardCards.append(". You have ");
            hardCards.append(mostMistakes);
            hardCards.append(" errors answering them.");
            System.out.println(hardCards);
            log.add(hardCards.toString());
        }
    }

    static void resetStats() {
        for (Card card : listCards) {
            card.resetMistakes();
        }
        System.out.println(STATS_RESET);
        log.add(STATS_RESET);
    }


    static void saveLogFile() {
        System.out.println(FILE_NAME_PROMPT);
        log.add(FILE_NAME_PROMPT);
        String name = input.nextLine();
        log.add(name);

        try (PrintWriter writer = new PrintWriter(name)) {
            for (var line : log) {
                writer.println(line);
            }
            writer.println(LOG_FILE_SAVED);
            writer.println(MENU_TEXT);
            System.out.println(LOG_FILE_SAVED);
            log.add(LOG_FILE_SAVED);
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
            log.add(FILE_NOT_FOUND);
        }
    }

    static void exportCards() {
        if (!isExportingCommandLine) {
            System.out.println(FILE_NAME_PROMPT);
            log.add(FILE_NAME_PROMPT);
            exportFileName = input.nextLine();
            log.add(exportFileName);
        }

        try (PrintWriter writer = new PrintWriter(exportFileName)) {
            int cardCounter = 0;
            for (var card : listCards) {
                writer.println(card.getTerm());
                writer.println(card.getDefinition());
                writer.println(card.getMistakes());
                cardCounter++;
            }
            String cardFileSaved = String.format("%d cards have been saved.", cardCounter);
            System.out.println(cardFileSaved);
            log.add(cardFileSaved);
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
            log.add(FILE_NOT_FOUND);
        }
    }

    static void importCards() {
        if (!isImportingCommandLine) {
            System.out.println(FILE_NAME_PROMPT);
            log.add(FILE_NAME_PROMPT);
            importFileName = input.nextLine();
            log.add(importFileName);
        }

        File fileName = new File(importFileName);

        try (Scanner scanner = new Scanner(fileName)) {
            int cardCounter = 0;
            while(scanner.hasNext()) {
                String term = scanner.nextLine();
                String definition = scanner.nextLine();
                int mistakes = Integer.parseInt(scanner.nextLine());

                if (containsTerm(term) > -1) {
                    Card duplicateCard = listCards.get(containsTerm(term));
                    listCards.remove(duplicateCard);
                }
                Card card = new Card(term, definition, mistakes);
                listCards.add(card);
                cardCounter++;
            }
            String cardFileImported = String.format("%d cards have been loaded.", cardCounter);
            System.out.println(cardFileImported);
            log.add(cardFileImported);
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
            log.add(FILE_NOT_FOUND);
        }
    }

    static void quizOnCards() {
        System.out.println(HOW_MANY_TO_QUIZ);
        log.add(HOW_MANY_TO_QUIZ);
        int numberOfQuestions = Integer.parseInt(input.nextLine());
        log.add(Integer.toString(numberOfQuestions));

        Random random = new Random();

        for (int i = 1; i <= numberOfQuestions; i++) {
            Card cardToGuess = listCards.get(random.nextInt(listCards.size()));
            String printTheDefinition = String.format("Print the definition of \"%s\"", cardToGuess.getTerm());
            System.out.println(printTheDefinition);
            log.add(printTheDefinition);
            String answer = input.nextLine();
            log.add(answer);
            String correctAnswer = cardToGuess.getDefinition();
            if (answer.equals(correctAnswer)) {
                System.out.println(CORRECT_ANSWER);
                log.add(CORRECT_ANSWER);
                continue;
            }
            int matchesOtherCard = containsDefinition(answer);
            if (matchesOtherCard > -1) {
                Card correctOtherCard = listCards.get(matchesOtherCard);
                String wrongAnswerButRightOther = String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".", correctAnswer, correctOtherCard.getTerm());
                System.out.println(wrongAnswerButRightOther);
                log.add(wrongAnswerButRightOther);
                cardToGuess.incrementMistakes();
                continue;
            }
            String wrongAnswer = String.format("Wrong. The right answer is \"%s\".", correctAnswer);
            System.out.println(wrongAnswer);
            log.add(wrongAnswer);
            cardToGuess.incrementMistakes();
        }
    }

    static void addCard() {
        System.out.println(ADD_CARD_PROMPT);
        log.add(ADD_CARD_PROMPT);

        String term = input.nextLine();
        log.add(term);
        if (containsTerm(term) >= 0 ) {
            String cardExistsMessage = String.format("The card \"%s\" already exists.",  term);
            System.out.println(cardExistsMessage);
            log.add(cardExistsMessage);
            return;
        }

        System.out.println(ADD_DEFINITION_PROMPT);
        log.add(ADD_DEFINITION_PROMPT);
        String definition = input.nextLine();
        log.add(definition);
        if (containsDefinition(definition) >= 0 ) {
            String cardExistsMessage = String.format("The definition \"%s\" already exists.",  definition);
            System.out.println(cardExistsMessage);
            log.add(cardExistsMessage);
            return;
        }

        Card card = new Card(term, definition);
        listCards.add(card);
        String cardAddedMessage = String.format("The pair (\"%s\":\"%s\") has been added.", term, definition);
        System.out.println(cardAddedMessage);
        log.add(cardAddedMessage);
    }

    static void removeCard() {
        System.out.println(QUERY_CARD_TO_REMOVE);
        log.add(QUERY_CARD_TO_REMOVE);
        String card = input.nextLine();
        log.add(card);
        for (Card currentCard : listCards) {
            if (currentCard.getTerm().equals(card)) {
                listCards.remove(currentCard);
                System.out.println(CARD_REMOVED);
                log.add(CARD_REMOVED);
                return;
            }
        }
        String removeCardMessage = String.format("Can't remove \"%s\": there is no such card.", card);
        System.out.println(removeCardMessage);
        log.add(removeCardMessage);
    }

    static int containsDefinition(String userDefinition) {
        for (Card card : listCards) {
            if (card.getDefinition().equals(userDefinition)) {
                return listCards.indexOf(card);
            }
        }
        return -1;
    }
    static int containsTerm(String userTerm) {
        for (Card card : listCards) {
            if (card.getTerm().equals(userTerm)) {
                return listCards.indexOf(card);
            }
        }
        return -1;
    }

}



