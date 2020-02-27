package model.players;

import model.Moveable;

//this Avatar is not meant to be used in game. It is more of a "placeholder" Avatar used for HistoricMatch
public class GenericAvatar extends Avatar {

    public GenericAvatar(int x, int y) {
        super(x, y);
    }

    public GenericAvatar() {
        super(0, 0);
    }

    @Override
    public boolean reachedWinCondition(Moveable object) {
        return false;
    }

    @Override
    public void move(String input){
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
    public boolean isHere(int index) {
        return false;
    }

    @Override
    protected void updatePosition() {
        updateArrayIndex();
    }
}
