
package model;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.*;

class Player2MovementTests extends Player1MovementTests {

    @BeforeEach
    public void runBefore() {
        super.runBefore();
    }

    @Test
    public void testPlayer2ValidMovement() {
        super.testPlayerValidMovement(p2);
    }

    @Test
    public void testPlayer2MoveOffBoard() {
        super.testPlayerMoveOffBoard(p2);
    }

    @Test
    public void testPlayer2MoveIntoWall() {
        super.testPlayerMoveIntoWall(p2);
    }
}
