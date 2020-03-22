package ui.gui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Cell;
import model.players.Avatar;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import org.w3c.dom.css.Rect;
import ui.Game;
import ui.gui.cell.GuiCell;

import java.awt.*;
import java.util.ArrayList;

public class GameGuiTool {
    public static final String BG_COLOR = "F3CBA3";
    Pane root = new Pane();
    private Game game;
    protected Avatar p1;
    protected Avatar p2;
    protected ArrayList<MiddleOfWall> wallMiddles;
    protected ArrayList<Cell> board;

    private Rectangle background;
    private Group cellGroup;
    private Group cornerGroup;
    private SidePanel sidePanel;
    private BottomPanel bottomPanel;

    private InputHandler inputHandler;

    //creates a GUI for the corresponding Game
    public GameGuiTool(Game game) {
        this.game = game;
        this.p1 = game.getP1();
        this.p2 = game.getP2();
        initializeBackground();

        inputHandler = new InputHandler(this);
        cellGroup = new Group();
        cornerGroup = new Group();
        sidePanel = new SidePanel(this);
        bottomPanel = new BottomPanel(this);
    }

    private void initializeBackground() {
        this.background = new Rectangle(Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH + GuiCell.SHORT_LENGTH,
                Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH + GuiCell.SHORT_LENGTH,
                Color.valueOf(BG_COLOR));
    }

    //resets the gui for when there is a new game
    public void reset() {
        this.wallMiddles = game.getWallTool().getWallMiddles();
        this.board = game.getBoard();
        assert (board.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);
        assert (wallMiddles.size() == WallTool.WALL_MIDDLES_LENGTH * WallTool.WALL_MIDDLES_LENGTH);

        //resetting bottom panel
        bottomPanel.displayGameOverLabel(false);

        //adding tiles
        cellGroup.getChildren().clear();
        initializeCellGroup();

        //adding corners
        cornerGroup.getChildren().clear();
        initializeCornerGroup();
    }

    private void initializeCornerGroup() {
        for (int y = 0; y < WallTool.WALL_MIDDLES_LENGTH; y++) {
            for (int x = 0; x < WallTool.WALL_MIDDLES_LENGTH; x++) {
                cornerGroup.getChildren().add(wallMiddles.get(y * WallTool.WALL_MIDDLES_LENGTH + x).getGuiCorner());
            }
        }
    }

    private void initializeCellGroup() {
        for (int y = 0; y < Game.SIDE_LENGTH; y++) {
            for (int x = 0; x < Game.SIDE_LENGTH; x++) {
                GuiCell guiCell = board.get(y * Game.SIDE_LENGTH + x).getGuiCell();
                cellGroup.getChildren().add(guiCell);
            }
        }
    }

    public Parent createContent() {
        root = new Pane();
        root.setPrefSize(ui.Menu.PREF_WIDTH,
                ui.Menu.PREF_HEIGHT);
        root.getChildren().addAll(background, cellGroup, cornerGroup, sidePanel, bottomPanel);

        return root;
    }

    public void displayGameOverScreen() {
        bottomPanel.displayGameOverLabel(true);
    }

    public void handleKeyboard(KeyEvent key) {
        if (!game.isGameOver()) {
            inputHandler.handleKeyboard(key);
        }
    }

    public void handleMousePressed(MouseEvent click) {
        if (!game.isGameOver()) {
            inputHandler.handleMousePressed(click);
        }
    }

    public void handleMouseDragged(MouseEvent drag) {
        if (!game.isGameOver()) {
            inputHandler.handleMouseDragged(drag);
        }
    }

    public void handleMouseReleased(MouseEvent release) {
        if (!game.isGameOver()) {
            inputHandler.handleMouseReleased(release);
        }
    }

    protected Pane getRoot() {
        return root;
    }

    public Game getGame() {
        return game;
    }

    public void updateSidePanel() {
        sidePanel.update();
    }
}
