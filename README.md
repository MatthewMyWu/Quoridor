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

Walls are straight and 2 units long — they are obstacles that prevent either player from moving through them.
 Walls can't be placed such that they are hanging/completely off the board,
 and they can't be placed on top of another wall.
 Once placed, they can not be moved, and you can not gain more walls if you run out.
 However, walls can not be placed in such a way that it is impossible to
 reach the other side (it must always be possible to get to the other side).

## Phase 3: how to use the program
At this stage I have implemented a fairly complete version of the game. When the program starts, you will be shown the
main menu. Press the "play" button to start the game.

Once in the game, you will see the board, with player 1 being represented by the cyan square, and player 2 by the red
square. To the right of the board, you will see information about the 2 players (eg. how many walls each player has
remaining, and their score). The green circle beside the player name indicates whose turn it is. Whichever player
presses the "Forfeit" button on their turn will forfeit the match. The "Restart" button restarts the match, and the
"Main Menu" button will return you to the main menu.

In order to move player 1, use the 'w', 'a', 's', and 'd' keys, to move up, left, down, and right, respectively.
Similarly, these controls are the up, left, down, and right arrow keys for player 2. Note that players can't move
off the board, over walls, or when it is not their turn. In order to place a wall, click the mouse at the corner where
you want the first end of the wall to be, and drag and release it at the corner where you want the other end of the wall
to be. (Make sure you are placing valid walls, and specified in the "How To Play Quorridor" section of this README).

Note that at this time, I was unable to finish the "Match History" (persistence) requirement for this project. 
Also note that I have had to made changes to how input and visual display are implemented, in order to implement the
GUI. I have not yet refactored the tests to account for these changes.

## Instructions for Grader
- You can generate the first required event by placing a wall on the board
(as specified in the "Phase 3: how to use the program" section of this README)
- You can generate the first required event by placing a wall on the board
  (as specified in the "Phase 3: how to use the program" section of this README)
- You can locate my visual component by looking on the board (where your wall will have been placed)
- You can save the state of my application by... (unimplemented at this time)
- You can reload the state of my application by... (unimplemented at this time)

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

##Phase 4: Task 2
I have decided to implement the "type hierarchy" option. The highest (super) class is the "Moveable" class (in model).
The subclasses are: Pathfinder, Avatar, GenericAvatar, P1, P2.
 - Problem 1: Originally I had implemented the methods to "move" in the Avatar class (which is meant to represent a
 player) I then realized that the pathfinder also needed access to these move methods so I abstracted these methods
 (and appropriate fields) to a new "Moveable" class (better cohesiveness).
- Originally I had both player 1 and player 2 being represented by the "Avatar" class. However, I realized that these
 two players had different win conditions, had different inputs needed to move, and had slightly different behaviour
 when they did move. As such, I created two new classes (P1 and P2) in order to have better cohesiveness
 (for Avatar, P1, and P2).
