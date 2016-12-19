package com.peter;

import se.kth.id1020.DataSource;
import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by KungPeter on 2016-12-06.
 */
public class Paths2 {

    private final Graph graph;
    private int[] distance;
    private int[] paths;
    private Queue<Vertex> vertexQueue;

    public Paths2(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<Integer> fintshortestPath(Vertex start, Vertex finish) {

        distance = new int[graph.numberOfVertices()];
        paths = new int[graph.numberOfVertices()];
        vertexQueue = new LinkedList<>();

        initArrays();

        vertexQueue.offer(start);
        distance[start.id] = 0;

        while (!vertexQueue.isEmpty()) {

            Vertex visitedVertex = vertexQueue.poll();

            for (Edge edge : graph.adj(visitedVertex.id)) {

                if (distance[edge.to] == -1) {
                    distance[edge.to] = distance[visitedVertex.id] + 1;
                    paths[edge.to] = visitedVertex.id;
                    vertexQueue.offer(graph.vertex(edge.to));
                }
            }
        }

        return analyseResult(start.id, finish.id);
    }

    private void initArrays() {

        for (int i = 0; i < distance.length; i++) {

            distance[i] = -1;
        }

    }

    public ArrayList<String> convertToString(ArrayList<Integer> shortestPath) {

        ArrayList<String> resultList = new ArrayList<>();

        int step = 0;
        for (Integer i : shortestPath) {

            resultList.add("Step " + step++ + ": " + graph.vertex(i));

        }

        return resultList;
    }

    private ArrayList<Integer> analyseResult(int start, int finish) {

        ArrayList<Integer> resultList = new ArrayList<>();
        int currentPos = finish;
        while (currentPos != start) {

            resultList.add(paths[currentPos]);
            currentPos = paths[currentPos];
        }

        Collections.reverse(resultList);
        System.out.println(resultList.size());
        return resultList;
    }


    public static void main(String[] args) {

        Graph g = DataSource.load();
        Paths2 paths2 = new Paths2(g);

        Vertex renyn = null;
        Vertex parses = null;
        for (Vertex vertex : g.vertices()) {

            if (vertex.label.equalsIgnoreCase("Renyn"))
                renyn = vertex;
            if (vertex.label.equalsIgnoreCase("Parses"))
                parses = vertex;

            if (renyn != null && parses != null)
                break;
        }

        System.out.println(renyn.id);
        System.out.println(parses.id);
        ArrayList<Integer> shortestPath = paths2.fintshortestPath(renyn, parses);
        System.out.println(shortestPath);
        ArrayList<String> asNamesList = paths2.convertToString(shortestPath);

        for (String s : asNamesList)
            System.out.println(s);


    }
}
