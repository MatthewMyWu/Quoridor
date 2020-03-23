package model;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import ui.Game;

import java.util.ArrayList;

//This class contains general methods for any object that should be able to be moved (eg. Avatar and Pathfinder)
public abstract class Moveable {
    protected int coordX;
    protected int coordY;
    protected int arrayIndex; //keeps track of the index of the cell in the array
    protected ArrayList<Cell> board;

    public Moveable(ArrayList<Cell> board) {
        assert (board.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);
        this.board = board;
    }

    //MODIFIES: this
    //EFFECTS : moves the object up
    protected void moveUp() throws OutOfBoundsException, WallObstructionException {
        if (coordY == 0) {
            throw new OutOfBoundsException();
        } else if (board.get(arrayIndex).isWallUp()) {
            throw new WallObstructionException();
        } else {
            coordY--;
            updatePosition();
        }
    }

    //MODIFIES: this
    //EFFECTS : moves the object left
    protected void moveLeft() throws OutOfBoundsException, WallObstructionException {
        if (coordX == 0) {
            throw new OutOfBoundsException();
        } else if (board.get(arrayIndex).isWallLeft()) {
            throw new WallObstructionException();
        } else {
            coordX--;
            updatePosition();
        }
    }

    //MODIFIES: this
    //EFFECTS : moves the object down
    protected void moveDown() throws OutOfBoundsException, WallObstructionException {
        if (coordY == Game.SIDE_LENGTH - 1) {
            throw new OutOfBoundsException();
        } else if (board.get(arrayIndex).isWallDown()) {
            throw new WallObstructionException();
        } else {
            coordY++;
            updatePosition();
        }
    }

    //MODIFIES: this
    //EFFECTS : moves the object right
    protected void moveRight() throws OutOfBoundsException, WallObstructionException {
        if (coordX == Game.SIDE_LENGTH - 1) {
            throw new OutOfBoundsException();
        } else if (board.get(arrayIndex).isWallRight()) {
            throw new WallObstructionException();
        } else {
            coordX++;
            updatePosition();
        }
    }

    //MODIFIES: this (arrayIndex)
    //EFFECTS : updates the arrayIndex (used if x or y coordinates change)
    protected void updateArrayIndex() {
        arrayIndex = coordY * Game.SIDE_LENGTH + coordX;
    }

    //MODIFIES: Game
    //EFFECTS : updates the position of the object on the board
    protected abstract void updatePosition();

    //MODIFIES: this
    //EFFECTS : moves the object to the specified coordinates on the board
    public void moveTo(int coordx, int coordY) {
        this.coordX = coordx;
        this.coordY = coordY;
        updatePosition();
    }

    //MODIFIES: this
    //EFFECTS : moves the object to the specified coordinates on the board
    public void moveTo(int index) {
        this.coordX = index % Game.SIDE_LENGTH;
        this.coordY = index / Game.SIDE_LENGTH;
        updatePosition();
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setBoard(ArrayList<Cell> board) {
        assert (board.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);
        this.board = board;
    }
}
