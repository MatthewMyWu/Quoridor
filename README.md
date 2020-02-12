# Matthew's Quorridor



In high school, I took a few computer programming courses, and our computer science teacher would always have some games
and/or puzzles set up in the classroom. My favourite was this 2 player board game named "Quorridor". It is fairly
simple to learn, but also implements some intriguing strategies. I think it will be fun and interesting to code,
especially since it ties back to my first experiences with coding. I also hope to make it enjoyable to play, as
I haven't been able to play it since I have finished that class; I want to be able to play it with my friends.
 


##How to play Quorridor

It is played on a 9x9 board, and each player starts with their avatar on opposite sides (google "Quorridor" for
a visual). It is a turn based game, and the objective is to reach the other side of the board before your opponent.
Each turn, a player may:
- move 1 step in any direction (not diagonally), or
- place down a wall (each player has 10 walls)

Walls are obstacles that prevent either player from moving through them. Once placed, they can not be moved, and
you can not gain more walls if you run out. However, walls can not be placed in such a way that it is impossible to
reach the other side (it must always be possible to get to the other side).

##User stories
- As a user, I want to be able to see both avatars on the board
- As a user, I want to be able to move my avatar using the keyboard
- As a user, I want to not be able to move off the board
- As a user, I want to be able to add walls using the mouse
- As a user, I want to not be able to move over walls
- As a user, I want the game to disallow any wall placements that would make the game unwinnable
- As a user, I want the game to be turn based
- As a user, I want an indicator to show whose turn it is
- As a user, I want the game to be visually appealing (eg. not have text-based graphics)
- (Optional) as a user, I want there to be a "minotaur" that starts in the middle of the map and moves randomly, and
if it reaches a player, that player loses