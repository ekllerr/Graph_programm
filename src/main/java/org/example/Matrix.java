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
        this.setMatrix(this.csvToArr(csvMatrix));
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

    public ArrayList<ArrayList<Integer>> getDistanceMatrix(){
        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<>();

        final int maxValue = Integer.MAX_VALUE;

        for(int i = 0; i < this.matrix.size(); i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0; j < this.matrix.get(i).size(); j++){
                if(i == j){
                    row.add(0);
                    continue;
                }

                if(j == 0){
                    row.add(maxValue);
                    continue;
                }

                //TODO

            }
            distanceMatrix.add(row);
        }

        return distanceMatrix;
    }


    public ArrayList<ArrayList<Integer>> adjMatrixToPower(int k)throws MatrixException{

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if(k<0) throw new MatrixException("Die Potenz darf nicht negativ sein");

        if(k==0){
            for(int i = 0; i < this.matrix.size(); i++){
                ArrayList<Integer> row = new ArrayList<>();
                for(int j = 0; j < this.matrix.get(i).size(); j++){
                    if(i == j){
                        row.add(1);
                        continue;
                    }
                    row.add(0);
                }
                result.add(row);
            }
            return result;
        }

        for(int i = 0; i < this.matrix.size(); i++){
            ArrayList<Integer> row = new ArrayList<>(this.matrix.get(i));
            result.add(row);
        }

        if(k==1){
            return result;
        }


        for(int i = 1; i < k;i++ ){
            result = Matrix.multiplyMatrices(result, this.matrix);
        }

        return result;
    }

    static public ArrayList<ArrayList<Integer>> multiplyMatrices (ArrayList<ArrayList<Integer>> matrix1, ArrayList<ArrayList<Integer>> matrix2) throws MatrixException{
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if((matrix1 == null || matrix1.isEmpty()) || (matrix2 == null || matrix2.isEmpty())) throw new MatrixException("Keine Matrix übergeben");
        if(matrix1.size() != matrix2.size()) throw new MatrixException("Matrixen können nicht multipliziert werden");


        for(int i = 0; i < matrix1.size(); i++){
            if((matrix1.get(i).size() != matrix1.size()) || (matrix2.get(i).size() != matrix1.size())) throw new MatrixException("Beide Matrizen müssen quadratisch sein");
        }

        
        for(int i = 0; i < matrix1.size(); i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0; j < matrix1.get(i).size(); j++){
                int sum = 0;
                for(int k = 0; k < matrix2.get(i).size(); k++){
                    sum += matrix1.get(i).get(k)*matrix2.get(k).get(j);
                }
                row.add(sum);
            }
            result.add(row);
        }

        return result;
    }

    public ArrayList<ArrayList<Integer>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<Integer>> matrix) {
        this.matrix = matrix;
    }
}
