import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadButtonListener implements EventHandler<ActionEvent> {
    private VBox rightVbox;
    private Rectangle2D screenBounds;
    private GraphicsContext gcMain;
    private FlowPane rightBottomFlowPane;
    private int rightVboxRightPadding;
    private FileChooser fileChooser = new FileChooser();
    private Stage primaryStage;
    private Canvas canvasMain;
    private Image[] imageMain = {new Image(new FileInputStream("C:\\Users\\install\\Documents\\JAVA\\!!Studia\\materiały\\4. INU\\" +
            "Prace domowe\\INU_HW2_MB\\INU_HW2_MB\\src\\lenna256px.png"))};
    private BufferedImage[] bimg = {null};

    public Image[] getImageMain(){
        return imageMain;
    }


    public LoadButtonListener(Stage primaryStage, Canvas canvasMain, VBox rightVbox, FlowPane rightBottomFlowPane,
                              int rightVboxRightPadding, GraphicsContext gcMain) throws FileNotFoundException {
        this.primaryStage = primaryStage;
        this.canvasMain = canvasMain;
        this.rightVbox = rightVbox;
        this.rightVboxRightPadding = rightVboxRightPadding;
        this.rightBottomFlowPane = rightBottomFlowPane;
        this.gcMain = gcMain;

        this.gcMain = canvasMain.getGraphicsContext2D();
        this.screenBounds = Screen.getPrimary().getBounds();
    }

    @Override
    public void handle(ActionEvent actionEvent){

        File file = fileChooser.showOpenDialog(primaryStage);


        try {
            bimg[0] = ImageIO.read(new File(String.valueOf(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null){
            gcMain.clearRect(0, 0, canvasMain.getWidth(), canvasMain.getHeight());
            try {
                imageMain[0] = new Image(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            canvasMain.setWidth(bimg[0].getWidth());
            if(bimg[0].getWidth() > (screenBounds.getWidth() - rightVbox.getWidth())){
                canvasMain.setWidth(screenBounds.getWidth() - rightVbox.getWidth());
            }


            canvasMain.setHeight(bimg[0].getHeight());
            if(bimg[0].getHeight() > screenBounds.getHeight() - 80){
                canvasMain.setHeight(screenBounds.getHeight() - 80);
            }

            primaryStage.setWidth(bimg[0].getWidth() + rightBottomFlowPane.getWidth() + rightVboxRightPadding);

            primaryStage.setHeight(bimg[0].getHeight());
            if(bimg[0].getHeight() < rightVbox.getHeight()){
                primaryStage.setHeight(rightVbox.getHeight());
            } else if (bimg[0].getHeight() > screenBounds.getHeight() - 40) {
                primaryStage.setHeight(screenBounds.getHeight() - 40);
            }

            gcMain.drawImage(imageMain[0], 0, 0);

        }
    }

}
