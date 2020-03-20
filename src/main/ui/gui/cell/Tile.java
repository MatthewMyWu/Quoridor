package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    protected Tile(int coordX, int coordY) {
        setWidth(GuiCell.LONG_LENGTH - 2 * GuiCell.INSET);
        setHeight(GuiCell.LONG_LENGTH - 2 * GuiCell.INSET);
        setPlayer1Here(false);
        setPlayer2Here(false);
        relocate(coordX, coordY);
    }

    protected void setPlayer1Here(boolean p1Here) {
        if (p1Here) {
            setFill(Color.CYAN);
        } else if (!p1Here) {
            setFill(Color.valueOf("A96118"));
        }
    }

    protected void setPlayer2Here(boolean p2Here) {
        if (p2Here) {
            setFill(Color.RED);
        } else if (!p2Here) {
            setFill(Color.valueOf("A96118"));
        }
    }
}
