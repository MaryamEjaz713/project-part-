import javax.swing.*; /* Library file used to create GUI components*/
import java.awt.*; /* Library file used to handle graphics shapes, colors, and layouts for GUI components*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List; /* Library file used to store and manage a list of elements in the GUI*/

public class GraphPanel extends JPanel {
    /* Instance of the Graph class to represent the graph*/
    private final Graph graph = new Graph();
    /* Canvas for drawing the graph*/
    private final DrawingCanvas canvas = new DrawingCanvas(graph);
    /* Text area for logging actions and messages*/
    private final JTextArea logArea = new JTextArea(8, 50);

    /* Constructor for the GraphPanel*/
    public GraphPanel() {
        /* Set the layout for the panel*/
        setLayout(new BorderLayout());

        /* Create a panel for the top buttons*/
        JPanel topPanel = new JPanel();

        /* Create buttons for various graph operations*/
        JButton addNodeBtn = new JButton("Add Node");
        JButton removeNodeBtn = new JButton("Remove Node");
        JButton addEdgeBtn = new JButton("Add Edge");
        JButton removeEdgeBtn = new JButton("Remove Edge");
        JButton runAlgoBtn = new JButton("Run Kruskal");
        JButton clearBtn = new JButton("Clear");

        /* Add buttons to the top panel*/
        topPanel.add(addNodeBtn);
        topPanel.add(removeNodeBtn);
        topPanel.add(addEdgeBtn);
        topPanel.add(removeEdgeBtn);
        topPanel.add(runAlgoBtn);
        topPanel.add(clearBtn);

        /* Create a scroll pane for the log area*/
        JScrollPane logScroll = new JScrollPane(logArea);
        logArea.setEditable(false); /* Make the log area non-editable*/
        logArea.setBackground(Color.PINK); /* Set background color*/
        logArea.setForeground(Color.BLACK); /* Set text color*/

        /* Add components to the main panel*/
        add(canvas, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(logScroll, BorderLayout.SOUTH);

        /*Initialize the graph with some nodes and edges*/
        graph.addNode("A", 100, 100);
        graph.addNode("B", 300, 100);
        graph.addNode("C", 500, 100);
        graph.addNode("D", 200, 300);
        graph.addNode("E", 400, 300);

        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "D", 2);
        graph.addEdge("B", "C", 6);
        graph.addEdge("B", "E", 3);
        graph.addEdge("C", "E", 1);
        graph.addEdge("D", "E", 5);

        /* Repaint the canvas to reflect the initial graph*/
        canvas.repaint();

        /* Action listener for adding a node*/
        addNodeBtn.addActionListener(evt -> {
            String id = JOptionPane.showInputDialog(this, "Enter node ID:"); /* Prompt for node ID*/
            if (id != null && !id.trim().isEmpty()) {
                /*Generate random coordinates for the new node*/
                int x = (int) (50 + Math.random() * 800);
                int y = (int) (50 + Math.random() * 500);
                graph.addNode(id.trim().toUpperCase(), x, y); /* Add the node to the graph*/
                canvas.repaint(); /*Repaint the canvas*/
                log("Added node: " + id); /*Log the action*/
            }
        });

        //* Action listener for removing a node*/
        removeNodeBtn.addActionListener(evt -> {
            String id = (String) JOptionPane.showInputDialog(this, "Enter node ID to remove:", "Remove Node", JOptionPane.PLAIN_MESSAGE, null, graph.getNodeIds(), "A");
            if (id != null) {
                graph.removeNode(id); /*Remove the node from the graph*/
                canvas.repaint(); /* Repaint the canvas*/
                log("Removed node: " + id); /* Log the action*/
            }
        });

        /* Action listener for adding an edge*/
        addEdgeBtn.addActionListener(evt -> {
            if (graph.getNodes().size() < 2) {
                log("Add at least 2 nodes first."); // Log if not enough nodes
                return;
            }
            /*Prompt for the from and to node IDs and edge weight*/
            String fromId = (String) JOptionPane.showInputDialog(this, "From node ID:", "Add Edge", JOptionPane.PLAIN_MESSAGE, null, graph.getNodeIds(), "A");
            String toId = (String) JOptionPane.showInputDialog(this, "To node ID:", "Add Edge", JOptionPane.PLAIN_MESSAGE, null, graph.getNodeIds(), "B");
            String weightStr = JOptionPane.showInputDialog(this, "Edge weight:");
            try {
                int weight = Integer.parseInt(weightStr); /* Parse the edge weight*/
                // Uncomment the following lines to restrict negative weights
                // if (weight < 0) {
                //     log("Negative weights are not allowed.");
                //     return;
                // }
                graph.addEdge(fromId, toId, weight); /* Add the edge to the graph*/
                canvas.repaint(); /* Repaint the canvas*/
                log("Added edge: " + fromId + " - " + toId + " = " + weight); /* Log the action*/
            } catch (Exception ex) {
                log("Invalid edge input."); /* Log if input is invalid*/
            }
        });

        /* Action listener for removing an edge*/
        removeEdgeBtn.addActionListener(evt -> {
            String from = (String) JOptionPane.showInputDialog(this, "From Node:", "Remove Edge", JOptionPane.PLAIN_MESSAGE, null, graph.getNodeIds(), "A");
            String to = (String) JOptionPane.showInputDialog(this, "To Node:", "Remove Edge", JOptionPane.PLAIN_MESSAGE, null, graph.getNodeIds(), "B");
            if (from != null && to != null) {
                graph.removeEdge(from, to); /* Remove the edge from the graph*/
                canvas.repaint(); /* Repaint the canvas*/
                log("Edge removed: " + from + " - " + to); /* Log the action*/
            }
        });

        /* Action listener for running Kruskal's algorithm*/
        runAlgoBtn.addActionListener(evt -> runKruskalWithAnimation());

        /*Action listener for clearing the graph*/
        clearBtn.addActionListener(evt -> {
            graph.clearAll(); /*Clear all nodes and edges from the graph*/
            canvas.setEdgeColors(new HashMap<>()); /*Reset edge colors*/
            canvas.repaint(); /* Repaint the canvas*/
            log("Graph cleared."); /* Log the action*/
        });
    }

