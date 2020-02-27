package model;

import exceptions.IllegalMoveException;
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

    @BeforeEach
    public void runBefore() {
        game = new Game();
        p1 = new P1();
        p2 = new P2();
        wallTool = new WallTool();
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
        assertEquals("0" + DisplayTool.DIVIDING_SPACE, game.board.get(0).displayCell());
        p1.moveTo(0, 0);
        assertEquals("1" + DisplayTool.DIVIDING_SPACE, game.board.get(0).displayCell());
        p1.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        p2.moveTo(0,0);
        assertEquals("2" + DisplayTool.DIVIDING_SPACE, game.board.get(0).displayCell());
    }

    @Test
    public void testDisplayCellWithWalls()  {
        try {
            wallTool.placeWall("A1,C1");
        } catch (InvalidWallException e) {
            fail("No InvalidWallException expected");
        }

        assertEquals("0" + DisplayTool.VERTICAL_WALL_SPACE + "|" + DisplayTool.VERTICAL_WALL_SPACE,
                game.board.get(0).displayCell());
        p1.moveTo(0, 0);
        assertEquals("1" + DisplayTool.VERTICAL_WALL_SPACE + "|" + DisplayTool.VERTICAL_WALL_SPACE,
                game.board.get(0).displayCell());
        p1.moveTo(Game.SIDE_LENGTH - 1, Game.SIDE_LENGTH - 1);
        p2.moveTo(0,0);
        assertEquals("2" + DisplayTool.VERTICAL_WALL_SPACE + "|" + DisplayTool.VERTICAL_WALL_SPACE,
                game.board.get(0).displayCell());
    }

    ///////////////////////////////implemented by subclasses (other test classes)////////////////////////
}
