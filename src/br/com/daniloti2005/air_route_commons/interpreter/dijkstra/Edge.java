package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

public class Edge {

    Node endNode = null;
    int edgeLength = -1;

    public Edge(Node initEndNode, int initLength){
        this.endNode = initEndNode;
        this.endNode.setDistanceFromPrevious(initLength);
        this.edgeLength = initLength;
    }

    public int getLength() {
        return this.edgeLength;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public void setEdgeLength(int edgeLength) {
        this.edgeLength = edgeLength;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "endNode=" + endNode +
                ", edgeLength=" + edgeLength +
                '}';
    }
}
