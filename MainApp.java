import javax.swing.*;
/*library file used to create GUI*/

public class MainApp {/*Main class*/
    public static void main(String[] args) {/*The program execution  starts from here*/
        SwingUtilities.invokeLater(() -> {/*ensures the GUI code runs safely on the event dispatch thread*/
            JFrame frame = new JFrame("Routing Simulator - With Shortest Path");/*Used for setting the title of the GUI window*/
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/*exits the entire program when user closes the window*/
            frame.setSize(1000, 700);/*sets the size of window  including its length and height*/
            frame.setLocationRelativeTo(null);/*centers the window  on the screen*/
            frame.setContentPane(new GraphPanel());/*creates a new GraphPanel object to use its methods and display the main content*/
            frame.setVisible(true);/* displays the window on the screen*/
        });
    }
}






