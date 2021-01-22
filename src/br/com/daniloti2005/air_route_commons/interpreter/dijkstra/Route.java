package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import br.com.daniloti2005.air_route_commons.singleton.route.RouteSingleton;

import java.util.*;

public class Route {

    private Map<String,Node> mapRoutes = null;


    public Route() {
        mapRoutes = new HashMap<String,Node>() ;

    }


    public void addRoute(String origin, String destination, Integer cost) {
        setMapRoutes(origin, destination, cost);
    }

    public void addRoute(Node originNode, Node destinationNode, Integer cost){
        setMapRoutes(originNode, createEdge(destinationNode, cost));
    }

    public List<Node> getNodes() {
        List<Node> nodesList = new ArrayList<>();
        for (Map.Entry<String,Node> item: mapRoutes.entrySet() ) {
            nodesList.add(item.getValue());
        }
        return nodesList;
    }

    public Node getNodeFromMap(String nameNode){
        Node ret = null;
        for (Map.Entry<String, Node> item: mapRoutes.entrySet() ) {
            if (item.getKey().equals(nameNode)) {
                ret = item.getValue();
            }
        }
        return ret;
    }

    public Edge getEdgeFromMap(String originNode, String nameDestionation){
        Edge ret = null;
        for (Map.Entry<String, Node> item: mapRoutes.entrySet()){
            if (item.getKey().equals(originNode)) {
                for (Edge oItem : item.getValue().getEdges()) {
                    if (oItem.getEndNode().equalsName(nameDestionation)){
                        ret = oItem;
                    }
                }
            }
        }
        return ret;
    }

    public Map.Entry<String, Node> getMapEntryFromMapByOriginAndDestination(String originNode, String nameDestionation){
        Map.Entry<String, Node> ret = null;
        for (Map.Entry<String, Node> item: mapRoutes.entrySet()){
            if (item.getKey().equals(originNode)) {
                for (Edge oItem : item.getValue().getEdges()) {
                    if (oItem.getEndNode().equalsName(nameDestionation)){
                        ret = item;
                    }
                }
            }
        }
        return ret;
    }