    /* Method to run Kruskal's algorithm with animation*/
    private void runKruskalWithAnimation() {
        graph.clearMST(); /* Clear any existing MST*/
        canvas.setEdgeColors(new HashMap<>()); /* Reset edge colors*/
        canvas.repaint(); /* Repaint the canvas*/

        KruskalAlgorithm kruskal = new KruskalAlgorithm(); /* Create an instance of Kruskal's algorithm*/
        List<Edge> mstEdges = kruskal.findMST(graph.getNodes(), graph.getEdges()); /* Find the MST edges*/
        List<Edge> traversal = kruskal.getTraversalOrder(); /* Get the order of edge traversal*/
        List<String> logs = kruskal.getLogSteps(); /* Get the log steps from the algorithm*/

        Map<Edge, Color> edgeColors = new HashMap<>(); /* Map to store edge colors*/

        /* Timer for animating the edge traversal*/
        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
            int index = 0; // Index to track the current edge

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < traversal.size()) {
                    Edge current = traversal.get(index); /* Get the current edge*/
                    String logMsg = logs.get(index); /* Get the corresponding log message*/

                    edgeColors.put(current, Color.GREEN); /* Set the current edge color to green*/
                    canvas.setEdgeColors(edgeColors); /* Update the canvas with new edge colors*/
                    canvas.repaint(); /*Repaint the canvas*/
                    log(logMsg); /*Log the current action*/

                    index++; /* Move to the next edge*/
                } else {
                    ((javax.swing.Timer) e.getSource()).stop(); /*Stop the timer*/

                    /*Set all MST edges to red*/
                    for (Edge edge : mstEdges) {
                        edgeColors.put(edge, Color.RED);
                    }

                    canvas.setEdgeColors(edgeColors); /*Update the canvas with MST edge colors*/
                    canvas.repaint(); /*Repaint the canvas*/

                    /*Log any remaining messages*/
                    for (int i = index; i < logs.size(); i++) {
                        log(logs.get(i));
                    }

                    log("\u2705 Final MST constructed."); /* Log the completion of the MST*/
                }
            }
        });

        timer.start(); /* Start the timer*/
    }

    /*Method to log messages to the log area*/
    private void log(String msg) {
        logArea.append(msg + "\n"); /* Append the message to the log area*/
    }
}