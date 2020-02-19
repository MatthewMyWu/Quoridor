package model;

import exceptions.InvalidWallException;
import model.pathfinding.P1Pathfinder;
import model.pathfinding.Pathfinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

public class PathfinderTests extends GameTests {
    Pathfinder p1Pathfinder;

    @BeforeEach
    public void runBefore() {
        super.runBefore();
        p1Pathfinder = new P1Pathfinder(p1);
    }

    @Test
    public void testValidPathScenario() {
        assertTrue(p1Pathfinder.canFindPath());
    }

    @Test
    public void testInvalidPathScenario() {
        //setting up walls right above player 1 to block path
        try {
            wallTool.placeWall("I0,I2");
            wallTool.placeWall("I2,I4");
            wallTool.placeWall("I4,I6");
            wallTool.placeWall("I6,I8");
            wallTool.placeWall("I8,G8");
            assertTrue(p1Pathfinder.canFindPath());

            //blocking off path (final wall)
            wallTool.placeWall("G7,G9");
            assertFalse(p1Pathfinder.canFindPath());
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }

    }

    @Test
    public void testBlockBehindScenario() {
        try {
            //moving p1 to centre of board
            p1.moveTo(Game.SIDE_LENGTH / 2);
            assertTrue(p1Pathfinder.canFindPath());

            //setting up wall "behind" p1
            wallTool.placeWall("I0,I2");
            wallTool.placeWall("I2,I4");
            wallTool.placeWall("I4,I6");
            wallTool.placeWall("I6,I8");
            wallTool.placeWall("I8,G8");
            wallTool.placeWall("G7,G9");
            assertTrue(p1Pathfinder.canFindPath());
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }

    }

    @Test
    public void testComplicatedPathScenario() {
        try {
            //moving p1 to centre of board
            p1.moveTo(Game.SIDE_LENGTH / 2);
            assertTrue(p1Pathfinder.canFindPath());

            //setting up wall "behind" p1, to block path to returning position
            wallTool.placeWall("I0,I2");
            wallTool.placeWall("I2,I4");
            wallTool.placeWall("I4,I6");
            wallTool.placeWall("I6,I8");
            wallTool.placeWall("I8,G8");
            wallTool.placeWall("G7,G9");
            assertTrue(p1Pathfinder.canFindPath());
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }

    }


}
