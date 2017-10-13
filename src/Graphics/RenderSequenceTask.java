package Graphics;

import Core.float2;
import Core.int2;

import java.awt.image.BufferedImage;

public class RenderSequenceTask extends RenderTask
{
    BufferedImage[] imgSequence;
    int numOfFrames;
    public RenderSequenceTask(float[][] map, Gradient g, int numOfFrames) throws IllegalArgumentException
    {
        super(map, g);
        if(numOfFrames <=0) throw new IllegalArgumentException("Number of frames must be >=1");
        this.numOfFrames = numOfFrames;
        imgSequence = new BufferedImage[numOfFrames];
        for(int i=0; i<numOfFrames; i++)
        {
            imgSequence[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
    }

    @Override
    public void doTask(Object in)
    {
        int2 converted = convertObject(in);
        if(converted == null) return;

        int x, y;
        x = converted.getX();
        y = converted.getY();

        imgSequence[y/height].setRGB(
                x,
                y % height,
                g.evaluate(
                        map[x][y % height] + (y / height) / (float) numOfFrames).getRGB());
    }

    @Override
    public Object getNext()
    {
        if(current <=-1 || isDone()) return null;

        return new int2(current%width, current++/ width);
    }

    @Override
    public boolean isDone() {
        return current >= max*numOfFrames;
    }

    public BufferedImage[] getImgSequence()
    {
        return imgSequence;
    }
}
