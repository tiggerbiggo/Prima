package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderCache;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;

public class RemoveZeroNode extends NodeInOut {

    @Override
    public String getName() {
        return "Remove Zero Node";
    }

    @Override
    public String getDescription() {
        return "Deals with divide by 0 errors";
    }
}
