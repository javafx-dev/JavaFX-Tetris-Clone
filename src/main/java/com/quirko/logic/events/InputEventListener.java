package com.quirko.logic.events;

import com.quirko.logic.ViewData;
import com.quirko.logic.DownData;

public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    void createNewGame(boolean isNewLevel);
}
