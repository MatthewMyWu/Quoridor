package model;

import model.players.Avatar;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import ui.Game;

import java.util.ArrayList;

//contains methods necessary to print out the board to the screen
public class DisplayTool {
    public static final String DIVIDING_SPACE = "   ";
    public static final String VERTICAL_WALL_SPACE = " "; // space between a cell and a vertical wall next to it
    private static final String HORIZONTAL_WALL_SEGMENT = "---"; // what to print out for a horizontal wall segment
    private static final String SIDE_ITEM_SPACE = "       ";//space between board and "Side items" (eg. walls and score)
    private Avatar p1;
    private Avatar p2;
    private ArrayList<MiddleOfWall> wallMiddles;
    private ArrayList<Cell> board;

    public DisplayTool(Avatar p1, Avatar p2, ArrayList<MiddleOfWall> wallMiddles, ArrayList<Cell> board) {
        assert (board.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);
        assert (wallMiddles.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);
        this.p1 = p1;
        this.p2 = p2;
        this.wallMiddles = wallMiddles;
        this.board = board;
    }

    //EFFECTS : creates console display of board
    public void displayBoard() {
        //printing out top row of coordinates
        //first character needs to represent both A and 0, so needs special case
        System.out.print("A/0" + DIVIDING_SPACE);
        for (int column = 1; column <= Game.SIDE_LENGTH; column++) {
            System.out.print(column + DIVIDING_SPACE);
        }

        System.out.print(SIDE_ITEM_SPACE.substring(0, SIDE_ITEM_SPACE.length() - 2) + "Score");
        System.out.println();

        //printing out actual grid
        for (int row = 0; row < Game.SIDE_LENGTH; row++) {
            printRowWithCells(row);
            //printing out side items
            printSideItemsForRowWithCells(row);


            //printing out row between the rows with cells ("inter-rows")
            if (row < Game.SIDE_LENGTH) {
                //printing out Letter coordinates
                System.out.print("\n" + (char) (66 + row) + DIVIDING_SPACE);
                //printing out horizontal walls below each cell, and middle wall segments (at bottom right
                // corners of cells)
                for (int column = 0; column < Game.SIDE_LENGTH; column++) {
                    printHorizontalWall(row, column);
                    if (column < Game.SIDE_LENGTH - 1 && row < Game.SIDE_LENGTH - 1) {
                        printMiddleOfWall(row, column);
                    }
                }
                //printing out side items
                printSideItemsForInterRows(row);
            }
            System.out.println();
        }
    }

    //EFFECTS : Prints out the "side items" (score and walls of players) for the rows between the cells
    private void printSideItemsForInterRows(int row) {
        if (row == 0) {
            System.out.print(DIVIDING_SPACE + SIDE_ITEM_SPACE + "Player 2: " + p2.getScore());
        } else if (row == 2) {
            System.out.print(DIVIDING_SPACE + SIDE_ITEM_SPACE + "Player 1: " + p1.getWalls());
        }
    }

    //EFFECTS : Prints out the "side items" (score and walls of players) for the rows with cells
    private void printSideItemsForRowWithCells(int row) {
        if (row == 0) {
            System.out.print(SIDE_ITEM_SPACE + "Player 1: " + p1.getScore());
        } else if (row == 2) {
            System.out.print(SIDE_ITEM_SPACE + "Walls");
        } else if (row == 3) {
            System.out.print(SIDE_ITEM_SPACE + "Player 2: " + p2.getWalls());
        }
    }

    /*EFFECTS : determines whether or not there is a wall segment at bottom right corner of each cell,
                and prints out the appropriate wall*/
    private void printMiddleOfWall(int row, int column) {
        //checking for middle wall segments
        MiddleOfWall wallMiddle = wallMiddles.get((row + 1) * Game.SIDE_LENGTH + (column + 1));
        if (wallMiddle.isWallHere()) {
            if (wallMiddle.isVertical()) {
                System.out.print(VERTICAL_WALL_SPACE + "|" + VERTICAL_WALL_SPACE);
            } else {
                System.out.print(HORIZONTAL_WALL_SEGMENT);
            }
        } else {
            System.out.print(DIVIDING_SPACE);
        }
    }

    //EFFECTS : prints out the necessary horizontal walls directly below each cell
    private void printHorizontalWall(int row, int column) {
        //checking for horizontal wall segment
        if (board.get(row * Game.SIDE_LENGTH + column).isWallDown()) {
            System.out.print("—");
        } else {
            System.out.print(" ");
        }
    }

    //EFFECTS : prints out a row of cells, with the necessary vertical wall inbetween them
    private void printRowWithCells(int row) {
        //Aligning left end of board
        System.out.print(" " + DIVIDING_SPACE);
        //printing out actual board
        for (int column = 0; column < Game.SIDE_LENGTH; column++) {
            //printing out cell and walls to the right of each cell
            System.out.print(board.get(row * Game.SIDE_LENGTH + column).displayCell());
        }
    }
}
