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
    //keys to move player 2. While in the GUI, these keys are updated, what I've done is mapped the new GUI keys to
    // these keys (so I don't have to recode movement)
    public static final String UP_KEY = "i";//is actually the UP key using the GUI
    public static final String LEFT_KEY = "j";//is actually the LEFT key using the GUI
    public static final String DOWN_KEY = "k";//is actually the DOWN key using the GUI
    public static final String RIGHT_KEY = "l";//is actually the RIGHT key using the GUI

    public P2(Game game) {
        super(Game.SIDE_LENGTH / 2, 0, game);
        startingCoordX = Game.SIDE_LENGTH / 2;
        startingCoordY = 0;
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
        return cell.isP1Here();
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
