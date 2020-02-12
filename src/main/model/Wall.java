package model;

import exceptions.InvalidWallException;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;

public class Wall {
    private static ArrayList<MiddleOfWall> wallMiddles = new ArrayList<>();

    public Wall() {
        for (int row = 0; row < Game.SIDE_LENGTH; row++) {
            for (int column = 0; column < Game.SIDE_LENGTH; column++) {
                wallMiddles.add(new MiddleOfWall(row, column));
            }
        }

    }

    //REQUIRES: input be of the correct format, and coordinates are on the board. This should be checked by the caller
    //MODIFIES: Game (board)
    //EFFECTS : Adds a wall that spans the specified locations
    public void placeWall(String input) throws InvalidWallException {
        //TODO check to see that wall still works if enter right coordinate first
        //x and y coordinates of initial and latter ends of wall respectively. Indexing starts at 0 for all coordinates
        int x1 = (int) input.charAt(1) - 48;
        int y1 = (int) input.charAt(0) - 65;
        int x2 = (int) input.charAt(4) - 48;
        int y2 = (int) input.charAt(3) - 65;

        //first needs to check if wall is of valid length and doesn't intersect with any other walls
        if (validWalllength(x1, y1, x2, y2) && noWallIntersection(x1, y1, x2, y2)) {
            //need to determine if wall is horizontal or vertical
            if (y1 == y2) {
                placeHorizontalWall(x1, y1, x2, y2);
            } else if (x1 == x2) {
                placeVerticalWall(x1, y1, x2, y2);
            }
        } else {
            throw new InvalidWallException();
        }
    }


    //REQUIRES: coordinates must be valid coordinates for a horizontal wall
    //EFFECTS : places a horizontal wall at the given coordinates
    private void placeHorizontalWall(int x1, int y1, int x2, int y2) {
        int middleWallY = Math.abs(y2 - y1) / 2; //y coordinate of middle of wall segment
        int middleWallX = Math.abs(x2 - x1) / 2; //y coordinate of middle of wall segment

            //TODO actually add the wall

        //adding wall middle to wallMiddles
        wallMiddles.get((middleWallY * Game.SIDE_LENGTH + middleWallX)).setWallHere(true);
    }


    //REQUIRES: coordinates must be valid coordinates for a vertical wall
    //EFFECTS : places a vertical wall at the given coordinates
    private void placeVerticalWall(int x1, int y1, int x2, int y2) {
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
    //EFFECTS : returns true if wall placement does not intersect with the midpoint of another wall. False otherwise
    private boolean noWallIntersection(int x1, int y1, int x2, int y2) throws InvalidWallException {
        int middleWallY = Math.abs(y2 - y1) / 2; //y coordinate of middle of wall segment
        int middleWallX = Math.abs(x2 - x1) / 2; //y coordinate of middle of wall segment
        if (wallMiddles.get((middleWallY * Game.SIDE_LENGTH + middleWallX)).isWallHere()) {
            return false;
        } else {
            return true;
        }
    }
}
