package model.persistence;

import model.Cell;
import model.players.Avatar;
import model.walls.MiddleOfWall;
import ui.Game;

import java.io.FileWriter;
import java.io.IOException;

//This class is used when a new "historic match" needs to be made from the active game.
public class HistoricMatchFromActiveGame extends HistoricMatch {

    // EFFECTS: constructs a writer with associated players, wallMiddles, board, and file.
    // This constructor is used for recording a finished game
    public HistoricMatchFromActiveGame(Game game, String filename) {
        this.game = game;
        this.gameGuiTool = game.getGameGuiTool();
        this.p1 = game.getP1();
        this.p2 = game.getP2();
        this.winner = game.getWinner();
        this.wallMiddles = game.getWallTool().getWallMiddles();
        this.board = game.getBoard();
        this.fileName = filename;

        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
