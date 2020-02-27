package model.persistence;

import model.Cell;
import model.players.Avatar;
import model.walls.MiddleOfWall;

import java.io.IOException;
import java.util.ArrayList;

//contains all the matches in the match history
public class MatchHistory {
    public static final int MAX_MATCHES = 10;
    private static ArrayList<HistoricMatch> matches = new ArrayList<>();
    private static String filename = "./data/lastGamePlayed";

    public static void display() {
        HistoricMatch lastGamePlayed = new HistoricMatch(filename);
        try {
            lastGamePlayed.readMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastGamePlayed.displayMatch();
    }

    public static void saveNewMatch(Avatar p1, Avatar p2, int winner, ArrayList<MiddleOfWall> wallMiddles,
                             ArrayList<Cell> board) {
        HistoricMatch lastGamePlayed = new HistoricMatch(p1, p2, winner, wallMiddles, board, filename);
        try {
            lastGamePlayed.recordMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readMatch(String filename) {
        HistoricMatch lastGamePlayed = new HistoricMatch(filename);
    }
}
