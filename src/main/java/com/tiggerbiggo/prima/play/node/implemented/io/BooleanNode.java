package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public class BooleanNode extends NodeInOut {

    ColorArrayInputLink A, B;
    VectorInputLink bool;

    ColorArrayOutputLink out;

    public BooleanNode(){
        A = new ColorArrayInputLink("A");
        B = new ColorArrayInputLink("B");
        bool = new VectorInputLink("0 = A, !0 = B");
        addInput(A, B, bool);

        out = new ColorArrayOutputLink("Out") {
            @Override
            public Color[] get(RenderParams p) {
                if(bool.get(p).magnitude() == 0.0)
                    return A.get(p);
                else
                    return B.get(p);
            }

            @Override
            public void generateGLSLMethod(StringBuilder s) {
                //TODO
                throw new NotImplementedException();
            }

            @Override
            public String getMethodName() {
                //TODO
                throw new NotImplementedException();
            }
        };
        addOutput(out);
    }

    @Override
    public String getName() {
        return "Boolean Node";
    }

    @Override
    public String getDescription() {
        return "If input is 0,0 then return A, else return B";
    }
}
