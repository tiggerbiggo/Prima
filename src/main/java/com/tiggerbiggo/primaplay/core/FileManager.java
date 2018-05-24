package com.tiggerbiggo.primaplay.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

/**
 * Provides methods for reading and writing images.
 */
public class FileManager {

  public static void writeGif(BufferedImage[] imgSequence, int BufferedImageType,
      int timeBetweenFramesMS, boolean loop, String filename) {
    try {
      try (ImageOutputStream output = new FileImageOutputStream(new File(filename + ".gif"))) {
        GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImageType,
            timeBetweenFramesMS, loop);
        for (BufferedImage B : imgSequence) {
          writer.writeToSequence(B);
        }
        writer.close();
      }
    } catch (Exception e) {
    }
  }

  public static void writeGif(BufferedImage[] imgSequence, String filename) {
    writeGif(imgSequence, BufferedImage.TYPE_INT_RGB, 0, true, filename);
  }

  public static byte[] writeByteArray(BufferedImage img) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      ImageIO.write(img, "png", out);
      out.flush();
      return out.toByteArray();
    } catch (Exception e) {
      return null;
    }
  }

  public static byte[] writeByteArray(BufferedImage[] imgSequence, int BufferedImageType,
      int timeBetweenFramesMS, boolean loop) {

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    try {
      try (ImageOutputStream output = new MemoryCacheImageOutputStream(outStream)) {
        GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImageType,
            timeBetweenFramesMS, loop);
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
    return writeByteArray(imgSequence, BufferedImage.TYPE_INT_RGB, 0, true);
  }

  /**
   * Reads in all images from a directory in an arbitrary order.
   *
   * @param dirName The directory to read images from
   * @return An array of images, or null if none are found
   * @throws IllegalArgumentException if dirName is null
   */
  public static BufferedImage[] getImgsFromFolder(String dirName) throws IllegalArgumentException {
    try {
      File dir = new File(dirName);
      if (!dir.isDirectory()) {
        return null;
      }

      FilenameFilter filter = new FilenameFilter() {
        String[] filenames = {".png", ".jpg", ".tiff"};

        /**
         * @param
         *
         * @return boolean
         */
        @Override
        public boolean accept(File dir, String name) {
          for (String s : filenames) {
            if (name.endsWith(s)) {
              return true;
            }
          }
          return false;
        }
      };

      File[] imageFiles = dir.listFiles(filter);
      if (imageFiles.length == 0) {
        return null;
      }

      BufferedImage[] toReturn = new BufferedImage[imageFiles.length];

      for (int i = 0; i < imageFiles.length; i++) {
        try {
          toReturn[i] = ImageIO.read(imageFiles[i]);
        } catch (IOException | IllegalArgumentException e) {
          System.err
              .printf("Exception when reading image from file '%s', %s", imageFiles[i].toString(),
                  e.toString());
          return null;
        }
      }
      return toReturn;
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("dirName was null");
    } catch (SecurityException e) {
      System.err.println(
          "Error in FileManager.getImgsFromFolder: Access was denied to directory \"" + dirName
              + "\"");
    }
    return null;
  }
}














