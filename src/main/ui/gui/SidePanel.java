package ui.gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import ui.Game;
import ui.gui.cell.GuiCell;

//this class is responsible for the "panel" at the side of the board (displays player's turn, score, walls, ff)
public class SidePanel extends Group {
    private GameGuiTool gameGuiTool;// The GameGuiTool this is coupled with
    private Game game;// The Game this is coupled with

    // These are the x and y coordinates for the top left corner of the panel
    private int coordX;
    private int coordY;

    // These are the components of the side panel
    private PlayerPanel p1Panel;
    private PlayerPanel p2Panel;
    private Button forfeitButton;

    // These are the x and y coordinates for the forfeit button
    private final int forfeitButtonCoordX;
    private final int forfeitButtonCoordY;

    //EFFECTS : Constructor that initializes (and positions) all components
    public SidePanel(GameGuiTool gameGuiTool) {
        this.gameGuiTool = gameGuiTool;
        this.game = gameGuiTool.getGame();
        coordX = Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH + 10;
        coordY = 0;
        forfeitButtonCoordX = coordX + 10;
        forfeitButtonCoordY = coordY + 180;

        p1Panel = new PlayerPanel(coordX, coordY, game.getP1(), this);
        p2Panel = new PlayerPanel(coordX, coordY + 70, game.getP2(), this);
        forfeitButton = new Button("Forfeit");

        forfeitButton.setPrefHeight(15);
        forfeitButton.setPrefWidth(55);
        forfeitButton.setFont(new Font(12));
        forfeitButton.setOnAction(event -> {
            game.update("/ff");
        });
        forfeitButton.relocate(forfeitButtonCoordX, forfeitButtonCoordY);

        update();

        this.getChildren().addAll(p1Panel, p2Panel, forfeitButton);
    }

    //MODIFIES: this
    //EFFECTS : updates this information
    public void update() {
        p1Panel.updatePlayerInfo();
        p2Panel.updatePlayerInfo();

        //updating turn indicator
        p1Panel.setTurnIndicator(game.isP1Turn());
        p2Panel.setTurnIndicator(!game.isP1Turn());
    }

    public Game getGame() {
        return game;
    }

    public GameGuiTool getGameGuiTool() {
        return gameGuiTool;
    }
}
