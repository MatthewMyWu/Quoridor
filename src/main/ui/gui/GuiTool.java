package ui.gui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Cell;
import model.players.Avatar;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import ui.Game;
import ui.gui.cell.GuiCell;

import java.util.ArrayList;

public class GuiTool {
    private Game game;
    protected Avatar p1;
    protected Avatar p2;
    protected ArrayList<MiddleOfWall> wallMiddles;
    protected ArrayList<Cell> board;

    private Group cellGroup;
    private Group cornerGroup;
    private Group lineGroup;
    private SidePanel sidePanel;
    private BottomPanel bottomPanel;

    private InputHandler inputHandler;

    //creates a GUI for the corresponding Game
    public GuiTool(Game game) {
        this.game = game;
        this.p1 = game.getP1();
        this.p2 = game.getP2();

        inputHandler = new InputHandler(this);
        cellGroup = new Group();
        cornerGroup = new Group();
        lineGroup = inputHandler.getLineGroup();
        sidePanel = new SidePanel(this);
        bottomPanel = new BottomPanel(this);
    }

    //resets the gui for when there is a new game
    public void reset() {
        this.wallMiddles = game.getWallTool().getWallMiddles();
        this.board = game.getBoard();
        assert (board.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);
        assert (wallMiddles.size() == WallTool.WALL_MIDDLES_LENGTH * WallTool.WALL_MIDDLES_LENGTH);

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
        Pane root = new Pane();
        root.setPrefSize(WallTool.WALL_MIDDLES_LENGTH * GuiCell.SIDE_LENGTH + 100,
                WallTool.WALL_MIDDLES_LENGTH * GuiCell.SIDE_LENGTH + 50);
        root.getChildren().addAll(cellGroup, cornerGroup, lineGroup, sidePanel, bottomPanel);

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

    public Game getGame() {
        return game;
    }

    public void updateSidePanel() {
        sidePanel.update();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}
