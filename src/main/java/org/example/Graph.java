package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Graph {
    private Matrix matrix;
    private ArrayList<GraphNode> nodes;

    public Graph(){
        this.setMatrix(new Matrix());
        this.nodes = new ArrayList<>();
        this.initializeNodes();
    }

    public Graph(String matrix) throws MatrixException {
        this.setMatrix(new Matrix(matrix));
        this.nodes = new ArrayList<>();
        this.initializeNodes();
    }

    public String findEccentricities() throws MatrixException, IOException {
        ArrayList<ArrayList<Integer>> distanceMatrix = this.matrix.getDistanceMatrix();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.nodes.size(); i++){

            GraphNode node = this.nodes.get(i);

            if(node.getEccentricity() == -1){
                ArrayList<Integer> distances = new ArrayList<>();

                for(int j =0; j < distanceMatrix.get(i).size(); j++){
                    if(i == j) continue;

                    int value = distanceMatrix.get(i).get(j);

                    if(value != Integer.MAX_VALUE){
                        distances.add(value);
                    }
                }

                if(distances.isEmpty()){
                    node.setEccentricity(-1);
                } else{
                    node.setEccentricity(Collections.max(distances));
                }
            }
            sb.append("Exzentrizität des Knotens ").append(node.getId()).append(": ").append(node.getEccentricity()).append("\n");
        }

        return sb.toString();
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


    private void initializeNodes(){
        this.nodes.clear();

        for(int i = 0; i < this.matrix.getMatrix().size(); i++){
            GraphNode node = new GraphNode(i+1);
            this.nodes.add(node);
        }
    }


    public Matrix getMatrix() {
        return matrix;
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
        return sb.toString();
    }
}
