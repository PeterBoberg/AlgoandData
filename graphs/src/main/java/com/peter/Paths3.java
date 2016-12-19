package com.peter;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import se.kth.id1020.DataSource;
import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.Vertex;

/**
 * Created by KungPeter on 2016-12-07.
 */
public class Paths3 {


    private Graph graph;
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> priorityQueue;


    public Paths3(Graph graph) {
        this.graph = graph;
    }

    public void djikstraShortestPath(Vertex start) {

        edgeTo = new Edge[graph.numberOfVertices()];
        distTo = new double[graph.numberOfVertices()];
        priorityQueue = new IndexMinPQ<>(graph.numberOfVertices());

        for (int v = 0; v < distTo.length; v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[start.id] = 0.0;

        priorityQueue.insert(start.id, 0.0);

        while (!priorityQueue.isEmpty()) {
            relax(priorityQueue.delMin());
        }
    }


    private void relax(int vertexId) {

        for (Edge edge : graph.adj(vertexId)) {
            int neighbourId = edge.to;
            if (distTo[neighbourId] > distTo[vertexId] + edge.weight) {

                distTo[neighbourId] = distTo[vertexId] + edge.weight;
                edgeTo[neighbourId] = edge;
                if (priorityQueue.contains(neighbourId))
                    priorityQueue.changeKey(neighbourId, distTo[neighbourId]);
                else
                    priorityQueue.insert(neighbourId, distTo[neighbourId]);

            }

        }
    }

    public double distTo(Vertex finish) {
        return distTo[finish.id];
    }

    public boolean hasPathTo(Vertex finitsh) {

        return distTo[finitsh.id] != Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(Vertex finish) {

        if (!hasPathTo(finish))
            return null;
        Stack<Edge> path = new Stack<>();
        for (Edge egde = edgeTo[finish.id]; egde != null; egde = edgeTo[egde.from])
            path.push(egde);

        return path;
    }


    public static void main(String[] args) {

        Graph g = DataSource.load();
        Paths3 paths3 = new Paths3(g);

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

        paths3.djikstraShortestPath(renyn);
        double distance = paths3.distTo(parses);
        boolean hasPath = paths3.hasPathTo(parses);

        System.out.println(distance);
        System.out.println(hasPath);

        Iterable<Edge> iterable = paths3.pathTo(parses);
        if (iterable != null)
            for (Edge edge : paths3.pathTo(parses))
                System.out.println(edge);
    }
}
