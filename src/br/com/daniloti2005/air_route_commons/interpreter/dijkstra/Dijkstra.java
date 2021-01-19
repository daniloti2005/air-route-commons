package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.ArrayList;

/**
 * Created by Nick on 11/29/2017.
 */
public class Dijkstra {
    private static StringBuilder path = new StringBuilder();
    private static boolean isPathFound = false;
    private static boolean pathDoesNotExist = false;
    private static ArrayList<Node> nodesToVisit = new ArrayList<>();
    public static String getPathDijkstra(Node origin, Node destination)
    {
        path.delete( 0, path.length() );
        nodesToVisit.clear();
        nodesToVisit.add(origin);
        isPathFound = false;
        pathDoesNotExist = false;

        while(!isPathFound && !pathDoesNotExist)
        {
            Node currentNode = nodesToVisit.get(0);
            nodesToVisit.remove(0);
            currentNode.setVisited(true);
            for( Edge e : currentNode.getEdges() )
            {
                if( !e.getEndNode().isVisited() ){
                    nodesToVisit.add(e.getEndNode());
                    if( shouldUpdateEdgeNode(currentNode, e) )
                    {
                        e.getEndNode().setDistanceFromOrigin(e.getLength() + currentNode.getDistanceFromOrigin());
                        e.getEndNode().setPreviousNode(currentNode);
                    }
                }
            }
            if(destination.isVisited())
                isPathFound = true;
            else if (nodesToVisit.isEmpty())
                pathDoesNotExist = true;
        }

        if(pathDoesNotExist)
            path.append(String.format("NO PATH EXISTS BETWEEN NODES %s AND %s", origin.getName(), destination.getName()));
        else
            backtracePath(destination);

        return path.toString();
    }

    private static boolean shouldUpdateEdgeNode(Node currentNode, Edge currentEdge)
    {
        return currentEdge.getLength() + currentNode.getDistanceFromOrigin() < currentEdge.getEndNode().getDistanceFromOrigin();
    }

    private static void backtracePath(Node destination)
    {
        Node pathNode = destination;
        do{
            path.insert(0, pathNode.getName());
            pathNode = pathNode.getPreviousNode();
        }while(pathNode != null);
    }
}
