package model.persistence;

import com.sun.tools.javah.Gen;
import model.Cell;
import model.players.Avatar;
import model.players.GenericAvatar;
import model.players.P1;
import model.players.P2;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import ui.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//contains all the matches in the match history
public class MatchHistory {
    public static final String DISPLAY_DIVIDER = "======================================";

    public static final int MAX_MATCHES = 10;
    public static final String FILE_STRING = "./data/match";
    private static Map<Integer, HistoricMatch> matches = new HashMap<>();

    //REQUIRES: There must be 10 games played (in files)
    //EFFECTS : Is a constructor, but also initiates matches so that it holds the last 10 matches played
    public MatchHistory() {
        for (int x = 0; x < MAX_MATCHES; x++) {
            try {
                matches.put(x, new HistoricMatch(getFileName(x)));
            } catch (IOException e) {
                //In this case, has run out of existing match history files, so will create empty games for rest of loop
                fillWithEmptyMatches(x);

            }
        }
    }

    //MODIFIES: this
    //EFFECTS : Fills in the rest of matches with "blank" matches (only called if MAX_MATCHES is bigger than amount
    //          of matches currently stored in match history)
    private void fillWithEmptyMatches(int x) {
        //making generic match info
        GenericAvatar p1 = new GenericAvatar();
        GenericAvatar p2 = new GenericAvatar();
        int winner = 0;
        ArrayList<MiddleOfWall> emptyWallMiddles = new WallTool().getWallMiddles();
        ArrayList<Cell> emptyBoard = new ArrayList<>();
        for (int z = 0; z < Game.SIDE_LENGTH * Game.SIDE_LENGTH; z++) {
            emptyBoard.add(new Cell());
        }

        //adding matches to ./data
        for (int y = x; y < MAX_MATCHES; y++) {
            String fileName = getFileName(y);
            HistoricMatch newMatch = new HistoricMatch(p1, p2, winner, emptyWallMiddles, emptyBoard, fileName);
            //recording match to ./data
            try {
                newMatch.recordMatch();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //adding match to matches
            matches.put(y, newMatch);
        }
    }

    //EFFECTS : Displays entire match history
    public void display() {
        for (int x = 0; x < MAX_MATCHES; x++) {
            matches.get(x).displayMatch();
            System.out.println(DISPLAY_DIVIDER);
        }
    }

    //EFFECTS : Saves this match as the most recent match
    public void saveNewMatch(Avatar p1, Avatar p2, int winner, ArrayList<MiddleOfWall> wallMiddles,
                             ArrayList<Cell> board) {
        HistoricMatch lastGamePlayed = new HistoricMatch(p1, p2, winner, wallMiddles, board, (getFileName(0)));
        try {
            pushDownMatches();
            matches.put(0, lastGamePlayed);
            lastGamePlayed.recordMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this (matches)
    //EFFECTS : "pushes" all matches to next slot (ex. match1 becomes match2, match2 becomes match3, etc.)
    private void pushDownMatches() {
        try {
            for (int a = MAX_MATCHES - 2; a >= 0; a--) {
                HistoricMatch match = matches.get(a);
                match.shiftTo(getFileName(a + 1));
                matches.put(a + 1, match);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //EFFECTS : returns the file name of matchNumber
    private String getFileName(int matchNumber) {
        return FILE_STRING + matchNumber + ".txt";
    }

    //////////////////////getters (for testing)//////////////////////////////////
}
