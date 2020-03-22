package ui.gui.cell;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Cell;

//contains information about a gui component of a cell, including the tile (piece where players stand on)
// and all surrounding wall segments (excluding corner pieces)
public class GuiCell extends Group {
    public static final int LONG_LENGTH = 30;
    public static final int SHORT_LENGTH = 10;
    public static final int INSET = 4;//only applies to tile
    public static final int SIDE_LENGTH = SHORT_LENGTH + LONG_LENGTH;
    public static final String WALL_COLOR = "#ed8f13";
    //protected Cell cell;

    //keeps track of top left corner of this cell
    protected int coordX;
    protected int coordY;

    Tile tile;
    HorizontalWall upperWall;
    HorizontalWall lowerWall;
    VerticalWall rightWall;
    VerticalWall leftWall;

    public GuiCell(int x, int y, Cell cell) {
        this.coordX = x * SIDE_LENGTH;
        this.coordY = y * SIDE_LENGTH;
        tile = new Tile(coordX + SHORT_LENGTH + INSET, coordY + SHORT_LENGTH + INSET);

        getChildren().addAll(tile);
    }

    //EFFECTS : Updates this cell to display p1 if p1 is on this cell (or not display if p1 is not)
    public void setP1Here(boolean p1Here) {
        tile.setPlayer1Here(p1Here);
        //TODO stub
    }

    //EFFECTS : Updates this cell to display p2 if p2 is on this cell (or not display if p2 is not)
    public void setP2Here(boolean p2Here) {
        tile.setPlayer2Here(p2Here);
        //TODO stub
    }

    public void setWallUp(boolean wallUp) {
        upperWall.setVisible(wallUp);
    }

    public void setWallLeft(boolean wallLeft) {
        leftWall.setVisible(wallLeft);
    }

    public void setWallDown(boolean wallDown) {
        lowerWall.setVisible(wallDown);
    }

    public void setWallRight(boolean wallRight) {
        rightWall.setVisible(wallRight);
    }


    //the following setters sets their respective walls and moves them to the correct location
    public void setUpperWallGui(HorizontalWall upperWall) {
        this.upperWall = upperWall;
        upperWall.moveTo(coordX + SHORT_LENGTH, coordY);
        getChildren().addAll(upperWall);
    }

    public void setLeftWallGui(VerticalWall leftWall) {
        this.leftWall = leftWall;
        leftWall.relocate(coordX, coordY + SHORT_LENGTH);
        getChildren().addAll(leftWall);
    }

    public void setLowerWallGui(HorizontalWall lowerWall) {
        this.lowerWall = lowerWall;
        lowerWall.moveTo(coordX + SHORT_LENGTH, coordY + SHORT_LENGTH + LONG_LENGTH);
        getChildren().addAll(lowerWall);
    }

    public void setRightWallGui(VerticalWall rightWall) {
        this.rightWall = rightWall;
        rightWall.relocate(coordX + SHORT_LENGTH + LONG_LENGTH, coordY + SHORT_LENGTH);
        getChildren().addAll(rightWall);
    }
}
