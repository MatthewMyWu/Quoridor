package model.pathfinding;

import model.players.Avatar;
import ui.Game;

//Pathfinder implementation for player 1
public class P2Pathfinder extends Pathfinder {

    public P2Pathfinder(Avatar player) {
        super(player);
    }

    @Override
    public boolean isSolved() {
        return coordY == Game.SIDE_LENGTH - 1;
    }
}
