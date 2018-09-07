package sample;

import gnode.GInputLink;
import gnode.GOutputLink;
import gnode.GUINode;
import java.util.ArrayList;
import java.util.List;

public class NodeSerializer {



  public static String SerializeList(List<GUINode> nodeList){
    StringBuilder savedString = new StringBuilder();

    for(int i=1; i<nodeList.size(); i++){
      GUINode n = nodeList.get(i);
      savedString.append(i);
      savedString.append(":");
      savedString.append(n.getNode().getClass().getName());
      savedString.append(":");
      savedString.append((int)n.getTranslateX());
      savedString.append(":");
      savedString.append((int)n.getTranslateY());
      savedString.append("\n");
    }

    savedString.append("-\n");

    for(int i=0; i<nodeList.size(); i++){
      GUINode n = nodeList.get(i);
      List<GInputLink> inputs = n.getInputs();
      for(int j=0; j<inputs.size(); j++){
        GInputLink in = inputs.get(j);
        savedString.append(getNodeOutputNumFromInput(in, nodeList));
        savedString.append(":");
        savedString.append(in.getCurrentGLink().getIndex());
        savedString.append(":");
        savedString.append(getNodeInputNum(in, nodeList));
        savedString.append(":");
        savedString.append(in.getIndex());
        savedString.append("\n");
      }
    }
    return savedString.toString();
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

    //Add the render node at position 0
    nodeList.add(control.nodeList.get(0));

    int currentLine = 0;
    do{
      String[] parsedLine = lines[currentLine].split(":");
      if(parsedLine.length != 4){
        throw new NodeParseException("Parsed line length != 4. Current Line: " + currentLine + ", String: " + lines[currentLine]);
      }

      nodeList.add(
          new GUINode(
              Integer.parseInt(parsedLine[2]),
              Integer.parseInt(parsedLine[3]),
              NodeReflection.parseNode(parsedLine[1]),
              control.nodeCanvas,
              control));

      currentLine++;
    }while(!lines[currentLine].equals("-"));

    currentLine++;

    do{
      int[] links = parseLinks(lines[currentLine]);

      GOutputLink output = nodeList.get(links[0]).getOutputs().get(links[1]);
      GInputLink input = nodeList.get(links[2]).getInputs().get(links[3]);

      input.link(output);
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
    String[] strings = str.split(":");
    if(strings.length != 4) throw new NodeParseException("Parsed line length != 4 when parsing links. String: " + str);
    int[] toReturn = new int[4];
    for(int i=0; i<=3; i++){
      toReturn[i] = Integer.parseInt(strings[i]);
    }
    return toReturn;
  }
}
