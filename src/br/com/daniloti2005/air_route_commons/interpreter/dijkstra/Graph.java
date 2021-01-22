package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.*;

public class Graph {

    HashMap<String, Node> nodeMap = new HashMap<>();

    public Graph(List<Node> initNodes)
    {
        for(Node n: initNodes)
            nodeMap.put(n.getName(), n);
    }

    public List<Node> dijkstra(Node origin, Node destination) throws Exception {
        List<Node> path = null;
        if(nodeMap.containsKey(origin.getName()) && nodeMap.containsKey(destination.getName())) {
            resetNodesAndSetNewOrigin(origin.getName());
            Dijkstra shortPath = new Dijkstra();
            path = shortPath.getPathDijkstra(nodeMap.get(origin.getName()), nodeMap.get(destination.getName()));
        } else {
            throw new Exception("Origin or Destination node specified does not exist in the graph.");
        }

        Collections.reverse(path);

        return path;
    }

    public void resetNodesAndSetNewOrigin(String newOriginName)
    {
        for(Node n : nodeMap.values())
            n.reset();
            nodeMap.get(newOriginName).setDistanceFromOrigin(0);
    }
}
