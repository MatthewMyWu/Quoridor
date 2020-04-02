package model;

import exceptions.InvalidWallException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

public class SpecificVerticalWallTests extends GameTests {

    @BeforeEach
    public void runBefore(){
        super.runBefore();
    }

    @Test
    public void testPlaceValidVerticallWall() {
        try {
            wallTool.placeWall("A1,C1");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }
        //cell to the top left of wall
        assertFalse(game.getBoard().get(0).isWallUp());
        assertFalse(game.getBoard().get(0).isWallLeft());
        assertFalse(game.getBoard().get(0).isWallDown());
        assertTrue(game.getBoard().get(0).isWallRight());

        //cell to the top right of wall
        assertFalse(game.getBoard().get(1).isWallUp());
        assertTrue(game.getBoard().get(1).isWallLeft());
        assertFalse(game.getBoard().get(1).isWallDown());
        assertFalse(game.getBoard().get(1).isWallRight());

        //cell to the bottom left of wall
        assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallUp());
        assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallLeft());
        assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallDown());
        assertTrue(game.getBoard().get(Game.SIDE_LENGTH).isWallRight());

        //cell to the bottom right of wall
        assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallUp());
        assertTrue(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallLeft());
        assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallDown());
        assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallRight());
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
            assertFalse(game.getBoard().get(0).isWallUp());
            assertFalse(game.getBoard().get(0).isWallLeft());
            assertFalse(game.getBoard().get(0).isWallDown());
            assertFalse(game.getBoard().get(0).isWallRight());

            //cell to the bottom right of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallRight());
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
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) - 1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallRight());
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
            assertFalse(game.getBoard().get(0).isWallUp());
            assertFalse(game.getBoard().get(0).isWallLeft());
            assertFalse(game.getBoard().get(0).isWallDown());
            assertTrue(game.getBoard().get(0).isWallRight());

            //cell to the top right of wall
            assertFalse(game.getBoard().get(1).isWallUp());
            assertTrue(game.getBoard().get(1).isWallLeft());
            assertFalse(game.getBoard().get(1).isWallDown());
            assertFalse(game.getBoard().get(1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallRight());

            //checking to see that second wall was not placed
            //cell to the top left of (invalid) wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3).isWallRight());

            //cell to the top right of (invalid) wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3 + 1).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3 + 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3 + 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 3 + 1).isWallRight());
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
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH).isWallRight());

            //cell to the top right of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 2).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 2).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 2).isWallDown());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH * 2).isWallRight());

            //cell to the bottom right of  wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 2 + 1).isWallUp());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH * 2 + 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 2 + 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH * 2 + 1).isWallRight());

            //checking to see that second wall was not placed
            //cell to the top left of (invalid) wall
            assertFalse(game.getBoard().get(0).isWallUp());
            assertFalse(game.getBoard().get(0).isWallLeft());
            assertFalse(game.getBoard().get(0).isWallDown());
            assertFalse(game.getBoard().get(0).isWallRight());

            //cell to the top right of (invalid) wall
            assertFalse(game.getBoard().get(1).isWallUp());
            assertFalse(game.getBoard().get(1).isWallLeft());
            assertFalse(game.getBoard().get(1).isWallDown());
            assertFalse(game.getBoard().get(1).isWallRight());

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
            assertFalse(game.getBoard().get(0).isWallUp());
            assertFalse(game.getBoard().get(0).isWallLeft());
            assertFalse(game.getBoard().get(0).isWallDown());
            assertTrue(game.getBoard().get(0).isWallRight());

            //cell to the top right of wall
            assertFalse(game.getBoard().get(1).isWallUp());
            assertTrue(game.getBoard().get(1).isWallLeft());
            assertFalse(game.getBoard().get(1).isWallDown());
            assertFalse(game.getBoard().get(1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallRight());
        }
    }

    @Test
    public void testVerticallWall() {
        try {
            wallTool.placeWall("A1,C1");
            wallTool.placeWall("C1,E1");
            wallTool.deleteVerticalWall(1, 2, 1, 4);

            //verifying first wall still exists
            //cell to the top left of wall
            assertFalse(game.getBoard().get(0).isWallUp());
            assertFalse(game.getBoard().get(0).isWallLeft());
            assertFalse(game.getBoard().get(0).isWallDown());
            assertTrue(game.getBoard().get(0).isWallRight());

            //cell to the top right of wall
            assertFalse(game.getBoard().get(1).isWallUp());
            assertTrue(game.getBoard().get(1).isWallLeft());
            assertFalse(game.getBoard().get(1).isWallDown());
            assertFalse(game.getBoard().get(1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH).isWallDown());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallUp());
            assertTrue(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(game.getBoard().get(Game.SIDE_LENGTH + 1).isWallRight());


            //verifying second wall is deleted
            //cell to the top left of wall
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH).isWallUp());
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH).isWallLeft());
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH).isWallDown());
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH).isWallRight());

            //cell to the top right of wall
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH + 1).isWallUp());
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(game.getBoard().get(2 * Game.SIDE_LENGTH + 1).isWallRight());

            //cell to the bottom left of wall
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH).isWallUp());
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH).isWallLeft());
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH).isWallDown());
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH + 1).isWallUp());
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(game.getBoard().get(3 * Game.SIDE_LENGTH + 1).isWallRight());
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }
    }
}
