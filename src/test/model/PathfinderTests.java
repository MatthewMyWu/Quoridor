
package model;

import exceptions.InvalidWallException;
import model.pathfinding.Pathfinder;
import model.walls.PathfindingTestWallTool;
import model.walls.WallTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class PathfinderTests extends GameTests {
    Pathfinder p1Pathfinder;
    Pathfinder p2Pathfinder;

    @BeforeEach
    public void runBefore() {
        super.runBefore();
        p1Pathfinder = new Pathfinder(p1, board);
        p2Pathfinder = new Pathfinder(p2, board);
        game.setP1Pathfinder(p1Pathfinder);
        game.setP2Pathfinder(p2Pathfinder);
    }

    /////////////////////Pathfinding scenario tests/////////////////////////
    @Test
    public void testInvalidPathScenario() {
        //need to use this special implementation of WallTool, which will allow us to completely block off
        // the path without throwing an exception.
        wallTool = new PathfindingTestWallTool(p1Pathfinder, p2Pathfinder, board);

        //setting up walls along entire I row to block path
        try {
            wallTool.placeWall("I0,I2");
            assertTrue(p1Pathfinder.canFindPath());
            assertTrue(p2Pathfinder.canFindPath());
            wallTool.placeWall("I2,I4");
            assertTrue(p2Pathfinder.canFindPath());
            assertTrue(p1Pathfinder.canFindPath());
            wallTool.placeWall("I4,I6");
            assertTrue(p1Pathfinder.canFindPath());
            assertTrue(p2Pathfinder.canFindPath());
            wallTool.placeWall("I6,I8");
            assertTrue(p1Pathfinder.canFindPath());
            assertTrue(p2Pathfinder.canFindPath());
            wallTool.placeWall("I8,G8");
            assertTrue(p1Pathfinder.canFindPath());
            assertTrue(p2Pathfinder.canFindPath());

            //blocking off path (final wall)
            wallTool.placeWall("G7,G9");
            assertFalse(p1Pathfinder.canFindPath());
            assertFalse(p2Pathfinder.canFindPath());
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }

        try {
            //testing that Pathfinder implementation works in WallTool() (instead of PathfindingTestWallTool())
            wallTool = new WallTool(p1Pathfinder, p2Pathfinder, board);
            wallTool.deleteHorizontalWall(7, 6, 9, 6);
            assertTrue(p1Pathfinder.canFindPath());
            assertTrue(p2Pathfinder.canFindPath());

            //blocking off path (final wall)
            wallTool.placeWall("G7,G9");
            fail("InvalidWallException should have been thrown here");
        } catch (InvalidWallException e) {
            //this exception is expected, as a path cannot be found
        }
    }

    @Test
    //Creating a complicated path that both players need to take in order to win
    // (players will have to move in every direction to reach win condition;
    // additionally, neither player will be blocked from being able to reach their starting positions;
    // ensures Pathfinder class is working properly)
    public void testComplicatedPathScenario() {
        try {
            //moving p1 near top right corner of board
            p1.moveTo(game.SIDE_LENGTH - 1, 1);
            //moving p2 near bottom left corner of board
            p2.moveTo(0, game.SIDE_LENGTH - 2);

            //setting up walls around p2 (bottom left corner)
            wallTool.placeWall("I0,I2");
            wallTool.placeWall("I1,G1");
            wallTool.placeWall("F0,F2");
            wallTool.placeWall("F2,H2");
            wallTool.placeWall("H2,H4");
            wallTool.placeWall("I2,I4");
            wallTool.placeWall("I4,I6");
            wallTool.placeWall("I5,G5");
            wallTool.placeWall("G5,E5");
            wallTool.placeWall("E5,E3");
            wallTool.placeWall("E3,E1");

            //setting up walls around p1 (top right corner)
            wallTool.placeWall("B9,B7");
            wallTool.placeWall("B8,D8");
            wallTool.placeWall("D8,F8");
            wallTool.placeWall("F8,H8");
            wallTool.placeWall("I9,I7");
            wallTool.placeWall("I7,G7");
            wallTool.placeWall("G7,E7");
            wallTool.placeWall("E7,C7");
            wallTool.placeWall("C7,C5");
            wallTool.placeWall("C5,C3");
            wallTool.placeWall("C3,C1");
            wallTool.placeWall("B1,D1");
            wallTool.placeWall("D0,D2");//this wall cuts off each player from starting points
            wallTool.placeWall("B7,B5");
            wallTool.placeWall("B5,B3");


            assertTrue(p1Pathfinder.canFindPath());
            assertTrue(p2Pathfinder.canFindPath());
        } catch (InvalidWallException e) {
            fail("No InvalidWasllException expected");
        }
    }

}

