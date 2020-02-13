
package model;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

class MyModelTest {
    private Game game;
    WallTool wallTool;
    Avatar p1;
    Avatar p2;

    @BeforeEach
    public void runBefore() {
        game = new Game();
        game.initialize();
        wallTool = new WallTool();
        p1 = new P1();
        p2 = new P2();
    }

    @Test
    public void testGameConstructor() {
        assertEquals(Game.SIDE_LENGTH * Game.SIDE_LENGTH, game.board.size());
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking if player 1 has been placed properly
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
            //checking that player 2 has been placed properly
            if (x == (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }

            //checking there are no walls on the map
            assertFalse(game.board.get(x).isWallUp());
            assertFalse(game.board.get(x).isWallLeft());
            assertFalse(game.board.get(x).isWallDown());
            assertFalse(game.board.get(x).isWallRight());
        }
    }

    @Test
    public void testDisplayCellNoWalls() {
        assertEquals("0" + Game.DIVIDING_SPACE, game.board.get(0).displayCell());
        p1.moveTo(0, 0);
        assertEquals("1" + Game.DIVIDING_SPACE, game.board.get(0).displayCell());
        p1.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        p2.moveTo(0,0);
        assertEquals("2" + Game.DIVIDING_SPACE, game.board.get(0).displayCell());
    }

    @Test
    public void testDisplayCellWithWalls() throws InvalidWallException {
        wallTool.placeWall("A1,C1");
        assertEquals("0" + Game.VERTICAL_WALL_SPACE + "|" + Game.VERTICAL_WALL_SPACE,
                game.board.get(0).displayCell());
        p1.moveTo(0, 0);
        assertEquals("1" + Game.VERTICAL_WALL_SPACE + "|" + Game.VERTICAL_WALL_SPACE,
                game.board.get(0).displayCell());
        p1.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        p2.moveTo(0,0);
        assertEquals("2" + Game.VERTICAL_WALL_SPACE + "|" + Game.VERTICAL_WALL_SPACE,
                game.board.get(0).displayCell());
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
    public void testIsVertical() throws InvalidWallException {
        wallTool.placeWall("A1,C1");
        assertTrue(WallTool.getWallMiddle(Game.SIDE_LENGTH + 1).isVertical());

        wallTool.placeWall("C0,C2");
        assertFalse(WallTool.getWallMiddle(Game.SIDE_LENGTH * 2 + 1).isVertical());
    }


    ///////////////////////////////////////////horizontal walls/////////////////////////////////////////////

    @Test
    public void testPlaceValidHorizontalWall() throws InvalidWallException {
        wallTool.placeWall("B0,B2");
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


    ///////////////////////////////////////////vertical walls/////////////////////////////////////////////

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


    ///////////////////////////////////////////player 1/////////////////////////////////////////////

    @Test
    public void testPlayer1ValidMovement() throws WallObstructionException, OutOfBoundsException {
        //testing moving up
        p1.move("w");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
        }

        //testing moving left
        p1.move("a");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
        }

        //testing moving down
        p1.move("s");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
        }

        //testing moving right
        p1.move("d");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
        }
    }

    @Test
    public void testPlayer1MoveOffTopOfBoard() throws WallObstructionException {
        p1.moveTo(0, 0);
        boolean exceptionThrown = false;
        try {
            p1.move("w");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == 0) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test
    public void testPlayer1MoveOffLeftOfBoard() throws WallObstructionException {
        p1.moveTo(0, 0);
        boolean exceptionThrown = false;
        try {
            p1.move("a");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == 0) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test
    public void testPlayer1MoveOffBottomOfBoard() throws WallObstructionException {
        p1.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        boolean exceptionThrown = false;
        try {
            p1.move("s");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test
    public void testPlayer1MoveOffRightOfBoard() throws WallObstructionException {
        p1.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        boolean exceptionThrown = false;
        try {
            p1.move("d");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test
    public void testPlayer1MoveIntoTopWall() throws OutOfBoundsException, InvalidWallException {
        p1.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("B0,B2");
            p1.move("w");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test
    public void testPlayer1MoveIntoLeftWall() throws OutOfBoundsException, InvalidWallException {
        p1.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("B1,D1");
            p1.move("a");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test
    public void testPlayer1MoveIntoBottomWall() throws OutOfBoundsException, InvalidWallException {
        p1.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("C1,C3");
            p1.move("s");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test
    public void testPlayer1MoveIntoRightWall() throws OutOfBoundsException, InvalidWallException {
        p1.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("C2,A2");
            p1.move("d");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP1Here());
                } else {
                    assertFalse(game.board.get(x).isP1Here());
                }
            }
        }
    }

    @Test //to increase code coverage
    public void testPlayer1Getters() {
        assertEquals("w", p1.getUpKey());
        assertEquals("a", p1.getLeftKey());
        assertEquals("s", p1.getDownKey());
        assertEquals("d", p1.getRightKey());
    }
    ///////////////////////////////////////////player 2/////////////////////////////////////////////

    @Test
    public void testPlayer2ValidMovement() throws WallObstructionException, OutOfBoundsException {
        p2.moveTo(Game.SIDE_LENGTH / 2, Game.SIDE_LENGTH - 1);
        assertTrue(game.board.get(Game.SIDE_LENGTH / 2 + Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1)).isP2Here());
        //testing moving up
        p2.move("i");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 2 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }
        }

        //testing moving left
        p2.move("j");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }
        }

        //testing moving down
        p2.move("k");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }
        }

        //testing moving right
        p2.move("l");
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }
        }
    }

    @Test
    public void testPlayer2MoveOffTopOfBoard() throws WallObstructionException {
        p2.moveTo(0, 0);
        boolean exceptionThrown = false;
        try {
            p2.move("i");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == 0) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test
    public void testPlayer2MoveOffLeftOfBoard() throws WallObstructionException {
        p2.moveTo(0, 0);
        boolean exceptionThrown = false;
        try {
            p2.move("j");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == 0) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test
    public void testPlayer2MoveOffBottomOfBoard() throws WallObstructionException {
        p2.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        boolean exceptionThrown = false;
        try {
            p2.move("k");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test
    public void testPlayer2MoveOffRightOfBoard() throws WallObstructionException {
        p2.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        boolean exceptionThrown = false;
        try {
            p2.move("l");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test
    public void testPlayer2MoveIntoTopWall() throws OutOfBoundsException, InvalidWallException {
        p2.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("B0,B2");
            p2.move("i");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test
    public void testPlayer2MoveIntoLeftWall() throws OutOfBoundsException, InvalidWallException {
        p2.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("B1,D1");
            p2.move("j");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test
    public void testPlayer2MoveIntoBottomWall() throws OutOfBoundsException, InvalidWallException {
        p2.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("C1,C3");
            p2.move("k");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test
    public void testPlayer2MoveIntoRightWall() throws OutOfBoundsException, InvalidWallException {
        p2.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("C2,A2");
            p2.move("l");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player 1 is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(game.board.get(x).isP2Here());
                } else {
                    assertFalse(game.board.get(x).isP2Here());
                }
            }
        }
    }

    @Test //to increase code coverage
    public void testPlayer2Getters() {
        assertEquals("i", p2.getUpKey());
        assertEquals("j", p2.getLeftKey());
        assertEquals("k", p2.getDownKey());
        assertEquals("l", p2.getRightKey());
    }
}
