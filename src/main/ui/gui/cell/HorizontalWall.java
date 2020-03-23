package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//This is the GUI display element for horizontal walls
public class HorizontalWall extends Rectangle {

    public HorizontalWall() {
        setWidth(GuiCell.LONG_LENGTH);
        setHeight(GuiCell.SHORT_LENGTH);
        setVisible(false);
        setFill(Color.valueOf(GuiCell.WALL_COLOR));
    }
}
