package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.persistence.MatchHistory;
import model.walls.WallTool;
import ui.gui.GameGuiTool;
import ui.gui.cell.GuiCell;

//This class contains code on how the Main Menu functions.
public class Menu {
    public static final int PREF_WIDTH = WallTool.WALL_MIDDLES_LENGTH * GuiCell.SIDE_LENGTH + 100;// width of main menu
    public static final int PREF_HEIGHT = WallTool.WALL_MIDDLES_LENGTH * GuiCell.SIDE_LENGTH + 50;// height of main menu

    private static final double playButtonWidth = 250.0;
    private static final double matchHistoryButtonWidth = 250;

    private static Main main;// The main program this is coupled with
    private MatchHistory matchHistory;
    private Game game;
    private GameGuiTool gameGuiTool;
    private Scene gameScene;// The scene that will be displayed if the game is running
    private Scene matchHistoryScene;// The scene that will be displayed when looking at match history
    private static Scene menuScene;// The scene that will be displayed for the main menu
    private static Scene activeScene;// The active scene (currently being displayed)

    public Menu(Main main) {
        this.main = main;
        //matchHistory = MatchHistory.getInstance();
        initializeMenuScene();
        activeScene = menuScene;
    }

    //MODIFIES: this and main
    //EFFECTS : displays the main menu
    public static void returnToMainMenu() {
        activeScene = menuScene;
        main.updateScene();
    }

    //MODIFIES: this and main
    //EFFECTS : displays a new game
    private Scene newGameScene() {
        game = new Game();
        gameGuiTool = game.getGameGuiTool();
        gameScene = new Scene(gameGuiTool.createContent());

        //adding listeners
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            gameGuiTool.handleKeyboard(key);
        });
        gameScene.addEventHandler(MouseEvent.MOUSE_PRESSED, click -> {
            gameGuiTool.handleMousePressed(click);
        });
        gameScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, drag -> {
            gameGuiTool.handleMouseDragged(drag);
        });
        gameScene.addEventHandler(MouseEvent.MOUSE_RELEASED, release -> {
            gameGuiTool.handleMouseReleased(release);
        });

        return gameScene;
    }

    //MODIFIES: this
    //EFFECTS : initializes the Main Menu screen
    private void initializeMenuScene() {
        Pane root = new AnchorPane();
        Rectangle background = new Rectangle(PREF_WIDTH, PREF_HEIGHT, Color.valueOf("#e88d15"));
        Label title = new Label("Quorridor");
        Button playButton = new Button("Play");
        Button matchHistoryButton = new Button("Match History");
        root.setPrefSize(PREF_WIDTH, PREF_HEIGHT);

        initializeTitleLabel(title);

        initializePlayButton(playButton);

        initializeMatchHistoryButton(matchHistoryButton);

        root.getChildren().addAll(background, title, playButton, matchHistoryButton);
        menuScene = new Scene(root);
    }

    //MODIFIES: this
    //EFFECTS : initializes the Match History Button
    private void initializeMatchHistoryButton(Button matchHistoryButton) {
        matchHistoryButton.setFont(new Font("Impact", 30));
        matchHistoryButton.setTextFill(Color.valueOf("#823b0e"));
        matchHistoryButton.setPrefWidth(matchHistoryButtonWidth);
        AnchorPane.setLeftAnchor(matchHistoryButton, Double.valueOf((PREF_WIDTH - matchHistoryButtonWidth) / 2));
        AnchorPane.setTopAnchor(matchHistoryButton, 300.0);

        matchHistoryButton.setOnAction(event -> {
            //TODO activeScene = matchHistoryScene();
            main.updateScene();
        });
    }

    //EFFECTS : Displays the match history
    private Scene matchHistoryScene() {
        matchHistoryScene = new Scene(matchHistory.createContent());
        return matchHistoryScene;
    }

    //MODIFIES: this
    //EFFECTS : nitializes the Play Button
    private void initializePlayButton(Button playButton) {
        playButton.setFont(new Font("Impact", 40));
        playButton.setTextFill(Color.valueOf("#823b0e"));
        playButton.setPrefWidth(playButtonWidth);
        AnchorPane.setLeftAnchor(playButton, Double.valueOf((PREF_WIDTH - playButtonWidth) / 2));
        AnchorPane.setTopAnchor(playButton, 200.0);

        playButton.setOnAction(event -> {
            activeScene = newGameScene();
            main.updateScene();
        });
    }

    //MODIFIES: this
    //EFFECTS : initializes the title
    private void initializeTitleLabel(Label title) {
        title.setFont(new Font("Impact", 100));
        title.setTextFill(Color.valueOf("#823b0e"));
        AnchorPane.setLeftAnchor(title, 0.0);
        AnchorPane.setRightAnchor(title, 0.0);
        AnchorPane.setTopAnchor(title, 30.0);
        title.setAlignment(Pos.CENTER);
    }

    public Scene getActiveScene() {
        return activeScene;
    }
}