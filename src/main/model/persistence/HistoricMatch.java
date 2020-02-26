package model.persistence;

import model.Cell;
import model.players.Avatar;
import model.walls.MiddleOfWall;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//contains the information that needs to be stored for 1 game in "match history"
public class HistoricMatch {
    private static final String DELIMITER = ",";
    private static final String ARRAY_ITEM_DELIMITER = ":";
    private Avatar p1;
    private Avatar p2;
    private ArrayList<Cell> board;
    private ArrayList<MiddleOfWall> wallMiddles;
    private String fileName = "testFile";
    private FileWriter writer;

    // EFFECTS: constructs a writer that will write data to file
    public HistoricMatch() {
        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordMatch() throws IOException {
        recordPlayerInfo(p1);
        writer.write("\n");
        recordPlayerInfo(p2);
        writer.write("\n");
        recordWallMiddlesInfo();
        writer.write("\n");
        recordBoardInfo();
    }

    private void recordPlayerInfo(Avatar player) throws IOException {
        writer.write(player.getCoordX() + DELIMITER + player.getCoordY() + DELIMITER + player.getScore()
                + DELIMITER + player.getWalls());
    }

    private void recordWallMiddlesInfo() throws IOException {
        for (MiddleOfWall x : wallMiddles) {
            writer.write(x.isWallHere() + DELIMITER + x.isVertical() + ARRAY_ITEM_DELIMITER);
        }
    }

    private void recordBoardInfo() {
    }


    /*file will be in the following format
    p1.coordX,p1.coordY,p1Score,p1Walls

    p2.coordX,p2.coordY,p2Score,p2Walls

    wallMiddles0.isWallHere,wallmiddles0.isVertical
    ...
    wallMiddles81.isWallHere,wallmiddles81.isVertical

    cell0.coordX,cell0.coordY,cell0.p1Here,cell0.p2Here,cell0.wallUp,cell0.wallLeft,cell0.wallDown,cell0.wallRight
    ...
    cell81.coordX,cell81.coordY,cell81.p1Here,cell81.p2Here,cell81.wallUp,cell81.wallLeft,cell81.wallDown,cell81.wallRight
     */

}
