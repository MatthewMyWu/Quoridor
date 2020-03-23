package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//This is the GUI display component for a "tile" (the squares that players can stand on)
public class Tile extends Rectangle {

    //EFFECTS : The constructor creates the tile and relocates it to the appropriate position
    protected Tile(int coordX, int coordY) {
        setWidth(GuiCell.LONG_LENGTH - 2 * GuiCell.INSET);
        setHeight(GuiCell.LONG_LENGTH - 2 * GuiCell.INSET);
        setPlayer1Here(false);
        setPlayer2Here(false);
        relocate(coordX, coordY);
    }

    //EFFECTS : Sets this tile to display whether or not p1 is on this tile
    protected void setPlayer1Here(boolean p1Here) {
        if (p1Here) {
            setFill(Color.CYAN);
        } else if (!p1Here) {
            setFill(Color.valueOf("A96118"));
        }
    }

    //EFFECTS : Sets this tile to display whether or not p2 is on this tile
    protected void setPlayer2Here(boolean p2Here) {
        if (p2Here) {
            setFill(Color.RED);
        } else if (!p2Here) {
            setFill(Color.valueOf("A96118"));
        }
    }
}
