package sample;

import com.tiggerbiggo.primaplay.node.core.INode;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

public class NodeList {

  private static List<Class<? extends INode>> allImplementedNodes;

  public static List<Class<? extends INode>> getAllImplementedNodes() {
    if (allImplementedNodes == null) {
      setNodeList();
    }
    return Collections.unmodifiableList(allImplementedNodes);
  }

  private static void setNodeList() {
    allImplementedNodes = new ArrayList<>();
    Set<Class<? extends INode>> classes = new Reflections(
        "com.tiggerbiggo.primaplay.node.implemented").getSubTypesOf(INode.class);
    classes.iterator().forEachRemaining(aClass -> {
      if (!Modifier.isAbstract(aClass.getModifiers())) {
        allImplementedNodes.add(aClass);
      }
    });
  }
}
