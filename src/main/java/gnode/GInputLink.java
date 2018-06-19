package gnode;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.primaplay.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GInputLink extends GLink {
  InputLink<?> link;

  Vector2 position;

  public GInputLink(InputLink<?> in, Vector2 position){
    link = in;

    if(link instanceof ColorArrayInputLink){
      setFill(Color.YELLOW);
    }
    else if(link instanceof NumberArrayInputLink){
      setFill(Color.GREY);
    }
    else if(link instanceof VectorArrayInputLink){
      setFill(Color.BLUE);
    }
    else if(link instanceof VectorInputLink){
      setFill(Color.AQUA);
    }
    else{
      System.out.println("ERR: " + link);
    }

    setPosition(position);

    setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        event.acceptTransferModes(TransferMode.LINK);

        System.out.println("DROPPED");

        event.consume();
      }
    });
  }

  public boolean link(OutputLink<?> out){
    return link.link(out);
  }

  public InputLink<?> getLink() {
    return link;
  }
}
