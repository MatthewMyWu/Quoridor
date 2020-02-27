package model.persistence;

import model.Cell;
import model.players.Avatar;
import model.walls.MiddleOfWall;

import java.io.IOException;
import java.util.ArrayList;

//contains all the matches in the match history
public class MatchHistory {
    //public static final int MAX_MATCHES = 10;
    //private static ArrayList<HistoricMatch> matches = new ArrayList<>();
    private String lastGamePlayed = "./data/lastGamePlayed";

    public MatchHistory() {
    }

    public void display() {
        HistoricMatch lastGamePlayed = new HistoricMatch(this.lastGamePlayed);
        lastGamePlayed.displayMatch();
    }

    public void saveNewMatch(Avatar p1, Avatar p2, int winner, ArrayList<MiddleOfWall> wallMiddles,
                             ArrayList<Cell> board) {
        HistoricMatch lastGamePlayed = new HistoricMatch(p1, p2, winner, wallMiddles, board,
                getLastGamePlayed());
        try {
            lastGamePlayed.recordMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //////////////////////getters (for testing)//////////////////////////////////
    public String getLastGamePlayed() {
        return lastGamePlayed;
    }
}
