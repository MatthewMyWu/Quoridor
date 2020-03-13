package model.persistence;

import model.Cell;
import model.DisplayTool;
import model.players.Avatar;
import model.players.GenericAvatar;
import model.walls.MiddleOfWall;
import ui.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//contains the information that needs to be stored for 1 game in "match history", as well as methods to store it.
public class HistoricMatch {
    private static final String FIELD_DELIMITER = ",";//used to separate fields of an object
    private static final String ARRAY_ELEMENT_DELIMITER = ":";//used to separate elements of an array
    private static final String NEXT_LINE_DELIMITER = "\n";//used to separate information on different objects
    private int winner = 0;//player # that won this match
    private Avatar p1 = new GenericAvatar();
    private Avatar p2 = new GenericAvatar();
    private ArrayList<MiddleOfWall> wallMiddles;
    private ArrayList<Cell> board;
    private String fileName;
    private DisplayTool displaytool;
    private FileWriter writer;

    // EFFECTS: constructs a writer with associated file.This constructor is generally used when the final state of
    // the game is unknown (eg. for reading the final state of the game from a file)
    public HistoricMatch(String fileName) throws IOException {
        this.fileName = fileName;
        readMatch();
    }

    // EFFECTS: constructs a writer with associated players, wallMiddles, board, and file.
    // This constructor is generally used when the final state of the game is known (eg. for recording a finished game)
    public HistoricMatch(Avatar p1, Avatar p2, int winner, ArrayList<MiddleOfWall> wallMiddles, ArrayList<Cell> board,
                         String filename) {
        this.p1 = p1;
        this.p2 = p2;
        this.winner = winner;
        this.wallMiddles = wallMiddles;
        this.board = board;
        this.fileName = filename;

        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //REQUIRES: players, wallMiddles, and board all be assigned
    //EFFECTS : creates a console display of this match
    public void displayMatch() {
        displaytool = new DisplayTool(p1, p2, wallMiddles, board);
        displaytool.displayBoard();
        System.out.println("Player " + winner + " wins!");
    }

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

    //EFFECTS : reads information for this match's associated file (and assigns the information to fields)
    private void readMatch() throws IOException {
        //reading from file
        File targetFile = new File(fileName);
        List<String> fileText = Files.readAllLines(targetFile.toPath());
        //ensuring the correct number of objects have been read from file
        assert (fileText.size() == 5);

        //assigning information to fields
        readPlayerInfo(this.p1, fileText.get(0));
        readPlayerInfo(this.p2, fileText.get(1));
        readWinnerInfo(fileText.get(2));
        readWallMiddlesInfo(fileText.get(3));
        readBoardInfo(fileText.get(4));
    }

    //REQUIRES: text be a valid representation of an Avatar
    //MODIFIES: this (p1 and p2)
    //EFFECTS : parses text and assigns the information to player
    private void readPlayerInfo(Avatar player, String text) {
        //parsing text
        List<String> parsedFields = parseFields(text);
        assert (parsedFields.size() == 4);//ensuring correct number of fields are in this object
        int coordX = Integer.parseInt(parsedFields.get(0));
        int coordY = Integer.parseInt(parsedFields.get(1));
        int score = Integer.parseInt(parsedFields.get(2));
        int walls = Integer.parseInt(parsedFields.get(3));

        //modifying player
        player.moveTo(coordX, coordY);
        player.setScore(score);
        player.setWalls(walls);
    }

    //REQUIRES: text be a valid representation of an Avatar
    //MODIFIES: this (winner)
    //EFFECTS : parses text and assigns the information to winner
    private void readWinnerInfo(String text) {
        this.winner = Integer.parseInt(text);
    }

    //REQUIRES: text be a valid representation of wallMiddles
    //MODIFIES: this (wallMiddles)
    //EFFECTS : parses text and assigns the information to wallMiddles
    private void readWallMiddlesInfo(String text) {
        //parsing text
        List<String> parsedElements = parseElements(text);
        assert (parsedElements.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);

        //creating wallMiddles
        wallMiddles = new ArrayList<MiddleOfWall>();
        for (String element : parsedElements) {
            List<String> parsedFields = parseFields(element);
            assert (parsedFields.size() == 2);
            boolean wallHere = Boolean.parseBoolean(parsedFields.get(0));
            boolean isVertical = Boolean.parseBoolean(parsedFields.get(1));

            wallMiddles.add(new MiddleOfWall(wallHere, isVertical));
        }
    }

    //REQUIRES: text be a valid representation of board
    //MODIFIES: this (board)
    //EFFECTS : parses text and assigns the information to board
    private void readBoardInfo(String text) {
        //parsing text
        List<String> parsedElements = parseElements(text);
        assert (parsedElements.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);

        //creating board
        board = new ArrayList<Cell>();
        for (String element : parsedElements) {
            List<String> parsedFields = parseFields(element);
            assert (parsedFields.size() == 6);
            boolean p1Here = Boolean.parseBoolean(parsedFields.get(0));
            boolean p2Here = Boolean.parseBoolean(parsedFields.get(1));
            boolean wallUp = Boolean.parseBoolean(parsedFields.get(2));
            boolean wallLeft = Boolean.parseBoolean(parsedFields.get(3));
            boolean wallDown = Boolean.parseBoolean(parsedFields.get(4));
            boolean wallRight = Boolean.parseBoolean(parsedFields.get(5));

            board.add(new Cell(p1Here, p2Here, wallUp, wallLeft, wallDown, wallRight));
        }
    }

    //EFFECTS : parses text into elements (of an array)
    private List<String> parseElements(String text) {
        return Arrays.asList(text.split(ARRAY_ELEMENT_DELIMITER));
    }

    //EFFECTS : parses text into fields (of an object)
    private List<String> parseFields(String text) {
        return Arrays.asList(text.split(FIELD_DELIMITER));
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
