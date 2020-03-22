package model;

/*A "cell" object represents a square on the board. It contains information pertaining to the x and y coordinates of
 * a square. Indexing starts at 0.
 * It also contains information pertaining to what this cell should display (eg. whether or not there is an avatar on
 * it.
 * It also contains information pertaining to where an Avatar can move if it is on this cell.*/

import ui.gui.cell.GuiCell;
import ui.gui.cell.HorizontalWall;
import ui.gui.cell.VerticalWall;

public class Cell {
    private boolean p1Here;//is player 1 on this cell
    private boolean p2Here;//is player 2 on this cell
    protected boolean visited;//used for WinnableSolver
    private GuiCell guiCell;//coupled with gui display element for this cell
    //the following booleans check if there is a wall on the respective side of the cell
    private boolean wallUp;
    private boolean wallLeft;
    private boolean wallDown;
    private boolean wallRight;

    //EFFECTS : This constructor is used for HistoricMatch.
    //          It constructs a cell where the final state of the cell is known (because the game is finished)
    public Cell(int x, int y, boolean p1Here, boolean p2Here,
                boolean wallUp, boolean wallLeft, boolean wallDown, boolean wallRight) {
        this.p1Here = p1Here;
        this.p2Here = p2Here;
        this.wallUp = wallUp;
        this.wallLeft = wallLeft;
        this.wallDown = wallDown;
        this.wallRight = wallRight;
        this.guiCell = new GuiCell(x, y, this);
    }

    public Cell(int x, int y) {
        wallUp = false;
        wallLeft = false;
        wallDown = false;
        wallRight = false;
        visited = false;
        this.guiCell = new GuiCell(x, y, this);
    }

    public boolean isWallUp() {
        return wallUp;
    }

    //MODIFIES: This and guiCell
    //EFFECTS : Setter for WallUp and updates the GUI component
    public void setWallUp(boolean wallUp) {
        this.wallUp = wallUp;
        guiCell.setWallUp(wallUp);
    }

    public boolean isWallLeft() {
        return wallLeft;
    }

    //MODIFIES: This and guiCell
    //EFFECTS : Setter for wallLeft and updates the GUI component
    public void setWallLeft(boolean wallLeft) {
        this.wallLeft = wallLeft;
        guiCell.setWallLeft(wallLeft);
    }

    public boolean isWallDown() {
        return wallDown;
    }

    //MODIFIES: This and guiCell
    //EFFECTS : Setter for wallDown and updates the GUI component
    public void setWallDown(boolean wallDown) {
        this.wallDown = wallDown;
        guiCell.setWallDown(wallDown);
    }

    public boolean isWallRight() {
        return wallRight;
    }

    //MODIFIES: This and guiCell
    //EFFECTS : Setter for wallRight and updates the GUI component
    public void setWallRight(boolean wallRight) {
        this.wallRight = wallRight;
        guiCell.setWallRight(wallRight);
    }

    public boolean isP1Here() {
        return p1Here;
    }

    //MODIFIES: This and guiCell
    //EFFECTS : Setter for p1Here and updates the GUI component
    public void setP1Here(boolean p1Here) {
        this.p1Here = p1Here;
        guiCell.setP1Here(p1Here);
    }

    public boolean isP2Here() {
        return p2Here;
    }

    //MODIFIES: This and guiCell
    //EFFECTS : Setter for p2Here and updates the GUI component
    public void setP2Here(boolean p2Here) {
        this.p2Here = p2Here;
        guiCell.setP2Here(p2Here);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public GuiCell getGuiCell() {
        return guiCell;
    }

    public void setUpperGuiWall(HorizontalWall horizontalWall) {
        guiCell.setUpperWallGui(horizontalWall);
    }

    public void setLeftGuiWall(VerticalWall verticalWall) {
        guiCell.setLeftWallGui(verticalWall);
    }

    public void setLowerGuiWall(HorizontalWall horizontalWall) {
        guiCell.setLowerWallGui(horizontalWall);
    }

    public void setRightGuiWall(VerticalWall verticalWall) {
        guiCell.setRightWallGui(verticalWall);
    }


    //TODO error trap so that player 1 and 2 can't be on the same square
//    }
}
