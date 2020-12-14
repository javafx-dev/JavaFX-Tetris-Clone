package com.quirko.logic;

public final class DownData {
    private final ClearRow clearRow;
    private final ClearColor clearColor;
    private final ViewData viewData;

    public DownData(ClearRow clearRow, ViewData viewData, ClearColor clearColor) {
        this.clearColor = clearColor;
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    public DownData(ClearColor clearColor, ViewData viewData, ClearRow clearRow) {
        this.clearColor = clearColor;
        this.viewData = viewData;
        this.clearRow = clearRow;
    }


    public ClearRow getClearRow() {
        return clearRow;
    }

    public ClearColor getClearColor(){
        return clearColor;
    }

    public ViewData getViewData() {
        return viewData;
    }
}
