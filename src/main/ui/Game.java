package ui;

import exceptions.*;
import model.*;
import model.pathfinding.Pathfinder;
import model.persistence.MatchHistory;
import model.players.Avatar;
import model.players.P1;
import model.players.P2;
import model.walls.WallTool;
import ui.gui.GuiTool;
import ui.gui.cell.HorizontalWall;
import ui.gui.cell.VerticalWall;

import java.util.Scanner;

import java.util.ArrayList;

/*This class handles user input, and contains all the methods for running the game*/

public class Game {
    public static final int SIDE_LENGTH = 9;
    private Scanner keyboard = new Scanner(System.in);

    private WallTool wallTool;
    public GuiTool guiTool;//TODO
    private MatchHistory matchHistory;
    private static Avatar p1;
    private static Avatar p2;
    private Pathfinder p1Pathfinder;
    private Pathfinder p2Pathfinder;
    private boolean isP1Turn = true;//true when it is p1 turn, false if p2 turn
    private boolean gameOver = false;
    private int winner; //Is the player that won the game (eg. 1 or 2)
    private boolean forfeit = false; //The player that sets this to true will have surrendered
    public static ArrayList<Cell> board;

    //MODIFIES: this
    //EFFECTS : creates a new game (resets all elements of game: board, players and walls)
    public Game() {
        //resetting players
        p1 = new P1(generateBoard());
        p2 = new P2(generateBoard());

        //creating guiTool
        guiTool = new GuiTool(this);

        //(re)setting board, walls, and displayTool
        restart();
    }

    private void saveToMatchHistory() {
        matchHistory.saveNewMatch(p1, p2, winner, WallTool.getWallMiddles(), board);
    }

    //EFFECTS : Displays the game over screen
    private void displayGameOverScreen() {
        guiTool.displayGameOverScreen();
        restart();
    }

    //EFFECTS : Interprets player input for the game over screen
    public void interpretBottomPanelInput(String input) {
        if (input.equals("1") || input.equals("1.") || input.equals("PLAY AGAIN")) {
            restart();
        } else if (input.equals("2") || input.equals("2.") || input.equals("MAIN MENU")) {
            //return;
        } else {
            System.out.println("That is not a valid input");
            interpretBottomPanelInput(input);
        }
    }

    //MODIFIES: this
    //EFFECTS : restarts the game, but does not reset the scores of each player
    private void restart() {
        //resetting variables
        gameOver = false;
        forfeit = false;
        winner = 0;

        //resetting the board and elements dependent on board (Walltool, pathfinders, players)
        resetBoard();

        //resetting players
        p1.initialize();
        p2.initialize();

        //resetting displayTool
        guiTool.reset(this);
    }

    private void resetBoard() {
        board = generateBoard();

        p1.setBoard(board);
        p2.setBoard(board);
        p1Pathfinder = new Pathfinder(p1, board);
        p2Pathfinder = new Pathfinder(p2, board);
        wallTool = new WallTool(p1Pathfinder, p2Pathfinder, board);

    }

    //EFFECTS : Returns a "blank 'board (no walls)
    public static ArrayList<Cell> generateBoard() {
        ArrayList<Cell> board = new ArrayList<>();

        //resetting tiles
        for (int y = 0; y < SIDE_LENGTH; y++) {
            for (int x = 0; x < SIDE_LENGTH; x++) {
                board.add(new Cell(x, y));
            }
        }

        //resetting horizontal walls
        for (int y = 1; y < SIDE_LENGTH; y++) {
            for (int x = 0; x < SIDE_LENGTH; x++) {
                HorizontalWall horizontalWall = new HorizontalWall();

                board.get((y - 1) * SIDE_LENGTH + x).setLowerGuiWall(horizontalWall);
                board.get(y * SIDE_LENGTH + x).setUpperGuiWall(horizontalWall);
            }
        }

        //resetting vertical walls
        for (int y = 0; y < SIDE_LENGTH; y++) {
            for (int x = 1; x < SIDE_LENGTH; x++) {
                VerticalWall verticalWall = new VerticalWall(x, y);

                board.get(y * SIDE_LENGTH + x - 1).setRightGuiWall(verticalWall);
                board.get(y * SIDE_LENGTH + x).setLeftGuiWall(verticalWall);
            }
        }

        return board;
    }

