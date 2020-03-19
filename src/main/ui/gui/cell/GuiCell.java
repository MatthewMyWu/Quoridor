package ui.gui.cell;

import javafx.scene.Group;
import model.Cell;

public class GuiCell extends Group {
    protected static final int LONG_LENGTH = 30;
    protected static final int SHORT_LENGTH = 10;
    protected static final int SEPERATING_SPACE = 3;
    public static final int SIDE_LENGTH = LONG_LENGTH + SHORT_LENGTH;
    //protected Cell cell;

    Tile tile;
    VerticalWall verticalWall;
    HorizontalWall horizontalWall;
    Corner corner;

    public GuiCell(int x, int y, Cell cell) {
        //this.cell = cell;
        int coordX = x * SIDE_LENGTH;
        int coordY = y * SIDE_LENGTH;
        tile = new Tile(coordX, coordY);
        verticalWall = new VerticalWall(coordX + LONG_LENGTH, coordY);
        horizontalWall = new HorizontalWall(coordX, coordY + LONG_LENGTH);
        corner = new Corner(getCornerX(x), getCornerY(coordY));

        getChildren().addAll(tile, verticalWall, horizontalWall, corner);
    }

    public static int getCornerX(int x) {
        return x + LONG_LENGTH;
    }

    public static int getCornerY(int y) {
        return y + LONG_LENGTH;
    }

    //EFFECTS : Updates this cell to display/not display wall below this cell
    public void setWallDown(boolean wallDown) {
        horizontalWall.setVisible(wallDown);
    }

    //EFFECTS : Updates this cell to display p1 if p1 is on this cell (or not display if p1 is not)
    public void setWallRight(boolean wallRight) {
        verticalWall.setVisible(wallRight);
    }

    //EFFECTS : Updates this cell to display/not display the corner (vertical if isVertical, horizontal if not)
    public void setCorner(boolean cornerHere, boolean isVertical) {
        corner.setVisible(cornerHere);
        //TODO stub
    }

    //EFFECTS : Updates this cell to display p1 if p1 is on this cell (or not display if p1 is not)
    public void setP1Here(boolean p1Here) {
        tile.setPlayer1Here(p1Here);
        //TODO stub
    }

    //EFFECTS : Updates this cell to display p2 if p2 is on this cell (or not display if p2 is not)
    public void setP2Here(boolean p2Here) {
        tile.setPlayer2Here(p2Here);
        //TODO stub
    }
}
