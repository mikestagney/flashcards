# Flashcards
Command line app to create, save and load digital flashcards and use them for quizzes.

## Things learned

First app created that logs all input and output

### Details

User options:

* Add a card. Duplicate cards are not allowed.
* Remove a card.
* Load cards from file. Adds to the current cards in memory.
* Save cards to file.
* Ask for definitions of some random cards. User chooses the number of cards and the app tracks the mistakes for each card.
* Save the application log to file.
* Print the card or cards that the user makes most mistakes
* Erase the mistake count for all cards.
* Exit the app.

The directory contains 2 files: 

* Main.java - sets up and runs the app
* Card.java - class that defines flashcard objects

Ninth project created for JetBrains Academy Java Developer course - hard level project.

### How to Run

The app can run with or without command line arguments.

Parses 2 arguments: -import, -export 
  
-import: takes the file name to read the initial card set from the external file and prints the message "n cards have been loaded."

-export: takes the file name to write all cards that are in the program memory into the file after the user has selected exit. The last line of the output is "n cards have been saved."

#### Run arguments examples:

java Flashcards -export animals.txt

java Flashcards -import words13june.txt -export words14june.txt

java Flashcards -export vocab.txt -import vocab.txt 

### Sample input and output:

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> hardest card\
There are no cards with errors.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> import\
File name:\
\> capitals.txt\
28 cards have been loaded.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> hardest card\
The hardest card is "France". You have 10 errors answering it.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> ask\
How many times to ask?\
\> 1\
Print the definition of "Russia":\
\> Paris\
Wrong. The right answer is "Moscow", but your definition is correct for "France" card.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> hardest card\
The hardest cards are "Russia", "France". You have 10 errors answering them.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> reset stats\
Card statistics have been reset.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> hardest card\
There are no cards with errors.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> log\
File name:\
\> todayLog.txt\
The log has been saved.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\
\> exit\
Bye bye!
