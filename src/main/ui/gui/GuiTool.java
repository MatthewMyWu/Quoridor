package ui.gui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.DisplayTool;
import model.players.P1;
import model.players.P2;
import ui.Game;
import ui.gui.cell.GuiCell;

public class GuiTool extends DisplayTool {
    private Group cellGroup;
    private Group lineGroup;
    private Line line;
    private Game game;

    //used for handling mouse events (wall placement)
    private double endX;
    private double endY;
    private double initialX;
    private double initialY;
    private static final double WALL_LENIENCY = 10.0;//number of pixels around a corner that a valid wall can be drawn

    //creates a GUI for the corresponding Game
    public GuiTool(Game game) {
        super(game.getP1(), game.getP2(), game.getWallTool().getWallMiddles(), game.getBoard());
        this.game = game;
        cellGroup = new Group();
        lineGroup = new Group();

        for (int y = 0; y < Game.SIDE_LENGTH; y++) {
            for (int x = 0; x < Game.SIDE_LENGTH; x++) {
                GuiCell guiCell = board.get(y * Game.SIDE_LENGTH + x).getGuiCell();
                cellGroup.getChildren().add(guiCell);
            }
        }
    }

    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH,
                Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH);
        root.getChildren().addAll(cellGroup, lineGroup);

        return root;
    }

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
    }

    public void handleMousePressed(MouseEvent press) {
        initialX = press.getSceneX();
        initialY = press.getSceneY();
        line = new Line(initialX, initialY, initialX, initialY);
        line.setStroke(Color.BROWN);
        line.setVisible(true);
        line.setStrokeWidth(5);
        lineGroup.getChildren().add(line);
    }

    public void handleMouseDragged(MouseEvent drag) {
        line.setEndX(drag.getSceneX());
        line.setEndY(drag.getSceneY());
    }

    public void handleMouseReleased(MouseEvent release) {
        endX = release.getSceneX();
        endY = release.getSceneY();
        lineGroup.getChildren().remove(line);
        addWall();
    }

    //EFFECTS : After player finished dragging mouse, will interpret the line that was drawn and convert it to
    //          a form that WallTool can understand, then pass it to WallTool
    private void addWall() {
        //keeps track of the corner coordinates of the wall
        int initialCornerX;
        int initialCornerY;
        int endCornerX;
        int endCornerY;

        //checking to see if coordinates are in acceptable range of a corner
        checkWallCoordinates();

        //x and y coordinates of initial and latter ends of wall respectively. Indexing starts at 0 for all coordinates
        int x1 = (int) input.charAt(1) - 48;
        int y1 = (int) input.charAt(0) - 65;
        int x2 = (int) input.charAt(4) - 48;
        int y2 = (int) input.charAt(3) - 65;
    }

    private boolean checkWallCoordinates() {
        int initialCornerX;
        int initialCornerY;
        int endCornerX;
        int endCornerY;

        //checking initialX
        for (int x = 0; x < Game.SIDE_LENGTH; x++) {
            if (initialX > GuiCell.getCornerX(x) - WALL_LENIENCY && initialX < GuiCell.getCornerX(x) + WALL_LENIENCY) {
                initialCornerX = x;
                break;
            }
        }

        //checking initialY
        for (int y = 0; y < Game.SIDE_LENGTH; y++) {
            if (initialY > GuiCell.getCornerY(y) - WALL_LENIENCY && initialY < GuiCell.getCornerY(y) + WALL_LENIENCY) {
                initialCornerY = y;
                break;
            }
        }

        //checking endX
        for (int x = 0; x < Game.SIDE_LENGTH; x++) {
            if (endX > GuiCell.getCornerX(x) - WALL_LENIENCY && endX < GuiCell.getCornerX(x) + WALL_LENIENCY) {
                endCornerX = x;
                break;
            }
        }

        //checking endY
        for (int y = 0; y < Game.SIDE_LENGTH; y++) {
            if (endY > GuiCell.getCornerY(y) - WALL_LENIENCY && endY < GuiCell.getCornerY(y) + WALL_LENIENCY) {
                endCornerY = y;
                break;
            }
        }
    }
}
