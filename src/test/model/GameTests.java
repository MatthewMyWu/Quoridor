package model;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.players.Avatar;
import model.players.P1;
import model.players.P2;
import model.walls.WallTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {
    protected Game game;
    WallTool wallTool;
    Avatar p1;
    Avatar p2;
    //TODO ask how we are supposed to structure tests (eg. I would like to make Player#MovementTests extend a
    // general "PlayerMOvementTests" class)

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
            //checking if player has been placed properly
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(p1.isHere(x));
            } else {
                assertFalse(p1.isHere(x));
            }
            //checking that player 2 has been placed properly
            if (x == (Game.SIDE_LENGTH / 2)) {
                assertTrue(p2.isHere(x));
            } else {
                assertFalse(p2.isHere(x));
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
    public void testDisplayCellWithWalls()  {
        try {
            wallTool.placeWall("A1,C1");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }

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

    ///////////////////////////////implemented by subclasses (other test classes)////////////////////////

    //////////////////////////////Movement tests////////////////////////
    public void testPlayerValidMovement(Avatar player) {
        player.moveTo(Game.SIDE_LENGTH/2, Game.SIDE_LENGTH - 1);
        //testing moving up
        try {
            player.move(player.getUpKey());
        } catch (OutOfBoundsException e) {
            fail("OutOfBoundsException not expected");
        } catch (WallObstructionException e) {
            fail("WallObstructionException not expected");
        }
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(player.isHere(x));
            } else {
                assertFalse(player.isHere(x));
            }
        }

        //testing moving left
        try {
            player.move(player.getLeftKey());
        } catch (OutOfBoundsException e) {
            fail("OutOfBoundsException not expected");
        } catch (WallObstructionException e) {
            fail("WallObstructionException not expected");
        }
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 2) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(player.isHere(x));
            } else {
                assertFalse(player.isHere(x));
            }
        }

        //testing moving down
        try {
            player.move(player.getDownKey());
        } catch (OutOfBoundsException e) {
            fail("OutOfBoundsException not expected");
        } catch (WallObstructionException e) {
            fail("WallObstructionException not expected");
        }
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2) - 1) {
                assertTrue(player.isHere(x));
            } else {
                assertFalse(player.isHere(x));
            }
        }

        //testing moving right
        try {
            player.move(player.getRightKey());
        } catch (OutOfBoundsException e) {
            fail("OutOfBoundsException not expected");
        } catch (WallObstructionException e) {
            fail("WallObstructionException not expected");
        }
        for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
            //checking player is in right place
            if (x == Game.SIDE_LENGTH * (Game.SIDE_LENGTH - 1) + (Game.SIDE_LENGTH / 2)) {
                assertTrue(player.isHere(x));
            } else {
                assertFalse(player.isHere(x));
            }
        }
    }

    public void testPlayerMoveOffTopOfBoard(Avatar player) {
        player.moveTo(0, 0);

        boolean exceptionThrown = false;
        try {
            player.move(player.getUpKey());
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } catch (WallObstructionException e) {
            fail("No WallObstructionException expected");
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == 0) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    public void testPlayerMoveOffLeftOfBoard(Avatar player) {
        player.moveTo(0, 0);
        boolean exceptionThrown = false;
        try {
            player.move(player.getLeftKey());
        } catch (WallObstructionException e) {
            fail("No WallObstructionException expected");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == 0) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    public void testPlayerMoveOffBottomOfBoard(Avatar player) {
        player.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        boolean exceptionThrown = false;
        try {
            p1.move(p1.getDownKey());
        } catch (WallObstructionException e) {
            fail("No WallObstructionException expected");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    public void testPlayerMoveOffRightOfBoard(Avatar player) {
        player.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        boolean exceptionThrown = false;
        try {
            player.move(player.getRightKey());
        } catch (WallObstructionException e) {
            fail("No WallObstructionException expected");
        } catch (OutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == Game.SIDE_LENGTH * Game.SIDE_LENGTH - 1) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    //combines all tests for player moving off the board
    public void testPlayerMoveOffBoard(Avatar player) {
        testPlayerMoveOffTopOfBoard(player);
        testPlayerMoveOffLeftOfBoard(player);
        testPlayerMoveOffBottomOfBoard(player);
        testPlayerMoveOffRightOfBoard(player);
    }

    public void testPlayerMoveIntoTopWall(Avatar player) {
        player.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("B0,B2");
            player.move(player.getUpKey());
        } catch (OutOfBoundsException e) {
            fail("No OutOfBounds expected");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    public void testPlayerMoveIntoLeftWall(Avatar player) {
        player.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("B1,D1");
            player.move(player.getLeftKey());
        } catch (OutOfBoundsException e) {
            fail("No OutOfBounds expected");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    public void testPlayerMoveIntoBottomWall(Avatar player) {
        player.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("C1,C3");
            player.move(player.getDownKey());
        } catch (OutOfBoundsException e) {
            fail("No OutOfBounds expected");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    public void testPlayerMoveIntoRightWall(Avatar player) {
        player.moveTo(1, 1);
        boolean exceptionThrown = false;
        try {
            wallTool.placeWall("C2,A2");
            player.move(player.getRightKey());
        } catch (OutOfBoundsException e) {
            fail("No OutOfBounds expected");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        } catch (WallObstructionException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
            for (int x = 0; x < Game.SIDE_LENGTH * Game.SIDE_LENGTH; x++) {
                //checking player is in right place
                if (x == Game.SIDE_LENGTH + 1) {
                    assertTrue(player.isHere(x));
                } else {
                    assertFalse(player.isHere(x));
                }
            }
        }
    }

    //combines all tests for player moving into walls
    public void testPlayerMoveIntoWall(Avatar player) {
        testPlayerMoveIntoTopWall(player);
        testPlayerMoveIntoLeftWall(player);
        testPlayerMoveIntoBottomWall(player);
        testPlayerMoveIntoRightWall(player);
    }





    //////////////////////////////Pathfinder tests////////////////////////
}
