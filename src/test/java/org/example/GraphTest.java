package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;


class GraphTest {
    @Test
    void test_initializeNodes() {
        Graph graph = new Graph();
        System.out.println(graph.toString());
    }

    @Test
    void test_findEccentricities_completeGraph(){
        try{

            Graph graph = new Graph("""
                0;1;1;1
                1;0;1;1
                1;1;0;1
                1;1;1;0
               """);

            String result = graph.findEccentricities();

            System.out.println(result);
            System.out.println(graph.toString());

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_findEccentricities_linearChainOf_4_Nodes(){
        try{

            Graph graph = new Graph("""
                0;1;0;0
                1;0;1;0
                0;1;0;1
                0;0;1;0
               """);

            String result = graph.findEccentricities();

            System.out.println(result);
            System.out.println(graph.toString());

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_findEccentricities_disconnectedGraph(){
        try{

            Graph graph = new Graph("""
                0;0;0
                0;0;0
                0;0;0
               """);

            String result = graph.findEccentricities();
            System.out.println(graph.getMatrix().getDistanceMatrix());
            System.out.println(result);
            System.out.println(graph);

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_findEccentricities_starGraph(){
        try{

            Graph graph = new Graph("""
                0;1;1;1
                1;0;0;0
                1;0;0;0
                1;0;0;0
               """);

            String result = graph.findEccentricities();
            System.out.println(result);
            System.out.println(graph);

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_findEccentricities_twoSeparateComponents(){
        try{

            Graph graph = new Graph("""
                0;1;0;0
                1;0;0;0
                0;0;0;1
                0;0;1;0
              """);

            String result = graph.findEccentricities();
            System.out.println(result);
            System.out.println(graph);

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_findEccentricities_lShapedGraph(){
        try{

            Graph graph = new Graph("""
                0;1;0;0
                1;0;1;0
                0;1;0;1
                0;0;1;0
              """);

            String result = graph.findEccentricities();
            System.out.println(result);
            System.out.println(graph);

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getRadius(){
        try{

            Graph graph = new Graph("""
                0;0
                0;0
              """);

            String result = graph.findEccentricities();
            System.out.println(result);
            try{
                System.out.println("Radius: " + graph.getRadius());
            } catch(GraphException e){
                System.out.println(e.getMessage());
            }

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getDiameter(){
        try{

            Graph graph = new Graph("""
                0;1;0;0
                1;0;1;0
                0;1;0;1
                0;0;1;0
              """);

            String result = graph.findEccentricities();
            System.out.println(result);
            System.out.println("Diameter: " + graph.getDiameter());

        } catch(MatrixException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getCenter(){
        try{

            Graph graph = new Graph("""
                0;1;0;0
                1;0;1;0
                0;1;0;1
                0;0;1;0
              """);

            String result = graph.findEccentricities();
            System.out.println(result);
            System.out.println("Center(s): " + graph.getCenterNodes());

        } catch(MatrixException | IOException | GraphException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_initializeAdjList(){
        try{

            Graph graph = new Graph("""
                    0;1;1;1
                    1;0;0;0
                    1;0;0;0
                    1;0;0;0
                    """);
            System.out.println(graph.getAdjList());
        }catch(MatrixException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_dfs_simpleConnected_Graph(){
        try{

            Graph graph = new Graph("""
                    0;1;1
                    1;0;1
                    1;1;0
                    """);
            System.out.println(graph.dfs(1));
        } catch(MatrixException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_dfs_disconnectedGraph(){
        try{

            Graph graph = new Graph("""
                    0;1;0;0
                    1;0;0;0
                    0;0;0;1
                    0;0;1;0
                    """);
            System.out.println(graph.dfs(1));
            System.out.println(graph.dfs(3));
        } catch(MatrixException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_dfs_noSuchNodeId(){
        try{

            Graph graph = new Graph("""
                    0;1;0;0
                    1;0;0;0
                    0;0;0;1
                    0;0;1;0
                    """);
            System.out.println(graph.dfs(5));
        } catch(MatrixException | GraphException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_dfs_starGraph(){
        try{

            Graph graph = new Graph("""
                    0;1;1;1
                    1;0;0;0
                    1;0;0;0
                    1;0;0;0
                    """);
            System.out.println(graph.dfs(1));
        } catch(MatrixException | GraphException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getComponents_starGraph(){
        try{

            Graph graph = new Graph("""
                    0;1;1;1
                    1;0;0;0
                    1;0;0;0
                    1;0;0;0
                    """);
            System.out.println(graph.getComponents());
        } catch(MatrixException | GraphException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getComponents_fullyConnectedGraph(){
        try{
            Graph graph = new Graph("""
                    0;1;1;1;1
                    1;0;1;1;1
                    1;1;0;1;1
                    1;1;1;0;1
                    1;1;1;1;0
                    """);
            System.out.println(graph.getComponents());
        } catch(MatrixException | GraphException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getComponents_disconnectedGraph(){
        try{

            Graph graph = new Graph("""
                    0;1;0;0;0
                    1;0;0;0;0
                    0;0;0;1;1
                    0;0;1;0;0
                    0;0;1;0;0
                    """);
            System.out.println(graph.getComponents());
        } catch(MatrixException | GraphException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test_getComponents_IsolatedNodes(){
        try{

            Graph graph = new Graph("""
                    0;0;0
                    0;0;0
                    0;0;0
                    """);
            System.out.println(graph.getComponents());
        } catch(MatrixException | GraphException e){
            System.out.println(e.getMessage());
        }
    }



}
