package model;

import exceptions.InvalidWallException;
import model.walls.WallTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralWallTests extends GameTests {

    @BeforeEach
    public void runBefore() {
        super.runBefore();
    }

    @Test
    public void testInvalidWalls() {
        boolean exceptionThrown = false;
        try {
            exceptionThrown = false;
            wallTool.placeWall("A1,A1");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }

        try {
            exceptionThrown = false;
            wallTool.placeWall("A1,A2");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }

        try {
            exceptionThrown = false;
            wallTool.placeWall("B1,A1");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }

        try {
            exceptionThrown = false;
            wallTool.placeWall("A1,B1");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }

        try {
            exceptionThrown = false;
            wallTool.placeWall("A7,F3");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }

        //ensuring no walls have been placed
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            assertFalse(game.board.get(x).isWallUp());
            assertFalse(game.board.get(x).isWallLeft());
            assertFalse(game.board.get(x).isWallDown());
            assertFalse(game.board.get(x).isWallRight());
        }
    }

    @Test
    public void testIntersectingWallsVerticalFirst() {
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("A1,C1");
            wallTool.placeWall("B0,B2");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);

            //checking only first wall was placed
            //cell to top left of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertFalse(Game.board.get(0).isWallDown());
            assertTrue(Game.board.get(0).isWallRight());

            //cell to top right of wall
            assertFalse(Game.board.get(1).isWallUp());
            assertTrue(Game.board.get(1).isWallLeft());
            assertFalse(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());

            //cell to bottom left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallRight());

            //cell to bottom right of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());
        }
    }

    @Test
    public void testIntersectingWallsHorizontalFirst() {
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("B0,B2");
            wallTool.placeWall("A1,C1");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);

            //checking only first wall was placed
            //cell to top left of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertTrue(Game.board.get(0).isWallDown());
            assertFalse(Game.board.get(0).isWallRight());

            //cell to top right of wall
            assertFalse(Game.board.get(1).isWallUp());
            assertFalse(Game.board.get(1).isWallLeft());
            assertTrue(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());

            //cell to bottom left of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallRight());

            //cell to bottom right of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());
        }
    }

    @Test //this is testing a getter to increase code coverage
    public void testIsVertical() {
        try {
            wallTool.placeWall("A1,C1");
            assertTrue(WallTool.getWallMiddle(Game.SIDE_LENGTH + 1).isVertical());

            wallTool.placeWall("C0,C2");
            assertFalse(WallTool.getWallMiddle(Game.SIDE_LENGTH * 2 + 1).isVertical());
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }
    }
}
