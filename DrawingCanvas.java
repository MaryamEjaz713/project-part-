import javax.swing.*;/*library file used to create Gui*/
import java.awt.*;/*library file used to handle graphics shapes,colors and layouts for GUI component*/
import java.util.HashMap;/*library file used to store data as  key value pairs like   mapping colors to edges*/
import java.util.List;/*libray file used to store and manage list of elements  in the Gui*/
import java.util.Map;/*library file used to define a structure for storing data as key value pairs*/

class DrawingCanvas extends JPanel {/*defines a custom class by extending Jpanel to draw nodes and edges on the GUI*/
    private final Graph graph;/*declares a Graph variable named "graph" to store the structure of nodes and edges*/
    private final Map<Edge, Color> edgeColors = new HashMap<>();/*declares a map to store edge color pairs for drawing coloured edges in GUI*/

    public DrawingCanvas(Graph graph) {/*declares a contructor that recieves a graph( with nodes,edges,weight) for drawing*/
        this.graph = graph;/*stores the input  graph in the class varaibale */
        setPreferredSize(new Dimension(900, 600));/*sets the preferred  size of canvas */
        setBackground(Color.WHITE);/*sets the background color of canvas to white*/
    }

    public void setEdgeColors(Map<Edge, Color> colors) {/*defines a method to update edge colors using the given map*/
        edgeColors.clear();/*clear old edge colors*/
        edgeColors.putAll(colors);/*add new edge color from the given input map*/
    }

    @Override/*tells the java that this method overrides the original paintComponent method from Jpanel*/
    protected void paintComponent(Graphics g) {/*defines the method that handles custom drawing on the panel*/
        super.paintComponent(g);/*clears the panel before drawing to avoid overlapping graphics*/
        drawEdges(g, graph.getEdges());/*calls a method to draw all edges of the  graph*/
        drawNodes(g);/*calls  a method to draw all nodes of  graph*/
    }

    private void drawEdges(Graphics g, List<Edge> edges) {/*defines a method that draws each edge from the provided list on canvas*/
        for (Edge edge : edges) {/*for loop is used to go through each edge in the list and draw it on the screen*/
            /*stores the x and y positions of the source and destination nodes*/
            int x1 = edge.getFrom().getX(), y1 = edge.getFrom().getY();
            int x2 = edge.getTo().getX(), y2 = edge.getTo().getY();

            Color edgeColor = edgeColors.getOrDefault(edge, Color.BLACK);/*checks weather the map has a color for the edge if not ,uses BLACK as default*/
            g.setColor(edgeColor);/*sets the current drawing color to the edge's color*/
            g.drawLine(x1, y1, x2, y2);/*draw lines between source and destination node*/

            g.setColor(Color.BLACK);/*sets the color to black for drawing the weight*/
            g.drawString(String.valueOf(edge.getWeight()), (x1 + x2) / 2, (y1 + y2) / 2);/*draws the edge weight at the midpoint of  the line*/
        }
    }

    private void drawNodes(Graphics g) {/*defines a method to draw all nodes from the graph*/
        for (Node node : graph.getNodes()) {/*iterates through each node in the graph and draws it on canvas*/
            g.setColor(Color.ORANGE);/*sets the fill color to orange for drawing the node*/
            g.fillOval(node.getX() - 15, node.getY() - 15, 30, 30);/* draws a filled circle to represent the node*/

            g.setColor(Color.BLACK);/*sets the color to black for drawing the noded label*/
            g.drawString(node.getId(), node.getX() - 5, node.getY() + 5);/*draws the node iD inside the circle*/
        }
    }
}
