
package model;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

class Player2MovementTests extends GameTests {

    @BeforeEach
    public void runBefore() {
        super.runBefore();
    }

    @Test
    public void testPlayer2ValidMovement() throws WallObstructionException, OutOfBoundsException {
        p2.moveTo(Game.SIDE_LENGTH / 2, Game.SIDE_LENGTH - 1);
        assertTrue(game.board.get(Game.SIDE_LENGTH / 2 + Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1)).isP2Here());
        //testing moving up
        p2.move(p2.getUpKey());
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 2 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }
        }

        //testing moving left
        p2.move(p2.getLeftKey());
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }
        }

        //testing moving down
        p2.move(p2.getDownKey());
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player 1 is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(game.board.get(x).isP2Here());
            } else {
                assertFalse(game.board.get(x).isP2Here());
            }
        }

        //testing moving right
        p2.move(p2.getRightKey());
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
            p2.move(p2.getUpKey());
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
            p2.move(p2.getLeftKey());
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
            p2.move(p2.getDownKey());
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
            p2.move(p2.getRightKey());
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
            p2.move(p2.getUpKey());
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
            p2.move(p2.getLeftKey());
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
            p2.move(p2.getDownKey());
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
            p2.move(p2.getRightKey());
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
}
