package sample;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import guinode.GUIInputLink;
import guinode.GUINode;
import guinode.GUIOutputLink;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class NodeSerializer {

  public static final String SEP = "@";

  public static String SerializeNodePane(NodePane in){
    return SerializeList(in.nodeList);
  }

  public static String SerializeList(List<GUINode> nodeList){
    StringBuilder savedString = new StringBuilder();

    GUINode n;//nodeList.get(0);

    //savedString.append((int)n.getTranslateX());
    //savedString.append(SEP);
    //savedString.append((int)n.getTranslateY());
    //savedString.append("\n");

    for(int i=0; i<nodeList.size(); i++){
      n = nodeList.get(i);
      savedString.append(i);
      savedString.append(SEP);
      savedString.append(n.getNode().getClass().getName());
      savedString.append(SEP);
      savedString.append(toJson(n.getNode()));
      savedString.append(SEP);
      savedString.append((int)n.getLayoutX());
      savedString.append(SEP);
      savedString.append((int)n.getLayoutY());
      savedString.append("\n");
    }

    savedString.append("-\n");

    for(int i=0; i<nodeList.size(); i++){
      n = nodeList.get(i);
      List<GUIInputLink> inputs = n.getInputs();
      for(int j=0; j<inputs.size(); j++){
        GUIInputLink in = inputs.get(j);
        try {
          savedString.append(getNodeOutputNumFromInput(in, nodeList));
        }
        catch(NodeParseException e){
          continue;
        }
        savedString.append(SEP);
        savedString.append(in.getCurrentGLink().getIndex());
        savedString.append(SEP);
        savedString.append(getNodeInputNum(in, nodeList));
        savedString.append(SEP);
        savedString.append(in.getIndex());
        savedString.append("\n");
      }
    }
    return savedString.toString();
  }

  public static String toJson(INode node){
    GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes f) {
        for(Annotation a : f.getAnnotations()){
          if(a.annotationType().equals(TransferGrid.class)){
            return false;
          }
        }
        return true;
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    });
    Gson gson = builder.create();
    return gson.toJson(node);
  }

  public static int getNodeInputNum(GUIInputLink in, List<GUINode> nodeList){
    if(in == null) throw new NodeParseException("Input link passed was null");
    if(nodeList == null) throw new NodeParseException("Node List passed was null");
    for(int i=0; i<nodeList.size(); i++){
      if(in.getOwner() == nodeList.get(i)) return i;
    }
    throw new NodeParseException("Input link passed does not match with any node in the list");
  }

  private static int getNodeOutputNumFromInput(GUIInputLink in, List<GUINode> nodeList) throws NodeParseException{
    if(in == null) throw new NodeParseException("Input link passed was null");
    if(nodeList == null) throw new NodeParseException("Node List passed was null");
    GUIOutputLink out = in.getCurrentGLink();
    if(out == null) {
      throw new NodeParseException("Input link passed has no linked output");
    }
    for(int i=0; i<nodeList.size(); i++){
      if(out.getOwner() == nodeList.get(i)) return i;
    }
    throw new NodeParseException("Input link passed does not match with any node in the list");
  }

  /**Parses a saved String
   *
   * @param toParse The String to parse
   * @param listen The listener to attach to the resultant node pane
   */
  public static NodePane parseNodes(String toParse, ChangeListener listen){
    NodePane pane = new NodePane(listen);

    String[] lines = toParse.split("\n");
    List<GUINode> nodeList = new ArrayList<>();

    int currentLine = 0;
    do{
      String[] parsedLine = lines[currentLine].split(SEP);
      /*if(currentLine == 0 && parsedLine.length == 2){
        //Add the render node at position 0
        nodeList.add(new GUINode(
                Integer.parseInt(parsedLine[0]),
                Integer.parseInt(parsedLine[1]),
                pane.renderNode,
                pane,
                listen
        ));
        currentLine++;
        continue;
      }*/
      if(parsedLine.length != 5){
        throw new NodeParseException("Parsed line length != 5. Current Line: " + currentLine + ", String: " + lines[currentLine]);
      }

      GUINode parsedNode = new GUINode(
          Integer.parseInt(parsedLine[3]),
          Integer.parseInt(parsedLine[4]),
          NodeReflection.parseNode(parsedLine[1], parsedLine[2]),
          pane,
          listen);

      nodeList.add(parsedNode);

      currentLine++;
    }while(!lines[currentLine].equals("-"));

    pane.addNode(nodeList);

    currentLine++;

    while(currentLine < lines.length){
      int[] links = parseLinks(lines[currentLine]);

      GUIOutputLink output = nodeList.get(links[0]).getOutputs().get(links[1]);
      GUIInputLink input = nodeList.get(links[2]).getInputs().get(links[3]);

      input.link(output);
      currentLine++;
    }
    return pane;
  }

      /*
  //at the top we have a description of all the nodes, their position and type
  //in the form id:name:x:y
  0:RenderNode:100:100
  1:MapGenNode:200:100
  2:AnimNode:300:100
  //... etc, then we have a list of links in the form outputId:outputLinkNum:inputId:inputLinkNum

  //so here we link MapGenNode's output to AnimNode's input
  1:0:2:0

  //then we link AnimNode to RenderNode
  2:0:0:0
  * */

  public static int[] parseLinks(String str){
    String[] strings = str.split(SEP);
    if(strings.length != 4) throw new NodeParseException("Parsed line length != 4 when parsing links. String: " + str);
    int[] toReturn = new int[4];
    for(int i=0; i<=3; i++){
      toReturn[i] = Integer.parseInt(strings[i]);
    }
    return toReturn;
  }

  public static NodePane parseFromFile(File file, ChangeListener listener) throws NodeParseException{
    try {
      List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
      String result = join(lines, "\n");
      return parseNodes(result, listener);
    } catch (IOException e) {
      return null;
    }
  }

  public static String saveToFile(NodePane pane, File toWrite) throws IOException {
    Objects.requireNonNull(pane);
    Objects.requireNonNull(toWrite);

    if(!toWrite.exists())
    {
      try {
        toWrite.createNewFile();
      } catch (IOException e) {
        throw e;
      }
    }

    String ser = SerializeNodePane(pane);

    try(FileWriter fw = new FileWriter(toWrite)) {
      fw.append(ser);
    } catch (IOException e) {
      throw e;
    }
    return ser;
  }

  private static String join(Collection var0, String var1) {
    StringBuffer var2 = new StringBuffer();

    for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
      if (var2.length() != 0) {
        var2.append(var1);
      }
    }

    return var2.toString();
  }
}
