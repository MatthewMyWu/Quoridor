package model;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.players.Avatar;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

//Contains general methods on testing player movements.
// Actual tests are in Player1MovementTests andPlayer2MovementTests
public abstract class PlayerMovementTests extends GameTests{

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
}
