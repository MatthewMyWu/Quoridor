package model;

import model.persistence.HistoricMatch;
import model.persistence.MatchHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchHistoryTests extends HistoricMatchTests {
    MatchHistory matchHistory = new MatchHistory();

    @BeforeEach
    public void runBefore(){
        super.runBefore();
    }

    @Test
    public void testSaveNewMatch() {
        p2.incrementScore();
        matchHistory.saveNewMatch(p1, p2, 2, wallTool.getWallMiddles(), game.board);

        HistoricMatch testMatch = new HistoricMatch(matchHistory.getLastGamePlayed());
        //used to see match
        //testMatch.displayMatch();
        testMatchesControlStartingP2Win(testMatch);
    }
}
