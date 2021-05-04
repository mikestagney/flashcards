# Flashcards
Command line app to create, save/load digital flashcards and use them for quizzes.

## Things learned

First app created that logs all input and output

### Details

User options:

* add a card
* remove a card
* load cards from file
* save cards to file
* ask for definitions of some random cards
* save the application log to the given file
* print the term or terms that the user makes most mistakes with
* erase the mistake count for all cards
* exit the program

The directory contains 2 files: 

* Main.java - sets up and runs the app
* Card.java - class that defines flashcard objects

Ninth project created for JetBrains Academy Java Developer course - hard level project.

### How to Run

The app can run with or without command line arguments.

parses 2 arguments: -import, -export 
  
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
