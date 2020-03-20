package model.walls;

import exceptions.InvalidWallException;
import ui.Game;

import java.util.ArrayList;

//This tool is used for dealing with walls (eg. adding walls)

public class WallTool {
    private static ArrayList<MiddleOfWall> wallMiddles;
    public static final int WALL_MIDDLES_LENGTH = Game.SIDE_LENGTH + 1;

    //creates a new ArrayList of middle of Wall segments. Indexing starts at 0, starts at coordinate B1
    public WallTool() {
        wallMiddles = new ArrayList<>();
        for (int row = 0; row < WALL_MIDDLES_LENGTH; row++) {
            for (int column = 0; column < WALL_MIDDLES_LENGTH; column++) {
                wallMiddles.add(new MiddleOfWall(column, row));
            }
        }
    }

    //REQUIRES: input be of the correct format, and coordinates are on the board. This should be checked by the caller
    //EFFECTS : Adds a wall that spans the specified locations
    public void placeWall(String input) throws InvalidWallException {
        //this variable is used for the "pathfinding check" (bottom section of this method) (if pathfinding check fails,
        // need to use this variable to determine if we need to delete a vertical or horizontal wall)
        boolean horizontalWallPlaced = false;

        //x and y coordinates of initial and latter ends of wall respectively. Indexing starts at 0 for all coordinates
        int x1 = input.charAt(0) - 48;
        int y1 = input.charAt(2) - 48;
        int x2 = input.charAt(4) - 48;
        int y2 = input.charAt(6) - 48;

        //first needs to check if wall is of valid length and doesn't intersect with any other walls
        if (validWalllength(x1, y1, x2, y2) && noMiddleWallIntersection(x1, y1, x2, y2)) {
            //need to determine if wall is horizontal or vertical
            if (y1 == y2) {
                placeHorizontalWall(x1, y1, x2, y2);
                horizontalWallPlaced = true;
            } else if (x1 == x2) {
                placeVerticalWall(x1, y1, x2, y2);
            }
        } else {
            throw new InvalidWallException();
        }

        pathfindingCheck(horizontalWallPlaced, x1, y1, x2, y2);

    }

    //REQUIRES: input be of the correct format, and coordinates are on the board. This should be checked by the caller
    //EFFECTS : Checks that path can still be found for both players, after the wall is placed.
    //          If for either player, no path is found, then throws InvalidWallException and deletes the wall
    protected void pathfindingCheck(boolean horizontalWallPlaced, int x1, int y1, int x2, int y2)
            throws InvalidWallException {
        if (!Game.p1Pathfinder.canFindPath() || !Game.p2Pathfinder.canFindPath()) {
            if (horizontalWallPlaced) {
                deleteHorizontalWall(x1, y1, x2, y2);
            } else {
                deleteVerticalWall(x1, y1, x2, y2);
            }
            throw new InvalidWallException();
        }
    }


    //REQUIRES: coordinates must be valid coordinates for a horizontal wall
    //MODIFIES: this and Game (board)
    //EFFECTS : places a horizontal wall at the given coordinates
    private void placeHorizontalWall(int x1, int y1, int x2, int y2) throws InvalidWallException {
        int middleWallY = y1; //y coordinate of middle of wall segment
        int middleWallX = Math.abs(x2 + x1) / 2; //y coordinate of middle of wall segment
        int middleWallIndex = middleWallY * (WALL_MIDDLES_LENGTH) + middleWallX;

        //checking to see if we need to throw an InvalidWallException
        if (y1 == 0 || y1 == Game.SIDE_LENGTH) {
            //can't add a horizontal wall to the top and bottom ends of board
            throw new InvalidWallException();
        } else if (wallMiddles.get(middleWallIndex).isWallHere()) {
            //can't have middle of wall intersecting the middle of another wall
            throw new InvalidWallException();
        } else if (Game.board.get(middleWallY * Game.SIDE_LENGTH + middleWallX).isWallUp()
                || Game.board.get(middleWallY * Game.SIDE_LENGTH + middleWallX + 1).isWallUp()) {
            //can't have middle of wall intersecting the end of another HORIZONTAL wall
            throw new InvalidWallException();
        }

        //the following 2 variables are indexes of the 2 cells above the wall
        int topLeftCell = calculateTopLeftCell(middleWallX, middleWallY);
        int topRightCell = getTopRightCell(middleWallX, middleWallY);
        //the following 2 variables are indexes of the 2 cells below the wall
        int bottomLeftCell = calculateBottomLeftCell(middleWallX, middleWallY);
        int bottomRightCell = calculateBottomRightCell(middleWallX, middleWallY);

        //adding wall to board
        Game.board.get(topLeftCell).setWallDown(true);
        Game.board.get(topRightCell).setWallDown(true);
        Game.board.get(bottomLeftCell).setWallUp(true);
        Game.board.get(bottomRightCell).setWallUp(true);

        //adding wall middle to wallMiddles
        wallMiddles.get(middleWallIndex).setWallHere(true);
        wallMiddles.get(middleWallIndex).setVertical(false);
    }

