package com.tiggerbiggo.prima.primaplay.core;

import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

/**
 * Provides methods for reading and writing images.
 */
public class FileManager {

  public static void writeGif(BufferedImage[] imgSequence, int BufferedImageType,
      int timeBetweenFramesMS, boolean loop, File file) {
    try {
      try (ImageOutputStream output = new FileImageOutputStream(file)) {
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

  public static void writeGif(BufferedImage[] imgSequence, File file) {
    writeGif(imgSequence, BufferedImage.TYPE_INT_RGB, 0, true, file);
  }


  public static void writeVideo(BufferedImage[] imgSequence, File file, int timesToLoop){
    if(timesToLoop <= 0)return;

    SeekableByteChannel out = null;
    try {
      out = NIOUtils.writableFileChannel(file.getAbsolutePath());

      AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
      for(int n=0; n<timesToLoop; n++) {
        for (int i = 0; i < imgSequence.length; i++) {
          encoder.encodeImage(imgSequence[i]);
        }
      }
      // Finalize the encoding, i.e. clear the buffers, write the header, etc.
      encoder.finish();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      NIOUtils.closeQuietly(out);
    }
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

  public static void writeImage(BufferedImage img, File out) throws IOException {
    ImageIO.write(img, out.getName().substring(out.getName().lastIndexOf('.')+1), out);
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
      File dir = new File(dirName);//fromRelative(dirName);
      if (!dir.isDirectory()) {
        dir = fromRelative(dirName);
        if(!dir.isDirectory())return null;
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
    return safeGetImg(fromRelative(filepath));

  }

  public static SafeImage safeGetImg(File f){
    if (!f.exists() || !f.canRead()) {
      System.err.println(
          "Error in safeGetImg: File (" +
              f.toString() +
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
  private static final DirectoryChooser dirChooser = new DirectoryChooser();

  public static File showFolderDialogue(String path) throws IOException{
    File f = fromRelative(path);
    f.mkdirs();

    dirChooser.setInitialDirectory(f);
    return showFolderDialogue();
  }

  public static File showFolderDialogue() throws IOException{
    File f = dirChooser.showDialog(null);

    if(f != null){
      return f;
    }
    throw new FileNotFoundException("User cancelled input, or file path invalid.");
  }

  {
    setInitialDirectory(fromRelative(""));
  }

  public static void setInitialDirectory(File f){
    fileChooser.setInitialDirectory(f);
  }

  public static File showOpenDialogue() throws IOException{
    return showOpenDialogue(PRIM);
  }

  public static File showOpenDialogue(ExtensionFilter ... filters) throws IOException{
    return showOpenDialogue(null, filters);
  }

  public static File showOpenDialogue(Stage stage,  ExtensionFilter ... filters) throws IOException {
    fileChooser.getExtensionFilters().clear();
    fileChooser.getExtensionFilters().addAll(filters);
    fileChooser.setTitle("Load");

    File file = fileChooser.showOpenDialog(stage);
    if(file != null){
      setInitialDirectory(file.getParentFile());
      return file;
    }
    throw new FileNotFoundException("User cancelled input, or file path invalid.");
  }

  public static File showSaveDialogue() throws IOException{
    return showSaveDialogue(null, PRIM);
  }

  public static File showSaveDialogue(Stage stage, ExtensionFilter ... filters) throws IOException {
    fileChooser.getExtensionFilters().clear();
    fileChooser.getExtensionFilters().addAll(filters);
    fileChooser.setTitle("Save");

    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      setInitialDirectory(file.getParentFile());
      return file;
    }
    throw new FileNotFoundException("User cancelled input, or file path invalid.");
  }

  public static String getRunningPath(){
    String path = FileManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    try {
      String decoded = URLDecoder.decode(path, "UTF-8");

      if(decoded.endsWith("jar"))
        decoded = decoded.substring(0, decoded.lastIndexOf("/") + 1);

      return decoded;
    } catch (UnsupportedEncodingException e) {
      System.err.println("Unable to get jar path: ");
      e.printStackTrace();
      System.exit(0);
    }
    return "";
  }

  public static File fromRelative(String rel){
    return new File(getRunningPath() + rel);
  }

  public static final ExtensionFilter PRIM = new ExtensionFilter("Prima Layout File", "*.prim");

  public static final ExtensionFilter GIF = new ExtensionFilter("Animated Gif", "*.gif");

  public static final ExtensionFilter MP4 = new ExtensionFilter("MP4 file", "*.mp4");

  public static final ExtensionFilter[] IMGS = new ExtensionFilter[]{
      new ExtensionFilter("PNG file", "*.png"),
      new ExtensionFilter("JPEG file", "*.jpeg, *.jpg"),
      new ExtensionFilter("TIFF file", "*.tiff, *.tif"),
      new ExtensionFilter("BMP file", "*.bmp"),
      new ExtensionFilter("All files", "*.*")
  };

}














