package model.walls;

/*Since each wall spans across 2 cells, this object stores information about the middle segment of the wall that lies
between the two cells. We need to keep track of this to ensure that walls are not placed on top of one another.*/
public class MiddleOfWall {
    private boolean wallHere; // true if there is a wall segment here; false otherwise
    private boolean isVertical; //orientation of this segment of wall. True for vertical, false for horizontal

    public MiddleOfWall(boolean wallHere, boolean isVertical) {
        this.wallHere = wallHere;
        this.isVertical = isVertical;
    }

    public MiddleOfWall() {
        wallHere = false;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public boolean isWallHere() {
        return wallHere;
    }

    public void setWallHere(boolean wallHere) {
        this.wallHere = wallHere;
    }
}
