package model;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import ui.Game;

/*Contains general methods for how each avatar should behave*/

public abstract class Avatar {
    private int coordX;
    private int coordY;
    protected int arrayIndex; //keeps track of the index of the cell in the array

    public Avatar(int x, int y) {
        coordX = x;
        coordY = y;
        updateArrayIndex();
    }

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
        updatePosition();
    }


    ///////////////////////probably not broken stuff///////////////////////

    //MODIFIES: Game
    //EFFECTS : places player on the board
    public abstract void initialize();

    //MODIFIES: this (arrayIndex)
    //EFFECTS : updates the arrayIndex (used if x or y coordinates change)
    protected void updateArrayIndex() {
        arrayIndex = coordY * Game.SIDE_LENGTH + coordX;
    }

    //MODIFIES: Game
    //EFFECTS : updates the position of the Avatar in Game
    protected abstract void updatePosition();

    //MODIFIES: this
    //EFFECTS : moves the player up
    private void moveUp() throws OutOfBoundsException, WallObstructionException {
        if (coordY == 0) {
            throw new OutOfBoundsException();
        } else if (Game.board.get(arrayIndex).isWallUp()) {
            throw new WallObstructionException();
        } else {
            coordY--;
        }
    }

    //MODIFIES: this
    //EFFECTS : moves the player left
    private void moveLeft() throws OutOfBoundsException, WallObstructionException {
        if (coordX == 0) {
            throw new OutOfBoundsException();
        } else if (Game.board.get(arrayIndex).isWallLeft()) {
            throw new WallObstructionException();
        } else {
            coordX--;
        }
    }

    //MODIFIES: this
    //EFFECTS : moves the player down
    private void moveDown() throws OutOfBoundsException, WallObstructionException {
        if (coordY == Game.SIDE_LENGTH - 1) {
            throw new OutOfBoundsException();
        } else if (Game.board.get(arrayIndex).isWallDown()) {
            throw new WallObstructionException();
        } else {
            coordY++;
        }
    }

    //MODIFIES: this
    //EFFECTS : moves the player right
    private void moveRight() throws OutOfBoundsException, WallObstructionException {
        if (coordX == Game.SIDE_LENGTH - 1) {
            throw new OutOfBoundsException();
        } else if (Game.board.get(arrayIndex).isWallRight()) {
            throw new WallObstructionException();
        } else {
            coordX++;
        }
    }

    //EFFECTS : return the key that needs to be entered to move Avatar up
    public abstract String getUpKey();

    //EFFECTS : return the key that needs to be entered to move Avatar left
    public abstract String getLeftKey();

    //EFFECTS : return the key that needs to be entered to move Avatar down
    public abstract String getDownKey();

    //EFFECTS : return the key that needs to be entered to move Avatar right
    public abstract String getRightKey();

    //used for testing purposes
    //MODIFIES: this
    //EFFECTS : moves the player to the specified coordinates on the board
    public void moveTo(int coordx, int coordY) {
        this.coordX = coordx;
        this.coordY = coordY;
        updatePosition();
    }
}
