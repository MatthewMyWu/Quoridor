package model.players;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.Cell;
import model.Moveable;
import model.players.Avatar;
import ui.Game;

import java.util.ArrayList;

/*Contains specific information about player 2 that differs from player 1*/
public class P2 extends Avatar {
    public static final String UP_KEY = "i";
    public static final String LEFT_KEY = "j";
    public static final String DOWN_KEY = "k";
    public static final String RIGHT_KEY = "l";

    public P2(ArrayList<Cell> board) {
        super(Game.SIDE_LENGTH / 2, 0, board);
        startingCoordX = Game.SIDE_LENGTH / 2;
        startingCoordY = 0;
    }

    @Override
    public void move(String input) throws OutOfBoundsException, WallObstructionException {
        super.moveDirection(UP_KEY, LEFT_KEY, DOWN_KEY, RIGHT_KEY, input);
        updatePosition();
    }

    @Override
    public boolean reachedWinCondition(Moveable object) {
        return object.getCoordY() == Game.SIDE_LENGTH - 1;
    }

    ///////////////////////probably not broken stuff///////////////////////

    @Override
    protected void updatePosition() {
        Game.board.get(arrayIndex).setP2Here(false);
        updateArrayIndex();
        Game.board.get(arrayIndex).setP2Here(true);
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
        return "2";
    }

    @Override
    public boolean isHere(int index) {
        return Game.board.get(index).isP2Here();
    }
}
