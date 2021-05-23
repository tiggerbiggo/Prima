package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.NumberOutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ConstantNumberNode extends NodeHasOutput {

    @TransferGrid
    float number = 0;

    NumberOutputLink out;

    public ConstantNumberNode(){
        out = new NumberOutputLink("Output") {
            @Override
            public Float get(RenderParams p) {
                return number;
            }

            @Override
            public void generateGLSLMethod(StringBuilder s) {
                throw new NotImplementedException();
            }

            @Override
            public String getMethodName() {
                return null;
            }
        };
        addOutput(out);
    }

    @Override
    public String getName() {
        return "Constant Number Node";
    }

    @Override
    public String getDescription() {
        return "Outputs a constant number";
    }
}
