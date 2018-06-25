package gnode;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import java.util.Objects;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

public class GLinkLine extends CubicCurve {
  GInputLink input;
  GOutputLink output;
  Pane parent;

  private final double AMNT = 50;

  public GLinkLine(GInputLink input, GOutputLink output, Pane parent){
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.parent = Objects.requireNonNull(parent);

    this.input.setLine(this);
    this.output.addLine(this);

    setStroke(Color.BLACK);

    setFill(Color.TRANSPARENT);

    updatePositions();
  }

  public void updatePositions(){
    System.out.println("Started.");
    setStartVec(input.getWorldPosition());
    setEndVec(output.getWorldPosition());
  }

  public void setStartVec(Vector2 vec){
    setControlX1(vec.X() - AMNT);
    setControlY1(vec.Y());
    setStartX(vec.X());
    setStartY(vec.Y());
  }

  public void setEndVec(Vector2 vec){
    setControlX2(AMNT + vec.X());
    setControlY2(vec.Y());
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
