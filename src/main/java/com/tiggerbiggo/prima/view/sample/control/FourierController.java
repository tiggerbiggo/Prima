package com.tiggerbiggo.prima.view.sample.control;

import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.graphics.*;
import com.tiggerbiggo.prima.play.node.implemented.BasicRenderNode;
import com.tiggerbiggo.prima.view.sample.ViewMain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser.ExtensionFilter;

public class FourierController implements Initializable {

    private int WIDTH = 300;
    private int HEIGHT = 300;

    @FXML
    ImageView imgView;

    @FXML
    Label lblInfo;

    SafeImage buf;
    Random r;

    SafeImage[] movie;
    int frames = 400;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buf = new SafeImage(WIDTH,HEIGHT);

        r = new Random();

        movie = new SafeImage[frames];
        imgView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown()){
                    if(event.isAltDown()) {
                        subSample();
                    }
                    else {
                        System.out.println("Start");
                        nextFrame2();
                        System.out.println("Edna");
                    }
                    showImage();
                }
                if(event.isSecondaryButtonDown()){
                    System.out.println("Started movie generation");
                    for(int i=0; i<frames; i++){
                        System.out.println("Frame " + i + " of " +frames);
                        nextFrame2();
                        //applyConvolution(0.5,sobelConv);
                        movie[i] = buf;//.clone();
                        //blurImage(0.03);
                    }
                    System.out.println("Writing...");
                    BufferedImage[] seq = ImageTools.toBufferedImage(movie);
                    FileManager.writeVideo(seq, new File("worblw.mp4"), 1);
                    //FileManager.writeGif(seq, new File("noisy.gif"));
                    System.out.println("Done");
                    lblInfo.setText("Done");
                }

            }
        });

        initImage();
    }

    Color A = new Color(0,0,255);
    Color B = new Color(255,0,0);
    Color C = new Color(0,255,0);

    ArrayList<Color> palette = Palette.colorCollectionFromHexList(Palette.tettPal);


    private void initImage(){
        /*Random r = new Random();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j <HEIGHT ; j++) {

                buf.setColor(i,j,ColorTools.randomColor(r));
                //buf.setColor(i,j,palette.get(r.nextInt(palette.size())));
            }
        }*/
        int n = 2;
        for(int i=1; i<=n; i++){
            float percent = (float)i / n;
            percent *= Math.PI*2;
            System.out.println(percent);
            System.out.println(new Vector2(Math.sin(percent), Math.cos(percent)).multiply(0.5));
            sys.points.add(new Vector2(Math.sin(percent), Math.cos(percent)).multiply(0.5));

        }
        //sys.points.add(new Vector2(0,0));
        //sys.points.add(new Vector2(-0.5,0));
        //sys.points.add(new Vector2(0,0));
        buf = sys.generateImage();
        sys.step();
        showImage();
    }

    int swapsPerFrame = 1000000;

    int hRange = 10;
    int vRange = 10;

    int diff = 200;

    private void nextFrame(){
        for(int i=0; i<swapsPerFrame; i++){
            int ax, ay, bx, by;
            ax = r.nextInt(WIDTH);
            ay = r.nextInt(HEIGHT);
            bx = r.nextInt(WIDTH);
            by = r.nextInt(HEIGHT);
            //bx = ax + r.nextInt(hRange);
            //by = ay + r.nextInt(vRange);

            int combinedDifference = averageLocalDifference(ax, ay, bx, by);
            combinedDifference += averageLocalDifference(bx, by, ax, ay);

            if(combinedDifference <= r.nextInt(255*2)){
                //swap
                Color c1 = buf.getColor(ax, ay);
                Color c2 = buf.getColor(bx, by);
                buf.setColor(ax,ay, c2);
                buf.setColor(bx,by, c1);
            }
        }
    }

    PendulumSystem sys = new PendulumSystem(WIDTH, HEIGHT);

    private void nextFrame2(){
        buf = sys.generateImage();
        sys.step();
    }

    private int averageDifference(){
        int total = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                //total += ColorTools.absoluteDifference()
            }
        }
        return 0;
    }

    private int averageLocalDifference(int ax, int ay, int bx, int by){
        Color a = buf.getColor(ax, ay);
        int total = 0;
        int count = 0;
        int n=3;
        for (int i = -n; i <=n; i++) {
            for (int j = -n; j <= n ; j++) {
                if(i == 0 && j == 0) continue;
                //if(Math.abs(i)<=n-2 && Math.abs(j)<=n-2) continue;

                total += ColorTools.absoluteDifference(a, buf.getColor(i+bx, j+by));
                count ++;
            }
        }
        return total / count;
    }

    private void showImage(){
        imgView.setImage(ImageTools.toFXImage(buf));
    }

    private void applyConvolution(double fac, double[][] convolution){
        SafeImage conv = new SafeImage(WIDTH, HEIGHT);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Color c = conv3x3At(i,j,fac, convolution);
                conv.setColor(i,j,c);
            }
        }
        buf = conv;
    }
    
    private void subSample(){
        SafeImage smaller = new SafeImage(buf.getWidth()/2, buf.getHeight()/2);
        for (int i = 0; i < smaller.getWidth(); i++) {
            for (int j = 0; j < smaller.getHeight(); j++) {
                Color A = buf.getColor(i*2, j*2);
                Color B = buf.getColor((i*2)+1, j*2);
                Color C = buf.getColor((i*2)+1, (j*2)+1);
                Color D = buf.getColor(i*2, (j*2)+1);
                smaller.setColor(i,j, ColorTools.colorAvg(A, B, C, D));
            }
        }
        buf = smaller;
        WIDTH = buf.getWidth();
        HEIGHT = buf.getHeight();
    }

    private Color getAverageSurroundings(int x, int y){
        Color[] cols = new Color[8];
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(i==0 && j==0) continue;
                cols[count] = buf.getColor(x+i, y+j);
                count++;
            }
        }

        return ColorTools.colorAvg(cols);
    }

    double[][] sharpenConv = {
            {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
    };
    double[][] sobelConv = {
            {0,-1,0},
            {-1,4,-1},
            {0,-1,0}
    };
    double[][] blurConv = {
            {.11,.11,.11},
            {.11,.11,.11},
            {.11,.11,.11}
    };
    private Color conv3x3At(int x, int y, double fac, double[][] conv){
        double r, g, b;
        r=g=b=0;
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <=2 ; j++) {
                if(conv[i][j]==0) continue;
                Color c = buf.getColor(x+i-1, y+j-1);
                r+=c.getRed()*conv[i][j];
                g+=c.getGreen()*conv[i][j];
                b+=c.getBlue()*conv[i][j];
            }
        }
        return ColorTools.colorLerp(
                buf.getColor(x,y),
                ColorTools.constrain((int)r, (int)g, (int)b),
                fac);
    }
}
class Palette{
    public static String tettPal = "372b26\n" +
            "c37c6b\n" +
            "dd997e\n" +
            "6e6550\n" +
            "9a765e\n" +
            "e1ad56\n" +
            "c6b5a5\n" +
            "e9b58c\n" +
            "efcbb3\n" +
            "f7dfaa\n" +
            "ffedd4\n" +
            "bbd18a\n" +
            "355525\n" +
            "557a41\n" +
            "112d19\n" +
            "45644f\n" +
            "62966a\n" +
            "86bb9a\n" +
            "15452d\n" +
            "396a76\n" +
            "86a2b7\n" +
            "92b3db\n" +
            "3d4186\n" +
            "6672bf\n" +
            "15111b\n" +
            "9a76bf\n" +
            "925ea2\n" +
            "c7a2cf\n" +
            "553549\n" +
            "a24d72\n" +
            "c38e92\n" +
            "e3a6bb";

