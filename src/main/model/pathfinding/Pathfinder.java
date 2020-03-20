package model.pathfinding;

import exceptions.IllegalMoveException;
import model.Cell;
import model.Moveable;
import model.players.Avatar;
import ui.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;

//Used to determine if the game is still winnable after a wall placement
// (eg. checks there is still a path to the opposite side).
//Works in very much the same way as a maze solver (recursive)
public class Pathfinder extends Moveable {
    private Avatar player; //keeps track of which player this pathfinder is for
    ArrayList<Cell> board;

    public Pathfinder(Avatar player, Game game) {
        this.player = player;
        this.coordX = player.getCoordX();
        this.coordY = player.getCoordY();

        this.board = game.getBoard();
        updatePosition();
    }

    //EFFECTS : Returns true if the current position is the winning condition (player has reached their objective,
    //          and path is found)
    public boolean isSolved() {
        return player.reachedWinCondition(this);
    }

    @Override
    protected void updatePosition() {
        updateArrayIndex();
    }

    //MODIFIES: board (resets "visited" status of all cells to false)
    //EFFECTS : returns true if path to objective (the other side) can be found. False otherwise
    public boolean canFindPath() {
        //syncing position of pathfinder and avatar
        this.coordX = player.getCoordX();
        this.coordY = player.getCoordY();
        updatePosition();

        boolean pathFound = pathFound();
        reset();
        System.out.println("x: " + coordX + ", y:" + coordY);//TODO debugging
        return pathFound;
    }

    //MODIFIES: board (sets cells visited to true)
    //EFFECTS : Recursive method for exploring the board and seeing if a path can be found to the other side
    private boolean pathFound() {
        //seeing if Pathfinder has made it to the other end
        if (isSolved()) {
            return true;
        }

        //stores the current position, so can return to this position after searching each branch
        int tempIndex = arrayIndex;

        //handling visited
        if (board.get(tempIndex).isVisited()) {
            //this branch has already been visited and thus we don't need to search it
            return false;
        } else {
            //otherwise store that this cell has been visited
            board.get(tempIndex).setVisited(true);
        }

        //recursive searching
        return recursiveDirectionalSearch(tempIndex);
    }

    //REQUIRES: tempIndex be a valid index of Game.board
    //EFFECTS : calls methods to recursively explore board in each direction (up, left, down, right) from position
    //          tempIndex
    private boolean recursiveDirectionalSearch(int tempIndex) {
        //starting search with top branch, then left, down, and finally right
        if (searchUp()) {
            return true;
        }
        //returning to original position to search next branch
        moveTo(tempIndex);
        if (searchLeft()) {
            return true;
        }
        //returning to original position to search next branch
        moveTo(tempIndex);
        if (searchDown()) {
            return true;
        }
        //returning to original position to search next branch
        moveTo(tempIndex);
        //this is the last branch, so we don't need an if statement (nothing to move on to if right branch is false)
        return searchRight();
    }

    //MODIFIES: this and Game (board)
    //EFFECTS : moves Pathfinder up and recurs. Returns true if path is found (isSolved), false otherwise
    private boolean searchUp() {
        try {
            moveUp();
            if (pathFound()) {
                return true;
            }
        } catch (IllegalMoveException e) {
            //this means path is blocked, and we should try next option
        }
        //if this line of code is reached, means no path can be found on this branch
        return false;
    }

    //MODIFIES: this and Game (board)
    //EFFECTS : moves Pathfinder left and recurs. Returns true if path is found (isSolved), false otherwise
    private boolean searchLeft() {
        try {
            moveLeft();
            if (pathFound()) {
                return true;
            }
        } catch (IllegalMoveException e) {
            //this means path is blocked, and we should try next option
        }
        //if this line of code is reached, means no path can be found on this branch
        return false;
    }

    //MODIFIES: this and Game (board)
    //EFFECTS : moves Pathfinder down and recurs. Returns true if path is found (isSolved), false otherwise
    private boolean searchDown() {
        try {
            moveDown();
            if (pathFound()) {
                return true;
            }
        } catch (IllegalMoveException e) {
            //this means path is blocked, and we should try next option
        }
        //if this line of code is reached, means no path can be found on this branch
        return false;
    }

    //MODIFIES: this and Game (board)
    //EFFECTS : moves Pathfinder right and recurs. Returns true if path is found (isSolved), false otherwise
    private boolean searchRight() {
        try {
            moveRight();
            if (pathFound()) {
                return true;
            }
        } catch (IllegalMoveException e) {
            //this means path is blocked, and we should try next option
        }
        //if this line of code is reached, means no path can be found on this branch
        return false;
    }

    //MODIFIES: this and board
    //EFFECTS : moves pathfinder to the original position of the player, and resets the "visited" boolean on all the
    //          cells on the board
    private void reset() {
        //resetting pathfinder position
        this.coordX = player.getCoordX();
        this.coordY = player.getCoordY();
        updatePosition();

        //resetting "visited" status of cells on board
        for (int index = 0; index < Game.SIDE_LENGTH * Game.SIDE_LENGTH; index++) {
            board.get(index).setVisited(false);
        }
    }
}
