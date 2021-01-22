package br.com.daniloti2005.air_route_commons;

import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Edge;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Node;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DijkstraAlgorithm {
    private Node startingNode;
    private Node endingNode;
    private List<Node> visitedNodes;
    private List<Node> unvisitedNodes;
    private List<Node> shortCosts;
    private Route route;

    public DijkstraAlgorithm(Node starting, Node ending, Route route){
        initializeAttributes();
        setRoute(route);
        setUnvisitedNodes(route.getNodes());
        setStartingNode(starting);
        setEndingNode(ending);
    }

    public void initializeAttributes() {
        Node startingNode = null;
        Node endingNode = null;
        List<Node> visitedNodes = new ArrayList<>();
        List<Node> unvisitedNodes = new ArrayList<>();
        List<Node> shortCosts = new ArrayList<>();
        Route route = null;
    }

    public List<Node> perform() {
        shortCosts = new ArrayList<>();

        // Let distance of start vertex from start vertex = 0
        getStartingNode().setDistanceFromOrigin(0);
        getStartingNode().setPreviousNode(getStartingNode());

        // Let distance of all other vertices from starting point as maximum value (~= infinity)
        // And set null to previous node;
        for (Node item : getUnvisitedNodes()) {
            //Others than starting Vertex
            if (!item.equalsName(getStartingNode().getStartingNode())){
                item.setDistanceFromOrigin(Integer.MAX_VALUE);
                item.setPreviousNode(null);
            }

        }

        // WHILE vertices remain unvisited
        while (isThereAnyUnvisited()) { //Visit all unvisited node
            //Visit unvisited vertex with smallest known distrance from start vertex (call this 'current vertex)
            Node currentVertex = getNodeSmallestDistanceFromStartingVertex();
            if (currentVertex != null) {
                //For each unvisited neighbour of the current vertex
                for (Edge routesToNeighbour : currentVertex.getEdges()) {
                    // Calculate the distance to Starting Vertex
                    Node neighbourNode = route.getNodeFromMap(routesToNeighbour.getEndNode().getName());
                    neighbourNode.setPreviousNode(currentVertex);
                    Integer distanceToStartingVertex = calculateDistancetoStartingVertex(neighbourNode, routesToNeighbour.getLength());
                    // If calculated distance of this vertex is less than the known distance
                    if (distanceToStartingVertex < neighbourNode.getDistanceFromOrigin()) {
                        // Update shortest distance from start vertex
                        neighbourNode.setDistanceFromOrigin(distanceToStartingVertex);
                        // Update previous vertex with previous node
                        neighbourNode.setPreviousNode(currentVertex);
                        // Update distance from previous node
                        neighbourNode.setDistanceFromPrevious(routesToNeighbour.getLength());
                    }
                    currentVertex.setVisited(true);
                }
            }
        }
        return buildShortRoute(endingNode);
    }

    private List<Node> buildShortRoute(Node destination){
        boolean finish = false;
        List<Node> shortRouteList = new ArrayList<>();
        while (finish != true){
            shortRouteList.add(destination);
            if (destination.isTherePreviousNode()){
                if (destination.equalsName(getStartingNode().getName())){
                    finish = true;
                } else {
                    destination = destination.getPreviousNode();
                }
            }
        }
        Collections.reverse(shortRouteList);
        return shortRouteList;
    }

    private Integer calculateDistancetoStartingVertex(Node neighbourNode, int distanceToPrevious) {
        boolean finish = false;
        Integer total = distanceToPrevious;
        while (finish != true){
            if (neighbourNode.isTherePreviousNode()){
                Node previousNode = neighbourNode.getPreviousNode();
                total += previousNode.getDistanceFromPrevious();
                if (neighbourNode.equalsName(getStartingNode().getName())){
                    finish = true;
                }
            }
        }
        return  total;
    }

    private Integer calculateDistanceToPreviousVertex(Node here){
        Integer distance = 0;
        return here.getDistanceFromPrevious();
    }

    private boolean isThereAnyUnvisited(){
        return getUnvisitedNodes().size() > 0;
    }

    private boolean isThereAnyVisited() {
        return getVisitedNodes().size() > 0;
    }

    private List<Node> getNodesAdjacents(Node node){
        List<Edge> edges = node.getEdges();
        List<Node> adjacents = new ArrayList<>();
        for (Edge item : edges){
            adjacents.add(item.getEndNode());
        }
        return adjacents;
    }

    private Node getAdjacentsSmallestDistance(Node fromHere){
        Integer SmallestValue = Integer.MAX_VALUE;
        Node SmallestNode = null;
        for (Node item : getNodesAdjacents(fromHere)){
            if (item.getDistanceFromOrigin() < SmallestValue) {
                    SmallestValue = item.getDistanceFromOrigin();
                    SmallestNode = item;
            }
        }
        return SmallestNode;
    }

    private Node getNodeSmallestDistanceFromStartingVertex(){
        Integer SmallestValue = Integer.MAX_VALUE;
        Node SmallestNode = null;
        for (Node item : getNodesAdjacents(getStartingNode())){
            if (item.getDistanceFromOrigin() <= SmallestValue) {
                SmallestValue = item.getDistanceFromOrigin();
                SmallestNode = item;
            }
        }
        return SmallestNode;
    }

    private void addVisitedNode(Node node){
        getVisitedNodes().add(node);
    }

    private void addUnvisitedNode(Node node){
        getUnvisitedNodes().add(node);
    }

    private void sendToVisited(Node node){
        getUnvisitedNodes().remove(node);
        setVisitedNode(node);
    }

    public Node getStartingNode(){
        return startingNode;
    }

    public void setStartingNode(Node initStartingNode) {
        this.startingNode = initStartingNode;
        for (Node node : getUnvisitedNodes()){
            node.setStartingNode(getStartingNode());
        }
    }

    public Node getEndingNode() {
        return endingNode;
    }

    public void setEndingNode(Node endingNode) {
        this.endingNode = endingNode;
    }

    public List<Node> getVisitedNodes() {
        return visitedNodes;
    }

    public Integer getTotalValueResult(List<Node> nodes) {
        Integer total = 0;
        Integer size = nodes.size();
        for (Node item :
             nodes ) {
            total += item.getDistanceFromOrigin();
        }
        return total;
    }

    public void inverseList(List lista){
        Collections.reverse(lista);
    }

    public void setVisitedNode(Node visitedNode) {
        this.visitedNodes.add(visitedNode);
    }

    public void removeUnvisitedNode(Node unvisitedNode){
        getUnvisitedNodes().remove(unvisitedNode);
    }

    public void removeVisitedNode(Node visitedNode){
        getVisitedNodes().remove(visitedNode);
    }

    public List<Node> getUnvisitedNodes() {
        return unvisitedNodes;
    }

    public void setUnvisitedNode(Node unvisitedNode) {
        getUnvisitedNodes().add(unvisitedNode);
    }

    public List<Node> getShortCosts() {
        return shortCosts;
    }

    public void setShortCosts(List<Node> shortCosts) {
        this.shortCosts = shortCosts;
    }

    public void setUnvisitedNodes(List<Node> unvisitedNodes) {
        this.unvisitedNodes = unvisitedNodes;
    }

    public void setVisitedNodes(List<Node> visitedNodes) {
        this.visitedNodes = visitedNodes;
    }

    public void changeUnvisitedToVisited(Node unvisited) {
        addVisitedNode(unvisited);
        removeUnvisitedNode(unvisited);
    }

    public void changeVisitedToUnvisited(Node visited){
        addUnvisitedNode(visited);
        removeVisitedNode(visited);
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
