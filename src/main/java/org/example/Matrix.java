package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private ArrayList<ArrayList<Integer>> matrix;

    public Matrix(){
        final String csvMatrix ="""
                0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0
                1;0;1;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;1;0;1;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;1;1;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;1;1;0;1;1;1;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;1;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;1;1;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;1;0;0;0;1;1;1;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;1;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;0;0;0;1;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;0;0;0;1;0;1;0;0;0;1;0;0;1;0;0;0;0;0;0;0
                0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0
                0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;0;0;0;0;0;0;0;0;0
                0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;1;0;1;1;0;0;0;0;0
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;1;0;0;0;0;0;0;0
                0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;1;0;0;0;1;1;0;0;0
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;0;0;0;0;0
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;1;0;0;0;0;0;0
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;1;0;0;0
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;1;0;1;0;0
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;1;1
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;1
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;1;0
                """;
        this.matrix = this.csvToArr(csvMatrix);
    }

    public Matrix(String csv) throws MatrixException {
        if(csv == null || csv.isEmpty()) throw new MatrixException("Kein csv vorgegeben");
        this.setMatrix(this.csvToArr(csv));
    }

    public ArrayList<ArrayList<Integer>> csvToArr(String csv){
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        String[] lines = csv.split("\n");

        for(String line : lines){
            String[] values = line.split(";");
            ArrayList<Integer> row = new ArrayList<>();
            for(String value : values){
                row.add(Integer.parseInt(value.trim()));
            }
            matrix.add(row);
        }


        return matrix;
    }

    public ArrayList<ArrayList<Integer>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<Integer>> matrix) {
        this.matrix = matrix;
    }
}
