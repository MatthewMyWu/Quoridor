package ui.gui.cell;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Cell;

//contains information about a gui component of a cell, including the tile (piece where players stand on)
// and all surrounding wall segments (excluding corner pieces)
public class GuiCell extends Group {
    public static final int LONG_LENGTH = 50; //The length of the long side of a wall
    public static final int SHORT_LENGTH = 15; //The length of the short side of a wall
    public static final int INSET = 6; //The space between the tile and the walls
    public static final int SIDE_LENGTH = SHORT_LENGTH + LONG_LENGTH;
    public static final String WALL_COLOR = "#ed8f13";

    //keeps track of top left corner of this cell
    protected int coordX;
    protected int coordY;

    Tile tile; //This is what the player stands on
    //The following are walls on their respective side of the tile
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
    }

    //EFFECTS : Updates this cell to display p2 if p2 is on this cell (or not display if p2 is not)
    public void setP2Here(boolean p2Here) {
        tile.setPlayer2Here(p2Here);
    }

    //EFFECTS : makes the wall at the top of the cell visible/real
    public void setWallUp(boolean wallUp) {
        upperWall.setVisible(wallUp);
    }

    //EFFECTS : makes the wall at the left of the cell visible/real
    public void setWallLeft(boolean wallLeft) {
        leftWall.setVisible(wallLeft);
    }

    //EFFECTS : makes the wall at the bottom of the cell visible/real
    public void setWallDown(boolean wallDown) {
        lowerWall.setVisible(wallDown);
    }

    //EFFECTS : makes the wall at the right of the cell visible/real
    public void setWallRight(boolean wallRight) {
        rightWall.setVisible(wallRight);
    }


    //EFFECTS : Is a setter for the upperWall, relocates it to the correct position, and adds it to this (children)
    public void setUpperWallGui(HorizontalWall upperWall) {
        this.upperWall = upperWall;
        upperWall.relocate(coordX + SHORT_LENGTH, coordY);
        getChildren().addAll(upperWall);
    }

    //EFFECTS : Is a setter for the leftWall, relocates it to the correct position, and adds it to this (children)
    public void setLeftWallGui(VerticalWall leftWall) {
        this.leftWall = leftWall;
        leftWall.relocate(coordX, coordY + SHORT_LENGTH);
        getChildren().addAll(leftWall);
    }

    //EFFECTS : Is a setter for the lowerWall, relocates it to the correct position, and adds it to this (children)
    public void setLowerWallGui(HorizontalWall lowerWall) {
        this.lowerWall = lowerWall;
        lowerWall.relocate(coordX + SHORT_LENGTH, coordY + SHORT_LENGTH + LONG_LENGTH);
        getChildren().addAll(lowerWall);
    }

    //EFFECTS : Is a setter for the rightWall, relocates it to the correct position, and adds it to this (children)
    public void setRightWallGui(VerticalWall rightWall) {
        this.rightWall = rightWall;
        rightWall.relocate(coordX + SHORT_LENGTH + LONG_LENGTH, coordY + SHORT_LENGTH);
        getChildren().addAll(rightWall);
    }
}
