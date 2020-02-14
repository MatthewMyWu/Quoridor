package model;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Player1MovementTests extends GameTests {

    @BeforeEach
    public void runBefore() {
        super.runBefore();
    }

    @Test
    public void testPlayer1ValidMovement() throws WallObstructionException, OutOfBoundsException {
        //testing moving up
        p1.move(p1.getUpKey());
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
        }

        //testing moving left
        p1.move(p1.getLeftKey());
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
        }

        //testing moving down
        p1.move(p1.getDownKey());
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP1Here());
            } else {
                assertFalse(game.board.get(x).isP1Here());
            }
        }

        //testing moving right
        p1.move(p1.getRightKey());
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
            p1.move(p1.getUpKey());
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
            p1.move(p1.getLeftKey());
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
            p1.move(p1.getDownKey());
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
            p1.move(p1.getRightKey());
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
            p1.move(p1.getUpKey());
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
            p1.move(p1.getLeftKey());
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
            p1.move(p1.getDownKey());
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
            p1.move(p1.getRightKey());
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
}
