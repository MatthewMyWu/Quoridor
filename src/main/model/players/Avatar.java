package model.players;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.Cell;
import model.Moveable;
import ui.Game;

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
    //when this is true, player can't place a wall (used when one player is "on top" of another player, and needs to
    // decide which direction to go
    protected Game game;

    public Avatar(int x, int y, Game game) {
        //we give super an empty board because when the avatar is created, the board is not yet initialized in game
        super(Game.generateBoard());
        this.game = game;
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

    @Override
    public void moveUp() throws WallObstructionException, OutOfBoundsException {
        //checking to see if there is a player above. Not using arrayIndex to account for limboState
        Cell cellUp = board.get((coordY - 1) * Game.SIDE_LENGTH + coordX);
        if (isAnotherPlayerHere(cellUp)) {
            handleUpDecision(cellUp);
        } else {
            super.moveUp();
            game.setLimboState(false);
        }
    }

    //EFFECTS : Deals with the case that player wants to move up, but there is another player above,
    // and then a wall above that second player
    private void handleUpDecision(Cell cellUp) throws OutOfBoundsException {
        //case where there is no wall above the player
        if (!cellUp.isWallUp()) {
            if (coordY > 1) {
                coordY -= 2;
                updatePosition();
            } else {
                throw new OutOfBoundsException();
            }
        } else {
            //case where there is a wall above the player
            game.displayVerticalDecisionMessage();
            coordY--;
            game.setLimboState(true);
        }
    }

    @Override
    public void moveLeft() throws WallObstructionException, OutOfBoundsException {
        //checking to see if there is a player to the left
        Cell cellLeft = board.get(coordY * Game.SIDE_LENGTH + (coordX - 1));
        if (isAnotherPlayerHere(cellLeft)) {
            handleLeftDecision(cellLeft);
        } else {
            super.moveLeft();
            game.setLimboState(false);
        }
    }

    //EFFECTS : Deals with the case that player wants to move left, but there is another player to the left,
    // and then a wall to the left of that second player
    private void handleLeftDecision(Cell cellLeft) throws OutOfBoundsException {
        //case where there is no wall above the player
        if (!cellLeft.isWallLeft()) {
            if (coordX > 1) {
                coordX -= 2;
                updatePosition();
            } else {
                throw new OutOfBoundsException();
            }
        } else {
            //case where there is a wall to the left of the player
            game.displayHorizontalDecisionMessage();
            coordX--;
            game.setLimboState(true);
        }
    }

    @Override
    public void moveDown() throws WallObstructionException, OutOfBoundsException {
        //checking to see if there is a player below
        Cell cellDown = board.get((coordY + 1) * Game.SIDE_LENGTH + coordX);
        if (isAnotherPlayerHere(cellDown)) {
            handleDownDecision(cellDown);
        } else {
            super.moveDown();
            game.setLimboState(false);
        }
    }

    //EFFECTS : Deals with the case that player wants to move left, but there is another player to the left,
    // and then a wall to the left of that second player
    private void handleDownDecision(Cell cellDown) throws OutOfBoundsException {
        //case where there is no wall above the player
        if (!cellDown.isWallDown()) {
            if (coordY < Game.SIDE_LENGTH - 2) {
                coordY += 2;
                updatePosition();
            } else {
                throw new OutOfBoundsException();
            }
        } else {
            //case where there is a wall below the player
            game.displayVerticalDecisionMessage();
            coordY++;
            game.setLimboState(true);
        }
    }

    @Override
    public void moveRight() throws WallObstructionException, OutOfBoundsException {
        //checking to see if there is a player to the right
        Cell cellRight = board.get(coordY * Game.SIDE_LENGTH + (coordX + 1));
        if (isAnotherPlayerHere(cellRight)) {
            handleRightDecision(cellRight);
        } else {
            super.moveRight();
            game.setLimboState(false);
        }
    }

    //EFFECTS : Deals with the case that player wants to move right, but there is another player to the right
    private void handleRightDecision(Cell cellRight) throws OutOfBoundsException {
        //case where there is no to the right of the player
        if (!cellRight.isWallRight()) {
            if (coordX < Game.SIDE_LENGTH - 2) {
                coordX += 2;
                updatePosition();
            } else {
                throw new OutOfBoundsException();
            }
        } else {
            //case where there is a wall to the left of the player
            game.displayHorizontalDecisionMessage();
            coordX++;
            game.setLimboState(true);
        }
    }

    //EFFECTS : Returns true if an Avatar (that is not this Avatar) is on cell
    protected abstract boolean isAnotherPlayerHere(Cell cell);


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
