import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;



public class HomeWork2INU extends Application {

    @Override
    public void start(Stage primaryStage){
        try{
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 1400, 800);

            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Canvas canvasMain = new Canvas(900,800);
            GraphicsContext gcMain = canvasMain.getGraphicsContext2D();

            ArrayList<Canvas> littleCanvasList = new ArrayList<>();

            int canvasWidth = 43;
            int canvasHeight = 43;
            for(int i = 0; i < 25; i++){
                Canvas canvas = new Canvas(canvasWidth,canvasHeight);
                littleCanvasList.add(canvas);
            }

            ArrayList<GraphicsContext> gcList = new ArrayList<>();

            for (Canvas canvas : littleCanvasList) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gcList.add(gc);
            }

            for(int i = 0; i < gcList.size(); i++) {
                gcList.get(i).setStroke(Color.BLACK);
                gcList.get(i).setLineWidth(1);
                gcList.get(i).strokeLine(0,0,0,43);
                gcList.get(i).strokeLine(0,43,43,43);
                gcList.get(i).strokeLine(43,43,43,0);
                gcList.get(i).strokeLine(43,0,0,0);
                gcList.get(i).stroke();
            }

            Group centerGroup = new Group();
            Group rightGroup = new Group();
            centerGroup.maxWidth(900);
            centerGroup.prefWidth(900);

            Button load = new Button("Wczytaj");
            Button clear = new Button("Wyczyść");

            VBox rightVbox = new VBox(30);
            VBox centerVbox = new VBox();
            centerVbox.getChildren().addAll(canvasMain);
            rightVbox.setPrefWidth(315);
            rightVbox.setPrefHeight(450);

            int rightVboxRightPadding = 20;
            rightVbox.setPadding(new Insets(20, rightVboxRightPadding, 20, 20));

            HBox rightTopHbox = new HBox( 30);
            Label rightLabel = new Label("Tekst związany z opisem działań");
            FlowPane rightBottomFlowPane = new FlowPane();
            rightBottomFlowPane.setMaxWidth(315);
            rightBottomFlowPane.setMinWidth(315);
            rightBottomFlowPane.prefWidth(315);
            rightBottomFlowPane.setVgap(15);
            rightBottomFlowPane.setHgap(15);

            rightTopHbox.getChildren().addAll(load, clear);
            rightBottomFlowPane.getChildren().addAll(littleCanvasList);

            rightVbox.getChildren().addAll(rightTopHbox, rightLabel, rightBottomFlowPane);

            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            primaryStage.setMaxWidth(screenBounds.getWidth());
            primaryStage.setMaxHeight(screenBounds.getHeight());

            LoadButtonListener loadButtonListener = new LoadButtonListener(primaryStage, canvasMain,
                    rightVbox, rightBottomFlowPane, rightVboxRightPadding, gcMain);
            load.setOnAction(loadButtonListener);

            WritableImage dstImage = new WritableImage((int)canvasMain.getWidth(), (int)canvasMain.getHeight());
            PixelWriter writer = dstImage.getPixelWriter();

            OnMouseClick onMouseClick = new OnMouseClick(littleCanvasList, canvasMain, gcList, dstImage, writer, loadButtonListener);

            canvasMain.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClick);

            Clear clearAction = new Clear(gcList);
            clear.setOnAction(clearAction);

            centerGroup.getChildren().add(centerVbox);
            rightGroup.getChildren().addAll(rightVbox);
            root.setCenter(centerGroup);
            root.setRight(rightGroup);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}


