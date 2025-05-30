package org.example;

import java.util.ArrayList;

public class Graph {
    private Matrix matrix;
    private ArrayList<GraphNode> nodes;

    public Graph(){
        this.setMatrix(new Matrix());
        this.nodes = new ArrayList<>();

    }

    public Graph(String matrix) throws MatrixException {
        this.setMatrix(new Matrix(matrix));
        this.nodes = new ArrayList<>();
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
}
