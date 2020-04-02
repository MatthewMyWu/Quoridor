package model.persistence;

import javafx.scene.Parent;
import model.Cell;
import model.players.Avatar;
import model.players.GenericAvatar;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import ui.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//This class is used when the "historic match" needs to be read from a file
public class HistoricMatchFromFile extends HistoricMatch {

    // EFFECTS: constructs a writer with associated file. This constructor is used for reading a game from a file
    public HistoricMatchFromFile(String fileName) throws IOException {
        this.fileName = fileName;
        readMatch();
    }

    //EFFECTS : reads information for this match's associated file (and assigns the information to fields)
    private void readMatch() throws IOException {
        //reading from file
        File targetFile = new File(fileName);
        List<String> fileText = Files.readAllLines(targetFile.toPath());
        //ensuring the correct number of objects have been read from file
        assert (fileText.size() == 5);

        //We create game before assigning player information so players can be tied to board
        readWallMiddlesInfo(fileText.get(3));
        readBoardInfo(fileText.get(4));
        readWinnerInfo(fileText.get(2));
        this.game = new Game(p1, p2, winner, wallMiddles, board);

        //assigning information to fields
        readPlayerInfo(this.p1, fileText.get(0));
        readPlayerInfo(this.p2, fileText.get(1));
    }

    //REQUIRES: text be a valid representation of an Avatar
    //MODIFIES: this (p1 and p2)
    //EFFECTS : parses text and assigns the information to player
    private void readPlayerInfo(Avatar player, String text) {
        player = new GenericAvatar(game);

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
        assert (parsedElements.size() == WallTool.WALL_MIDDLES_LENGTH * WallTool.WALL_MIDDLES_LENGTH);

        //creating wallMiddles
        wallMiddles = new ArrayList<MiddleOfWall>();
        for (int y = 1; y < WallTool.WALL_MIDDLES_LENGTH; y++) {
            for (int x = 1; x < WallTool.WALL_MIDDLES_LENGTH; x++) {
                String element = parsedElements.get((y - 1) * Game.SIDE_LENGTH + x);
                List<String> parsedFields = parseFields(element);
                assert (parsedFields.size() == 2);
                boolean wallHere = Boolean.parseBoolean(parsedFields.get(0));
                boolean isVertical = Boolean.parseBoolean(parsedFields.get(1));

                wallMiddles.add(new MiddleOfWall(x, y, wallHere, isVertical));
            }
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
        for (int y = 0; y < Game.SIDE_LENGTH; y++) {
            for (int x = 0; x < Game.SIDE_LENGTH; x++) {
                String element = parsedElements.get(y * Game.SIDE_LENGTH + x);
                List<String> parsedFields = parseFields(element);
                assert (parsedFields.size() == 6);
                boolean p1Here = Boolean.parseBoolean(parsedFields.get(0));
                boolean p2Here = Boolean.parseBoolean(parsedFields.get(1));
                boolean wallUp = Boolean.parseBoolean(parsedFields.get(2));
                boolean wallLeft = Boolean.parseBoolean(parsedFields.get(3));
                boolean wallDown = Boolean.parseBoolean(parsedFields.get(4));
                boolean wallRight = Boolean.parseBoolean(parsedFields.get(5));

                board.add(new Cell(x, y, p1Here, p2Here, wallUp, wallLeft, wallDown, wallRight));
            }
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


}
