package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.*;


public class Dijkstra {
    private List<Node> path = new ArrayList<>();
    private boolean isPathFound = false;
    private boolean pathDoesNotExist = false;
    private ArrayList<Node> nodesToVisit = new ArrayList<>();

    public List<Node> getPathDijkstra(Node origin, Node destination) throws Exception {

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
            throw new Exception((String.format("NO PATH EXISTS BETWEEN NODES %s AND %s", origin.getName(), destination.getName())));
        else
            backtracePath(destination);

        return path;
    }

    private boolean shouldUpdateEdgeNode(Node currentNode, Edge currentEdge)
    {
        return currentEdge.getLength() + currentNode.getDistanceFromOrigin() < currentEdge.getEndNode().getDistanceFromOrigin();
    }

    private void backtracePath(Node destination)
    {
        Node pathNode = destination;
        do{
            path.add(pathNode);
            pathNode = pathNode.getPreviousNode();
        }while(pathNode != null);
    }
}
