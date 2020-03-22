package model.walls;

import model.Cell;
import model.pathfinding.Pathfinder;
import model.players.GenericAvatar;
import ui.Game;

import java.util.ArrayList;

//This special instance of the WallTool is used exclusively for "test.PathfinderTests". It allows me to place walls
// that would block off players from reaching their objective - if I used the normal WallTool, an exception would be
// thrown and I would not be able to clearly test the Pathfinder
public class PathfindingTestWallTool extends WallTool {

    public PathfindingTestWallTool(Pathfinder p1Pathfinder, Pathfinder p2Pathfinder, ArrayList<Cell> board) {
        super(p1Pathfinder, p2Pathfinder, board);
    }

    @Override
    protected void pathfindingCheck(boolean horizontalWallPlaced, int x1, int y1, int x2, int y2) {
        //this is intentionally left blank
    }

}
