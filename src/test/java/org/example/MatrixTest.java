package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class MatrixTest{
    @Test
    void test_csvToArr(){
        String csv = """
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
                0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;1;0""";
    }

    @Test
    void test_toString(){
        try{

            Matrix matrix = new Matrix("""
                    0;1;1
                    1;0;0
                    1;0;0
                    """);
            System.out.println(matrix.toString());

        } catch (MatrixException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_matrixConstructorNoParameters(){
        Matrix matrix = new Matrix();
        System.out.println(matrix.getMatrix());
    }

    @Test
    void test_multiplyMatrices(){
        try{
            Matrix matrix = new Matrix("""
                    2;3
                    1;4
                    """);
            ArrayList<ArrayList<Integer>> matrixSquared = Matrix.multiplyMatrices(matrix.getMatrix(),matrix.getMatrix());
            System.out.println(matrixSquared);
        } catch(MatrixException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_matrixToPower(){
        try{
            Matrix matrix = new Matrix("""
                    0;1;0
                    1;0;1
                    0;1;0
                    """);

            ArrayList<ArrayList<Integer>> newMatrix = Matrix.matrixToPower(matrix.getMatrix(), 3);

            System.out.println(newMatrix);



        } catch(MatrixException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_adjMatrixToPower(){
        try{
            Matrix matrix = new Matrix("""
                    0;1;0
                    1;0;1
                    0;1;0
                    """);

            ArrayList<ArrayList<Integer>> newMatrix = matrix.adjMatrixToPower(3);
            System.out.println(newMatrix);
            Matrix expected = new Matrix("""
                    0;2;0
                    2;0;2
                    0;2;0""");
            assertEquals(expected.getMatrix(),newMatrix);
        } catch(MatrixException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getDistanceMatrix(){
        try {

            Matrix matrix = new Matrix("""
                    0;1;1;1;0
                    1;0;0;1;1
                    1;0;0;1;0
                    1;1;1;0;0
                    0;1;0;0;0
            """);

            Matrix matrix2 = new Matrix("""
                    0;1
                    1;0
                    """);

            ArrayList<ArrayList<Integer>> distanceMatrix = matrix.getDistanceMatrix();
            System.out.println("Adj Matrix: " + matrix.getMatrix());
            System.out.println("Distance Matrix: " + distanceMatrix);

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

}