package ui.gui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Cell;
import model.players.Avatar;
import model.walls.MiddleOfWall;
import model.walls.WallTool;
import ui.Game;
import ui.gui.cell.GuiCell;

import java.util.ArrayList;

public class GameGuiTool {
    public static final String BG_COLOR = "F3CBA3";
    private Pane root = new Pane();
    private Game game;
    protected Avatar p1;
    protected Avatar p2;
    protected ArrayList<MiddleOfWall> wallMiddles;
    protected ArrayList<Cell> board;

    private Rectangle background;// This rectangle will be the background of the board
    private Group cellGroup;// This group will contain all the cells for the board
    private Group cornerGroup;// This group will contain all the corner pieces (MiddleOfWall segments) of the board
    private SidePanel sidePanel;// This group will contain the "side panel" (displayed beside the board)
    private BottomPanel bottomPanel;// This group will contain the "bottom panel" (displayed under the board)

    private InputHandler inputHandler;// This handles keyboard and mouse inputs

    //creates a GuiTool for an active game
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

    //EFFECTS : Creates a GuiTool for a finished game (eg. when reading from a file)
    public GameGuiTool(Game game, ArrayList<MiddleOfWall> wallMiddles) {
        this.game = game;
        this.p1 = game.getP1();
        this.p2 = game.getP2();
        this.wallMiddles = wallMiddles;
        initializeBackground();

        cellGroup = new Group();
        cornerGroup = new Group();
        sidePanel = new SidePanel(this);
        bottomPanel = new BottomPanel(this);
    }

    //EFFECTS : Creates the background for the board
    private void initializeBackground() {
        this.background = new Rectangle(Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH + GuiCell.SHORT_LENGTH,
                Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH + GuiCell.SHORT_LENGTH,
                Color.valueOf(BG_COLOR));
    }

    //EFFECTS : resets the GUI for when there is a new game
    public void reset() {
        this.wallMiddles = game.getWallTool().getWallMiddles();
        this.board = game.getBoard();
        assert (board.size() == Game.SIDE_LENGTH * Game.SIDE_LENGTH);
        assert (wallMiddles.size() == WallTool.WALL_MIDDLES_LENGTH * WallTool.WALL_MIDDLES_LENGTH);

        //resetting bottom panel
        bottomPanel.displayTextLabel(false);

        //adding tiles
        cellGroup.getChildren().clear();
        initializeCellGroup();

        //adding corners
        cornerGroup.getChildren().clear();
        initializeCornerGroup();
    }

    //EFFECTS : Resets the cornerGroup (all the corner pieces/MiddleOfWall segments on the board)
    private void initializeCornerGroup() {
        for (int y = 0; y < WallTool.WALL_MIDDLES_LENGTH; y++) {
            for (int x = 0; x < WallTool.WALL_MIDDLES_LENGTH; x++) {
                cornerGroup.getChildren().add(wallMiddles.get(y * WallTool.WALL_MIDDLES_LENGTH + x).getGuiCorner());
            }
        }
    }

    //EFFECTS : Resets the cellGroup (all the tiles on the board)
    private void initializeCellGroup() {
        for (int y = 0; y < Game.SIDE_LENGTH; y++) {
            for (int x = 0; x < Game.SIDE_LENGTH; x++) {
                GuiCell guiCell = board.get(y * Game.SIDE_LENGTH + x).getGuiCell();
                cellGroup.getChildren().add(guiCell);
            }
        }
    }

    //EFFECTS : returns the pane that contains the display of the game
    public Parent createContent() {
        root = new Pane();
        root.setPrefSize(ui.Menu.PREF_WIDTH,
                ui.Menu.PREF_HEIGHT);
        root.getChildren().addAll(background, cellGroup, cornerGroup, sidePanel, bottomPanel);

        return root;
    }

    //EFFECTS : Displays the game-over screen
    public void displayGameOverScreen() {
        bottomPanel.displayGameOverLabel();
        bottomPanel.displayTextLabel(true);
    }

    //EFFECTS : handles keyboard presses
    public void handleKeyboard(KeyEvent key) {
        if (!game.isGameOver()) {
            inputHandler.handleKeyboard(key);
        }
    }

    //EFFECTS : handles mouse presses
    public void handleMousePressed(MouseEvent click) {
        if (!game.isGameOver()) {
            inputHandler.handleMousePressed(click);
        }
    }

    //EFFECTS : handles mouse drags
    public void handleMouseDragged(MouseEvent drag) {
        if (!game.isGameOver()) {
            inputHandler.handleMouseDragged(drag);
        }
    }

    //EFFECTS : handles mouse releases
    public void handleMouseReleased(MouseEvent release) {
        if (!game.isGameOver()) {
            inputHandler.handleMouseReleased(release);
        }
    }

    //EFFECTS : updates the sidePanel
    public void updateSidePanel() {
        sidePanel.update();
    }

    protected Pane getRoot() {
        return root;
    }

    public Game getGame() {
        return game;
    }

    //EFFECTS : displays "vertical decision message" (ask player to move left or right)
    public void displayVerticalDecisionMessage() {
        bottomPanel.displayVerticalDecisionMessage();
        bottomPanel.displayTextLabel(true);
    }

    //EFFECTS : displays "horizontal decision message" (ask player to up left or down)
    public void displayHorizontalDecisionMessage() {
        bottomPanel.displayHorizontalDecisionMessage();
        bottomPanel.displayTextLabel(true);
    }

    //EFFECTS : Makes the bottom panel display no message
    public void clearBottomPanelDisplay() {
        bottomPanel.displayTextLabel(false);
    }
}