    //EFFECTS : plays one "move" of the game (gives player 1 and 2 a turn)
    public void update(String input) {
        if (isP1Turn) {
            interpretInGameInput(p1, input);
            if (p1.reachedWinCondition(p1)) {
                p1Win();
            } else if (forfeit) {
                p2Win();
            }
        } else {
            interpretInGameInput(p2, input);
            if (p2.reachedWinCondition(p2)) {
                p2Win();
            } else if (forfeit) {
                p1Win();
            }
        }

        if (gameOver) {
            System.out.println("p1: " + p1.getScore());
            System.out.println("p2: " + p2.getScore());
            guiTool.updateSidePanel();
            endGame();
        }
    }

    private void endGame() {
        //saveToMatchHistory();
        displayGameOverScreen();
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
    //TODO
    private void interpretInGameInput(Avatar player, String input) {
        try {
            //if the input matches one of the keys to move the player:
            if (input.equals(player.getUpKey()) || input.equals(player.getLeftKey())
                    || input.equals(player.getDownKey()) || input.equals(player.getRightKey())) {
                handleMovementInput(player, input);
                //if the input matches the format of placing a wall
            } else if (isWallCommand(input)) {
                handleWallPlacementInput(player, input);
                //if input is to forfeit
            } else if (input.equals("/ff") || input.equals("/FF")) {
                forfeit = true;
                //if input is invalid
            } else {
                throw new BadInputException();
            }

            //switching the players turn (can only be reached if no exception is thrown)
            isP1Turn = (isP1Turn == false);

        } catch (InvalidWallException e) {
            System.out.println("You can not place a wall there");
        } catch (NoMoreWallsException e) {
            System.out.println("You have no more walls");
        }  catch (OutOfBoundsException e) {
            System.out.println("You can not move off the board");
        } catch (WallObstructionException e) {
            System.out.println("Can not move over a wall");
        } catch (BadInputException e) {
            System.out.println("That is not a valid input");
        }
    }

    //EFFECTS : Handles player input if it is to place a wall
    private void handleWallPlacementInput(Avatar player, String input) throws InvalidWallException,
            NoMoreWallsException {
        //first needs to check if player has any walls left
        if (player.getWalls() <= 0) {
            throw new NoMoreWallsException();
        } else {
            wallTool.placeWall(input);
            player.decrementWall();
        }
    }

    //EFFECTS : Handles player input if it is to move the player
    private void handleMovementInput(Avatar player, String input) throws WallObstructionException,
            OutOfBoundsException {
        player.move(input);
    }

    //EFFECTS : determines if input is in the proper format for a command to place a wall (x1,y1,x2,y2)
    protected boolean isWallCommand(String input) {
        //checking proper placements of commas
        assert (input.charAt(1) == ',');
        assert (input.charAt(3) == ',');
        assert (input.charAt(5) == ',');

        return (input.length() == 7)
                //checking first coordinate is valid (x1)
                && (int) input.charAt(0) >= 48 && (int) input.charAt(0) <= 48 + SIDE_LENGTH
                //checking second coordinate is valid (y2)
                && (int) input.charAt(2) >= 48 && (int) input.charAt(2) <= 48 + SIDE_LENGTH
                //checking fourth coordinate is valid (x2)
                && (int) input.charAt(4) >= 48 && (int) input.charAt(4) <= 48 + SIDE_LENGTH
                //checking fifth character is a proper number coordinate
                && (int) input.charAt(6) >= 48 && (int) input.charAt(6) <= 48 + SIDE_LENGTH;

    }



    //getters
    public static Avatar getP1() {
        return p1;
    }

    public static Avatar getP2() {
        return p2;
    }

    public WallTool getWallTool() {
        return wallTool;
    }

    public ArrayList<Cell> getBoard() {
        return board;
    }

    public Pathfinder getP1Pathfinder() {
        return p1Pathfinder;
    }

    public Pathfinder getP2Pathfinder() {
        return p2Pathfinder;
    }

    public boolean isP1Turn() {
        return isP1Turn;
    }

    //these setters used mainly for testing
    public void setP1Pathfinder(Pathfinder p1Pathfinder) {
        this.p1Pathfinder = p1Pathfinder;
    }

    public void setP2Pathfinder(Pathfinder p2Pathfinder) {
        this.p2Pathfinder = p2Pathfinder;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getWinner() {
        return winner;
    }
}
