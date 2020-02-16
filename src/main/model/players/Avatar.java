package model.players;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.Moveable;
import ui.Game;

/*Contains methods for how each avatar (player) should behave*/
public abstract class Avatar extends Moveable {

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
    }


    ///////////////////////probably not broken stuff///////////////////////

    //MODIFIES: Game
    //EFFECTS : places player on the board
    public abstract void initialize();

    //EFFECTS : return the key that needs to be entered to move Avatar up
    public abstract String getUpKey();

    //EFFECTS : return the key that needs to be entered to move Avatar left
    public abstract String getLeftKey();

    //EFFECTS : return the key that needs to be entered to move Avatar down
    public abstract String getDownKey();

    //EFFECTS : return the key that needs to be entered to move Avatar right
    public abstract String getRightKey();

    //REQUIRES: index is a valid index on the board
    //EFFECTS : returns true if the player is at index, false otherwise (used for testing)
    public abstract boolean isHere(int index);
}
