package com.tiggerbiggo.prima.core;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Provides methods for writing images.
 */
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
    public static void writeGif(BufferedImage[] imgSequence, int BufferedImageType, int timeBetweenFramesMS, boolean loop, String filename)
    {
        try
        {
            try (ImageOutputStream output = new FileImageOutputStream(new File(filename + ".gif")))
            {
                GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImageType, timeBetweenFramesMS, loop);
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

    public static byte[] writeByteArray(BufferedImage[] imgSequence, int BufferedImageType, int timeBetweenFramesMS, boolean loop) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            try (ImageOutputStream output = new MemoryCacheImageOutputStream(outStream)) {
                GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImageType, timeBetweenFramesMS, loop);
                for (BufferedImage B : imgSequence) {
                    writer.writeToSequence(B);
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return outStream.toByteArray();
    }

    public static byte[] writeByteArray(BufferedImage[] imgSequence) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            try (ImageOutputStream output = new MemoryCacheImageOutputStream(outStream)) {
                GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_RGB, 0, true);
                for (BufferedImage B : imgSequence) {
                    writer.writeToSequence(B);
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return outStream.toByteArray();
    }
}












