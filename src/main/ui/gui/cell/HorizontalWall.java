package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HorizontalWall extends Rectangle {
    private static final int WIDTH = GuiCell.LONG_LENGTH;
    private static final int HEIGHT = GuiCell.SHORT_LENGTH;

    protected HorizontalWall(int x, int y) {
        setWidth(WIDTH - GuiCell.SEPERATING_SPACE);
        setHeight(HEIGHT - GuiCell.SEPERATING_SPACE);
        setFill(Color.valueOf("F5D760"));
        relocate(x, y);

    }
}
