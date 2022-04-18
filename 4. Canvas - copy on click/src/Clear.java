import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Clear implements EventHandler<ActionEvent> {
    private ArrayList<GraphicsContext> gcList;

    public Clear(ArrayList<GraphicsContext> gcList){

        this.gcList = gcList;
    }

    @Override

    public void handle(ActionEvent event){
        for(GraphicsContext graphics: gcList)
            graphics.clearRect(2, 2, 40, 40 );

        OnMouseClick.setCount(0);

    }

}
