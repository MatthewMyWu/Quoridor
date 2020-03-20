package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VerticalWall extends Rectangle {
    public VerticalWall(int x, int y) {
        setWidth(GuiCell.SHORT_LENGTH);
        setHeight(GuiCell.LONG_LENGTH);
        setFill(Color.valueOf(GuiCell.WALL_COLOR));
        relocate(x, y);
        setVisible(false);
    }
}
