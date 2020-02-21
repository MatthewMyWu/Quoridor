package model;

import exceptions.InvalidWallException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

public class SpecificHorizontalWallTests extends GameTests{

    @BeforeEach
    public void runBefore() {
        super.runBefore();
    }

    @Test
    public void testPlaceValidHorizontalWall() {
        try {
            wallTool.placeWall("B0,B2");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }
        //cell to the top left of wall
        assertFalse(Game.board.get(0).isWallUp());
        assertFalse(Game.board.get(0).isWallLeft());
        assertTrue(Game.board.get(0).isWallDown());
        assertFalse(Game.board.get(0).isWallRight());

        //cell to the top right of wall
        assertFalse(Game.board.get(1).isWallUp());
        assertFalse(Game.board.get(1).isWallLeft());
        assertTrue(Game.board.get(1).isWallDown());
        assertFalse(Game.board.get(1).isWallRight());

        //cell to the bottom left of wall
        assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallUp());
        assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
        assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
        assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallRight());

        //cell to the bottom right of wall
        assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
        assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
        assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
        assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());
    }

    @Test
    public void testPlaceOutOfBoundsHorizontalWallTopLeft() {
        boolean invalidWallExceptionThrown = false;

        try {
            wallTool.placeWall("A0,A2");
        } catch (InvalidWallException e) {
            //I am expecting this method to throw an exception but I don't know how to test for that
            invalidWallExceptionThrown = true;
        } finally {
            assertTrue(invalidWallExceptionThrown);

            //cell to the top left of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertFalse(Game.board.get(0).isWallDown());
            assertFalse(Game.board.get(0).isWallRight());

            //cell to the bottom right of wall
            assertFalse(Game.board.get(1).isWallUp());
            assertFalse(Game.board.get(1).isWallLeft());
            assertFalse(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());
        }
    }

    @Test
    public void testPlaceOutOfBoundsHorizontalWallBottomRight() {
        boolean invalidWallExceptionThrown = false;

        try {
            wallTool.placeWall("J9,J7");
        } catch (InvalidWallException e) {
            //I am expecting this method to throw an exception but I don't know how to test for that
            invalidWallExceptionThrown = true;
        } finally {
            assertTrue(invalidWallExceptionThrown);

            //cell to the top left of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 2).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 2).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 2).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 2).isWallRight());

            //cell to the top right of wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1).isWallRight());
        }
    }

    @Test
    public void testRightOverlapHorizontalWall() {
        boolean exceptionThrown = false;

        try {
            wallTool.placeWall("B0,B2");
            wallTool.placeWall("B1,B3");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            //verifying original wall is placed correctly
            //cell to the top left of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertTrue(Game.board.get(0).isWallDown());
            assertFalse(Game.board.get(0).isWallRight());

            //cell to the top right of wall
            assertFalse(Game.board.get(1).isWallUp());
            assertFalse(Game.board.get(1).isWallLeft());
            assertTrue(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());

            //cell to the bottom left of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());

            //checking to see that second wall was not placed
            //cell to the top right of (invalid) wall
            assertFalse(Game.board.get(2).isWallUp());
            assertFalse(Game.board.get(2).isWallLeft());
            assertFalse(Game.board.get(2).isWallDown());
            assertFalse(Game.board.get(2).isWallRight());

            //cell to the bottom right of (invalid) wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallRight());
        }
    }

    @Test
    public void testLeftOverlapHorizontalWall() {
        boolean exceptionThrown = false;

        try {
            wallTool.placeWall("B1,B3");
            wallTool.placeWall("B0,B2");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            //verifying original wall is placed correctly
            //cell to the bottom left of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());

            //cell to the bottom right of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());

            //cell to the top right of wall
            assertFalse(Game.board.get(2).isWallUp());
            assertFalse(Game.board.get(2).isWallLeft());
            assertTrue(Game.board.get(2).isWallDown());
            assertFalse(Game.board.get(2).isWallRight());

            //cell to the bottom right of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 2).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallRight());

            //checking to see that second wall was not placed
            //cell to the top left of (invalid) wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertFalse(Game.board.get(0).isWallDown());
            assertFalse(Game.board.get(0).isWallRight());

            //cell to the bottom left of (invalid) wall
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallRight());
        }
    }

    @Test
    public void testDuplicateHorizontalWall() {
        boolean exceptionThrown = false;

        try {
            wallTool.placeWall("B0,B2");
            wallTool.placeWall("B0,B2");
        } catch (InvalidWallException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            //verifying original wall is placed correctly
            //cell to the top left of wall
            assertFalse(Game.board.get(0).isWallUp());
            assertFalse(Game.board.get(0).isWallLeft());
            assertTrue(Game.board.get(0).isWallDown());
            assertFalse(Game.board.get(0).isWallRight());

            //cell to the top right of wall
            assertFalse(Game.board.get(1).isWallUp());
            assertFalse(Game.board.get(1).isWallLeft());
            assertTrue(Game.board.get(1).isWallDown());
            assertFalse(Game.board.get(1).isWallRight());

            //cell to the bottom left of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallRight());

            //cell to the bottom right of wall
            assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
            assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());
        }
    }

    @Test
    public void testDeleteHorizontalWall() {
       try {
           wallTool.placeWall("B0,B2");
           wallTool.placeWall("B2,B4");
           wallTool.deleteHorizontalWall(2, 1, 4, 1);

           //verifying first wall still exists
           //cell to the top left of wall
           assertFalse(Game.board.get(0).isWallUp());
           assertFalse(Game.board.get(0).isWallLeft());
           assertTrue(Game.board.get(0).isWallDown());
           assertFalse(Game.board.get(0).isWallRight());

           //cell to the top right of wall
           assertFalse(Game.board.get(1).isWallUp());
           assertFalse(Game.board.get(1).isWallLeft());
           assertTrue(Game.board.get(1).isWallDown());
           assertFalse(Game.board.get(1).isWallRight());

           //cell to the bottom left of wall
           assertTrue(Game.board.get(Game.SIDE_LENGTH).isWallUp());
           assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallLeft());
           assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallDown());
           assertFalse(Game.board.get(Game.SIDE_LENGTH).isWallRight());

           //cell to the bottom right of wall
           assertTrue(Game.board.get(Game.SIDE_LENGTH + 1).isWallUp());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallLeft());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallDown());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 1).isWallRight());


           //verifying second wall is deleted
           //cell to the top left of wall
           assertFalse(Game.board.get(2).isWallUp());
           assertFalse(Game.board.get(2).isWallLeft());
           assertFalse(Game.board.get(2).isWallDown());
           assertFalse(Game.board.get(2).isWallRight());

           //cell to the top right of wall
           assertFalse(Game.board.get(3).isWallUp());
           assertFalse(Game.board.get(3).isWallLeft());
           assertFalse(Game.board.get(3).isWallDown());
           assertFalse(Game.board.get(3).isWallRight());

           //cell to the bottom left of wall
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallUp());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallLeft());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallDown());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 2).isWallRight());

           //cell to the bottom right of wall
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 3).isWallUp());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 3).isWallLeft());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 3).isWallDown());
           assertFalse(Game.board.get(Game.SIDE_LENGTH + 3).isWallRight());
       } catch (InvalidWallException e) {
           fail("No InvalidWallException expected");
       }
    }
}
