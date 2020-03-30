package ui.gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ui.Game;
import ui.Menu;
import ui.gui.cell.GuiCell;

//This class contains information about the "panel" at the bottom of the board (when the game is running)
// This includes displaying the message for when the game is over, as well as the "restart" and "Main Menu" buttons
public class BottomPanel extends Group {
    GameGuiTool gameGuiTool;
    Game game;

    //keeps track of the top left coordinates of this panel
    private int coordX;
    private int coordY;

    //These are the various components that exist in this panel
    private Label textLabel;
    private Button restartButton;
    private Button mainMenuButton;

    //These are the x and y coordinates of the various components for this panel
    private int gameOverLabelCoordX;
    private int gameOverLabelCoordY;
    private int restartButtonCoordX;
    private int restartButtonCoordY;
    private int mainMenuButtonCoordX;
    private int mainMenuButtonCoordY;

    //EFFECTS : Constructor instantiates variables
    public BottomPanel(GameGuiTool gameGuiTool) {
        this.gameGuiTool = gameGuiTool;
        this.game = gameGuiTool.getGame();
        textLabel = new Label("");
        restartButton = new Button("Restart");
        mainMenuButton = new Button("Main Menu");

        coordX = 0;
        coordY = Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH + 33;

        gameOverLabelCoordX = coordX + 20;
        gameOverLabelCoordY = coordY - 20;
        restartButtonCoordX = gameOverLabelCoordX;
        restartButtonCoordY = gameOverLabelCoordY + 25;
        mainMenuButtonCoordX = restartButtonCoordX + 80;
        mainMenuButtonCoordY = restartButtonCoordY;

        initialize();
    }

    //EFFECTS : Initializes the components for this class
    //          (eg. positions elements and sets ActionEvent handlers for thebuttons)
    private void initialize() {
        this.getChildren().addAll(textLabel, restartButton, mainMenuButton);
        textLabel.setVisible(false);

        textLabel.relocate(gameOverLabelCoordX, gameOverLabelCoordY);
        restartButton.relocate(restartButtonCoordX, restartButtonCoordY);
        mainMenuButton.relocate(mainMenuButtonCoordX, mainMenuButtonCoordY);

        restartButton.setOnAction(event -> {
            game.restart();
        });

        mainMenuButton.setOnAction(event -> {
            Menu.returnToMainMenu();
        });
    }

    //MODIFIES: this
    //EFFECTS : Sets text label to show game over message
    public void displayGameOverLabel() {
        textLabel.setText("Player " + game.getWinner() + " wins!");
    }

    //MODIFIES: this
    //EFFECTS : Sets text label to show "vertical decision message" (ask player to move left or right)
    public void displayVerticalDecisionMessage() {
        textLabel.setText("Move left or right?");
    }

    //MODIFIES: this
    //EFFECTS : Sets text label to show "horizontal decision message" (ask player to move up or down)
    public void displayHorizontalDecisionMessage() {
        textLabel.setText("Move up or down?");
    }

    //MODIFIES: this
    //EFFECTS : Displays (or doesn't display) the text label
    public void displayTextLabel(boolean display) {
        textLabel.setVisible(display);
    }
}
