package com.tiggerbiggo.prima.view.sample.control.point;

import com.tiggerbiggo.prima.play.core.Callback;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.view.sample.components.DraggableValueField;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PointController implements Initializable {
    @FXML
    private Canvas mainCanvas;

    @FXML
    private Button addButton, delButton;

    @FXML
    private ListView<Point> pointListView;

    private Point dragging = null;

    @FXML
    private CheckMenuItem showGhostsMenu;

    @FXML
    private VBox propertyBox;

    private DraggableValueField rotXField, rotYField;

    private DraggableValueField rotNumField;



    //TODO: Add ability to animate points


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add a default point
        addPoint();

        pointListView.getItems().addListener((ListChangeListener<Point>) c -> refreshCanvas());

        mainCanvas.setOnMouseMoved(e -> {
            doMouseMoved(e);
            refreshCanvas();
        });

        mainCanvas.setOnMouseDragged(e -> {
            doDragPoint(e);
        });

        mainCanvas.setOnMousePressed(e -> {
            dragging = getMouseOver(e.getX(), e.getY());
            updateProperties(dragging);

            for(Point p : pointListView.getItems()){
                p.setSelected(false);
            }
            if(dragging != null) dragging.setSelected(true);
            pointListView.getSelectionModel().select(dragging);

            refreshCanvas();
            e.consume();
        });

        rotXField = new DraggableValueField(0.5, -1, 2, 0.05);
        rotXField.addCallback(value -> {
            if(dragging != null) dragging.setRotatePoint(new Vector2(rotXField.getValue(), dragging.getRotatePoint().Y()));
            refreshCanvas();
        });
        rotYField = new DraggableValueField(0.5, -1, 2, 0.05);
        rotYField.addCallback(value -> {
            if(dragging != null) dragging.setRotatePoint(new Vector2(dragging.getRotatePoint().X(), rotYField.getValue()));
            refreshCanvas();
        });
        rotNumField = new DraggableValueField(5, 1, 300, 1);
        rotNumField.setDisplayAsInteger(true);
        rotNumField.addCallback(value -> {
            if(dragging != null) dragging.setRotateNum((int)rotNumField.getValue());
            refreshCanvas();
        });

        propertyBox.getChildren().add(new HBox(rotXField, rotYField));
        propertyBox.getChildren().add(rotNumField);

    }

    private void updateProperties(Point point) {
        if(point == null){
            rotXField.setDisable(true);
            rotYField.setDisable(true);
            rotNumField.setDisable(true);
            return;
        }

        rotXField.setDisable(false);
        rotYField.setDisable(false);
        rotNumField.setDisable(false);

        Vector2 pt = point.getRotatePoint();
        rotXField.setValue(pt.X());
        rotYField.setValue(pt.Y());

        rotNumField.setValue(point.getRotateNum());
    }

    @FXML
    private void addPoint(){
        //add to list
        pointListView.getItems().add(new Point(0,0,mainCanvas.getWidth(),mainCanvas.getHeight()));
    }

    @FXML
    private void deletePoint(){
        //remove selected from list
        List<Point> toDelete = pointListView.getSelectionModel().getSelectedItems();
        if(toDelete.size() == 0) return;

        for(Point p : toDelete){
            pointListView.getItems().remove(p);
        }
    }

    private void doMouseMoved(MouseEvent e) {
        //set all points to non mouse over
        //check mouse over, update state

        if(showGhostsMenu.isSelected()){
            for (Point p : pointListView.getItems()) {
                p.setMouseOver(true);
            }
            return;
        }
        for (Point p : pointListView.getItems()) {
            p.setMouseOver(false);
        }
        Point p = getMouseOver(e.getX(), e.getY());
        if(p != null){
            p.setMouseOver(true);
        }
    }

    public void doDragPoint(MouseEvent e){
        e.consume();
        if(dragging != null) {
            dragging.setX(e.getX(), mainCanvas.getWidth());
            dragging.setY(e.getY(), mainCanvas.getHeight());
        }
        refreshCanvas();
    }

    private void refreshCanvas(){
        GraphicsContext g = mainCanvas.getGraphicsContext2D();

        g.drawImage(renderPreview(), 0, 0);


        List<Point> list = pointListView.getItems();

        for(Point p : list){
            p.draw(mainCanvas.getWidth(), mainCanvas.getHeight(), g);
        }
    }

    private Point getMouseOver(double x, double y){
        for(Point p : pointListView.getItems()){
            if(p.isMouseOver(x, y, mainCanvas.getWidth(), mainCanvas.getHeight())) return p;
        }
        return null;
    }

    private List<Vector2> getAllPoints(){
        List<Vector2> toReturn = new ArrayList<>();

        for(Point p : pointListView.getItems()){
            toReturn.addAll(p.expandPoints());
        }

        return toReturn;
    }

    private Image renderPreview(){
        BufferedImage img = new BufferedImage((int)mainCanvas.getWidth(), (int)mainCanvas.getHeight(), BufferedImage.TYPE_INT_RGB);

        List<Vector2> points = getAllPoints();

        int w = img.getWidth();
        int h = img.getHeight();

        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
                double distToNearest = Double.MAX_VALUE;
                for(Vector2 v : points){
                    double dist = v.distanceBetween(new Vector2(i,w-j).divide(new Vector2(w,h)));
                    if(distToNearest > dist) distToNearest = dist;
                }
                img.setRGB(i, j, ColorTools.colorLerp(java.awt.Color.BLACK, java.awt.Color.WHITE, distToNearest * 2.5).getRGB());
            }
        }

        return ImageTools.toFXImage(img);
    }
}
