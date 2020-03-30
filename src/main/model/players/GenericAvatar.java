package model.players;

import model.Cell;
import model.Moveable;
import ui.Game;

import java.util.ArrayList;

//this Avatar is not meant to be used in game. It is more of a "placeholder" Avatar used for HistoricMatch
//This Avatar isn't meant to be moved; it's used to store information about an Avatar in a finished game
// (eg. walls, score, end position coordinates).
// As a result, many of the implemented methods in this class are stubs.
public class GenericAvatar extends Avatar {

    public GenericAvatar(int x, int y, Game game) {
        super(x, y, game);
    }

    public GenericAvatar(Game game) {
        super(0, 0, game);
    }

    @Override
    public boolean reachedWinCondition(Moveable object) {
        return true;
    }

    @Override
    public void move(String input){
    }

    @Override
    protected boolean isAnotherPlayerHere(Cell cell) {
        return false;
    }

    @Override
    public String getUpKey() {
        return null;
    }

    @Override
    public String getLeftKey() {
        return null;
    }

    @Override
    public String getDownKey() {
        return null;
    }

    @Override
    public String getRightKey() {
        return null;
    }

    @Override
    public String getPlayerNumber() {
        return "0";
    }

    @Override
    public boolean isHere(int index) {
        return false;
    }

    @Override
    protected void updatePosition() {
        updateArrayIndex();
    }
}
