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
import model.walls.WallTool;
import ui.gui.GameGuiTool;
import ui.gui.cell.GuiCell;

public class Menu {
    public static final int PREF_WIDTH = WallTool.WALL_MIDDLES_LENGTH * GuiCell.SIDE_LENGTH + 100;
    public static final int PREF_HEIGHT = WallTool.WALL_MIDDLES_LENGTH * GuiCell.SIDE_LENGTH + 50;

    private static final double playButtonWidth = 250.0;
    private static final double matchHistoryButtonWidth = 250;

    private static Main main;
    private Game game;
    private GameGuiTool gameGuiTool;
    private Scene gameScene;
    private static Scene menuScene;
    private static Scene activeScene;
    //public MatchHistory matchHistory = new MatchHistory();

    public Menu(Main main) {
        this.main = main;
        initializeMenuScene();
        activeScene = menuScene;
    }

    public static void returnToMainMenu() {
        activeScene = menuScene;
        main.updateScene();
    }

    private Scene newGameScene() {
        game = new Game();
        gameGuiTool = game.gameGuiTool;
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

    private void initializeMatchHistoryButton(Button matchHistoryButton) {
        matchHistoryButton.setFont(new Font("Impact", 30));
        matchHistoryButton.setTextFill(Color.valueOf("#823b0e"));
        matchHistoryButton.setPrefWidth(matchHistoryButtonWidth);
        AnchorPane.setLeftAnchor(matchHistoryButton, Double.valueOf((PREF_WIDTH - matchHistoryButtonWidth) / 2));
        AnchorPane.setTopAnchor(matchHistoryButton, 300.0);
    }

    private void initializePlayButton(Button playButton) {
        playButton.setFont(new Font("Impact", 40));
        playButton.setTextFill(Color.valueOf("#823b0e"));
        playButton.setPrefWidth(playButtonWidth);
        AnchorPane.setLeftAnchor(playButton, Double.valueOf((PREF_WIDTH - playButtonWidth) / 2));
        AnchorPane.setTopAnchor(playButton, 200.0);

        playButton.setOnAction(event -> {
            System.out.println("working");
            activeScene = newGameScene();
            main.updateScene();
        });
    }

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

//    //EFFECTS : Interprets player input and puts it into effect
//    private void interpretInput(String input) {
//        if (input.equals("1") || input.equals("1.") || input.equalsIgnoreCase("play game")) {
//            game = new Game();
//            //TODO game.play();
//        } else if (input.equals("2") || input.equals("2.") || input.equalsIgnoreCase("match history")) {
//            matchHistory.display();
//        } else if (input.equals("0") || input.equals("0.") || input.equalsIgnoreCase("exit")) {
//            exit = true;
//        } else {
//            System.out.println("That is not a valid input");
//        }
//    }
}
//./data/match0