# TileO Game

### General Description
  This is a board game applicaiton that allows a user to design a board, set action cards and customize other aspects of the game, save it, and play it as many times as wanted.
  
### Design Mode
  When a game is first created the application enters design mode where the user can place tiles and create connections between them to let the players move. Then a single hidden tile, which makes a player win the game, is added to the board. Action tiles, which allow the player to draw a random action card are available as well. The deck has to be designed as well, several action cards with different effects are available to be picked. Any amount of any card can be choosen as long as the deck has 32 cards in total. Once the game is designed it gets saved and the user can play it as many times as wanted.
  
### Game Mode
  When the game starts players take turns to roll the dice and move around the board. Action cards are activated when a player lands on them and they release an action card that affects the game. The game ends when a player lands on the hidden tile and wins.
  
### Iplementation
  The game was implemented using a model oriented approach. A class diagram was created at the beginning of the development process and umple was used to generate this model. Specific functionality was then added to the generated, and manually creared classes.
