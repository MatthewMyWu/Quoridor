package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Menu menu;
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        menu = new Menu(this);

        Scene scene = menu.getActiveScene();

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("Quorridor");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void updateScene() {
        Scene scene = menu.getActiveScene();
        this.primaryStage.setScene(scene);
    }


    public static void main(String[] args) {
        //System.out.println(javafx.scene.text.Font.getFamilies());
        launch(args);
    }
}
