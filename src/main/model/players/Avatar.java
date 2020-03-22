package model.players;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.Cell;
import model.Moveable;
import ui.Game;

import java.util.ArrayList;

/*Contains methods for how each avatar (player) should behave. Also contains the score and number of walls each player
 * has*/
public abstract class Avatar extends Moveable {
    private int score;
    //number of walls this player has
    protected int walls;
    //starting x coordinate of this player
    protected int startingCoordX;
    //starting y coordinate of this player
    protected int startingCoordY;

    public Avatar(int x, int y, ArrayList<Cell> board) {
        super(board);
        coordX = x;
        coordY = y;
        updateArrayIndex();
        score = 0;
        walls = 10;
    }

    //EFFECTS : returns true if object has reached the win-condition (win conditions differ between P1 and P2).
    // Object is parameterized because this method needs to be used by Pathfinder
    public abstract boolean reachedWinCondition(Moveable object);

    //MODIFIES: this and Game
    /*EFFECTS : Moves the player in a direction. Abstract so up, left, down, and right input keys can be parameterized*/
    public abstract void move(String input) throws OutOfBoundsException, WallObstructionException;

    //MODIFIES: this and Game
    //EFFECTS : tries to move player in target direction, throws exceptions where necessary
    protected void moveDirection(String up, String left, String down, String right, String input)
            throws OutOfBoundsException, WallObstructionException {
        if (input.equals(up)) {
            moveUp();
        } else if (input.equals(left)) {
            moveLeft();
        } else if (input.equals(down)) {
            moveDown();
        } else if (input.equals(right)) {
            moveRight();
        }
    }


    ///////////////////////probably not broken stuff///////////////////////

    //MODIFIES: Game
    //EFFECTS : places player on the board
    public void initialize() {
        coordX = startingCoordX;
        coordY = startingCoordY;
        updatePosition();
        walls = 10;
    }

    //EFFECTS : return the key that needs to be entered to move Avatar up
    public abstract String getUpKey();

    //EFFECTS : return the key that needs to be entered to move Avatar left
    public abstract String getLeftKey();

    //EFFECTS : return the key that needs to be entered to move Avatar down
    public abstract String getDownKey();

    //EFFECTS : return the key that needs to be entered to move Avatar right
    public abstract String getRightKey();

    //EFFECTS : returns this player number (ex. player will will return "1")
    public abstract String getPlayerNumber();

    //REQUIRES: index is a valid index on the board
    //EFFECTS : returns true if the player is at index, false otherwise (used for testing)
    public abstract boolean isHere(int index);

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    //MODIFIES: this (score)
    //EFFECTS : increments the score of this player by 1
    public void incrementScore() {
        score++;
    }

    public int getWalls() {
        return walls;
    }

    //MODIFIES: this (walls)
    //EFFECTS : decrements the walls of this player by 1
    public void decrementWall() {
        walls--;
    }

    public void setWalls(int walls) {
        this.walls = walls;
    }

    //EFFECTS : returns the array index of the Avatar's starting position
    public int getStartingArrayIndex() {
        return startingCoordY * Game.SIDE_LENGTH + startingCoordX;
    }
}
