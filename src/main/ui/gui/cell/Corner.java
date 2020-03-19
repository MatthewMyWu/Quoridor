package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Corner extends Rectangle {
    private static final int SIDE_LENGTH = GuiCell.SHORT_LENGTH;

    protected Corner(int x, int y) {
        setWidth(SIDE_LENGTH - GuiCell.SEPERATING_SPACE);
        setHeight(SIDE_LENGTH - GuiCell.SEPERATING_SPACE);
        setFill(Color.valueOf("F5D760"));
        relocate(x, y);
    }
}