    //REQUIRES: coordinates must be valid coordinates for a vertical wall
    //MODIFIES: this and Game (board)
    //EFFECTS : places a vertical wall at the given coordinates
    private void placeVerticalWall(int x1, int y1, int x2, int y2) throws InvalidWallException {
        int middleWallY = Math.abs(y2 + y1) / 2; //x coordinate of middle of wall segment
        int middleWallX = x1; //x coordinate of middle of wall segment
        int middleWallIndex = middleWallY * (WALL_MIDDLES_LENGTH) + middleWallX;

        //checking to see if we need to throw an InvalidWallException
        if (x1 == 0 || x1 == Game.SIDE_LENGTH) {
            //can't add a horizontal wall to the top and bottom ends of board
            throw new InvalidWallException();
        } else if (wallMiddles.get(middleWallIndex).isWallHere()) {
            //can't have middle of wall intersecting the middle of another wall
            throw new InvalidWallException();
        } else if (Game.board.get(middleWallY * Game.SIDE_LENGTH + middleWallX).isWallLeft()
                || Game.board.get((middleWallY - 1) * Game.SIDE_LENGTH + middleWallX).isWallLeft()) {
            //can't have middle of wall intersecting the end of another VERTICAL wall
            throw new InvalidWallException();
        }

        //the following 2 variables are indexes of the 2 cells next to the top half of the wall
        int topLeftCell = calculateTopLeftCell(middleWallX, middleWallY);
        int topRightCell = getTopRightCell(middleWallX, middleWallY);
        //the following 2 variables are indexes of the 2 cells next to the bottom half of the wall
        int bottomLeftCell = calculateBottomLeftCell(middleWallX, middleWallY);
        int bottomRightCell = calculateBottomRightCell(middleWallX, middleWallY);

        //adding wall to board
        Game.board.get(topLeftCell).setWallRight(true);
        Game.board.get(topRightCell).setWallLeft(true);
        Game.board.get(bottomLeftCell).setWallRight(true);
        Game.board.get(bottomRightCell).setWallLeft(true);

        //adding wall middle to wallMiddles
        wallMiddles.get(middleWallIndex).setWallHere(true);
        wallMiddles.get(middleWallIndex).setVertical(true);
    }

    //REQUIRES: coordinates must be valid coordinates for a horizontal wall
    //MODIFIES: this (wallMiddles) and Game (board)
    //EFFECTS : deletes horizontal wall with endpoints at the given coordinates
    public void deleteVerticalWall(int x1, int y1, int x2, int y2) {
        int middleWallY = Math.abs(y2 + y1) / 2; //x coordinate of middle of wall segment
        int middleWallX = x1; //x coordinate of middle of wall segment
        int middleWallIndex = middleWallY * (WALL_MIDDLES_LENGTH) + middleWallX;

        //the following 2 variables are indexes of the 2 cells on the top half of the wall
        int topLeftCell = calculateTopLeftCell(middleWallX, middleWallY);
        int topRightCell = getTopRightCell(middleWallX, middleWallY);
        //the following 2 variables are indexes of the 2 cells on the bottom half of the wall
        int bottomLeftCell = calculateBottomLeftCell(middleWallX, middleWallY);
        int bottomRightCell = calculateBottomRightCell(middleWallX, middleWallY);

        //deleting wall from board
        Game.board.get(topLeftCell).setWallRight(false);
        Game.board.get(topRightCell).setWallLeft(false);
        Game.board.get(bottomLeftCell).setWallRight(false);
        Game.board.get(bottomRightCell).setWallLeft(false);

        //deleting wall middle from wallMiddles
        wallMiddles.get(middleWallIndex).setWallHere(false);
    }

