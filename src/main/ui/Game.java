package ui;

import exceptions.*;
import model.*;
import model.pathfinding.Pathfinder;
import model.persistence.MatchHistory;
import model.players.Avatar;
import model.players.P1;
import model.players.P2;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import ui.gui.GameGuiTool;
import ui.gui.cell.HorizontalWall;
import ui.gui.cell.VerticalWall;

import java.util.ArrayList;

//Contains methods and information necessary for the running of the main game
public class Game {
    public static final int SIDE_LENGTH = 9;// The number of cells on one side of the board

    private WallTool wallTool;// Tool used for placing walls
    private GameGuiTool gameGuiTool;// Tool used for GUI
    private MatchHistory matchHistory;//TODO Keeps track of MatchHistory
    private Avatar p1;
    private Avatar p2;
    private Pathfinder p1Pathfinder;// Pathfinder for p1
    private Pathfinder p2Pathfinder;// Pathfinder for p2
    private boolean isP1Turn = true;// true when it is p1 turn, false if p2 turn
    private boolean gameOver = false;// true if game is over
    private int winner; //Is the player that won the game (eg. 1 or 2)
    private Boolean limboState;
    private boolean forfeit = false; //The player that sets this to true will have surrendered
    private ArrayList<Cell> board;


    //EFFECTS : creates a new game (resets all elements of game: board, players and walls).
    //          This constructor used to play game
    public Game() {
        limboState = false;

        //resetting players
        p1 = new P1(this);
        p2 = new P2(this);

        //creating guiTool
        gameGuiTool = new GameGuiTool(this);

        //(re)setting board, walls, and displayTool
        restart();
    }

    //EFFECTS : A constructor for a finished game (used when reading matches from data)
    public Game(Avatar p1, Avatar p2, int winner, ArrayList<MiddleOfWall> wallMiddles, ArrayList<Cell> board) {
        assert (wallMiddles.size() == WallTool.WALL_MIDDLES_LENGTH * WallTool.WALL_MIDDLES_LENGTH);
        assert (wallMiddles.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);

        this.p1 = p1;
        this.p2 = p2;
        this.winner = winner;
        this.board = board;
        this.gameGuiTool = new GameGuiTool(this, wallMiddles);
    }

    //MODIFIES: this
    //EFFECTS : restarts the game, but does not reset the scores of each player
    public void restart() {
        //resetting variables
        gameOver = false;
        forfeit = false;
        winner = 0;
        isP1Turn = true;

        //resetting the board and elements dependent on board (Walltool, pathfinders, players)
        resetBoard();

        //resetting players
        p1.initialize();
        p2.initialize();

        //resetting displayTool
        gameGuiTool.reset();
        gameGuiTool.updateSidePanel();
    }

    //MODIFIES: this
    //EFFECTS : resets the board
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
            gameGuiTool.updateSidePanel();
            endGame();
        }
    }

    //EFFECTS : Ends the game
    private void endGame() {
        //saveToMatchHistory();
        gameGuiTool.displayGameOverScreen();
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

    //EFFECTS : interprets the player input and calls the appropriate method
    private void interpretInGameInput(Avatar player, String input) {
        try {
            //if the input matches one of the keys to move the player:
            if (input.equals(player.getUpKey()) || input.equals(player.getLeftKey())
                    || input.equals(player.getDownKey()) || input.equals(player.getRightKey())) {
                handleMovementInput(player, input);
                //if the input matches the format of placing a wall
            } else if (isWallCommand(input) && !limboState) {
                handleWallPlacementInput(player, input);
                //if input is to forfeit
            } else if (input.equals("/ff") || input.equals("/FF")) {
                forfeit = true;
                //if input is invalid
            } else {
                throw new BadInputException();
            }

            //switching the players turn (can only be reached if no exception is thrown, and not in limboState)
            if (!limboState) {
                isP1Turn = (isP1Turn == false);
            }
        } catch (Exception e) {
            handleInputExceptions(e);
        }
    }

    private void handleInputExceptions(Exception e) {
        Object exception = e.getClass();
        if (exception.equals(new InvalidWallException().getClass())) {
            System.out.println("You can not place a wall there");
        } else if (exception.equals(new NoMoreWallsException().getClass())) {
            System.out.println("You have no more walls");
        }  else if (exception.equals(new OutOfBoundsException().getClass())) {
            System.out.println("You can not move off the board");
        } else if (exception.equals(new WallObstructionException().getClass())) {
            System.out.println("Can not move over a wall");
        } else if (exception.equals(new BadInputException().getClass())) {
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
        return (input.length() == 7)
                //checking first coordinate is valid (x1)
                && (int) input.charAt(0) >= 48 && (int) input.charAt(0) <= 48 + SIDE_LENGTH
                //checking second coordinate is valid (y2)
                && (int) input.charAt(2) >= 48 && (int) input.charAt(2) <= 48 + SIDE_LENGTH
                //checking fourth coordinate is valid (x2)
                && (int) input.charAt(4) >= 48 && (int) input.charAt(4) <= 48 + SIDE_LENGTH
                //checking fifth character is a proper number coordinate
                && (int) input.charAt(6) >= 48 && (int) input.charAt(6) <= 48 + SIDE_LENGTH
                //checking proper placements of commas
                && input.charAt(1) == ',' && (input.charAt(3) == ',' && (input.charAt(5) == ','));

    }

    public void displayVerticalDecisionMessage() {
        gameGuiTool.displayVerticalDecisionMessage();
    }

    public void displayHorizontalDecisionMessage() {
        gameGuiTool.displayHorizontalDecisionMessage();
    }

    //MODIFIES: mathHistory
    //EFFECTS : Saves this game to match history
    private void saveToMatchHistory() {
        matchHistory.saveNewMatch(this);
        //TODO
    }


    /////////////////////////////////////getters and setters///////////////////////////////////////
    public Avatar getP1() {
        return p1;
    }

    public Avatar getP2() {
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

    public Boolean getLimboState() {
        return limboState;
    }

    public GameGuiTool getGameGuiTool() {
        return gameGuiTool;
    }

    //EFFECTS : setter for limbo state, but if not in limbo state, also clears the bottom pannel
    public void setLimboState(Boolean limboState) {
        this.limboState = limboState;
        if (!limboState) {
            gameGuiTool.clearBottomPanelDisplay();
        }
    }
}
