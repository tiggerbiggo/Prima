package com.tiggerbiggo.prima.primaplay.core;

import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
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
  public static BufferedImage[] getImgsFromFolder(String dirName, boolean sort)throws IllegalArgumentException {
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

      List<File> imageFiles = Arrays.asList(dir.listFiles(filter));
      if (imageFiles.size() == 0) {
        return null;
      }

      if (sort) {
        imageFiles.sort(new Comparator<File>() {
          @Override
          public int compare(File o1, File o2) {
            String sA, sB;
            sA = o1.getName();
            sB = o2.getName();

            sA = sA.substring(sA.lastIndexOf(' ') + 1, sA.indexOf('.'));
            sB = sB.substring(sB.lastIndexOf(' ') + 1, sB.indexOf('.'));

            return Integer.compare(Integer.parseInt(sA), Integer.parseInt(sB));
          }
        });
      }

      BufferedImage[] toReturn = new BufferedImage[imageFiles.size()];

      for (int i = 0; i < imageFiles.size(); i++) {
        try {
          toReturn[i] = ImageIO.read(imageFiles.get(i));
          System.out.println(imageFiles.get(i).getName());
        } catch (IOException | IllegalArgumentException e) {
          System.err
              .printf("Exception when reading image from file '%s', %s",
                  imageFiles.get(i).toString(),
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

  public static BufferedImage[] getImgsFromFolder(String dirName) throws IllegalArgumentException {
    return getImgsFromFolder(dirName, false);
  }

  public static SafeImage safeGetImg(String filepath){
    File f = new File(filepath);
    if (!f.exists() || !f.canRead()) {
      System.err.println(
          "Error in safeGetImg: File (" +
              filepath +
              ") does not exist or we do not have permission to read it.");
      return new SafeImage(1, 1);
    }

    try {
      return new SafeImage(ImageIO.read(f));
    } catch (IOException e) {
      return new SafeImage(1, 1);
    }
  }

  private static final FileChooser fileChooser = new FileChooser();

  public static File showOpenDialogue() throws IOException {
    return showOpenDialogue(null);
  }

  public static File showOpenDialogue(Stage stage) throws IOException {
    new File("saves/").createNewFile();
    fileChooser.getExtensionFilters().clear();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Prima layout file", "*.prim"));
    fileChooser.setTitle("Load");
    fileChooser.setInitialDirectory(new File("saves/"));
    File file = fileChooser.showOpenDialog(stage);
    if(file != null){
      return file;
    }
    throw new FileNotFoundException("User cancelled input, or file path invalid.");
  }

  public static File showSaveDialogue() throws IOException{
    return showSaveDialogue(null);
  }

  public static File showSaveDialogue(Stage stage) throws IOException {
    new File("saves/").createNewFile();
    fileChooser.getExtensionFilters().clear();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Prima layout file", "*.prim"));
    fileChooser.setTitle("Save");
    fileChooser.setInitialDirectory(new File("saves/"));
    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      return file;
    }
    throw new FileNotFoundException("User cancelled input, or file path invalid.");
  }
}














