package br.com.daniloti2005.air_route_commons;

import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Edge;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Node;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DijkstraAlgorithm {
    private static Node startingNode;
    private static Node endingNode;
    private static List<Node> visitedNodes;
    private static List<Node> unvisitedNodes;
    private static List<Node> shortCosts;
    private static Route route;

    public DijkstraAlgorithm(Node starting, Node ending, Route route){
        initializeAttributes();
        setRoute(route);
        setUnvisitedNodes(route.getNodes());
        setStartingNode(starting);
        setEndingNode(ending);
    }

    public static void initialization(Node starting, Node ending, Route route){
        initializeAttributes();
        setRoute(route);
        List<Node> temp = route.getNodes();
        setUnvisitedNodes(temp);
        setStartingNode(starting);
        setEndingNode(ending);
        getStartingNode().setVisited(true);
    }

    public static void initializeAttributes() {
        Node startingNode = null;
        Node endingNode = null;
        List<Node> visitedNodes = new ArrayList<>();
        List<Node> unvisitedNodes = new ArrayList<>();
        List<Node> shortCosts = new ArrayList<>();
        Route route = null;
    }

    public static List<Node> perform() {
        initializeAttributes();
        shortCosts = new ArrayList<>();

        // Let distance of start vertex from start vertex = 0
        getStartingNode().setDistanceFromOrigin(0);
        getStartingNode().setPreviousNode(null);

        // Let distance of all other vertices from starting point as maximum value (~= infinity)
        // And set null to previous node;
        for (Node item : getUnvisitedNodes()) {
            //Others than starting Vertex
            if (!item.equalsName(getStartingNode().getName())){
                item.setDistanceFromOrigin(Integer.MAX_VALUE);
                item.setPreviousNode(null);
            }
        }

        // WHILE vertices remain unvisited
        while (isThereAnyUnvisited()) { //Visit all unvisited node
            //Visit unvisited vertex with smallest known distrance from start vertex (call this 'current vertex)
            Node currentVertex = getNodeSmallestDistanceFromStartingVertex();
            System.out.println(currentVertex.getName());
            currentVertex.setVisited(true);
                //For each unvisited neighbour of the current vertex
                for (Edge routesToNeighbour : currentVertex.getEdges()) {

                    // Calculate the distance to Starting Vertex
                    Node neighbourNode = route.getNodeFromMap(routesToNeighbour.getEndNode().getName());
                    neighbourNode.setDistanceFromPrevious(routesToNeighbour.getLength());

                    Integer distanceToStartingVertex = calculateDistancetoStartingVertex(currentVertex, routesToNeighbour.getLength());
                    System.out.println(distanceToStartingVertex);
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
        return buildShortRoute(endingNode);
    }

    public static boolean isMorePrevious(Node endNode){
        boolean isMore = false;
        isMore = endNode.isTherePreviousNode();
        return isMore;
    }

    public static List<Node> buildShortRoute(Node destination){
        boolean finish = false;
        List<Node> shortRouteList = new ArrayList<>();
        Node currentFlowNode = destination;
        shortRouteList.add(destination);
        do{
            currentFlowNode = destination.getPreviousNode();
            shortRouteList.add(currentFlowNode);
        } while ( currentFlowNode.getDistanceFromOrigin() != 0 );
        Collections.reverse(shortRouteList);
        return shortRouteList;
    }

    public static Integer calculateDistancetoStartingVertex(Node currentVertex, int distanceToPrevious) {
        boolean finish = false;
        Integer total = distanceToPrevious;
        total += currentVertex.getDistanceFromOrigin();
        return  total;
    }

    public static Integer calculateDistanceToPreviousVertex(Node here){
        Integer distance = 0;
        return here.getDistanceFromPrevious();
    }

    public static boolean isThereAnyUnvisited(){
        List<Node> unvisitedList = getUnvisitedNodes().stream().filter(Node::isUnvisited).collect(Collectors.toList());
        return unvisitedList.size() > 0;
    }

    public static List<Node> getUnvisiteds(){
        List<Node> unvisitedList = getUnvisitedNodes().stream().filter(Node::isUnvisited).collect(Collectors.toList());
        return  unvisitedList;

    }

    public static boolean isThereAnyVisited() {
        return getVisitedNodes().size() > 0;
    }

    public static List<Node> getNodesAdjacents(Node node){
        List<Edge> edges = node.getEdges();
        List<Node> adjacents = new ArrayList<>();
        for (Edge item : edges){
            adjacents.add(item.getEndNode());
        }
        return adjacents.stream().filter(Node::isUnvisited).collect(Collectors.toList());
    }

    public static Node getAdjacentsSmallestDistance(Node fromHere){
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

    public static Node getNodeSmallestDistanceFromStartingVertex(){
        Integer SmallestValue = Integer.MAX_VALUE;
        Node SmallestNode = null;
        for (Node item : getNodesAdjacents(getStartingNode())){
            item.setDistanceFromOrigin(item.getDistanceFromPrevious());
            item.setPreviousNode(getStartingNode());
            if (item.getDistanceFromOrigin() < SmallestValue) {
                SmallestValue = item.getDistanceFromOrigin();
                SmallestNode = item;
            }
        }
        return SmallestNode;
    }

    public static void addVisitedNode(Node node){
        getVisitedNodes().add(node);
    }

    public static void addUnvisitedNode(Node node){
        getUnvisitedNodes().add(node);
    }

    public static void sendToVisited(Node node){
        getUnvisitedNodes().remove(node);
        setVisitedNode(node);
    }

    public static Node getStartingNode(){
        return startingNode;
    }

    public static void setStartingNode(Node initStartingNode) {
        startingNode = initStartingNode;
        for (Node node : getUnvisitedNodes()){
            node.setStartingNode(getStartingNode());
        }
    }

    public static Node getEndingNode() {
        return endingNode;
    }

    public static void setEndingNode(Node initEndingNode) {
        endingNode = initEndingNode;
    }

    public static List<Node> getVisitedNodes() {
        return visitedNodes;
    }

    public static Integer getTotalValueResult(List<Node> nodes) {
        Integer total = 0;
        Integer size = nodes.size();
        for (Node item :
             nodes ) {
            total += item.getDistanceFromOrigin();
        }
        return total;
    }

    public static void inverseList(List lista){
        Collections.reverse(lista);
    }

    public static void setVisitedNode(Node initVisitedNode) {
        visitedNodes.add(initVisitedNode);
    }

    public static void removeUnvisitedNode(Node unvisitedNode){
        getUnvisitedNodes().remove(unvisitedNode);
    }

    public static void removeVisitedNode(Node visitedNode){
        getVisitedNodes().remove(visitedNode);
    }

    public static List<Node> getUnvisitedNodes() {
        return unvisitedNodes;
    }

    public static void setUnvisitedNode(Node initUnvisitedNode) {
        getUnvisitedNodes().add(initUnvisitedNode);
    }

    public static List<Node> getShortCosts() {
        return shortCosts;
    }

    public static void setShortCosts(List<Node> initShortCosts) {
        shortCosts = initShortCosts;
    }

    public static void setUnvisitedNodes(List<Node> initUnvisitedNodes) {
        unvisitedNodes = initUnvisitedNodes;
    }

    public static void setVisitedNodes(List<Node> initVisitedNodes) {
        visitedNodes = initVisitedNodes;
    }

    public static void changeUnvisitedToVisited(Node unvisited) {
        addVisitedNode(unvisited);
        removeUnvisitedNode(unvisited);
    }

    public static void changeVisitedToUnvisited(Node visited){
        addUnvisitedNode(visited);
        removeVisitedNode(visited);
    }

    public static Route getRoute() {
        return route;
    }

    public static void setRoute(Route initRoute) {
        route = initRoute;
    }
}
