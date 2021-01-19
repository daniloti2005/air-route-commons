package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

public class Edge {

    Node endNode = null;
    int edgeLength = -1;

    Edge(Node initNode, int initLength){
        endNode = initNode;
        edgeLength = initLength;
    }

    public int getLength() { return edgeLength;}
    public Node getEndNode() { return endNode; }
}
