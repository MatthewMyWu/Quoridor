package ui;

import exceptions.InvalidWallException;
import exceptions.OutOfBoundsException;
import exceptions.WallObstructionException;
import model.*;
import model.pathfinding.Pathfinder;
import model.persistence.MatchHistory;
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
    private Scanner keyboard = new Scanner(System.in);
    private WallTool wallTool = new WallTool();
    private DisplayTool displayTool;
    private static Avatar p1 = new P1();
    private static Avatar p2 = new P2();
    public static Pathfinder p1Pathfinder = new Pathfinder(p1);
    public static Pathfinder p2Pathfinder = new Pathfinder(p2);
    private boolean gameOver = false;
    private int winner; //Is the player that won the game (eg. 1 or 2)
    private boolean forfeit = false; //The player that sets this to true will have surrendered
    public static ArrayList<Cell> board;


    //MODIFIES: this
    //EFFECTS : creates a new game (resets all elements of game: board, players and walls)
    public Game() {
        //resetting players
        p1 = new P1();
        p2 = new P2();

        //(re)setting board, walls, and displayTool
        restart();
    }


    //EFFECTS : starts the game
    public void play() {
        while (!gameOver) {
            update();
        }
        displayBoard();
        saveToMatchHistory();
        displayGameOverScreen();
    }

    private void saveToMatchHistory() {
        //TODO
        MatchHistory.saveNewMatch(p1, p2, winner, WallTool.getWallMiddles(), board);
    }

    //EFFECTS : displays the board to the console
    private void displayBoard() {
        displayTool.displayBoard();
    }

    //EFFECTS : Displays the game over screen
    private void displayGameOverScreen() {
        System.out.println("Player " + winner + " wins!");
        System.out.println("1. PLAY AGAIN");
        System.out.println("2. MAIN MENU");
        interpretGameOverInput();
    }

    //EFFECTS : Interprets player input for the game over screen
    private void interpretGameOverInput() {
        String input = keyboard.nextLine();
        if (input.equals("1") || input.equals("1.") || input.equals("PLAY AGAIN")) {
            System.out.println("working");
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
        //resetting variables
        gameOver = false;
        forfeit = false;
        winner = 0;

        //resetting the board
        board = new ArrayList<>();
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int column = 0; column < SIDE_LENGTH; column++) {
                board.add(new Cell());
            }
        }

        //resetting players
        p1.initialize();
        p2.initialize();

        //resetting walls
        wallTool = new WallTool();

        //resetting displayTool
        displayTool = new DisplayTool(p1, p2, WallTool.getWallMiddles(), board);
    }

    //EFFECTS : plays one "move" of the game (gives player 1 and 2 a turn)
    public void update() {
        displayBoard();
        System.out.println("Player 1 move");
        interpretInGameInput(p1);
        if (p1.reachedWinCondition(p1)) {
            p1Win();
            return;
        } else if (forfeit) {
            p2Win();
            return;
        }

        displayBoard();
        System.out.println("Player 2 move");
        interpretInGameInput(p2);
        if (p2.reachedWinCondition(p2)) {
            p2Win();
            return;
        } else if (forfeit) {
            p1Win();
            return;
        }
    }

    //MODIFIES: this (gameOver and winner) and P2 (increments score)
    //EFFECTS : increments player 2 score if they win
    private void p2Win() {
        gameOver = true;
        p2.incrementScore();
        winner = 2;
    }

    //MODIFIES: this (gameOver and winner) and P1 (increments score)
    //EFFECTS : increments player 1 score if they win
    private void p1Win() {
        gameOver = true;
        p1.incrementScore();
        winner = 1;
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
        } else if (input.equals("/ff") || input.equals("/FF")) {
            forfeit = true;
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
