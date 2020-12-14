package com.quirko.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void add(int i){
        if(SuperPoint.point.getValue()>0)
        score.setValue(score.getValue() + i*SuperPoint.point.getValue());
        else
        score.setValue(score.getValue() + i);
    }

    public void reset() {
        score.setValue(0);
    }
}
