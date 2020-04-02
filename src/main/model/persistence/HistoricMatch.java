package model.persistence;

import javafx.scene.Node;
import javafx.scene.Parent;
import model.Cell;
import model.players.Avatar;
import model.players.GenericAvatar;
import model.walls.MiddleOfWall;
import ui.Game;
import ui.gui.GameGuiTool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

//This match contains general information for matches in "Match History".
public abstract class HistoricMatch {
    protected static final String FIELD_DELIMITER = ",";//used to separate fields of an object
    protected static final String ARRAY_ELEMENT_DELIMITER = ":";//used to separate elements of an array
    protected static final String NEXT_LINE_DELIMITER = "\n";//used to separate information on different objects
    protected int winner = 0;//player # that won this match
    protected Avatar p1;
    protected Avatar p2;
    protected ArrayList<MiddleOfWall> wallMiddles;
    protected ArrayList<Cell> board;
    protected String fileName;
    protected Game game; //This is the game that this match is tied to
    protected GameGuiTool gameGuiTool;
    protected FileWriter writer;

    //REQUIRES: players, wallMiddles, and board all be known
    //MODIFIES: file
    //EFFECTS : records this match into file
    public void recordMatch() throws IOException {
        recordPlayerInfo(p1);
        writer.write(NEXT_LINE_DELIMITER);
        recordPlayerInfo(p2);
        writer.write(NEXT_LINE_DELIMITER);
        recordWinnerInfo();
        writer.write(NEXT_LINE_DELIMITER);
        recordWallMiddlesInfo();
        writer.write(NEXT_LINE_DELIMITER);
        recordBoardInfo();
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS : Moves this so that it is recorded in fileName
    protected void shiftTo(String fileName) throws IOException {
        this.fileName = fileName;
        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recordMatch();
    }

    //MODIFIES: file
    //EFFECTS : records player into file
    private void recordPlayerInfo(Avatar player) throws IOException {
        writer.write(player.getCoordX() + FIELD_DELIMITER + player.getCoordY() + FIELD_DELIMITER + player.getScore()
                + FIELD_DELIMITER + player.getWalls());
    }

    //MODIFIES: file
    //EFFECTS : records winner into file
    private void recordWinnerInfo() throws IOException {
        String winnerString = "" + this.winner;
        writer.write(winnerString);
    }

    //MODIFIES: file
    //EFFECTS : records wallMiddles into file
    private void recordWallMiddlesInfo() throws IOException {
        for (MiddleOfWall x : wallMiddles) {
            writer.write(x.isWallHere() + FIELD_DELIMITER + x.isVertical() + ARRAY_ELEMENT_DELIMITER);
        }
    }

    //MODIFIES: file
    //EFFECTS : records board into file
    private void recordBoardInfo() throws IOException {
        for (Cell x : board) {
            writer.write(x.isP1Here() + FIELD_DELIMITER + x.isP2Here()
                    + FIELD_DELIMITER + x.isWallUp() + FIELD_DELIMITER + x.isWallLeft()
                    + FIELD_DELIMITER + x.isWallDown() + FIELD_DELIMITER + x.isWallRight() + ARRAY_ELEMENT_DELIMITER);
        }
    }
    
    //REQUIRES: players, wallMiddles, and board all be assigned
    //EFFECTS : creates a console display of this match
    public Parent createContent() {
        return gameGuiTool.createContent();
    }


    /*file will be in the following format
    p1.coordX,p1.coordY,p1Score,p1Walls

    p2.coordX,p2.coordY,p2Score,p2Walls

    wallMiddles0.isWallHere,wallmiddles0.isVertical
    ...
    wallMiddles81.isWallHere,wallmiddles81.isVertical

    cell0.p1Here,cell0.p2Here,cell0.wallUp,cell0.wallLeft,cell0.wallDown,cell0.wallRight
    ...
    cell81.p1Here,cell81.p2Here,cell81.wallUp,cell81.wallLeft,cell81.wallDown,cell81.wallRight
     */


    ////////////////////////////////////getters (for testing)///////////////////////////////////////

    public Avatar getP1() {
        return p1;
    }

    public Avatar getP2() {
        return p2;
    }

    public int getWinner() {
        return winner;
    }

    public ArrayList<MiddleOfWall> getWallMiddles() {
        return wallMiddles;
    }

    public ArrayList<Cell> getBoard() {
        return board;
    }
}
