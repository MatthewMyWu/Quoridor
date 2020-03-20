package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Corner extends Rectangle {
    //following coordinates keeps track of the top left corner of this rectangle
    private double coordX;
    private double coordY;

    public Corner(int x, int y) {
        this.coordX = getCoord(x);
        this.coordY = getCoord(y);

        setWidth(GuiCell.SHORT_LENGTH);
        setHeight(GuiCell.SHORT_LENGTH);
        //setFill(Color.valueOf("F5D760"));
        setFill(Color.valueOf(GuiCell.WALL_COLOR));
        setVisible(false);
        relocate(coordX, coordY);
    }

    public static double getCoord(int coord) {
        return coord * GuiCell.SIDE_LENGTH;
    }

    public void setWallHere(boolean wallHere) {
        setVisible(wallHere);
    }

    public void setVertical(boolean vertical) {
    }
}
