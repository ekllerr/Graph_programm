package org.example;

import com.sun.source.tree.ArrayAccessTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


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

    public ArrayList<ArrayList<Integer>> getDistanceMatrix() throws IOException, MatrixException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt", true));
        //wenn das Distanz ist unendlich
        int maxValue = Integer.MAX_VALUE;

        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<>();
        ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();
        ArrayList<ArrayList<Integer>> poweredMatrix = new ArrayList<>();

        writer.newLine();
        writer.write("====== NEW LOGS ======");
        writer.newLine();
        writer.write("---START OF COPYING ADJ MATRIX--");
        writer.newLine();
        writer.write("Adj Matrix: ");
        writer.newLine();
        writer.write(String.valueOf(this.matrix));
        writer.newLine();

        //Matrix kopieren, um das originale Matrix nicht ändern
        for(int i = 0; i < this.matrix.size(); i++){
            ArrayList<Integer> row = new ArrayList<>(this.matrix.get(i));
            adjMatrix.add(row);
        }

        writer.write("Copied Matrix: ");
        writer.newLine();
        writer.write(String.valueOf(adjMatrix));
        writer.newLine();
        writer.write("--END OF COPYING ADJ MATRIX--");
        writer.newLine();

        writer.flush();

        writer.write("-- START OF CREATING THE DISTANCE MATRIX OF THE 1st POWER --");
        writer.newLine();


        for(int i = 0; i < adjMatrix.size(); i++){

            ArrayList<Integer> row = new ArrayList<>();

            for(int j = 0; j < adjMatrix.get(i).size(); j++){
                // werte auf Diagonale sind immer 0
                if(i == j){
                    row.add(0);
                }
                // wenn es keine Kante zwischen Knoten gibt, dann das Distanz is unendlich
                else if(adjMatrix.get(i).get(j) == 0){
                    row.add(maxValue);
                }
                // in anderen Fällen Knote i und Knote j sind verbinden, deshalb schreiben wir 1
                else{
                    row.add(1);
                }

            }

            distanceMatrix.add(row);
        }

        writer.write("Created Distance Matrix: ");
        writer.newLine();
        writer.write(String.valueOf(distanceMatrix));
        writer.newLine();
        writer.write("Created from adj Matrix: ");
        writer.newLine();
        writer.write(String.valueOf(adjMatrix));
        writer.newLine();

        writer.write("-- END OF CREATING THE DISTANCE MATRIX OF THE 1st POWER --");

        writer.flush();

        writer.write("-- START OF POWERING THE DISTANCE MATRIX --");
        writer.newLine();

        // wir starten von k = 2, weil das distanz Matrix schon in Potenz 1 ist
        for(int k = 2; k < adjMatrix.size(); k++){
            // hasChanged überprüft, ob die matrix geändert hat, um das Algorithmus zu optimieren
            boolean hasChanged = false;
            //Adjazenz Matrix zu Potenz k
            poweredMatrix = Matrix.matrixToPower(adjMatrix,k);

            writer.write(String.format("k = %d, powered Matrix: ", k));
            writer.newLine();
            writer.write(String.valueOf(poweredMatrix));
            writer.newLine();
            writer.write("Powered from adj Matrix: ");
            writer.newLine();
            writer.write(String.valueOf(adjMatrix));
            writer.newLine();

            //
            for(int i = 0; i < adjMatrix.size(); i++){

                for(int j = 0; j < adjMatrix.get(i).size(); j++){
                    // wenn in Distanzmatrix noch kein wert in diese Stelle ist, und in Potenzmatrix gibt ein Wert größer als 0, heisst das, dass es ein weg zwischen diesen Knoten gibt(und es ist das kürzeste Weg)
                    if(distanceMatrix.get(i).get(j) == maxValue && poweredMatrix.get(i).get(j) > 0){
                        //wir setzen k statt Infinity, und geben an, dass Matrix geändert hat
                        distanceMatrix.get(i).set(j, k);
                        hasChanged = true;
                    }
                }
            }
            // wenn Matrix hat sich nicht verändert, heisst das, dass alle Distanzen schon gefunden
            if(!hasChanged) break;
        }

        writer.write("Powered Distance Matrix: ");
        writer.newLine();
        writer.write(String.valueOf(distanceMatrix));
        writer.newLine();
        writer.write("Created from adj Matrix: ");
        writer.newLine();
        writer.write(String.valueOf(adjMatrix));
        writer.newLine();
        writer.write("-- END OF POWERING THE DISTANCE MATRIX --");
        writer.newLine();

        writer.flush();
        writer.close();


        return distanceMatrix;
    }

    static public ArrayList<ArrayList<Integer>> matrixToPower(ArrayList<ArrayList<Integer>> matrix, int k) throws MatrixException{
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if(matrix == null || matrix.isEmpty()) throw new MatrixException("Keine Matrix übergeben");

        //Überprüfung, ob die Matrix quadratisch ist
        for(int i = 0; i < matrix.size(); i++){
            if(matrix.size() != matrix.get(i).size())throw new MatrixException("Die Matrix muss quadratisch sein");
        }

        if(k < 0) throw new MatrixException("Die Potenz darf nicht negativ sein");

        //wenn k = 0, dann ist die Matrix mit Einser ausgefüllt
        if(k==0){
            for(int i = 0; i < matrix.size(); i++){
                ArrayList<Integer> row = new ArrayList<>();
                for(int j = 0; j < matrix.get(i).size(); j++){
                    if(i == j){
                        row.add(0);
                        continue;
                    }
                    row.add(1);
                }
                result.add(row);
            }
            return result;
        }

        //wir kopieren die Matrix, um es weiter zu multiplizieren
        for(int i = 0; i < matrix.size(); i++){
            ArrayList<Integer> row = new ArrayList<>(matrix.get(i));
            result.add(row);
        }

        //wenn k = 1, dann liefern wir die Matrix zurück
        if(k==1){
            return result;
        }

        //wir multiplizieren die übergebene Matrix mit sich selbst k mal
        for(int i = 1; i < k; i++){
            result = Matrix.multiplyMatrices(matrix, result);
        }


        return result;
    }


    public ArrayList<ArrayList<Integer>> adjMatrixToPower(int k)throws MatrixException{
        return Matrix.matrixToPower(this.getMatrix(), k);
    }

    static public ArrayList<ArrayList<Integer>> multiplyMatrices (ArrayList<ArrayList<Integer>> matrix1, ArrayList<ArrayList<Integer>> matrix2) throws MatrixException{
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if((matrix1 == null || matrix1.isEmpty()) || (matrix2 == null || matrix2.isEmpty())) throw new MatrixException("Keine Matrix übergeben");
        if(matrix1.size() != matrix2.size()) throw new MatrixException("Matrizen können nicht multipliziert werden");


        for(int i = 0; i < matrix1.size(); i++){
            if((matrix1.get(i).size() != matrix1.size()) || (matrix2.get(i).size() != matrix1.size())) throw new MatrixException("Beide Matrizen müssen quadratisch sein");
        }

        
        for(int i = 0; i < matrix1.size(); i++){
            ArrayList<Integer> row = new ArrayList<>();
            //Schleife für Werte(aus Spalte) aus 1. Matrix
            for(int j = 0; j < matrix1.get(i).size(); j++){
                int sum = 0;
                //Schleife für Werte(aus Spalte) aus 2. Matrix
                for(int k = 0; k < matrix2.get(i).size(); k++){
                    //wir multiplizieren jeden Wert aus der Spalte und schreiben das Ergebnis in sum
                    sum += matrix1.get(i).get(k)*matrix2.get(k).get(j);
                }
                row.add(sum);
            }
            result.add(row);
        }

        return result;
    }

    public ArrayList<ArrayList<Integer>> getMatrix(){
        return matrix;
    }

    public void setMatrix(String matrix){
        this.matrix = this.csvToArr(matrix);
    }

    public void setMatrix(ArrayList<ArrayList<Integer>> matrix) {
        this.matrix = matrix;
    }

    public String matrixToString(ArrayList<ArrayList<Integer>> matrix){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < matrix.size(); i++){
            StringBuilder row = new StringBuilder();
            for(int j = 0; j < matrix.get(i).size(); j++){
                row.append(matrix.get(i).get(j).toString());
                if(j != matrix.get(i).size() - 1) row.append(";");
            }
            sb.append(row);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Adjazenzmatrix Matrix: \n");
        sb.append(matrixToString(this.matrix));
        try{
            ArrayList<ArrayList<Integer>> distanceMatrix = this.getDistanceMatrix();
            sb.append("\nDistanz Matrix: \n");
            sb.append(this.matrixToString(distanceMatrix));
        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }


        return sb.toString();
    }
}
