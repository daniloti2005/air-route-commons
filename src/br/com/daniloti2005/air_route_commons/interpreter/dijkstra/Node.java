package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Node {
    private String name;
    private List<Edge> edges = null;
    private int distanceFromOrigin = Integer.MAX_VALUE;
    private Node previousNode = null;
    private Node startingNode = null;
    private boolean isVisited = false;
    private int distanceFromPrevious;

    public Node(String initName, int initDist, Node initPreviousNode, Edge... initEdges)
    {
        this.edges = new ArrayList<>();
        this.edges = Arrays.stream(initEdges).collect(Collectors.toList());
        this.name = initName;
        this.distanceFromOrigin = initDist;
        this.previousNode = initPreviousNode;
    }

    public Node(String initName, int initDist, Node initPreviousNode)
    {
        this.name = initName;
        this.distanceFromOrigin = initDist;
        this.previousNode = initPreviousNode;
        this.edges = new ArrayList<>();
    }

    public Node(String initName) {
        // same as Node a = new Node(NODE_A, Integer.MAX_VALUE, null);
        this.name = initName;
        this.distanceFromOrigin = Integer.MAX_VALUE;
        this.previousNode = null;
        this.edges = new ArrayList<>();
    }

    public void setEdge(String initNextNode, Integer initDistance ) {
            setEdge(initNextNode, initDistance);
    }

    public void setEdge(Node initNextNode, Integer initDistance ) {
        // same as Node a = new Node(NODE_A, Integer.MAX_VALUE, null);
        Edge localEdge = new Edge(initNextNode, initDistance);
        this.edges.add(localEdge);
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public List<Edge> getEdges() { return edges; }
    public int getDistanceFromOrigin () { return distanceFromOrigin; }
    public Node getPreviousNode() { return previousNode; }
    public boolean isVisited() { return isVisited; }
    public boolean isUnvisited() { return !isVisited; }
    public void setEdges(List<Edge> initEdges) { edges = initEdges; }
    public void setDistanceFromOrigin(int value) { distanceFromOrigin = value; }
    public void setPreviousNode(Node someNode) { previousNode = someNode; }
    public void setVisited(boolean val) { isVisited = val; }

    public String getName() {return name; }

    public void reset() {
        setDistanceFromOrigin(Integer.MAX_VALUE);
        setPreviousNode(null);
        setVisited(false);
    }

    public boolean isTherePreviousNode(){
        return !this.getPreviousNode().equalsName(startingNode.getName())
                && this.getPreviousNode() != null;
    }

    public int getDistanceFromPrevious() {
        return distanceFromPrevious;
    }

    public void setDistanceFromPrevious(int distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }

    public boolean equalsName(Node node) {
        return this.getName().equals(node.getName());
    }

    public boolean equalsName(String node) {
        return this.getName().equals(node);
    }

    public Node getStartingNode() {
        return startingNode;
    }

    public void setStartingNode(Node startingNode) {
        this.startingNode = startingNode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void copyTo(Node node){
        node.isVisited = this.isVisited;
        node.distanceFromOrigin = this.distanceFromOrigin;
        node.previousNode = this.previousNode;
        node.name = this.name;
        node.edges = this.edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return distanceFromOrigin == node.distanceFromOrigin && isVisited == node.isVisited && Objects.equals(name, node.name) && Objects.equals(edges, node.edges) && Objects.equals(previousNode, node.previousNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, edges, distanceFromOrigin, previousNode, isVisited);
    }


}
