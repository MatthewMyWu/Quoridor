package ui.gui.cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HorizontalWall extends Rectangle {
    public HorizontalWall() {
        setWidth(GuiCell.LONG_LENGTH);
        setHeight(GuiCell.SHORT_LENGTH);
        setVisible(false);
        setFill(Color.valueOf(GuiCell.WALL_COLOR));
    }

    //EFFECTS : relocates this cell to the given coordinates
    public void moveTo(double coordX, double coordY) {
        relocate(coordX, coordY);
    }
}
