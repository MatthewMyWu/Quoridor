package ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.players.P1;
import model.players.P2;
import model.walls.WallTool;
import ui.Game;
import ui.gui.cell.Corner;

// This class handles keyboard and mouse input for when the game is running
public class InputHandler {
    private GameGuiTool gameGuiTool;// The GameGuiTool that this is coupled with
    private Line line;// The "preview" for where a wall will be placed, when the player drags their mouse
    private Game game;// The game that this inputHandler is responsible for

    //used for handling mouse events (wall placement)
    private double initialX;// The initial x coordinate for a wall placement
    private double initialY;// The initial y coordinate for a wall placement
    private double endX;// The end x coordinate for a wall placement
    private double endY;// The end y coordinate for a wall placement
    private static final double WALL_LENIENCY = 20.0;//number of pixels around a corner that a valid wall can be drawn

    public InputHandler(GameGuiTool gameGuiTool) {
        this.gameGuiTool = gameGuiTool;
        this.game = gameGuiTool.getGame();
    }

    //MODIFIES: game
    //EFFECTS : handles keyboard events
    public void handleKeyboard(KeyEvent key) {
        if (key.getCode().equals(KeyCode.W)) {
            game.update(P1.UP_KEY);
        } else if (key.getCode() == KeyCode.A) {
            game.update(P1.LEFT_KEY);
        } else if (key.getCode() == KeyCode.S) {
            game.update(P1.DOWN_KEY);
        } else if (key.getCode() == KeyCode.D) {
            game.update(P1.RIGHT_KEY);
        } else if (key.getCode() == KeyCode.UP) {
            game.update(P2.UP_KEY);
        } else if (key.getCode() == KeyCode.LEFT) {
            game.update(P2.LEFT_KEY);
        } else if (key.getCode() == KeyCode.DOWN) {
            game.update(P2.DOWN_KEY);
        } else if (key.getCode() == KeyCode.RIGHT) {
            game.update(P2.RIGHT_KEY);
        }
        gameGuiTool.updateSidePanel();
    }

    //MODIFIES: game
    //EFFECTS : handles mouse presses
    public void handleMousePressed(MouseEvent press) {
        initialX = press.getSceneX();
        initialY = press.getSceneY();
        line = new Line(initialX, initialY, initialX, initialY);
        line.setStroke(Color.BROWN);
        line.setVisible(true);
        line.setStrokeWidth(5);
        line.setOpacity(30.0);
        gameGuiTool.getRoot().getChildren().add(line);
    }

    //MODIFIES: game
    //EFFECTS : handles mouse drags
    public void handleMouseDragged(MouseEvent drag) {
        line.setEndX(drag.getSceneX());
        line.setEndY(drag.getSceneY());
    }

    //MODIFIES: game
    //EFFECTS : handles mouse releases
    public void handleMouseReleased(MouseEvent release) {
        endX = release.getSceneX();
        endY = release.getSceneY();
        line.setVisible(false);
        gameGuiTool.getRoot().getChildren().remove(line);
        updateWalls();
    }

    //EFFECTS : After player finished dragging mouse, will interpret the line that was drawn and convert it to
    //          a form that WallTool can understand, then pass it to WallTool
    private void updateWalls() {
        //keeps track of the corner coordinates of the wall
        int initialCornerX = getCornerNumber(initialX);
        int initialCornerY = getCornerNumber(initialY);
        int endCornerX = getCornerNumber(endX);
        int endCornerY = getCornerNumber(endY);

        //creating input String for WallTool to handle
        String input = initialCornerX + "," + initialCornerY + "," + endCornerX + "," + endCornerY;

        //passing input to game
        game.update(input);
        gameGuiTool.updateSidePanel();
    }

    //EFFECTS : Returns the row/column of the corner that this coordinate is closest to, or -1 if not close to a corner
    private int getCornerNumber(double coordinate) {
        for (int a = 0; a < WallTool.WALL_MIDDLES_LENGTH; a++) {
            if (coordinate > Corner.calculateCoord(a) - WALL_LENIENCY
                    && coordinate < Corner.calculateCoord(a) + WALL_LENIENCY) {
                return a;
            }
        }

        return -1;
    }

    public Game getGame() {
        return game;
    }
}
