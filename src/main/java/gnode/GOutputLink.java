package gnode;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.NumberArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;
import javafx.scene.paint.Color;

public class GOutputLink extends GLink {
  OutputLink<?> link;

  private Vector2 position;

  public GOutputLink(OutputLink<?> in, Vector2 position){
    link = in;

    if(link instanceof ColorArrayOutputLink){
      setFill(Color.YELLOW);
    }
    else if(link instanceof NumberArrayOutputLink){
      setFill(Color.GREY);
    }
    else if(link instanceof VectorArrayOutputLink){
      setFill(Color.BLUE);
    }
    else if(link instanceof VectorOutputLink){
      setFill(Color.AQUA);
      System.out.println("SET BLUE");
    }

    setPosition(position);
  }

  public OutputLink<?> getLink() {
    return link;
  }
}
