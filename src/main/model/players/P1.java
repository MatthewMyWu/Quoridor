package model.players;

import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.players.Avatar;
import ui.Game;

/*Contains specific information about player 1 that differs from player 2*/
public class P1 extends Avatar {
    private static final String UP_KEY = "w";
    private static final String LEFT_KEY = "a";
    private static final String DOWN_KEY = "s";
    private static final String RIGHT_KEY = "d";

    public P1() {
        super(Game.SIDE_LENGTH / 2, Game.SIDE_LENGTH - 1);
    }

    @Override
    public void move(String input) throws OutOfBoundsException, WallObstructionException {
        super.moveDirection(UP_KEY, LEFT_KEY, DOWN_KEY, RIGHT_KEY, input);
        updatePosition();
    }


    ///////////////////////probably not broken stuff///////////////////////

    @Override
    public void initialize() {
        Game.board.get(arrayIndex).setP1Here(true);
    }

    @Override
    protected void updatePosition() {
        Game.board.get(arrayIndex).setP1Here(false);
        updateArrayIndex();
        Game.board.get(arrayIndex).setP1Here(true);
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
    public boolean isHere(int index) {
        return Game.board.get(index).isP1Here();
    }
}
