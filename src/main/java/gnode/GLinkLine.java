package gnode;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import java.util.Objects;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GLinkLine extends Line {
  GInputLink input;
  GOutputLink output;
  Pane parent;

  public GLinkLine(GInputLink input, GOutputLink output, Pane parent){
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.parent = Objects.requireNonNull(parent);

    this.input.setLine(this);
    this.output.addLine(this);

    setStroke(Color.WHITE);

    updatePositions();
  }

  public void updatePositions(){
    System.out.println("Started.");
    setStartVec(input.getWorldPosition());
    setEndVec(output.getWorldPosition());
  }

  public void setStartVec(Vector2 vec){
    setStartX(vec.X());
    setStartY(vec.Y());
  }

  public void setEndVec(Vector2 vec){
    setEndX(vec.X());
    setEndY(vec.Y());
  }

  //commit sudoku
  public void delete(){
    input.forgetLine();
    output.forgetLine(this);
    parent.getChildren().remove(this);
  }
}
