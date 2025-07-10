public class Node {/*declares a class named  node that represents a single node in the network*/
    private final String id;/*each node has a unique ID that cannot be changed once assigned*/
    private final int x, y;/*Stores the position of the node in the graph on both x-axis and y-axis*/
    /*declares a constructor that sets the node's ID and its position*/
    public Node(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    /*Returns the unique ID of the node*/
    public String getId() {
        return id;
    }
    /*Returns the x coordinate of the node*/
    public int getX() {
        return x;
    }
    /*Returns the y coordinate of the node*/
    public int getY() {
        return y;
    }
}




