import javax.swing.*;

public class GameWindow extends JFrame {
    GameWindow(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //game appears in middle of screen
        this.setLocationRelativeTo(null);
    }
}
