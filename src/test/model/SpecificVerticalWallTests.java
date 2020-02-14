package model;

import exceptions.InvalidWallException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpecificVerticalWallTests extends GameTests {

    @BeforeEach
    public void runBefore(){
        super.runBefore();
    }

    @Test
    public void testPlaceValidVerticallWall() throws InvalidWallException {
        wallTool.placeWall("A1,C1");
        //cell to the top left of wall
        assertFalse(Game.board.get(0).isWallUp());
        assertFalse(Game.board.get(0).isWallLeft());
        assertFalse(Game.board.get(0).isWallDown());
        assertTrue(Game.board.get(0).isWallRight());

        //cell to the top right of wall
        assertFalse(Game.board.get(1).isWallUp());
        assertTrue(Game.board.get(1).isWallLeft());
        assertFalse(Game.board.get(1).isWallDown());
        assertFalse(Game.board.get(1).isWallRight());

        //cell to the bottom left of wall
        assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallUp());
        assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
        assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
        assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallRight());

        //cell to the bottom right of wall
        assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
        assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
        assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
        assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());
    }

    @Test
    public void testPlaceOutOfBoundsVerticalWallTopLeft() {
        boolean invalidWallExceptionThrown = false;

        try {
            wallTool.placeWall("A0,C0");
        } catch (InvalidWallException e) {
            //I am expecting this method to throw an exception but I don't know how to test for that
            invalidWallExceptionThrown = true;
        } finally {
            assertTrue(invalidWallExceptionThrown);

            //cell to the top right of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertFalse(Game.board.get(0).isWallDown());
            assertFalse(Game.board.get(0).isWallRight());

            //cell to the bottom right of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallRight());
        }
    }

    @Test
    public void testPlaceOutOfBoundsVerticalWallBottomRight() {
        boolean invalidWallExceptionThrown = false;

        try {
            wallTool.placeWall("J9,J7");
        } catch (InvalidWallException e) {
            //I am expecting this method to throw an exception but I don't know how to test for that
            invalidWallExceptionThrown = true;
        } finally {
            assertTrue(invalidWallExceptionThrown);

            //cell to the top left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallRight());
        }
    }

    @Test
    public void testBottomOverlapVerticalWall() {
        boolean exceptionThrown = false;

        try {
            wallTool.placeWall("A1,C1");
            wallTool.placeWall("B1,D1");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            //verifying original wall is placed correctly
            //cell to the top left of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertFalse(Game.board.get(0).isWallDown());
            assertTrue(Game.board.get(0).isWallRight());

            //cell to the top right of wall
            assertFalse(Game.board.get(1).isWallUp());
            assertTrue(Game.board.get(1).isWallLeft());
            assertFalse(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());

            //checking to see that second wall was not placed
            //cell to the top left of (invalid) wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3).isWallRight());

            //cell to the top right of (invalid) wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3 + 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3 + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3 + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 3 + 1).isWallRight());
        }
    }

    @Test
    public void testTopOverlapVerticalWall() {
        boolean exceptionThrown = false;

        try {
            wallTool.placeWall("B1,D1");
            wallTool.placeWall("A1,C1");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            //verifying original wall is placed correctly
            //cell to the top left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallRight());

            //cell to the top right of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 2).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 2).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 2).isWallDown());
            assertTrue(Game.board.get(Game.SIDE_LENGTH * 2).isWallRight());

            //cell to the bottom right of  wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 2 + 1).isWallUp());
            assertTrue(Game.board.get(Game.SIDE_LENGTH * 2 + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 2 + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * 2 + 1).isWallRight());

            //checking to see that second wall was not placed
            //cell to the top left of (invalid) wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertFalse(Game.board.get(0).isWallDown());
            assertFalse(Game.board.get(0).isWallRight());

            //cell to the top right of (invalid) wall
            assertFalse(Game.board.get(1).isWallUp());
            assertFalse(Game.board.get(1).isWallLeft());
            assertFalse(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());

        }
    }

    @Test
    public void testDuplicateVerticalWall() {
        boolean exceptionThrown = false;

        try {
            wallTool.placeWall("A1,C1");
            wallTool.placeWall("A1,C1");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            //verifying original wall is placed correctly
            //cell to the top left of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertFalse(Game.board.get(0).isWallDown());
            assertTrue(Game.board.get(0).isWallRight());

            //cell to the top right of wall
            assertFalse(Game.board.get(1).isWallUp());
            assertTrue(Game.board.get(1).isWallLeft());
            assertFalse(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());
        }
    }
}
