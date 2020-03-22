package ui.gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ui.Game;
import ui.gui.cell.GuiCell;

public class BottomPanel extends Group {
    GuiTool guiTool;
    Game game;

    //keeps track of the top left coordinates of this panel
    private int coordX;
    private int coordY;

    private int gameOverLabelCoordX;
    private int gameOverLabelCoordY;
    private int restartButtonCoordX;
    private int restartButtonCoordY;
    private int mainMenuButtonCoordX;
    private int mainMenuButtonCoordY;

    private Label gameOverLabel;
    private Button restartButton;
    private Button mainMenuButton;

    public BottomPanel(GuiTool guiTool) {
        this.guiTool = guiTool;
        this.game = guiTool.getGame();
        gameOverLabel = new Label("Player - wins!");
        restartButton = new Button("Restart");
        mainMenuButton = new Button("Main Menu");

        coordX = 0;
        coordY = Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH + 30;

        gameOverLabelCoordX = coordX + 20;
        gameOverLabelCoordY = coordY - 20;
        restartButtonCoordX = gameOverLabelCoordX;
        restartButtonCoordY = gameOverLabelCoordY + 25;
        mainMenuButtonCoordX = restartButtonCoordX + 80;
        mainMenuButtonCoordY = restartButtonCoordY;

        initialize();
    }

    private void initialize() {
        this.getChildren().addAll(gameOverLabel, restartButton, mainMenuButton);
        gameOverLabel.setVisible(false);

        gameOverLabel.relocate(gameOverLabelCoordX, gameOverLabelCoordY);
        restartButton.relocate(restartButtonCoordX, restartButtonCoordY);
        mainMenuButton.relocate(mainMenuButtonCoordX, mainMenuButtonCoordY);
    }

    public void displayGameOverLabel(boolean display) {
        gameOverLabel.setText("Player " + game.getWinner() + " wins!");
        gameOverLabel.setVisible(display);
    }
}
