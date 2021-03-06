package model.walls;

import ui.gui.cell.Corner;

/*Since each wall spans across 2 cells, this object stores information about the middle segment of the wall that lies
between the two cells. We need to keep track of this to ensure that walls are not placed on top of one another.*/
public class MiddleOfWall {
    private boolean wallHere; // true if there is a wall segment here; false otherwise
    private boolean isVertical; //orientation of this segment of wall. True for vertical, false for horizontal
    private Corner guiCorner; //this is the GUI component that represents this section of wall

    //this constructor is used for HistoricMatch, when the final state of this section of wall is already known
    public MiddleOfWall(int x, int y, boolean wallHere, boolean isVertical) {
        this.wallHere = wallHere;
        this.isVertical = isVertical;
        guiCorner = new Corner(x, y);
    }

    public MiddleOfWall(int x, int y) {
        wallHere = false;
        guiCorner = new Corner(x, y);
    }

    public boolean isVertical() {
        return isVertical;
    }

    //updates the "vertical" of this wall, and also updates the GUI component
    public void setVertical(boolean vertical) {
        isVertical = vertical;
        guiCorner.setVertical(vertical);
    }

    public boolean isWallHere() {
        return wallHere;
    }

    //updates the "presence" of this wall, and also updates the GUI component
    public void setWallHere(boolean wallHere) {
        this.wallHere = wallHere;
        guiCorner.setWallHere(wallHere);
    }

    public Corner getGuiCorner() {
        return guiCorner;
    }
}
