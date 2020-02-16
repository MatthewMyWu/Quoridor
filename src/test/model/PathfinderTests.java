package model;

import exceptions.InvalidWallException;
import model.pathfinding.P1Pathfinder;
import model.pathfinding.Pathfinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PathfinderTests extends GameTests {
    Pathfinder p1Pathfinder;

    @BeforeEach
    public void runBefore() {
        super.runBefore();
        p1Pathfinder = new P1Pathfinder(p1);
    }

    @Test
    public void validPath() {
        //stub
    }
//TODO actually make these tests
    @Test
    public void invalidPath() {
        //setting up walls right above player 1 to block path
        try {
            wallTool.placeWall("I0,I2");
            wallTool.placeWall("I2,I4");
            wallTool.placeWall("I4,I6");
            wallTool.placeWall("I6,I8");
            wallTool.placeWall("I8,G8");
            //assertTrue(p1Pathfinder.pathFound());

            //blocking off path (final wall)
            wallTool.placeWall("G7,G9");
            assertFalse(p1Pathfinder.pathFound());
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }

    }


}