    /**
     *
     * @param hexVals String value containing the palette, each colour on its own line
     * @return
     */
    public static ArrayList<Color> colorCollectionFromHexList(String hexVals){
        ArrayList<Color> toReturn = new ArrayList<>();
        for(String s : hexVals.split("\n")){
            Color c = ColorTools.fromHexString(s);
            if(c != null) {
                toReturn.add(c);
            }
        }
        if(toReturn.size() == 0){
            throw new IllegalArgumentException("No hex colors were found.");
        }
        return toReturn;
    }
}

class PendulumSystem{
    ArrayList<Vector2> points;
    int width, height;
    Pendulum[][] pendulums;

    static SafeImage texture = FileManager.safeGetImg(new File("imgs/shoot.png"));

    public PendulumSystem(int width, int height){
        this.width = width;
        this.height = height;
        points = new ArrayList<>();
        initPendulums();
    }
    public void step(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pendulums[i][j].attractStepList(points, 0.0005);
            }
        }
    }
    public SafeImage generateImage(){
        SafeImage toReturn = new SafeImage(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2 pos = pendulums[i][j].position;
                //DoubleGradient g = new DoubleGradient(Color.RED, Color.GREEN, Color.BLUE,true);
                //toReturn.setColor(i, j, g.evaluate(pos));
                toReturn.setColor(i, j, texture.getColor(texture.denormVector(pos.add(6))));
            }
        }

        return toReturn;
    }

    public void initPendulums(){
        pendulums = new Pendulum[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2 pos = new Vector2(
                        (double)i / width,
                        (double)j / height
                );
                pos = pos.subtract(new Vector2(0.5)).multiply(4);
                pendulums[i][j] = new Pendulum(new Vector2(0.1,0), pos);
            }
        }
    }
}

class Pendulum{

    public Vector2 velocity;
    public Vector2 position;

    public Pendulum(Vector2 velocity, Vector2 position){
        this.velocity = velocity;
        this.position = position;
    }

    public Pendulum(){
        this(Vector2.ZERO, Vector2.ZERO);
    }

    public void attractStep(Vector2[] points, double attractScale){
        Vector2 attraction = Vector2.ZERO;
        for(Vector2 point : points){
            //calculate distance
            double dist = point.distanceBetween(position);
            //add to attraction
            attraction = attraction.add(point.subtract(position).multiply(1/dist).multiply(attractScale));
        }
        velocity = velocity.add(attraction);
        velocity = velocity.multiply(0.99);
        position = position.add(velocity);
    }
    public void attractStepList(ArrayList<Vector2> points, double attractScale){
        Vector2 attraction = Vector2.ZERO;
        for(Vector2 point : points){
            //calculate distance
            double dist = point.distanceBetween(position);
            //add to attraction
            attraction = attraction.add(point.subtract(position).multiply(1/dist).multiply(attractScale));
        }
        velocity = velocity.add(attraction);
        velocity = velocity.multiply(0.994);
        position = position.add(velocity);
    }
}