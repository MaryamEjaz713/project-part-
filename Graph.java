import java.util.*;

public class Graph {
    /* List to store nodes in the graph*/
    private final List<Node> nodes = new ArrayList<>();
    /* List to store edges in the graph*/
    private final List<Edge> edges = new ArrayList<>();
    /* List to store edges of the Minimum Spanning Tree (MST)*/
    private List<Edge> mstEdges = new ArrayList<>();

    /* Method to add a node to the graph*/
    public void addNode(String id, int x, int y) {
        nodes.add(new Node(id, x, y)); /* Create a new node and add it to the list*/
    }

    /* Method to remove a node from the graph*/
    public void removeNode(String id) {
        Node target = getNodeById(id); /* Find the node by ID*/
        if (target != null) {
            nodes.remove(target); /* Remove the node from the list*/
            /* Remove edges connected to the node*/
            edges.removeIf(e -> e.getFrom().equals(target) || e.getTo().equals(target));
        }
    }

    /* Method to add an edge between two nodes*/
    public void addEdge(String fromId, String toId, int weight) {
        Node from = getNodeById(fromId); /* Get the starting node*/
        Node to = getNodeById(toId); /* Get the ending node*/
        /* Check if both nodes exist and are not the same*/
        if (from != null && to != null && !from.equals(to)) {
            edges.add(new Edge(from, to, weight)); /*Create and add the edge*/
        }
    }

    /* Method to remove an edge between two nodes*/
    public void removeEdge(String fromId, String toId) {
        Node from = getNodeById(fromId); /* Get the starting node*/
        Node to = getNodeById(toId); /* Get the ending node*/
        if (from != null && to != null) {
            /* Remove the edge if it exists in either direction*/
            edges.removeIf(e -> (e.getFrom().equals(from) && e.getTo().equals(to)) ||
                    (e.getFrom().equals(to) && e.getTo().equals(from)));
        }
    }

    /* Method to clear the MST edges*/
    public void clearMST() {
        mstEdges.clear(); /* Clear the list of MST edges*/
    }

    /* Method to set the MST edges*/
    public void setMST(List<Edge> mst) {
        this.mstEdges = mst; /* Set the MST edges*/
    }

    /* Method to clear all nodes, edges, and MST edges*/
    public void clearAll() {
        nodes.clear(); /* Clear the list of nodes*/
        edges.clear(); /* Clear the list of edges*/
        mstEdges.clear(); /* Clear the list of MST edges*/
    }

    /* Getter for the list of nodes*/
    public List<Node> getNodes() {
        return nodes;
    }

    /* Getter for the list of edges*/
    public List<Edge> getEdges() {
        return edges;
    }

    /* Getter for the list of MST edges*/
    public List<Edge> getMSTEdges() {
        return mstEdges;
    }

    /* Method to get a node by its ID*/
    public Node getNodeById(String id) {
        for (Node n : nodes) {
            if (n.getId().equalsIgnoreCase(id)) return n; /* Return the node if found*/
        }
        return null; /* Return null if not found*/
    }

    /* Method to get an array of node IDs*/
    public String[] getNodeIds() {
        return nodes.stream().map(Node::getId).toArray(String[]::new); /* Convert node IDs to an array*/
    }

    /* Method to find the shortest path between two nodes using Dijkstra's algorithm*/
    public List<String> shortestPath(String sourceId, String destId) {
        Map<Node, Integer> dist = new HashMap<>(); /* Map to store distances from the source*/
        Map<Node, Node> prev = new HashMap<>(); /* Map to store previous nodes in the path*/
        Set<Node> visited = new HashSet<>(); /*Set to track visited nodes*/
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get)); /* Priority queue for the nodes*/

        Node source = getNodeById(sourceId); /* Get the source node*/
        Node destination = getNodeById(destId); /* Get the destination node*/

        if (source == null || destination == null) return null; /* Return null if source or destination is not found*/

        /*Initialize distances and previous nodes*/
        for (Node node : nodes) {
            dist.put(node, Integer.MAX_VALUE); /* Set initial distance to infinity*/
            prev.put(node, null); /* No previous node initially*/
        }
        dist.put(source, 0); /* Distance to the source is 0*/
        queue.add(source); /*Add the source node to the queue*/

        /* Main loop of Dijkstra's algorithm*/
        while (!queue.isEmpty()) {
            Node current = queue.poll(); /* Get the node with the smallest distance*/
            if (!visited.add(current)) continue; /* Skip if already visited*/

            /* Check all edges connected to the current node*/
            for (Edge edge : edges) {
                Node neighbor = null;
                /* Determine the neighbor node*/
                if (edge.getFrom().equals(current)) {
                    neighbor = edge.getTo();
                } else if (edge.getTo().equals(current)) {
                    neighbor = edge.getFrom();
                }
                /* If the neighbor is valid and not visited*/
                if (neighbor != null && !visited.contains(neighbor)) {
                    int newDist = dist.get(current) + edge.getWeight(); /* Calculate new distance*/
                    /* If the new distance is shorter, update it*/
                    if (newDist < dist.get(neighbor)) {
                        dist.put(neighbor, newDist); /* Update distance*/
                        prev.put(neighbor, current); /* Update previous node*/
                        queue.add(neighbor); /*Add neighbor to the queue*/
                    }
                }
            }
        }

        /* Build the path from source to destination*/
        List<String> path = new ArrayList<>();
        for (Node at = destination; at != null; at = prev.get(at)) {
            path.add(0, at.getId()); /*Add nodes to the path in reverse order*/
        }

        /* Return null if the path does not include the source*/
        if (path.size() == 1 && !path.get(0).equals(sourceId)) return null;
        return path; /*Return the found path*/
    }
}