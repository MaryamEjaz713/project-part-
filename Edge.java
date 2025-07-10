public class Edge implements Comparable<Edge> {/*declares a class named  Edge  that that connects two nodes and allows comparison between edges based on weight*/
    private final Node from, to;/*an Edge has a unique source and destination node,both of which cannot be changed once assigned*/
    private final int weight;/*the weight of Edge cannot be changed once it is assigned*/
    /*declares a constructor that sets the source node, destination node and weight of the edge*/
    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    /*Returns the source node of the edge*/
    public Node getFrom() {
        return from;
    }
    /*Returns the destination node of the edge*/
    public Node getTo() {
        return to;
    }
    /*retuns the weight of the edge*/
    public int getWeight() {
        return weight;
    }

    @Override/*this method compares edges based on their weight and helps sort them to construct the MST as part of Kruskal algorithm*/
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);/*Returns a negative,zero,positive value based on comparison of edge weights*/
    }
}


