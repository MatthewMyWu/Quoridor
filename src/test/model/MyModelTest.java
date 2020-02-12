package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyModelTest {
    private Game game;

    @BeforeEach
    void runBefore() {
        game = new Game();
    }

    @Test
    void testIsWallCommand() {
        assertTrue(game.isWallCommand("A1,B1"));
        assertTrue(game.isWallCommand("H7,G7"));
        assertFalse(game.isWallCommand("H7, G7"));
        assertFalse(game.isWallCommand("H7 ,G"));
        assertFalse(game.isWallCommand("37 ,G7"));
    }
}