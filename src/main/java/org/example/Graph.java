package org.example;

import java.io.IOException;
import java.util.*;

public class Graph {
    private Matrix matrix;
    private ArrayList<GraphNode> nodes;
    private ArrayList<ArrayList<Integer>> adjList;
    private ArrayList<ArrayList<GraphNode>> components;
    private ArrayList<GraphNode> articulations;
    private ArrayList<ArrayList<GraphNode>> bridges;

    public Graph() {
        this.setMatrix(new Matrix());
        this.nodes = new ArrayList<>();
        this.adjList = new ArrayList<>();
        this.components = new ArrayList<>();
        this.articulations = new ArrayList<>();
        this.bridges = new ArrayList<>();

        this.initializeNodes();
        this.initializeAdjList();
        this.findComponents();
        this.findArticulations();
        this.findBridges();
    }

    public Graph(String matrix) throws MatrixException {
        this.setMatrix(new Matrix(matrix));
        this.nodes = new ArrayList<>();
        this.adjList = new ArrayList<>();
        this.components = new ArrayList<>();
        this.articulations = new ArrayList<>();
        this.bridges = new ArrayList<>();


        this.initializeNodes();
        this.initializeAdjList();
        this.findComponents();
        this.findArticulations();
        this.findBridges();
    }

    public String findEccentricities() throws MatrixException, IOException {
        ArrayList<ArrayList<Integer>> distanceMatrix = this.matrix.getDistanceMatrix();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < this.nodes.size(); i++){

            GraphNode node = this.nodes.get(i);

            //Überprüfung, ob die Knote schon ein Exzentrizität hat
            if(node.getEccentricity() == -1){
                ArrayList<Integer> distances = new ArrayList<>();

                //wir speichern alle Werte aus Distanzmatrix für diese Knote in Array distances
                for(int j =0; j < distanceMatrix.get(i).size(); j++){
                    if(i == j) continue;

                    int value = distanceMatrix.get(i).get(j);

                    // wenn der Wert nicht Infinity ist, dann speichern wir diesen Wert
                    if(value != Integer.MAX_VALUE){
                        distances.add(value);
                    }
                }

                //Es kann sein, dass die Knote ist isoliert, dann wird das distances Array leer, und es wird -1 als Exzentrizität zugewiesen
                if(distances.isEmpty()){
                    node.setEccentricity(-1);
                } else{
                    //wenn das distances Array ist nicht leer, dann speichern wir der größte Wert als Exzentrizität
                    node.setEccentricity(Collections.max(distances));
                }
            }
            sb.append("Exzentrizität des Knotens ").append(node.getId()).append(": ").append(node.getEccentricity()).append("\n");
        }

        return sb.toString();
    }

    private void findComponents() {
        Set<GraphNode> visited = new HashSet<>();


        for(GraphNode node : this.nodes){
            ArrayList<GraphNode> component = new ArrayList<>();

            if (!visited.contains(node)) {
                component = this.dfs(node.getId());
                visited.addAll(component);
                this.components.add(component);
            }

        }

    }

    private void findArticulations() {
        Set<GraphNode> articulations = new HashSet<>();

        ArrayList<GraphNode> fullDfs = new ArrayList<>();
        ArrayList<GraphNode> dfsIgnoring = new ArrayList<>();


        for(GraphNode node : this.nodes){

            if(this.adjList.get(node.getId() - 1).isEmpty()) continue;

            int startNodeId = getStartNodeId(node);
            int ignoredNodeId = node.getId();


            fullDfs = this.dfs(startNodeId);
            dfsIgnoring = this.dfs(startNodeId, ignoredNodeId);

            if(fullDfs.size() - 1 != dfsIgnoring.size()){
                articulations.add(node);
            }
        }

        this.articulations.addAll(articulations);
    }

    private int getStartNodeId(GraphNode node){
        int startNodeId = Integer.MAX_VALUE;



        for(ArrayList<GraphNode> component : this.components){
            if(component.contains(node)){

                for(GraphNode connectedNode : component){
                    if(connectedNode != node) return connectedNode.getId();
                }

            }
        }

        return startNodeId;
    }

