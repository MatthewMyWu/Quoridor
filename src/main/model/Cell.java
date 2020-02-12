package model;

/*A "cell" object represents a square on the board. It contains information pertaining to the x and y coordinates of
 * a square. Indexing starts at 0.
 * It also contains information pertaining to what this cell should display (eg. whether or not there is an avatar on
 * it.
 * It also contains information pertaining to where an Avatar can move if it is on this cell.*/

public class Cell {
    private int coordX;
    private int coordY;
    private int arrayIndex; //keeps track of the index of the cell in the array
    private boolean p1Here;//is player 1 on this cell
    private boolean p2Here;//is player 2 on this cell
    //the following booleans check if there is a wall on the respective side of the cell
    private boolean wallUp;
    private boolean wallLeft;
    private boolean wallDown;
    private boolean wallRight;

    public boolean isWallUp() {
        return wallUp;
    }

    public void setWallUp(boolean wallUp) {
        this.wallUp = wallUp;
    }

    public boolean isWallLeft() {
        return wallLeft;
    }

    public void setWallLeft(boolean wallLeft) {
        this.wallLeft = wallLeft;
    }

    public boolean isWallDown() {
        return wallDown;
    }

    public void setWallDown(boolean wallDown) {
        this.wallDown = wallDown;
    }

    public boolean isWallRight() {
        return wallRight;
    }

    public void setWallRight(boolean wallRight) {
        this.wallRight = wallRight;
    }

    public boolean isP1Here() {
        return p1Here;
    }

    public void setP1Here(boolean p1Here) {
        this.p1Here = p1Here;
    }

    public boolean isP2Here() {
        return p2Here;
    }

    public void setP2Here(boolean p2Here) {
        this.p2Here = p2Here;
    }

    public Cell(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        arrayIndex = Game.SIDE_LENGTH * coordY + coordX;
        wallUp = false;
        wallLeft = false;
        wallDown = false;
        wallRight = false;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public String displayCell() {
        String returnString = "";
        //figuring out what to display in cell
        if (p1Here) {
            returnString = "1";
        } else if (p2Here) {
            returnString = "2";
        } else {
            returnString = "0";
        }

        //figuring out if we need to display a wall to the right of the cell
        if (wallRight) {
            returnString += Game.VERTICAL_WALL_SPACE + "|" + Game.VERTICAL_WALL_SPACE;
        }
        else {
            returnString += Game.DIVIDING_SPACE;
        }
        return returnString;

        //TODO error trap so that player 1 and 2 can't be on the same square
    }
}
