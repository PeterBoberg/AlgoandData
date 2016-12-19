package com.peter;

import se.kth.id1020.DataSource;
import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Paths1 {

    private Graph graph = DataSource.load();

    private List<List<Vertex>> resultList = new ArrayList<>();
    private List<Vertex> rawList = new ArrayList<>();

    public void depthFirstSearch() {

        for (Vertex vertex : graph.vertices())
            rawList.add(vertex);

        int count = 0;
        while (!rawList.isEmpty()){
            resultList.add(count, new ArrayList<>());
            visit(rawList.get(0).id, resultList.get(count));
            count++;
        }
    }

    public void visit(int vertexId, List<Vertex> subGraphResultList) {

        if (subGraphResultList.contains(graph.vertex(vertexId)))
            return;

        subGraphResultList.add(graph.vertex(vertexId));
        rawList.remove(graph.vertex(vertexId));

        for (Edge edge : graph.adj(vertexId)) {
            visit(edge.to, subGraphResultList);
        }
    }

    public List<List<Vertex>> getResultList() {
        return resultList;
    }


    public static void main(String[] args) {

        Paths1 paths1 = new Paths1();
        paths1.depthFirstSearch();

        for (List<Vertex> list : paths1.getResultList()) {

            for (Vertex vertex : list){
                System.out.println(vertex);
            }

            System.out.println();
            System.out.println();
            System.out.println("*******************************");
            System.out.println();
            System.out.println();
        }
        System.out.println("Number of subgraphs are "+ paths1.getResultList().size());
    }


}
