package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorOutputLink;
import com.tiggerbiggo.prima.view.sample.components.Knob;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.lang.reflect.Field;

public class HueCycleNode extends NodeInOut {

    private ColorInputLink in;
    private ColorOutputLink out;

    transient Knob k;

    @TransferGrid
    private double hueToAdd = 1;

    public HueCycleNode(){
        in = new ColorInputLink("In");

        out = new ColorOutputLink("Out") {
            @Override
            public Color get(RenderParams p) {
                Color c = in.get(p);
                float h = ColorTools.getHue(c);
                float s = ColorTools.getSaturation(c);
                float b = ColorTools.getBrightness(c);
                return Color.getHSBColor((float)(h+k.getValue())%1,s,b);
            }

            @Override
            public void generateGLSLMethod(StringBuilder s) {
                throw new NotImplementedException();
            }

            @Override
            public String getMethodName() {
                throw new NotImplementedException();
            }
        };

        addInput(in);

        addOutput(out);
    }

    @Override
    public String getName() {
        return "Hue Cycle Node";
    }

    @Override
    public String getDescription() {
        return "Takes a Colour as an input, and adjusts its hue value.";
    }

    @Override
    public Node getFXNode(ChangeListener listener) {
        k = new Knob(0,1);
        k.setValue(hueToAdd);

        k.setChangeListener(new ChangeListener() {
            @Override
            public void onObjectValueChanged(Field field, Object o, Object o1, Object o2) {
                hueToAdd = k.getValue();
            }
        });

        return new HBox(k);
    }
}
