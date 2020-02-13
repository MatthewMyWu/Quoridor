# Matthew's Quorridor

In high school, I took a few computer programming courses, and our computer science teacher would always have some games
 and/or puzzles set up in the classroom. My favourite was this 2 player board game named "Quorridor". It is fairly
 simple to learn, but also implements some intriguing strategies. I think it will be fun and interesting to code,
 especially since it ties back to my first experiences with coding. I also hope to make it enjoyable to play, as
 I haven't been able to play it since I have finished that class; I want to be able to play it with my friends.
 

## How to play Quorridor

Quorridor is played on a 9x9 board, and each player starts with their avatar on opposite sides (google "Quorridor" for
a visual). It is a turn based game, and the objective is to reach the other side of the board before your opponent.
Each turn, a player may:
- move 1 step in any direction (not diagonally), or
- place down a wall (each player has 10 walls)

Walls are 2 units long — they are obstacles that prevent either player from moving through them.
 Walls can't be placed such that they are hanging/completely off the board,
 and they can't be placed on top of another wall.
 Once placed, they can not be moved, and you can not gain more walls if you run out.
 However, walls can not be placed in such a way that it is impossible to
 reach the other side (it must always be possible to get to the other side).

## First deliverable: how to use the program
So far this is a very rudimentary version of the game.
- The graphics are text-based, with each block on the board being represented by an "0".
- If player 1 is standing on a block, then that block is displayed as a "1", and "2" if player 2 is standing on it.
- When it is player 1's turn, he can move up, left, down, or right, by using the "w", "a", "s", and "d" keys respectively.
The same applies for player two, except his controls are "i", "j", "k", and "l" (respectively).
- In order to place a wall, you must enter the coordinates of the endpoints of the wall in the form "A1,A3"
([letter coordinate][number coordinate],[letter coordinate][number coordinate]).

Currently, there is no mechanism for either player to win the game;
 it is possible to trap the other player in a box of walls (making the game unwinnable);
 it is possible for players to be on top of each other (player 1 will be displayed if this is the case);
 and both players have unlimited walls. I hope to fix all of these issues by the final deliverable.
 
**To run the program, simply run the "Main" class in the ui package**

## Implemented user stories
- As a user, I want to be able to see both avatars on the board
- As a user, I want to be able to move my avatar using the keyboard
- As a user, I want to not be able to move off the board
- As a user, I want to be able to add walls to the board
- As a user, I want to not be able to move over walls
- As a user, I want the game to be turn based
- As a user, I want an indicator to show whose turn it is

## Unimplemented user stories
- As a user, I want to be able to use the mouse to add walls to the board
- As a user, I want the game to disallow any wall placements that would make the game unwinnable
- As a user, I want the game to be visually appealing (eg. not have text-based graphics)
- As a user, I want to be able to win the game
- As a user, I want to have the option to restart the game at any point
- As a user, I want to be able to "forfeit" the match at any point
- As a user, I want to be able to play again when the game ends
- As a user, I want the game to display the score of each player
- (Optional) as a user, I want there to be a "minotaur" that starts in the middle of the map and moves randomly, and
if it reaches a player, that player loses