/*    private int getStartNodeId(GraphNode node, GraphNode node2){
        int startNodeId = Integer.MAX_VALUE;

        for(ArrayList<GraphNode> component : this.components){
            if(component.contains(node) && component.contains(node2)){
                for(GraphNode connectedNode : component){
                    if((connectedNode != node) && (connectedNode != node2)) return connectedNode.getId();
                }
            }
        }

        return startNodeId;
    }*/

    private void findBridges() throws GraphException {

        Set<ArrayList<GraphNode>> edges = new HashSet<>();

        for(int i = 0; i < this.adjList.size(); i++){
            for(int j = 0; j < this.adjList.get(i).size(); j++){
                ArrayList<GraphNode> edge = new ArrayList<>();

                int nodeFromId = i+1;
                int nodeToId = this.adjList.get(i).get(j);

                GraphNode fromNode = this.getNodeById(Math.min(nodeFromId, nodeToId));
                GraphNode toNode = this.getNodeById(Math.max(nodeFromId, nodeToId));

                edge.add(fromNode);
                edge.add(toNode);


                edges.add(edge);
            }
        }

        ArrayList<GraphNode> fullDfs = new ArrayList<>();
        ArrayList<GraphNode> dfsIgnoring = new ArrayList<>();

        for(ArrayList<GraphNode> edge : edges){
            if(edge.size() != 2) throw new GraphException("Die Kante ist falsch definiert");

//            int startNodeId = getStartNodeId(edge.getFirst(), edge.getLast());
//            if(startNodeId == Integer.MAX_VALUE) continue;

            dfsIgnoring = this.dfs(edge.getFirst().getId(), edge.getFirst().getId(), edge.getLast().getId());

            if(!dfsIgnoring.contains(edge.getLast())){
                this.bridges.add(edge);
            }


        }
//        System.out.println(edges);

    }

    public GraphNode getNodeById(int id){
        for(GraphNode node : this.nodes){
            if(node.getId() == id) return node;
        }
        throw new GraphException("Keine Knote mit diesem Id");
    }

    public ArrayList<GraphNode> dfs(int nodeId) throws GraphException{
        if(nodeId > this.nodes.size()) throw new GraphException("Keine Knote mit id " + nodeId);
        Stack<GraphNode> stack = new Stack<>();
        ArrayList<GraphNode> result = new ArrayList<>();
        Set<GraphNode> visited = new HashSet<>();

        for(GraphNode node : this.nodes){
            if(node.getId() == nodeId)
                stack.push(node);
        }

        while(!stack.isEmpty() && visited.size() < this.nodes.size()){
            GraphNode node = stack.pop();
            int currentNodeId = node.getId();
            ArrayList<Integer> currentNodeConnections = this.adjList.get(currentNodeId - 1);

            if(!visited.contains(node)){
                visited.add(node);
                result.add(node);

                for(int i = 0; i < currentNodeConnections.size(); i++){
                    GraphNode u = this.nodes.get(currentNodeConnections.get(i) - 1);
                    stack.push(u);
                }
            }
        }

        return result;
    }

    public ArrayList<GraphNode> dfs(int nodeId, int ignoredNodeId) throws GraphException{
        if(nodeId > this.nodes.size() || ignoredNodeId > this.nodes.size()) throw new GraphException("Keine Knote mit id " + nodeId);
        if(nodeId == ignoredNodeId) throw new GraphException("Start Knote darf nicht ignoriert werden");

        Set<GraphNode> visited = new HashSet<>();
        Stack<GraphNode> stack = new Stack<>();
        ArrayList<GraphNode> result = new ArrayList<>();

        for(GraphNode node : this.nodes){
            if(node.getId() == nodeId)
                stack.push(node);
        }

        while(!stack.isEmpty() && visited.size() < this.nodes.size()){
            GraphNode node = stack.pop();
            int currentNodeId = node.getId();
            ArrayList<Integer> currentNodeConnections = this.adjList.get(currentNodeId - 1);

            if(!visited.contains(node)){
                visited.add(node);
                result.add(node);
            }

            for(int i = 0; i < currentNodeConnections.size(); i++){
                GraphNode u = this.nodes.get(currentNodeConnections.get(i) - 1);
                if(!visited.contains(u) && u.getId() != ignoredNodeId)
                    stack.push(u);
            }

        }

        return result;
    }

    public ArrayList<GraphNode> dfs(int nodeId, int ignoredNodeIdFrom, int ignoredNodeIdTo) {
        if(nodeId > this.nodes.size() || ignoredNodeIdFrom > this.nodes.size() || ignoredNodeIdTo > this.nodes.size()) throw new GraphException("Keine Knote mit id " + nodeId);
        /*if(nodeId == ignoredNodeIdFrom || nodeId == ignoredNodeIdTo) throw new GraphException("Start Knote darf nicht ignoriert werden");*/
        if(ignoredNodeIdFrom == ignoredNodeIdTo) throw new GraphException("Keine Kante zwischen gleichen Knoten");

        Set<GraphNode> visited = new HashSet<>();
        Stack<GraphNode> stack = new Stack<>();
        ArrayList<GraphNode> result = new ArrayList<>();

        for(GraphNode node : this.nodes){
            if(node.getId() == nodeId)
                stack.push(node);
        }

        while(!stack.isEmpty() && visited.size() < this.nodes.size()){
            GraphNode node = stack.pop();
            int currentNodeId = node.getId();
            ArrayList<Integer> currentNodeConnections = this.adjList.get(currentNodeId - 1);

            if(!visited.contains(node)){
                visited.add(node);
                result.add(node);
            }

            for(int i = 0; i < currentNodeConnections.size(); i++){
                GraphNode u = this.nodes.get(currentNodeConnections.get(i) - 1);
                if (!visited.contains(u)) {
                    if (!((currentNodeId == ignoredNodeIdFrom && u.getId() == ignoredNodeIdTo)
                        ||(currentNodeId == ignoredNodeIdTo && u.getId() == ignoredNodeIdFrom))) {

                        stack.push(u);
                    }
                }
            }
        }

        return result;

    }

    public int getRadius() throws GraphException{
        ArrayList<Integer> eccentricities = this.getEccentricities();
        if(!eccentricities.contains(-1)){
            return Collections.min(eccentricities);
        }

        int radius = Integer.MAX_VALUE;

        for(int i = 0; i < eccentricities.size(); i++){
            if(eccentricities.get(i) != -1 && eccentricities.get(i) < radius){
                radius = eccentricities.get(i);
            }
        }
        if(radius == Integer.MAX_VALUE) throw new GraphException("Graph ist nicht zusammenhängend");
        return radius;
    }

    public int getDiameter(){
        ArrayList<Integer> eccentricities = this.getEccentricities();
        return Collections.max(eccentricities);
    }

    public ArrayList<GraphNode> getCenterNodes() throws GraphException{
        int minValue = this.getRadius();

        ArrayList<GraphNode> centerNodes = new ArrayList<>();

        for(GraphNode node : this.nodes){
            if(node.getEccentricity() == minValue){
                centerNodes.add(node);
            }
        }

        return centerNodes;
    }

    private void initializeAdjList(){
        for(int i = 0; i < this.matrix.getMatrix().size(); i++){
            ArrayList<Integer> neighbors = new ArrayList<>();

            for(int j = 0; j < this.matrix.getMatrix().get(i).size(); j++){
                if(this.matrix.getMatrix().get(i).get(j) > 0) {
                    neighbors.add(j + 1);
                }
            }

            adjList.add(neighbors);
        }
    }

    private void initializeNodes(){
        this.nodes.clear();

        for(int i = 0; i < this.matrix.getMatrix().size(); i++){
            GraphNode node = new GraphNode(i+1);
            this.nodes.add(node);
        }
    }

    public ArrayList<ArrayList<GraphNode>> getBridges(){
        return this.bridges;
    }

    public ArrayList<GraphNode> getArticulations(){
        return this.articulations;
    }

    public ArrayList<ArrayList<GraphNode>> getComponents(){
        return this.components;
    }

    public ArrayList<ArrayList<Integer>> getAdjList(){
        return this.adjList;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    public ArrayList<GraphNode> getNodes(){
        return this.nodes;
    }

    public void setMatrix(ArrayList<ArrayList<Integer>> matrix){
        this.matrix.setMatrix(matrix);
    }

    public void setMatrix(String matrix){
        this.matrix.setMatrix(matrix);
    }

    public void setMatrix(Matrix matrix) {
        if(matrix == null) throw new GraphException("Matrix ist null");
        this.matrix = matrix;
    }

    public ArrayList<Integer> getEccentricities(){
        ArrayList<Integer> eccentricities = new ArrayList<>();
        for(GraphNode node : this.nodes){
            eccentricities.add(node.getEccentricity());
        }
        return eccentricities;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Matrizen: \n").append(this.matrix.toString());
        sb.append("\nKnoten: \n");
        for(GraphNode node : this.nodes){
            sb.append(node.toString()).append("\n");
        }
        /*
        try{
            this.findEccentricities();
        }catch(MatrixException | IOException e){
            System.err.println(e.getMessage());
        }
        sb.append("Zentrum: ").append(this.getCenterNodes().toString()).append("\n");
        sb.append("Diameter: ").append(this.getDiameter()).append("\n");
        sb.append("Radius: ").append(this.getRadius()).append("\n");
        sb.append("Komponenten: ").append(this.getComponents()).append("\n");
        sb.append("Artikulationen: ").append(this.getArticulations()).append("\n");*/

        return sb.toString();
    }
}