    //REQUIRES: coordinates must be valid coordinates for a horizontal wall
    //MODIFIES: this (wallMiddles) and Game (board)
    //EFFECTS : deletes vertical wall with endpoints at the given coordinates
    public void deleteHorizontalWall(int x1, int y1, int x2, int y2) {
        int middleWallY = y1; //y coordinate of middle of wall segment
        int middleWallX = Math.abs(x2 + x1) / 2; //x coordinate of middle of wall segment
        int middleWallIndex = middleWallY * WALL_MIDDLES_LENGTH + middleWallX;

        //the following 2 variables are indexes of the 2 cells above the wall
        int topLeftCell = calculateTopLeftCell(middleWallX, middleWallY);
        int topRightCell = getTopRightCell(middleWallX, middleWallY);
        //the following 2 variables are indexes of the 2 cells below the wall
        int bottomLeftCell = calculateBottomLeftCell(middleWallX, middleWallY);
        int bottomRightCell = calculateBottomRightCell(middleWallX, middleWallY);

        //deleting wall from board
        Game.board.get(topLeftCell).setWallDown(false);
        Game.board.get(topRightCell).setWallDown(false);
        Game.board.get(bottomLeftCell).setWallUp(false);
        Game.board.get(bottomRightCell).setWallUp(false);

        //deleting wall middle from wallMiddles
        wallMiddles.get(middleWallIndex).setWallHere(false);
    }

    private int calculateTopLeftCell(int middleX, int middleY) {
        return (middleY - 1) * Game.SIDE_LENGTH + (middleX - 1);
    }

    private int getTopRightCell(int middleX, int middleY) {
        return (middleY - 1) * Game.SIDE_LENGTH + middleX;
    }

    private int calculateBottomLeftCell(int middleX, int middleY) {
        return middleY * Game.SIDE_LENGTH + middleX - 1;
    }

    private int calculateBottomRightCell(int middleX, int middleY) {
        return middleY * Game.SIDE_LENGTH + middleX;
    }

    //REQUIRES: coordinates must be valid coordinates on the board
    //EFFECTS : returns true if wall is of the proper length/shape. False otherwise
    private boolean validWalllength(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            //wall can not be a point
            return false;
        } else if (y1 == y2) {
            //case where wall is horizontal, checking length
            return (Math.abs((x1 - x2)) == 2);
        } else if (x1 == x2) {
            //case where wall is vertical, checking length
            return (Math.abs((y1 - y2)) == 2);
        } else {
            return false;
        }
    }

    //REQUIRES: coordinates must be valid coordinates on the board
    //EFFECTS : returns true if middle of wall does not intersect with the midpoint of another wall. False otherwise
    private boolean noMiddleWallIntersection(int x1, int y1, int x2, int y2) throws InvalidWallException {
        int middleWallY = Math.abs(y2 - y1) / 2; //y coordinate of middle of wall segment
        int middleWallX = Math.abs(x2 - x1) / 2; //y coordinate of middle of wall segment
        if (wallMiddles.get((middleWallY * WALL_MIDDLES_LENGTH + middleWallX)).isWallHere()) {
            return false;
        } else {
            return true;
        }
    }

    public static MiddleOfWall getWallMiddle(int index) {
        return wallMiddles.get(index);
    }

    public static ArrayList<MiddleOfWall> getWallMiddles() {
        return wallMiddles;
    }
}
/*note that placeHorizontalWall and placeVerticalWall are very similar, but they differ in a few ways that I
can't parameterize*/
