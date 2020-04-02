//package model;
//
//import model.persistence.HistoricMatch;
//import model.persistence.MatchHistory;
//import model.players.Avatar;
//import model.players.GenericAvatar;
//import model.walls.MiddleOfWall;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ui.Game;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//public class HistoricMatchTests extends GameTests{
//    public static String controlStartingP2Win = "./data/testStartingConditionsP2Win";
//    public static String experimentalFile = "./data/testExperimental";
//    protected int p1Position;
//    protected int p2Position;
//
//    @BeforeEach
//    public void runBefore() {
//        super.runBefore();
//    }
//
//    @Test
//    public void testReadFileStartingConditionsP2Win() throws IOException {
//        p1Position = p1.getStartingArrayIndex();
//        p2Position = p2.getStartingArrayIndex();
//        //reading from file
//        HistoricMatch testMatch = new HistoricMatch(controlStartingP2Win);
//        //used to view testMatch
//        //testMatch.displayMatch();
//        testMatchesControlStartingP2Win(testMatch);
//
//    }
//
//    @Test
//    public void testWriteToFileStartingConditionsP2Win() throws IOException {
//        p2.incrementScore();
//        //writing to file
//        HistoricMatch testMatch = new HistoricMatch(p1, p2, 2, wallTool.getWallMiddles(), game.board,
//                experimentalFile);
//        testMatch.recordMatch();
//        //used to view testMatch
//        //testMatch.displayMatch();
//        testMatchesControlStartingP2Win(testMatch);
//    }
//
//    //sees if testMatch matches with information in controlStartingP2Win file
//    public void testMatchesControlStartingP2Win(HistoricMatch testMatch) {
//        p1Position = p1.getStartingArrayIndex();
//        p2Position = p2.getStartingArrayIndex();
//
//        //checking to see if file was read correctly
//        Avatar p1 = testMatch.getP1();
//        Avatar p2 = testMatch.getP2();
//        ArrayList<MiddleOfWall> wallMiddles = testMatch.getWallMiddles();
//        ArrayList<Cell> board = testMatch.getBoard();
//
//        //players
//        assertEquals(0, p1.getScore());
//        assertEquals(10, p1.getWalls());
//        assertEquals(1, p2.getScore());
//        assertEquals(10, p2.getWalls());
//
//        //wallMiddles
//        assertEquals(Game.SIDE_LENGTH * Game.SIDE_LENGTH, wallMiddles.size());
//        for (MiddleOfWall x : wallMiddles) {
//            assertFalse(x.isWallHere());
//        }
//
//        //board
//        assertEquals(Game.SIDE_LENGTH * Game.SIDE_LENGTH, board.size());
//        int arrayIndex = 0;
//        for (Cell x : board) {
//            assertFalse(x.isWallUp());
//            assertFalse(x.isWallLeft());
//            assertFalse(x.isWallDown());
//            assertFalse(x.isWallRight());
//            if (arrayIndex == p1Position) {
//                assertTrue(x.isP1Here());
//            } else if (arrayIndex == p2Position) {
//                assertTrue(x.isP2Here());
//            } else {
//                assertFalse(x.isP1Here());
//                assertFalse(x.isP2Here());
//            }
//            arrayIndex++;
//        }
//    }
//
//    @Test
//    public void testGenericAvatarConstructor() {
//        Game placeholderGame = new Game();
//        Avatar testAvatar = new GenericAvatar(placeholderGame);
//
//        assertFalse(testAvatar.reachedWinCondition(testAvatar));
//        assertNull(testAvatar.getUpKey());
//        assertNull(testAvatar.getLeftKey());
//        assertNull(testAvatar.getDownKey());
//        assertNull(testAvatar.getRightKey());
//        int arrayIndex = 0;
//        for (Cell x : game.board) {
//            assertFalse(testAvatar.isHere(arrayIndex));
//            arrayIndex++;
//        }
//    }
//}
