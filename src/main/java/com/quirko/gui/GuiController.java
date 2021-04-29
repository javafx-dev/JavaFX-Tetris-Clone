package com.quirko.gui;

import com.quirko.logic.DownData;
import com.quirko.logic.ViewData;
import com.quirko.logic.events.*;
import com.quirko.logic.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class GuiController implements Initializable {

    private boolean DEBUG = false;

    // === Private Methods -- Keybindings ===============================
    // Default control keybindings are set here. Control keybindings can
    // be modified in changeControlKeys().

    private KeyCode LEFT_HORIZONTAL_CONTROL = KeyCode.LEFT;
    private KeyCode RIGHT_HORIZONTAL_CONTROL = KeyCode.RIGHT;
    private KeyCode ROTATE_COUNTER_CONTROL = KeyCode.UP;
    private KeyCode ROTATE_CLOCKWISE_CONTROL;
    private KeyCode DOWN_CONTROL = KeyCode.DOWN;
    private KeyCode DROP_CONTROL;
    private KeyCode STORE_CONTROL;
    private boolean assign_flag = false;
    private int instruction = 0;
    private ArrayList<String> mistakesArray; // COUNTS THE NUMBER OF INVALID CONTOL INVOCATIONS
    // WARNING:
    // KeyCode.N should be reserved for New Game.
    // KeyCode.P should be reserved for Pause

    private int totalControlInvocations;
    // ==================================================================



    private static final int BRICK_SIZE = 20;

    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    @FXML
    private GridPane gamePanel;

    @FXML
    private Text scoreValue;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private ToggleButton pauseButton;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    // === Private Members - Brick Preview Pane ===============================
	// This was very difficult to implement. Used to update slots in the 
	// Next Brick window

    //@FXML
    //private GridPane storeBrick;

    @FXML
    private GridPane nextBrick0;

    @FXML
    private GridPane nextBrick1;

    @FXML
    private GridPane nextBrick2;

    @FXML
    private GridPane nextBrick3;

    private Board board;
	
	// =======================================================================


    // ========================================================
    // Initializes the GUI components
    // ========================================================
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (DEBUG) System.out.println("GuiController.initialize()");
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        mistakesArray = new ArrayList<String>();



        mistakesArray.add("Gameplay started at " + dateFormat.format(new Date()));
        totalControlInvocations = 0;
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (DEBUG) System.out.println("GuiController.initialize->handle");

                // ====================================================================
                // Keybindings for controls are initially set here
                // ====================================================================

                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == LEFT_HORIZONTAL_CONTROL) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    else if (keyEvent.getCode() == RIGHT_HORIZONTAL_CONTROL) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    else if (keyEvent.getCode() == ROTATE_COUNTER_CONTROL || keyEvent.getCode() == ROTATE_CLOCKWISE_CONTROL) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    else if (keyEvent.getCode() == DOWN_CONTROL) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    else
                    {
                        mistakesArray.add(keyEvent.getCode().toString() + ", " + dateFormat.format(new Date())); // counts mistake and records time
                        }
                    totalControlInvocations += 1;
                }
                else if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
                else if (keyEvent.getCode() == KeyCode.P) {
                    pauseButton.selectedProperty().setValue(!pauseButton.selectedProperty().getValue());
                }
                else if (assign_flag == true || (isPause.getValue() == Boolean.TRUE && keyEvent.getCode() == KeyCode.BACK_QUOTE))
                {
                    reassignControls(keyEvent.getCode());
                }
                else if (isPause.getValue() == Boolean.TRUE && keyEvent.getCode() == KeyCode.TAB)
                {
                    printMistakes();
                    mistakesArray.clear();
                    totalControlInvocations = 0;
                    mistakesArray.add("Gameplay restarted at " + dateFormat.format(new Date()));
                }
                else
                System.out.println("I'm not sure what just happened");


                keyEvent.consume();
            }
        });
        gameOverPanel.setVisible(false);
        pauseButton.selectedProperty().bindBidirectional(isPause);
        pauseButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    timeLine.pause();
                    pauseButton.setText("Resume");
                } else {
                    timeLine.play();
                    pauseButton.setText("Pause");
                }
            }
        });
        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
        scoreValue.setEffect(reflection);

        if (DEBUG) System.out.println("GuiController.initialize()2");
    }

    // ====================================================================
    // Keybindings for controls are customized here
    // ====================================================================

    private void reassignControls(KeyCode c)
    {
        if (assign_flag == false) {
            System.out.println("\n\n======================================================================");
            System.out.println("Assigning new controls. Don't mess this up (press backspace if you mess up).");
            assign_flag = true;
            instruction = 0;
        }
        if (c == KeyCode.BACK_SPACE)
            instruction = 0;

        if (c != KeyCode.BACK_QUOTE) System.out.println(c);
        switch (instruction) {
            case 0:
                System.out.print("Indicate new keybinding for LEFT_HORIZONTAL_CONTROL : ");
                break;
            case 1:
                LEFT_HORIZONTAL_CONTROL = c;
                System.out.print("Indicate new keybinding for RIGHT_HORIZONTAL_CONTROL : ");
                break;
            case 2:
                RIGHT_HORIZONTAL_CONTROL = c;
                System.out.print("Indicate new keybinding for ROTATE_COUNTER_CONTROL : ");
                break;
            case 3:
                ROTATE_COUNTER_CONTROL = c;
                System.out.print("Indicate new keybinding for ROTATE_CLOCKWISE_CONTROL : ");
                break;
            case 4:
                ROTATE_CLOCKWISE_CONTROL = c;
                System.out.print("Indicate new keybinding for DOWN_CONTROL : ");
                break;
            case 5:
                DOWN_CONTROL = c;
                System.out.print("Indicate new keybinding for STORE_CONTROL : ");
                break;
            case 6:
                STORE_CONTROL = c;
                instruction = -1;
                assign_flag = false;
                System.out.println("New keybindings have been set. You may resume playing");
                System.out.println("======================================================================");

                break;
        }
        instruction += 1;
    }

    private void printMistakes()
    {
        System.out.println("======================================================================");
        System.out.println("Printing invalid control invocations");
        int i;
        for(i = 0; i < mistakesArray.size(); i++)
            System.out.println(mistakesArray.get(i));
        System.out.println("The UI recorded " + (i-1) + " invalid control invocations.");
        System.out.println("The UI recorded " + totalControlInvocations + " total control invocations.");
        System.out.println("======================================================================");
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        if (DEBUG) System.out.println("GuiController.initGameView()");
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        generatePreviewPanel(brick);


        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    public void passBoard(Board b) { this.board = b; }


    // ======================================================
    // Implements Next Block Column
    // ======================================================
    private void generatePreviewPanel(ViewData brick) {
        //if (DEBUG) System.out.println("GuiController.generatePreviewPanel()");
        int [][] nextBrickData = brick.getNextBrickDataAt(0);
        nextBrick0.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextBrick0.add(rectangle, j, i);
                }
            }
        }

        nextBrickData = brick.getNextBrickDataAt(1);
        nextBrick1.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextBrick1.add(rectangle, j, i);
                }
            }
        }

        nextBrickData = brick.getNextBrickDataAt(2);
        nextBrick2.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextBrick2.add(rectangle, j, i);
                }
            }
        }

        nextBrickData = brick.getNextBrickDataAt(3);
        nextBrick3.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextBrick3.add(rectangle, j, i);
                }
            }
        }
    }

    private void refreshBrick(ViewData brick) {
        // if (DEBUG) System.out.println("GuiController.refreshBrick()");

        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            generatePreviewPanel(brick);
        }
    }

    public void refreshGameBackground(int[][] board) {
        if (DEBUG) System.out.println("GuiController.refreshGameBackground()");

        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }


    private void moveDown(MoveEvent event) {
        // if (DEBUG) System.out.println("GuiController.moveDown()");

        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        if (DEBUG) System.out.println("GuiController.setEventListener()");

        this.eventListener = eventListener;
    }


    public void bindScore(IntegerProperty integerProperty) {
        if (DEBUG) System.out.println("GuiController.bindScore()");

        scoreValue.textProperty().bind(integerProperty.asString());
    }

    // ======================================================================
    // Handles Brick Coloring
    // ======================================================================
    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUE;
                break;
            case 3:
                returnPaint = Color.DARKORANGE;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.LIMEGREEN;
                break;
            case 6:
                returnPaint = Color.MAGENTA;
                break;
            case 7:
                returnPaint = Color.RED;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
    
	// ======================================================================


    public void gameOver() {
        if (DEBUG) System.out.println("GuiController.gameOver()");


        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);

    }

    public void newGame(ActionEvent actionEvent) {
        if (DEBUG) System.out.println("GuiController.newGame()");

        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (DEBUG) System.out.println("GuiController.pauseGame()");
        System.out.println("\n\n======================================================================");
        System.out.println("Game Paused");
        System.out.println("Press ` to reassign controls");
        System.out.println("Press TAB to retrieve invalid control invocation table (resetting it afterward).");
        System.out.println("======================================================================");
        gamePanel.requestFocus();
    }
}
