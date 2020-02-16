package model.pathfinding;

import model.players.Avatar;

//Pathfinder implementation for player 1
public class P1Pathfinder extends Pathfinder {

    public P1Pathfinder(Avatar player) {
        super(player);
    }

    @Override
    public boolean isSolved() {
        return coordY == 0;
    }
}
