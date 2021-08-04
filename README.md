## What is Quoridor

Quoridor is traditionally a 2-player turn-based board game, played on a 9x9 board.
 I wanted to create a virtual version of this game 

## How to play Quoridor

Each player starts with their avatar on opposite sides of the board.
 The objective is to reach the other side of the board before your opponent.
 Players take turns performing actions — each turn, a player may:
- move 1 step in any direction (not diagonally), or
- place down a wall (each player has 10 walls)

Walls are straight and 2 units long — they are obstacles that prevent either player from moving through them.
 * Walls can't be placed such that they are hanging/completely off the board,
 * Walls can't be placed on top of another wall.
 * Once placed, walls can not be moved.
 * Walls can not be placed in such a way that it completely blocks off either player from reaching their objective

## How to use the program

In order to move player 1, use the 'w', 'a', 's', and 'd' keys, to move up, left, down, and right, respectively.
Similarly, these controls are the up, left, down, and right arrow keys for player 2.

In order to place a wall, click the mouse at the corner where
you want the first end of the wall to be, and drag and release it at the corner where you want the other end of the wall
to be. (Make sure you are placing valid walls, and specified in the "How To Play Quorridor" section of this README).

## Implemented user stories
- As a user, I want to be able to see both avatars on the board
- As a user, I want to be able to move my avatar using the keyboard
- As a user, I want to not be able to move off the board
- As a user, I want to be able to add walls to the board
- As a user, I want to not be able to move over walls
- As a user, I want the game to be turn based
- As a user, I want an indicator to show whose turn it is
- As a user, I want the game to disallow any wall placements that would make the game unwinnable
- As a user, I want to be able to use the mouse to add walls to the board
- As a user, I want the game to be visually appealing (eg. not have text-based graphics)
- As a user, I want to be able to win the game
- As a user, I want to have the option to restart the game at any point
- As a user, I want to be able to "forfeit" the match at any point
- As a user, I want to be able to play again when the game ends
- As a user, I want the game to display the score of each player
- As a user, I want each player to be able to "jump" over each other (if moving into each other)

## Unimplemented user stories
- As a user, I want each finished game to be recorded into the "match history" (final layout of board),
which will store the past 10 games
- As a user, I want to be able to view the "match history"
- (Optional) as a user, I want there to be a "minotaur" that starts in the middle of the map and moves randomly, and
if it reaches a player, that player loses