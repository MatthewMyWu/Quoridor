package ui;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.*;
import model.pathfinding.P1Pathfinder;
import model.pathfinding.Pathfinder;
import model.players.Avatar;
import model.players.P1;
import model.players.P2;
import model.walls.MiddleOfWall;
import model.walls.WallTool;

import java.util.Scanner;

import java.util.ArrayList;

/*This class handles user input, and contains all the methods for running the game*/

public class Game {
    public static final int SIDE_LENGTH = 9;
    public static final String DIVIDING_SPACE = "   ";
    public static final String VERTICAL_WALL_SPACE = " "; // space between a cell and a vertical wall next to it
    public static final String HORIZONTAL_WALL_SEGMENT = "---"; // what to print out for a horizontal wall segment
    private Scanner keyboard = new Scanner(System.in);
    private WallTool wallTool = new WallTool();
    private static Avatar p1 = new P1();
    private static Avatar p2 = new P2();
    public static ArrayList<Cell> board;
    //TODO delete after testing
    private Pathfinder p1Pathfinder = new P1Pathfinder(p1);

    //MODIFIES: this
    //EFFECTS : creates an empty square board with side-length SIDE_LENGTH
    public Game() {
        board = new ArrayList<>();//used to reset the board for @BeforeEach (tests)
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int column = 0; column < SIDE_LENGTH; column++) {
                board.add(new Cell(column, row));
            }
        }
    }

    //MODIFIES: this
    //EFFECTS : initializes game by putting Avatars in starting positions
    public void initialize() {
        p1.initialize();
        p2.initialize();
    }

    //EFFECTS : creates console display of board
    public void displayBoard() {
        //printing out top row of coordinates
        //first character needs to represent both A and 0, so needs special case
        System.out.print("A/0" + DIVIDING_SPACE);
        for (int column = 1; column <= SIDE_LENGTH; column++) {
            System.out.print(column + DIVIDING_SPACE);
        }
        System.out.println();

        //printing out actual grid
        for (int row = 0; row < SIDE_LENGTH; row++) {
            printRowWithCells(row);

            //printing out row between the rows with cells ("inter-rows")
            if (row < SIDE_LENGTH) {
                //printing out Letter coordinates
                System.out.print("\n" + (char) (66 + row) + DIVIDING_SPACE);
                //printing out horizontal walls below each cell, and middle wall segments (at corners of cells)
                for (int column = 0; column < SIDE_LENGTH; column++) {
                    printHorizontalWall(row, column);
                    if (column < SIDE_LENGTH - 1 && row < SIDE_LENGTH - 1) {
                        //There is not middle wall segements at the ends (right and bottom) of the board
                        printMiddleOfWall(row, column);
                    }
                }
            }
            System.out.println();
        }
    }

    /*EFFECTS : determines whether or not there is a wall segment at bottom right corner of each cell,
                and prints out the appropriate wall*/
    private void printMiddleOfWall(int row, int column) {
        //checking for middle wall segments
        MiddleOfWall wallMiddle = WallTool.getWallMiddle((row + 1) * SIDE_LENGTH + (column + 1));
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
        if (board.get(row * SIDE_LENGTH + column).isWallDown()) {
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
        for (int column = 0; column < SIDE_LENGTH; column++) {
            //printing out cell and walls to the right of each cell
            System.out.print(board.get(row * SIDE_LENGTH + column).displayCell());
        }
    }

    //EFFECTS : plays one "move" of the game (gives player 1 and 2 a turn)
    public void update() {
        System.out.println("Player 1 move");
        interpretInput(p1);
        displayBoard();

        //TODO delete after testing
        if (p1Pathfinder.canFindPath()) {
            System.out.println("Path found");
        } else {
            System.out.println("Path not found");
        }

        System.out.println("Player 2 move");
        interpretInput(p2);
        displayBoard();

        //TODO delete after testing
        if (p1Pathfinder.canFindPath()) {
            System.out.println("Path found");
        } else {
            System.out.println("Path not found");
        }
    }

    //EFFECTS : interprets the player input and calls the appropriate method
    private void interpretInput(Avatar player) {
        String input = keyboard.nextLine();
        //if the input matches one of the keys to move the player:
        if (input.equals(player.getUpKey()) || input.equals(player.getLeftKey()) || input.equals(player.getDownKey())
                || input.equals(player.getRightKey())) {
            try {
                player.move(input);
            } catch (OutOfBoundsException e) {
                System.out.println("You can not move off the board");
                interpretInput(player);
            } catch (WallObstructionException e) {
                System.out.println("Can not move over a wall");
                interpretInput(player);
            }
            //if the input matches the format of placing a wall
        } else if (isWallCommand(input)) {
            try {
                wallTool.placeWall(input);
            } catch (InvalidWallException e) {
                System.out.println("You can not place a wall there");
                interpretInput(player);
            }

        } else {
            System.out.println("That is not a valid input");
            interpretInput(player);
        }
    }

    //EFFECTS : determines if input is in the proper format for a command to place a wall
    protected boolean isWallCommand(String input) {
        return (input.length() == 5)
                //checking first character is a proper letter coordinate
                && (int) input.charAt(0) >= 65 && (int) input.charAt(0) <= 65 + SIDE_LENGTH
                //checking second character is a proper number coordinate
                && input.charAt(1) >= 48 && input.charAt(1) <= 48 + SIDE_LENGTH
                //checking third character is a comma
                && (input.charAt(2) == ',')
                //checking fourth character is a proper letter coordinate
                && (int) input.charAt(3) >= 65 && (int) input.charAt(3) <= 65 + SIDE_LENGTH
                //checking fifth character is a proper number coordinate
                && input.charAt(4) >= 48 && input.charAt(4) <= 48 + SIDE_LENGTH;

    }
}
