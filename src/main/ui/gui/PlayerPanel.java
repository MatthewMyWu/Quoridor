package ui.gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.players.Avatar;
import ui.Game;

// This class is responsible for displaying the information for one player (player name, turn indicator, walls,
// and score) on the side panel
public class PlayerPanel extends Group {
    private SidePanel sidePanel;// The SidePanel this is coupled with
    private Game game;// The game this is coupled with
    private Avatar player;// The player this playerPanel is responsible for

    //these coordinates are the top left corner of this panel
    private int coordX;
    private int coordY;

    //These are components of the playerLabel
    private Label playerLabel;
    private Circle playerTurnIndicator;
    private Label playerWallsLabel;
    private Label playerScoreLabel;

    //These are x and y coordinates for their respective components
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

    //MODIFIES: this
    //EFFECTS : Instantiates and positions each component
    private void initializeComponents() {
        playerLabel = new Label();
        playerTurnIndicator = new Circle();
        playerWallsLabel = new Label();
        playerScoreLabel = new Label();

        playerLabel.setText("Player " + player.getPlayerNumber());
        playerTurnIndicator.setFill(Color.GREEN);
        playerTurnIndicator.setRadius(5);

        playerLabel.relocate(playerLabelCoordX, playerLabelCoordY);
        playerTurnIndicator.relocate(playerTurnIndicatorCoordX, playerTurnIndicatorCoordY);
        playerWallsLabel.relocate(playerWallsLabelCoordX, playerWallsLabelCoordY);
        playerScoreLabel.relocate(playerScoreLabelCoordX, playerScoreLabelCoordY);

        this.getChildren().addAll(playerLabel, playerTurnIndicator, playerWallsLabel, playerScoreLabel);
    }

    //MODIFIES: this
    //EFFECTS : Updates the panel (walls and score)
    protected void updatePlayerInfo() {
        playerWallsLabel.setText("Walls : " + player.getWalls());
        playerScoreLabel.setText(("Score: " + player.getScore()));
    }

    //MODIFIES: this
    //EFFECTS : Updates the turn indicator for this player
    protected void setTurnIndicator(boolean turnIndicatorStatus) {
        playerTurnIndicator.setVisible(turnIndicatorStatus);
    }
}
