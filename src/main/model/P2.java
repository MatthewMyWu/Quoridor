package model;

import exceptions.OutOfBoundsException;

/*Contains specific information about player 1 that differs from player 2*/
public class P2 extends Avatar {
    private final static String UP_KEY = "i";
    private final static String LEFT_KEY = "j";
    private final static String DOWN_KEY = "k";
    private final static String RIGHT_KEY = "l";

    public P2() {
        super(Game.SIDE_LENGTH / 2, 0);
    }

    protected void move(String input) throws OutOfBoundsException {
        super.moveDirection(UP_KEY, LEFT_KEY, DOWN_KEY, RIGHT_KEY, input);
        updatePosition();
    }




    ///////////////////////probably not broken stuff///////////////////////

    @Override
    protected void initialize() {
        Game.board.get(arrayIndex).setP2Here(true);
    }

    @Override
    protected void updatePosition() {
        Game.board.get(arrayIndex).setP2Here(false);
        updateArrayIndex();
        Game.board.get(arrayIndex).setP2Here(true);
    }

    @Override
    protected String getUpKey() {
        return UP_KEY;
    }

    @Override
    protected String getLeftKey() {
        return LEFT_KEY;
    }

    @Override
    protected String getDownKey() {
        return DOWN_KEY;
    }

    @Override
    protected String getRightKey() {
        return RIGHT_KEY;
    }
}
