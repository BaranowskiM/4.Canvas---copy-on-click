import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.*;

public class OnMouseClick implements EventHandler<MouseEvent>{
    private Canvas canvasMain;
    private LoadButtonListener loadButtonListener;
    private WritableImage dstImage;
    private PixelWriter writer ;
    private ArrayList<Canvas> littleCanvasList;
    private static ArrayList<GraphicsContext> gcList;
    private HashMap<Double, WritableImage> redPixCanvasHashMap = new HashMap<>();
    private TreeMap<Double, WritableImage> sortedHashMap = new TreeMap<>();
    private ArrayList<WritableImage> sortedImages = new ArrayList<WritableImage>();
    private ArrayList<WritableImage> sampleImages = new ArrayList<WritableImage>();

    private Color color;
    private static int count = 0;
    private Double readOnePix;
    private static Double sumOfAllRedPic = 0.0;
    private static ArrayList<Double> redValue = new ArrayList<>();

    public static int setCount(int number){
        return count = number;
    }

    public OnMouseClick(ArrayList<Canvas> littleCanvasList, Canvas canvasMain, ArrayList<GraphicsContext> gcList, WritableImage dstImage,
                        PixelWriter writer, LoadButtonListener loadButtonListener){

        this.littleCanvasList = littleCanvasList;
        this.canvasMain = canvasMain;
        this.gcList = gcList;
        this.dstImage = dstImage;
        this.writer = writer;
        this.loadButtonListener = loadButtonListener;
    }

    @Override
    public void handle(MouseEvent event) {


        int mouseLocX = (int) event.getX();
        int mouseLocY = (int) event.getY();
     //   System.out.println("X: " + mouseLocX + " Y: " + mouseLocY);

        if (event.getX() > canvasMain.getWidth() - 21) {
            mouseLocX = (int) canvasMain.getWidth() - 21;
        }
        if (event.getX() < 20) {
            mouseLocX = 20;
        }
        if (event.getY() > canvasMain.getHeight() - 21) {
            mouseLocY = (int) canvasMain.getHeight() - 21;
        }
        if (event.getY() < 20) {
            mouseLocY = 20;
        }


        for (int x = 0; x < 41; x++) {
            for (int y = 0; y < 41; y++) {
                color = loadButtonListener.getImageMain()[0].getPixelReader().getColor(mouseLocX - 20 + x, mouseLocY - 20 + y);

                writer.setColor(x, y, Color.color(
                        color.getRed(),
                        color.getGreen(),
                        color.getBlue()));

                readOnePix = (color.getRed() * 255);
                sumOfAllRedPic = Double.sum(sumOfAllRedPic, readOnePix);
            }
        }
        sampleImages.add(dstImage);

        redValue.add(sumOfAllRedPic);

      // System.out.println(redValue);

//Dodanie do HashMapy Wartości sumy czerwonych pikseli oraz zapisanego obrazu
        redPixCanvasHashMap.put(sumOfAllRedPic, dstImage);

        sortedHashMap.putAll(redPixCanvasHashMap);
     //   System.out.println(sortedHashMap);
/*
        for( Map.Entry<Double, WritableImage> treeValue: sortedHashMap.entrySet()){
            sortedImages.add(treeValue.getValue());
        }
*/
        //for(int i = 0; i < count + 1; i++) {
            sortedImages.add(sortedHashMap.get(sumOfAllRedPic));
     //   }

       // System.out.println(sortedImages);

    //    for(int i = 0; i < count + 1 ; i++) {
            if (count < gcList.size() - 1) {
                gcList.get(count).drawImage(dstImage, 1, 1);


            } else {
                gcList.get(gcList.size() - 1).drawImage(dstImage, 1, 1);
            }
    //    }


    /*
        if(count < gcList.size() - 1){
            gcList.get(count).drawImage(dstImage, 1, 1);
        } else {
            gcList.get(gcList.size() - 1).drawImage(dstImage, 1, 1);
        }
    */

        count++;
        sumOfAllRedPic = 0.0;
    }
}