    public boolean isThereThisMapEntryFromMap(String originNode, String nameDestionation){
        boolean ret = false;
        for (Map.Entry<String, Node> item: mapRoutes.entrySet()){
            if (item.getKey().equals(originNode)) {
                for (Edge oItem : item.getValue().getEdges()) {
                    if (oItem.getEndNode().equalsName(nameDestionation)){
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    public boolean isEdgeThisOriginFromMap(String originNode, String nameDestination){
        boolean ret = false;
        for (Map.Entry<String, Node> item: mapRoutes.entrySet()){
            if (item.getKey().equals(originNode)) {
                for (Edge oItem : item.getValue().getEdges()) {
                    if (oItem.getEndNode().equalsName(nameDestination)){
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    public boolean isNotEdgeThisOriginFromMap(String origin, String destination){
        return !isEdgeThisOriginFromMap(origin, destination);
    }

    public boolean isNodeInMapByName(String name){
        boolean ret = false;
        for (Map.Entry<String, Node> item: mapRoutes.entrySet() ) {
            if (item.getKey().equals(name)) {
                ret = true;
            }
        }
        return ret;
    }

    public boolean isNotNodeInMapByName(String name) {
        return !isNodeInMapByName(name);
    }

    public Edge createEdge(Node destination, int edge) {
        return new Edge(destination, edge);
    }

    public Node createNode(String origin){
        return new Node(origin,Integer.MAX_VALUE, null);
    }

    public void addNode(Node node){
         getMapRoutes().put(node.getName(),node);
    }


    public void addEdge(String nameNode, List<Edge> listEdge){
        for (Edge e: listEdge){
            getMapRoutes().get(nameNode).addEdge(e);
        }
    }

    public void addEdge(Node node, List<Edge> listEdge){
        for (Edge e: listEdge){
            getMapRoutes().get(node).addEdge(e);
        }
    }

    public Map<String, Node> getMapRoutes() {
        return mapRoutes;
    }


    public void saveMapInMemory(Map<String, Node> mapaEdges){
        RouteSingleton.setMapRoute(mapaEdges);
    }

    public Map<String, Node> getMapInMemory(){
        return RouteSingleton.getMapRoute();
    }


    public void makeRoute(String origin, String destination, Integer cost){
        Node originLocal = createNodeIfNotExists(origin);
        Node destinationLocal = createNodeIfNotExists(destination);

        List<Edge> edgeList = new ArrayList<>();

        originLocal.addEdge(createEdge(destinationLocal, cost));

        addNode(destinationLocal);
        addNode(originLocal);
    }

    public void setMapRoutes(String origin, String destination, Integer cost){
        boolean isOriginNew = false;
        boolean isDestinationNew = false;

        Node originIntoMap = getNodeFromMap(origin);
        Node destinationIntoMap = getNodeFromMap(destination);

        if (originIntoMap == null)
        originIntoMap = createNodeIfNotExists(origin, originIntoMap);

        if (destinationIntoMap == null)
        destinationIntoMap = createNodeIfNotExists(destination, destinationIntoMap);

        Edge localEdge = createEdgeIfNotExists(origin, destination, cost, originIntoMap, destinationIntoMap);

        if (isNodeInMapByName(origin)) {
            Node nodeLocal = null;
            if (isThereThisMapEntryFromMap(origin, destination)) {
                nodeLocal = getMapEntryFromMapByOriginAndDestination(origin, destination).getValue();
            } else {
                nodeLocal = originIntoMap;
            }

            if (localEdge == null) {
                mapRoutes.get(nodeLocal.getName()).getEdges().forEach(p -> {
                    if (p.getEndNode().equalsName(destination)) {
                        p.setEdgeLength(cost);
                    }

                });
            } else {
                mapRoutes.put(nodeLocal.getName(), nodeLocal);
            }
        } else {
            Node nodeLocal = null;
            if (isThereThisMapEntryFromMap(origin, destination)) {
                nodeLocal = getMapEntryFromMapByOriginAndDestination(origin, destination).getValue();
            } else {
                nodeLocal = originIntoMap;
            }

            List<Edge> localList = new ArrayList<>();
            localList.add(localEdge);
            mapRoutes.put(nodeLocal.getName(), nodeLocal);
        }
    }

    private Node createNodeIfNotExists(String nodeName) {
        Node ret = null;
        if (isNodeInMapByName(nodeName)) {
            ret = getNodeFromMap(nodeName);
        } else {
            ret = createNode(nodeName);
        }
        return ret;
    }

    private Node createNodeIfNotExists(String origin, Node originIntoMap) {
        boolean isOriginNew;
        if (originIntoMap == null) {
            originIntoMap = createNode(origin);
            isOriginNew = true;
        }
        return originIntoMap;
    }

    private Edge createEdgeIfNotExists(String origin, String destination, Integer cost, Node originIntoMap, Node destinationIntoMap) {
        Edge ret = null;
        if (isNotEdgeThisOriginFromMap(origin, destination)){
            Edge edgeIntoMap = createEdge(destinationIntoMap, cost);
            originIntoMap.addEdge(edgeIntoMap);
            ret = edgeIntoMap;
        } else {
            //ret = getEdgeFromMap(origin, destination);
        }
        return ret;
    }

    public void setMapRoutes(Node origin, Edge edge){
        if(isNodeInMapByName(origin.getName())){
            getMapRoutes().get(origin).addEdge(edge);
        } else {
            List<Edge> lista = new ArrayList<>();
            lista.add(edge);
            this.mapRoutes.put(origin.getName(), origin);
        }
    }

    @Override
    public String toString() {
        return "Route{" +
                "mapRoutes=" + mapRoutes +
                '}';
    }
}
