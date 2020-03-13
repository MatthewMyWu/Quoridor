package model;

import model.persistence.HistoricMatch;
import model.persistence.MatchHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MatchHistoryTests extends HistoricMatchTests {
    MatchHistory matchHistory;

    @BeforeEach
    public void runBefore(){
        super.runBefore();
        matchHistory = new MatchHistory();
    }

    @Test
    public void testSaveNewMatch() throws IOException {
        p2.incrementScore();
        matchHistory.saveNewMatch(p1, p2, 2, wallTool.getWallMiddles(), game.board);

        //HistoricMatch testMatch = new HistoricMatch(matchHistory.getLastGamePlayed());
        HistoricMatch testMatch = new HistoricMatch(MatchHistory.FILE_STRING + 0);
        //used to see match
        testMatch.displayMatch();
        testMatchesControlStartingP2Win(testMatch);
    }
}
