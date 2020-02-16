package model;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

public class Player1MovementTests extends GameTests {

    @BeforeEach
    public void runBefore() {
        super.runBefore();
    }

    @Test
    public void testPlayerValidMovement() {
        super.testPlayerValidMovement(p1);
    }

    @Test
    public void testPlayer1MoveOffBoard() {
        super.testPlayerMoveOffBoard(p1);
    }

    @Test
    public void testPlayer1MoveIntoWall() {
        super.testPlayerMoveIntoWall(p1);
    }
}
