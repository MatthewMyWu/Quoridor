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
import model.walls.WallTool;
import ui.Game;
import ui.gui.cell.Corner;
import ui.gui.cell.GuiCell;

public class GuiTool extends DisplayTool {
    private Game game;
    private Group cellGroup;
    private Group cornerGroup;
    private Group lineGroup;
    private SidePanel sidePanel;

    private InGameInputHandler inputHandler;

    //creates a GUI for the corresponding Game
    public GuiTool(Game game) {
        super(game.getP1(), game.getP2(), game.getWallTool().getWallMiddles(), game.getBoard());
        this.game = game;
        inputHandler = new InGameInputHandler(this);
        cellGroup = new Group();
        cornerGroup = new Group();
        lineGroup = inputHandler.getLineGroup();
        sidePanel = new SidePanel(this);

        //adding tiles
        initializeCellGroup();

        //adding corners
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
                WallTool.WALL_MIDDLES_LENGTH * GuiCell.SIDE_LENGTH);
        root.getChildren().addAll(cellGroup, cornerGroup, lineGroup, sidePanel);

        return root;
    }

    public void handleKeyboard(KeyEvent key) {
        inputHandler.handleKeyboard(key);
    }

    public void handleMousePressed(MouseEvent click) {
        inputHandler.handleMousePressed(click);
    }

    public void handleMouseDragged(MouseEvent drag) {
        inputHandler.handleMouseDragged(drag);
    }

    public void handleMouseReleased(MouseEvent release) {
        inputHandler.handleMouseReleased(release);
    }

    public Game getGame() {
        return game;
    }

    public void updateSidePanel() {
        sidePanel.update();
    }

    public InGameInputHandler getInputHandler() {
        return inputHandler;
    }
}
