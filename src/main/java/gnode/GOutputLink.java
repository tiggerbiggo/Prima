package gnode;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.NumberArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GOutputLink extends GLink {
  OutputLink<?> link;

  List<GLinkLine> lineList;

  private Vector2 position;

  private final Pane parent;

  public GOutputLink(OutputLink<?> in, Vector2 position, Pane parent){
    link = in;
    setPosition(position);
    lineList = new ArrayList<>();

    this.parent = parent;

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

    setOnDragDropped(event -> {
      GInputLink source;
      if(event.getGestureSource() instanceof GInputLink){
        source = (GInputLink)event.getGestureSource();
        if(source.link(this)){
          //success, create line
          this.parent.getChildren().add(new GLinkLine(source, this, this.parent));
        }
      }
      updatePosition();
      updateLinePos();
    });
  }

  @Override
  public void updatePosition(Vector2 offset) {
    super.updatePosition(offset);

  }

  public void updateLinePos(){
    lineList.forEach(GLinkLine::updatePositions);
  }

  public OutputLink<?> getLink() {
    return link;
  }

  public void addLine(GLinkLine line){
    lineList.add(line);
  }

  public void forgetLine(GLinkLine toForget){
    toForget = Objects.requireNonNull(toForget);
    lineList.remove(toForget);
  }
}
