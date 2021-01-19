package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import br.com.daniloti2005.air_route_commons.singleton.route.RouteSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Route {

    private Map<Node, List<Edge>> mapaEdges;


    public Route() {
        mapaEdges = new HashMap<>();

    }

    public List<Node> getNodes() {
        List<Node> listNode = new ArrayList<>();
        for (Map.Entry<Node,List<Edge>> item: mapaEdges.entrySet() ) {
            listNode.add(item.getKey());
        }
        return listNode;
    }

    public Edge createEdge(Node origin, int edge) {
        return new Edge(origin, edge);
    }

    public Node createNode(String origin){
        return new Node(origin,Integer.MAX_VALUE, null);
    }

    public void addNode(String nameNode){
        Node node = createNode(nameNode);
        getMapEdges().put(node,node.getEdges());
    }

    public void addNode(Node node){
        getMapEdges().put(node,node.getEdges());
        Node.addLstNode(node);
    }


    public void addEdge(String nameNode, List<Edge> listEdge){
        for (Edge e: listEdge){
            getMapEdges().get(createNode(nameNode)).add(e);
        }
    }

    public void addEdge(Node node, List<Edge> listEdge){
        for (Edge e: listEdge){
            getMapEdges().get(node).add(e);
        }
    }

    public List<Edge> getEdges(Node node){
        return getMapEdges().get(node);
    }

    public List<Edge> getEdges(String node){
        return getMapEdges().get(createNode(node));
    }



    public Map<Node, List<Edge>> getMapEdges() {
        return mapaEdges;
    }

    public void setMapEdges(Map<Node, List<Edge>> mapaEdges) {
        this.mapaEdges = mapaEdges;
    }

    public void saveMapInMemory(Map<Node, List<Edge>> mapaEdges){
        RouteSingleton.setMapRoute(mapaEdges);
    }

    public Map<Node, List<Edge>> getMapInMemory(){
        return RouteSingleton.getMapRoute();
    }

    public void setMapaEdges(Node origin, Node destination, Edge edge){
        if(this.mapaEdges == null){
            this.mapaEdges = new HashMap<>();
            List<Edge> lista = new ArrayList<>();
            lista.add(edge);
            this.mapaEdges.put(origin, lista);

        } else {
            if(getMapEdges().get(origin) != null){
                getMapEdges().get(origin).add(edge);
            } else {
                List<Edge> lista = new ArrayList<>();
                lista.add(edge);
                getMapEdges().put(origin, lista);
            }
        }
    }
}
