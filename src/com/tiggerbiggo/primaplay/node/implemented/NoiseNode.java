package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

import java.util.Random;

public class NoiseNode implements NodeHasOutput{
    long seed;

    public NoiseNode(long seed) {
        this.seed = seed;
    }

    public NoiseNode() {
        this(new Random().nextLong());
    }

    VectorOutputLink out = new VectorOutputLink() {
        @Override
        public Vector2 get(RenderParams p) {
            long toAdd = p.x() + (p.y() * p.width());
            return rand(seed + toAdd);
        }
    };

    public Vector2 rand(long seed){
        Random r = new Random(seed);
        return new Vector2(r.nextDouble(), r.nextDouble());
    }


    @Override
    public OutputLink<?>[] getOutputs() {
        return new OutputLink[]{out};
    }

    @Override
    public OutputLink<?> getOutput(int n) {
        return out;
    }
}
