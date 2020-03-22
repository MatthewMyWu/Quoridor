package ui.gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.players.Avatar;
import ui.Game;

import java.awt.event.ActionEvent;

public class PlayerPanel extends Group {
    private SidePanel sidePanel;
    private Game game;
    private Avatar player;
    //these coordinates are the top left corner of this panel
    private int coordX;
    private int coordY;

    private Label playerLabel;
    private Circle playerTurnIndicator;
    private Label playerWallsLabel;
    private Label playerScoreLabel;

    private final int playerLabelCoordX;
    private final int playerLabelCoordY;
    private final int playerWallsLabelCoordX;
    private final int playerWallsLabelCoordY;
    private final int playerScoreLabelCoordX;
    private final int playerScoreLabelCoordY;
    private final int playerTurnIndicatorCoordX;
    private final int playerTurnIndicatorCoordY;


    public PlayerPanel(int coordX, int coordY, Avatar player, SidePanel sidePanel) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.player = player;
        this.sidePanel = sidePanel;
        this.game = sidePanel.getGame();
        playerLabelCoordX = coordX + 10;
        playerLabelCoordY = coordY + 20;
        playerWallsLabelCoordX = playerLabelCoordX;
        playerWallsLabelCoordY = playerLabelCoordY + 20;
        playerScoreLabelCoordX = playerLabelCoordX;
        playerScoreLabelCoordY = playerLabelCoordY + 40;
        playerTurnIndicatorCoordX = playerLabelCoordX + 50;
        playerTurnIndicatorCoordY = playerLabelCoordY + 2;

        initializeComponents();

        updatePlayerInfo();
    }

    private void initializeComponents() {
        playerLabel = new Label();
        playerTurnIndicator = new Circle();
        playerWallsLabel = new Label();
        playerScoreLabel = new Label();

        playerTurnIndicator.setFill(Color.GREEN);
        playerTurnIndicator.setRadius(5);

        playerLabel.relocate(playerLabelCoordX, playerLabelCoordY);
        playerTurnIndicator.relocate(playerTurnIndicatorCoordX, playerTurnIndicatorCoordY);
        playerWallsLabel.relocate(playerWallsLabelCoordX, playerWallsLabelCoordY);
        playerScoreLabel.relocate(playerScoreLabelCoordX, playerScoreLabelCoordY);

        this.getChildren().addAll(playerLabel, playerTurnIndicator, playerWallsLabel, playerScoreLabel);
    }

    protected void updatePlayerInfo() {
        playerLabel.setText("Player " + player.getPlayerNumber());
        playerWallsLabel.setText("Walls : " + player.getWalls());
        playerScoreLabel.setText(("Score: " + player.getScore()));
        playerTurnIndicator.setVisible(true);
    }

    protected void setTurnIndicator(boolean turnIndicatorStatus) {
        playerTurnIndicator.setVisible(turnIndicatorStatus);
    }
}
