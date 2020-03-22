package ui.gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.players.Avatar;

public class PlayerPanel extends Group {
    private Avatar player;
    //these coordinates are the top left corner of this panel
    private int coordX;
    private int coordY;

    private Label playerLabel;
    private Label playerWallsLabel;
    private Label playerScoreLabel;
    private Circle playerTurnIndicator;

    private final int playerLabelCoordX;
    private final int playerLabelCoordY;
    private final int playerWallsLabelCoordX;
    private final int playerWallsLabelCoordY;
    private final int playerScoreLabelCoordX;
    private final int playerScoreLabelCoordY;
    private final int playerTurnIndicatorCoordX;
    private final int playerTurnIndicatorCoordY;


    public PlayerPanel(int coordX, int coordY, Avatar player) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.player = player;
        playerLabelCoordX = coordX + 10;
        playerLabelCoordY = coordY + 20;
        playerWallsLabelCoordX = playerLabelCoordX;
        playerWallsLabelCoordY = playerLabelCoordY + 20;
        playerScoreLabelCoordX = playerLabelCoordX;
        playerScoreLabelCoordY = playerLabelCoordY + 40;
        playerTurnIndicatorCoordX = playerLabelCoordX + 60;
        playerTurnIndicatorCoordY = playerLabelCoordY;

        initializeComponents();

        updatePlayerInfo();
    }

    private void initializeComponents() {
        playerLabel = new Label();
        playerWallsLabel = new Label();
        playerScoreLabel = new Label();
        playerTurnIndicator = new Circle();
        playerTurnIndicator.setFill(Color.GREEN);
        playerTurnIndicator.setRadius(10);

        playerLabel.relocate(playerLabelCoordX, playerLabelCoordY);
        playerWallsLabel.relocate(playerWallsLabelCoordX, playerWallsLabelCoordY);
        playerScoreLabel.relocate(playerScoreLabelCoordX, playerScoreLabelCoordY);
        playerTurnIndicator.relocate(playerTurnIndicatorCoordX, playerTurnIndicatorCoordY);

        this.getChildren().addAll(playerLabel, playerWallsLabel, playerScoreLabel, playerTurnIndicator);
    }

    public void updatePlayerInfo() {
        playerLabel.setText("Player " + player.getPlayerNumber());
        playerWallsLabel.setText("Walls : " + player.getWalls());
        playerScoreLabel.setText(("Score: " + player.getScore()));
        playerTurnIndicator.setVisible(true);
    }
}
