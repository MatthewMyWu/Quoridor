package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    private static final int SIDE_LENGTH = GuiCell.LONG_LENGTH;

    protected Tile(int x, int y) {
        setWidth(SIDE_LENGTH - GuiCell.SEPERATING_SPACE);
        setHeight(SIDE_LENGTH - GuiCell.SEPERATING_SPACE);
        setPlayer1Here(false);
        setPlayer2Here(false);
        relocate(x, y);
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
