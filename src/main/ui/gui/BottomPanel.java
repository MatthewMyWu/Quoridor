package ui.gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.players.Avatar;
import model.players.P1;
import ui.Game;
import ui.gui.cell.GuiCell;

//this class is responsible for the "panel" at the bottom of the board (displays player's turn, score, walls, ff)
public class BottomPanel extends Group {
    private int coordX;
    private int coordY;

    private PlayerPanel p1Panel;
    private PlayerPanel p2Panel;

    private Rectangle rect;

    public BottomPanel(Avatar p1, Avatar p2) {
        coordX = Game.SIDE_LENGTH * GuiCell.SIDE_LENGTH;
        coordY = 0;
        p1Panel = new PlayerPanel(coordX, coordY, p1);
        p2Panel = new PlayerPanel(coordX, coordY + 100, p2);

        rect = new Rectangle(100, 150, Color.RED);
        rect.relocate(coordX, coordY);

        this.getChildren().addAll(p1Panel, p2Panel);
    }
}
