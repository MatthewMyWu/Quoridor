package model.players;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.Cell;
import model.Moveable;
import ui.Game;

/*Contains specific information about player 1 that differs from player 2*/
public class P1 extends Avatar {
    //input keys to move this player
    public static final String UP_KEY = "w";
    public static final String LEFT_KEY = "a";
    public static final String DOWN_KEY = "s";
    public static final String RIGHT_KEY = "d";


    public P1(Game game) {
        super(Game.SIDE_LENGTH / 2, Game.SIDE_LENGTH - 1, game);
        startingCoordX = Game.SIDE_LENGTH / 2;
        startingCoordY = Game.SIDE_LENGTH - 1;
    }

    @Override
    public void move(String input) throws OutOfBoundsException, WallObstructionException {
        super.moveDirection(UP_KEY, LEFT_KEY, DOWN_KEY, RIGHT_KEY, input);
        if (!game.getLimboState()) {
            updatePosition();
        }
    }

    @Override
    protected boolean isAnotherPlayerHere(Cell cell) {
        return cell.isP2Here();
    }

    @Override
    public boolean reachedWinCondition(Moveable object) {
        return object.getCoordY() == 0;
    }


    ///////////////////////probably not broken stuff///////////////////////

    @Override
    protected void updatePosition() {
        board.get(arrayIndex).setP1Here(false);
        updateArrayIndex();
        board.get(arrayIndex).setP1Here(true);
    }

    @Override
    public String getUpKey() {
        return UP_KEY;
    }

    @Override
    public String getLeftKey() {
        return LEFT_KEY;
    }

    @Override
    public String getDownKey() {
        return DOWN_KEY;
    }

    @Override
    public String getRightKey() {
        return RIGHT_KEY;
    }

    @Override
    public String getPlayerNumber() {
        return "1";
    }

    @Override
    public boolean isHere(int index) {
        return game.getBoard().get(index).isP1Here();
    }
}
