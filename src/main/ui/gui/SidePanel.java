package ui.gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.players.Avatar;
import model.players.P1;
import ui.Game;
import ui.gui.cell.GuiCell;

import java.awt.event.ActionEvent;

//this class is responsible for the "panel" at the side of the board (displays player's turn, score, walls, ff)
public class SidePanel extends Group {
    private GuiTool guiTool;
    private Game game;

    private int coordX;
    private int coordY;

    private final int forfeitButtonCoordX;
    private final int forfeitButtonCoordY;

    private PlayerPanel p1Panel;
    private PlayerPanel p2Panel;
    private Button forfeitButton;

    public SidePanel(GuiTool guiTool) {
        this.guiTool = guiTool;
        this.game = guiTool.getGame();
        coordX = Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH;
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

    public GuiTool getGuiTool() {
        return guiTool;
    }
}
