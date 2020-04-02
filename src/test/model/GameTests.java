package model;

import exceptions.InvalidWallException;
import model.players.Avatar;
import model.players.P1;
import model.players.P2;
import model.walls.WallTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {
    protected Game game;
    WallTool wallTool;
    Avatar p1;
    Avatar p2;
    ArrayList<Cell> board;

    @BeforeEach
    public void runBefore() {
        game = new Game();
        board = game.getBoard();
        p1 = game.getP1();
        p2 = game.getP2();
        wallTool = game.getWallTool();
    }

    @Test
    public void testGameConstructor() {
        assertEquals(Game.SIDE_LENGTH * Game.SIDE_LENGTH, game.getBoard().size());
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
            assertFalse(game.getBoard().get(x).isWallUp());
            assertFalse(game.getBoard().get(x).isWallLeft());
            assertFalse(game.getBoard().get(x).isWallDown());
            assertFalse(game.getBoard().get(x).isWallRight());
        }
    }
}
