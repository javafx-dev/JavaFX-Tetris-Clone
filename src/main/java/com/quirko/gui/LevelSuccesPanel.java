package com.quirko.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class LevelSuccesPanel extends BorderPane {

    Label levelSuccesLabel;
    String succes = "";

    public LevelSuccesPanel() {
        levelSuccesLabel = new Label(succes);
        levelSuccesLabel.getStyleClass().add("levelSuccesStyle");
        setCenter(levelSuccesLabel);
    }

    public void setLeftMatrixCount(int leftMatrixCount) {
        if (leftMatrixCount < 9) succes = "God Tier!";
        else if (leftMatrixCount < 17) succes = "Excellent!";
        else if (leftMatrixCount < 25) succes = "Very Good!";
        else if (leftMatrixCount < 33) succes = "Good!";
        else if (leftMatrixCount < 41) succes = "Good Enough";
        else if (leftMatrixCount < 50) succes = "You MUST\n do better!";
        else if (leftMatrixCount < 70) succes = "Terrible.";
        else succes = "Quit this\n GAME.";

        levelSuccesLabel = null;
        levelSuccesLabel = new Label(succes);
        levelSuccesLabel.getStyleClass().add("levelSuccesStyle");
        setCenter(levelSuccesLabel);
    }
}
