package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<String, Node> nodeMap = new HashMap<>();

    Graph(List<Node> initNodes)
    {
        for(Node n: initNodes)
            nodeMap.put(n.getName(), n);
    }

    public String dijkstra(String originName, String destinationName)
    {
        String path = "";
        if(nodeMap.containsKey(originName) && nodeMap.containsKey(destinationName)) {
            resetNodesAndSetNewOrigin(originName);
            path = Dijkstra.getPathDijkstra(nodeMap.get(originName), nodeMap.get(destinationName));
        } else {
            path = "Origin or Destination node specified does not exist in the graph.";
        }

        return path;
    }

    public void resetNodesAndSetNewOrigin(String newOriginName)
    {
        for(Node n : nodeMap.values())
            n.reset();
        nodeMap.get(newOriginName).setDistanceFromOrigin(0);
    }
}
