package Core;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;

public class FileManager
{
    public static void writeGif(BufferedImage[] imgSequence, String filename)
    {
        try
        {
            try (ImageOutputStream output = new FileImageOutputStream(new File(filename + ".gif")))
            {
                GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_RGB, 0, true);
                for(BufferedImage B : imgSequence)
                    {
                    writer.writeToSequence(B);
                }
                writer.close();
            }
        }
        catch(Exception e)
        {
        }
    }
}
