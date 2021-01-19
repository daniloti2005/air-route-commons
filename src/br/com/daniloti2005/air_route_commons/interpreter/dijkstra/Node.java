package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Node {
    private static List<Node> lstNode;
    private String name;
    private List<Edge> edges = null;
    private int distanceFromOrigin = -1;
    private Node previousNode = null;
    private boolean isVisited = false;

    public Node(String initName, int initDist, Node initPreviousNode, Edge... initEdges)
    {
        lstNode = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.edges = Arrays.stream(initEdges).collect(Collectors.toList());
        this.name = initName;
        this.distanceFromOrigin = initDist;
        this.previousNode = initPreviousNode;
        addLstNode(this);
    }

    public Node(String initName, int initDist, Node initNextNode)
    {
        lstNode = new ArrayList<>();
        this.name = initName;
        this.distanceFromOrigin = Integer.MAX_VALUE;
        this.previousNode = null;
        this.edges = new ArrayList<>();
        setEdge(initNextNode, initDist);
        addLstNode(this);
    }

    public Node(String initName) {
        // same as Node a = new Node(NODE_A, Integer.MAX_VALUE, null);
        lstNode = new ArrayList<>();
        this.name = initName;
        this.distanceFromOrigin = Integer.MAX_VALUE;
        this.previousNode = null;
        this.edges = new ArrayList<>();
        addLstNode(this);
    }

    public void setEdge(String initNextNode, Integer initDistance ) {
        // same as Node a = new Node(NODE_A, Integer.MAX_VALUE, null);
        boolean isThere = Node.getLstNode().stream().anyMatch(o -> o.getName().equals(initNextNode));

        Node nextNode;
        if (isThere) {
            nextNode = Node.getLstNode().stream().filter(o -> o.getName().equals(initNextNode))
                    .distinct().findFirst().get();

            Node.getLstNode().forEach(o -> {
                if (o.getName() == nextNode.getName()) {
                    o.setEdge(initNextNode, initDistance);
            }
            });
        } else {
            nextNode = new Node(initNextNode);
            nextNode.setEdge(initNextNode, initDistance);
            addLstNode(nextNode);
        }



        Edge localEdge = new Edge(nextNode, initDistance);
        this.edges.add(localEdge);

    }

    public void setEdge(Node initNode, Integer initDistance ) {
        // same as Node a = new Node(NODE_A, Integer.MAX_VALUE, null);
        Edge localEdge = new Edge(initNode, initDistance);
        this.edges.add(localEdge);
    }

    public List<Edge> getEdges() { return edges; }
    public int getDistanceFromOrigin () { return distanceFromOrigin; }
    public Node getPreviousNode() { return previousNode; }
    public boolean isVisited() { return isVisited; }

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

    public static void setLstNode(List<Node> lstNode) {
        Node.lstNode = lstNode;
    }

    public static List<Node> getLstNode() {
        return lstNode;
    }

    public static void addLstNode(Node initNode) {
        Node.lstNode.add(initNode);
    }

    public static void removeNode(Node iniNode) {
        lstNode.remove(iniNode);
    }
}
