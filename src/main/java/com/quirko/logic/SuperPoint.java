package com.quirko.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class SuperPoint {

    public static IntegerProperty point = new SimpleIntegerProperty(0);

    public IntegerProperty pointProperty() {
        return point;
    }

    public void add(int i){
        point.setValue(point.getValue() + i*2);
    }

    public void reset() {
        point.setValue(0);
    }

    public int getSuperPoint(){
        return point.getValue();
    }
}
