package com.quirko.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class VERY_SPECIAL_Panel extends BorderPane {

    public VERY_SPECIAL_Panel() {
        final Label VERY_SPECIAL_label = new Label("SPECIAL DROP"
        +"\nChange the color in the\n"
        +"middle of the big square\n"
        +"to destroy all the\n"
        +"bricks of that color!\n\n\n"
        +"VERY SPECIAL DROP\n"
        +"Get Sprpoints to multiply\n"
        +"score increase with them!\n"
        +"To get Sprpoints, touch the\n"
        +"far right and far left with\n"
        +"the single square.");
        VERY_SPECIAL_label.getStyleClass().add("verySpecialStyle");
        setCenter(VERY_SPECIAL_label);
    }

}