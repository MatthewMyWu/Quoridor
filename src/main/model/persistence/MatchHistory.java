package model.persistence;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import model.Cell;
import model.players.Avatar;
import model.players.GenericAvatar;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import ui.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//contains all the matches in the match history. Employs singleton design pattern
public class MatchHistory {
    private static MatchHistory matchHistory = null;
    private Pane root = new Pane();
    private static Map<Integer, HistoricMatch> matches = new HashMap<>();
    public static final int MAX_MATCHES = 10;
    public static final String FILE_STRING = "./data/match";

    //REQUIRES: There must be 10 games played (in files)
    //EFFECTS : Is a constructor, but also initiates matches so that it holds the last 10 matches played
    private MatchHistory() {
        for (int x = 0; x < MAX_MATCHES; x++) {
            try {
                matches.put(x, new HistoricMatchFromFile(getFileName(x)));
            } catch (IOException e) {
                //In this case, has run out of existing match history files, so will create empty games for rest of loop
                fillWithEmptyMatches(x);

            }
        }
    }

    public static MatchHistory getInstance() {
        if (matchHistory == null) {
            matchHistory = new MatchHistory();
        }
        return matchHistory;
    }

    //MODIFIES: this
    //EFFECTS : Fills in the rest of matches (all matches past threshold) with "blank" matches
    // (only called if MAX_MATCHES is bigger than amount of matches currently stored in match history)
    private void fillWithEmptyMatches(int threshold) {
        //making generic match info
        int winner = 0;
        ArrayList<MiddleOfWall> emptyWallMiddles = WallTool.generateWallMiddles();
        ArrayList<Cell> emptyBoard = new ArrayList<>();
        for (int y = 0; y < Game.SIDE_LENGTH; y++) {
            for (int x = 0; x < Game.SIDE_LENGTH; x++) {
                emptyBoard.add(new Cell(x, y));
            }
        }

        Game placeholderGame = new Game();
        GenericAvatar p1 = new GenericAvatar(placeholderGame);
        GenericAvatar p2 = new GenericAvatar(placeholderGame);

        //adding matches to ./data
        for (int y = threshold; y < MAX_MATCHES; y++) {
            String fileName = getFileName(y);
            HistoricMatch newMatch = new HistoricMatchFromActiveGame(new Game(), fileName);
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

    //EFFECTS : Returns a scene that will display the entire match history
    public Parent createContent() {
        root = new Pane();
        root.setPrefSize(ui.Menu.PREF_WIDTH, ui.Menu.PREF_HEIGHT);
        root.getChildren().addAll(matches.get(0).createContent());


        //matches.get(0).displayMatch();
        return root;
    }

    //EFFECTS : Saves this match as the most recent match
    public void saveNewMatch(Game game) {
        HistoricMatch lastGamePlayed = new HistoricMatchFromActiveGame(game, (getFileName(0)));
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
