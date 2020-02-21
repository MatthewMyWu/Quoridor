package ui;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.*;
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
    private static final String SIDE_ITEM_SPACE = "       ";//space between board and "Side items" (eg. walls and score)
    private Scanner keyboard = new Scanner(System.in);
    private WallTool wallTool = new WallTool();
    private static Avatar p1 = new P1();
    private static Avatar p2 = new P2();
    public static Pathfinder p1Pathfinder = new Pathfinder(p1);
    public static Pathfinder p2Pathfinder = new Pathfinder(p2);
    private boolean gameOver = false;
    private String winner; //Is the player that won the game (eg. 1 or 2)
    public static ArrayList<Cell> board;


    //MODIFIES: this
    //EFFECTS : creates a new game (resets all elements of game: board, players and walls)
    public Game() {
        //resetting the board
        board = new ArrayList<>();
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int column = 0; column < SIDE_LENGTH; column++) {
                board.add(new Cell(column, row));
            }
        }

        //resetting players
        p1.initialize();
        p2.initialize();
    }


    //EFFECTS : starts the game
    public void play() {
        while (!gameOver) {
            update();
        }
        displayBoard();
        displayGameOverScreen();
    }

    //EFFECTS : Displays the game over screen
    private void displayGameOverScreen() {
        System.out.println(winner + " wins!");
        System.out.println("1. PLAY AGAIN");
        System.out.println("2. MAIN MENU");
        interpretGameOverInput();
    }

    //EFFECTS : Interprets player input for the game over screen
    private void interpretGameOverInput() {
        String input = keyboard.nextLine();
        if (input.equals("1") || input.equals("1.") || input.equals("PLAY AGAIN")) {
            restart();
            play();
        } else if (input.equals("2") || input.equals("2.") || input.equals("MAIN MENU")) {
            //return;
        } else {
            System.out.println("That is not a valid input");
            interpretGameOverInput();
        }
    }

    //MODIFIES: this
    //EFFECTS : restarts the game, but does not reset the scores of each player
    private void restart() {
        //resetting the board
        board = new ArrayList<>();
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int column = 0; column < SIDE_LENGTH; column++) {
                board.add(new Cell(column, row));
            }
        }

        //resetting players
        p1.initialize();
        p2.initialize();

        //resetting walls
        wallTool = new WallTool();
    }

    //EFFECTS : creates console display of board
    public void displayBoard() {
        //printing out top row of coordinates
        //first character needs to represent both A and 0, so needs special case
        System.out.print("A/0" + DIVIDING_SPACE);
        for (int column = 1; column <= SIDE_LENGTH; column++) {
            System.out.print(column + DIVIDING_SPACE);
        }

        System.out.print(SIDE_ITEM_SPACE.substring(0, SIDE_ITEM_SPACE.length() - 2) + "Score");
        System.out.println();

        //printing out actual grid
        for (int row = 0; row < SIDE_LENGTH; row++) {
            printRowWithCells(row);
            //printing out side items
            printSideItemsForRowWithCells(row);


            //printing out row between the rows with cells ("inter-rows")
            if (row < SIDE_LENGTH) {
                //printing out Letter coordinates
                System.out.print("\n" + (char) (66 + row) + DIVIDING_SPACE);
                //printing out horizontal walls below each cell, and middle wall segments (at bottom right
                // corners of cells)
                for (int column = 0; column < SIDE_LENGTH; column++) {
                    printHorizontalWall(row, column);
                    if (column < SIDE_LENGTH - 1 && row < SIDE_LENGTH - 1) {
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
        displayBoard();
        System.out.println("Player 1 move");
        interpretInGameInput(p1);
        if (p1.reachedWinCondition(p1)) {
            gameOver = true;
            p1.incrementScore();
            winner = "Player 1";
            return;
        }

        displayBoard();
        System.out.println("Player 2 move");
        interpretInGameInput(p2);
        if (p2.reachedWinCondition(p2)) {
            gameOver = true;
            p2.incrementScore();
            winner = "Player 2";
            return;
        }
    }

    //EFFECTS : interprets the player input while game is running, and calls the appropriate method
    private void interpretInGameInput(Avatar player) {
        String input = keyboard.nextLine();
        //if the input matches one of the keys to move the player:
        if (input.equals(player.getUpKey()) || input.equals(player.getLeftKey()) || input.equals(player.getDownKey())
                || input.equals(player.getRightKey())) {
            handleMovementInput(player, input);
            //if the input matches the format of placing a wall
        } else if (isWallCommand(input)) {
            handleWallPlacementInput(player, input);
            //if the input is invalid
        } else {
            System.out.println("That is not a valid input");
            interpretInGameInput(player);
        }
    }

    //EFFECTS : Handles player input if it is to place a wall
    private void handleWallPlacementInput(Avatar player, String input) {
        //first needs to check if player has any walls left
        if (player.getWalls() <= 0) {
            System.out.println("You do not have any more walls");
            interpretInGameInput(player);
        } else {
            try {
                wallTool.placeWall(input);
                player.decrementWall();
            } catch (InvalidWallException e) {
                System.out.println("You can not place a wall there");
                interpretInGameInput(player);
            }
        }
    }

    //EFFECTS : Handles player input if it is to move the player
    private void handleMovementInput(Avatar player, String input) {
        try {
            player.move(input);
        } catch (OutOfBoundsException e) {
            System.out.println("You can not move off the board");
            interpretInGameInput(player);
        } catch (WallObstructionException e) {
            System.out.println("Can not move over a wall");
            interpretInGameInput(player);
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
