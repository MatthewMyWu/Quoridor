package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ui.gui.GuiTool;

public class Main extends Application {
    Game game;
    GuiTool guiTool;

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();
        guiTool = game.guiTool;
        Scene scene = new Scene(game.guiTool.createContent());

        //adding listeners
        scene.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            guiTool.handleKeyboard(key);
        });
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, click -> {
            guiTool.handleMousePressed(click);
        });
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, drag -> {
            guiTool.handleMouseDragged(drag);
        });
        scene.addEventHandler(MouseEvent.MOUSE_RELEASED, release -> {
            guiTool.handleMouseReleased(release);
        });

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("this prints after");
    }
}
