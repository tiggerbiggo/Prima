package sample;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import gnode.GInputLink;
import gnode.GOutputLink;
import gnode.GUINode;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class NodeSerializer {

  public static final String SEP = "@";

  public static String SerializeList(List<GUINode> nodeList){
    StringBuilder savedString = new StringBuilder();

    GUINode n = nodeList.get(0);

    savedString.append((int)n.getTranslateX());
    savedString.append(SEP);
    savedString.append((int)n.getTranslateY());
    savedString.append("\n");

    for(int i=1; i<nodeList.size(); i++){
      n = nodeList.get(i);
      savedString.append(i);
      savedString.append(SEP);
      savedString.append(n.getNode().getClass().getName());
      savedString.append(SEP);
      savedString.append(toJson(n.getNode()));
      savedString.append(SEP);
      savedString.append((int)n.getTranslateX());
      savedString.append(SEP);
      savedString.append((int)n.getTranslateY());
      savedString.append("\n");
    }

    savedString.append("-\n");

    for(int i=0; i<nodeList.size(); i++){
      n = nodeList.get(i);
      List<GInputLink> inputs = n.getInputs();
      for(int j=0; j<inputs.size(); j++){
        GInputLink in = inputs.get(j);
        savedString.append(getNodeOutputNumFromInput(in, nodeList));
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

  public static int getNodeInputNum(GInputLink in, List<GUINode> nodeList){
    if(in == null) throw new NodeParseException("Input link passed was null");
    if(nodeList == null) throw new NodeParseException("Node List passed was null");
    for(int i=0; i<nodeList.size(); i++){
      if(in.getOwner() == nodeList.get(i)) return i;
    }
    throw new NodeParseException("Input link passed does not match with any node in the list");
  }

  private static int getNodeOutputNumFromInput(GInputLink in, List<GUINode> nodeList) throws NodeParseException{
    if(in == null) throw new NodeParseException("Input link passed was null");
    if(nodeList == null) throw new NodeParseException("Node List passed was null");
    GOutputLink out = in.getCurrentGLink();
    if(out == null) {
      throw new NodeParseException("Input link passed has no linked output");
    }
    for(int i=0; i<nodeList.size(); i++){
      if(out.getOwner() == nodeList.get(i)) return i;
    }
    throw new NodeParseException("Input link passed does not match with any node in the list");
  }

  public static void parseNodes(String toParse, MainController control){
    control.clearNodes();

    String[] lines = toParse.split("\n");
    List<GUINode> nodeList = new ArrayList<>();

    int currentLine = 0;
    do{
      String[] parsedLine = lines[currentLine].split(SEP);
      if(currentLine == 0 && parsedLine.length == 2){
        //Add the render node at position 0
        nodeList.add(new GUINode(
                Integer.parseInt(parsedLine[0]),
                Integer.parseInt(parsedLine[1]),
                control.renderNode,
                control.nodeCanvas,
                control
        ));
        currentLine++;
        continue;
      }
      else if(parsedLine.length != 5){
        throw new NodeParseException("Parsed line length != 5. Current Line: " + currentLine + ", String: " + lines[currentLine]);
      }

      nodeList.add(
          new GUINode(
              Integer.parseInt(parsedLine[3]),
              Integer.parseInt(parsedLine[4]),
              NodeReflection.parseNode(parsedLine[1], parsedLine[2]),
              control.nodeCanvas,
              control));

      currentLine++;
    }while(!lines[currentLine].equals("-"));

    control.nodeCanvas.getChildren().addAll(nodeList);
    control.nodeList.addAll(nodeList);

    currentLine++;

    do{
      int[] links = parseLinks(lines[currentLine]);

      GOutputLink output = nodeList.get(links[0]).getOutputs().get(links[1]);
      GInputLink input = nodeList.get(links[2]).getInputs().get(links[3]);

      input.link(output);



      currentLine++;
    }while(currentLine < lines.length);
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
}
