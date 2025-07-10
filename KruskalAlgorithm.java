import java.util.*;

public class KruskalAlgorithm {
    /* Map to keep track of the parent of each node for Union-Find*/
    private final Map<Node, Node> parent = new HashMap<>();
    /* List to store the edges of the Minimum Spanning Tree (MST)*/
    private final List<Edge> mst = new ArrayList<>();
    /* List to log the steps taken during the algorithm*/
    private final List<String> logSteps = new ArrayList<>();
    /*List to keep track of the order in which edges are traversed*/
    private final List<Edge> traversalOrder = new ArrayList<>();

    /* Getter for the traversal order of edges*/
    public List<Edge> getTraversalOrder() {
        return traversalOrder;
    }

    /*Getter for the log of steps taken during the algorithm*/
    public List<String> getLogSteps() {
        return logSteps;
    }

    /* Method to find the Minimum Spanning Tree (MST) using Kruskal's algorithm*/
    public List<Edge> findMST(List<Node> nodes, List<Edge> edges) {
        /* Clear previous logs and results*/
        logSteps.clear();
        traversalOrder.clear();
        mst.clear();
        parent.clear();

        /* Union-Find initialization: each node is its own parent*/
        for (Node node : nodes) {
            parent.put(node, node);
        }

        /* Sort all edges based on their weights*/
        List<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Comparator.comparingInt(Edge::getWeight));

        /*Iterate through each edge in sorted order*/
        for (Edge edge : sortedEdges) {
            Node from = edge.getFrom();
            Node to = edge.getTo();

            /* Log the current edge being checked*/
            logSteps.add("Checking edge " + from.getId() + " - " + to.getId() + " (" + edge.getWeight() + ")");
            traversalOrder.add(edge); // Add edge to traversal order for logging

            /* Find the roots of the nodes connected by the edge*/
            Node root1 = find(from);
            Node root2 = find(to);

            /* If the roots are different, no cycle is formed*/
            if (!root1.equals(root2)) {
                logSteps.add("✅ Adding edge to MST: " + from.getId() + " - " + to.getId());
                mst.add(edge); /* Add edge to the MST*/
                union(root1, root2); /* Union the two sets*/
            } else {
                /* If the roots are the same, a cycle is detected*/
                logSteps.add("❌ Skipping edge " + from.getId() + " - " + to.getId() + ": Cycle detected");
            }

            /* Check if the MST is complete (n-1 edges for n nodes)*/
            if (mst.size() == nodes.size() - 1) break;
        }

        /* Check if the MST is fully constructed or if the graph is disconnected*/
        if (mst.size() < nodes.size() - 1) {
            logSteps.add("⚠ Graph is disconnected. MST not fully possible.");
            logSteps.add("⛰ Multiple components detected: Forming a Minimum Spanning Forest (MSF).");
        } else {
            logSteps.add("✅ Final MST constructed.");
        }

        /* Return the list of edges in the MST*/
        return new ArrayList<>(mst);
    }

    /* Find the root of a node with path compression*/
    private Node find(Node node) {
        if (parent.get(node) != node) {
            parent.put(node, find(parent.get(node))); // Path compression
        }
        return parent.get(node);
    }

    /* Union two sets by linking their roots*/
    private void union(Node a, Node b) {
        parent.put(find(a), find(b)); /* Union the roots of the two nodes*/
    }
}

