package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VerticalWall extends Rectangle {
    private static final int WIDTH = GuiCell.SHORT_LENGTH;
    private static final int HEIGHT = GuiCell.LONG_LENGTH;

    protected VerticalWall(int x, int y) {
        setWidth(WIDTH - GuiCell.SEPERATING_SPACE);
        setHeight(HEIGHT - GuiCell.SEPERATING_SPACE);
        setFill(Color.valueOf("F5D760"));
        relocate(x, y);
        setVisible(false);
    }
}
