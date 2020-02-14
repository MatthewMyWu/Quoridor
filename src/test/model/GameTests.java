package model;

import exceptions.InvalidWallException;
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
}
