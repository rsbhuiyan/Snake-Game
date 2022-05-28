import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {

    //screen size
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    //object size
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH *SCREEN_HEIGHT)/UNIT_SIZE;

    static final int DELAY = 75;
    //x and y coordinates of body parts for snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6;
    //var for points snake has to eat
    int pointsEaten;
    int pointsX;
    int pointsY;

    //snake beings going right
    char direction = 'R';
    boolean running = false;

    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        //intialize game window before game starts
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        //backround color
        this.setBackground(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        start();
    }
    public void start(){
        newPoints();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            //draw points
            g.setColor(Color.yellow);
            g.fillOval(pointsX, pointsY, UNIT_SIZE, UNIT_SIZE);

            //draw snake
            for (int i = 0; i < bodyParts; i++) {
                //head
                if (i == 0) {
                    g.setColor(new Color(222, 49, 99));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                //body
                else {
                    g.setColor(new Color(227, 34, 39));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //score text up top
            g.setColor(Color.PINK);
            g.setFont(new Font("Serif", Font.BOLD,40));
            FontMetrics center = getFontMetrics((g.getFont()));
            g.drawString("SCORE: " + pointsEaten,(SCREEN_WIDTH- center.stringWidth("SCORE: " + pointsEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    //creates a point
    public void newPoints(){
        //generate x and y coordinate for points to spawn
        pointsX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))* UNIT_SIZE;
        pointsY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))* UNIT_SIZE;

    }
    //controls to move snake
    public void move(){
        for(int i = bodyParts; i>0; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //switch statment for what direction each block should move depending on the char R = right etc.
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }

    }
    public void checkPoints(){
        //check if head of snake touches points
        if((x[0]== pointsX) && (y[0] == pointsY)){
            bodyParts++;
            pointsEaten++;
            newPoints();
        }
    }
    public void checkCollisions(){
        for(int i = bodyParts; i > 0; i--){
            //checks if head touches body
            if((x[0]==x[i]) &&(y[0]==y[i])){
                running = false;
            }
        }
        //checks if head touches borders
        //left
        if(x[0] < 0){
            running = false;
        }
        //right
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //top
        if(y[0] < 0){
            running = false;
        }
        //bottom
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running)
        {
            timer.stop();
        }



    }
    public void gameOver(Graphics g){
        //text for score
        g.setColor(Color.PINK);
        g.setFont(new Font("Serif", Font.BOLD,40));
        FontMetrics center1 = getFontMetrics((g.getFont()));
        g.drawString("SCORE: " + pointsEaten,(SCREEN_WIDTH- center1.stringWidth("SCORE: " + pointsEaten))/2, g.getFont().getSize());
        //game over text
        g.setColor(Color.PINK);
        g.setFont(new Font("Serif", Font.ITALIC,75));
        //line text to center
        FontMetrics center = getFontMetrics((g.getFont()));
        g.drawString("GAME OVER",(SCREEN_WIDTH- center.stringWidth(" GAME OVER"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if games running will call move func, check for variables
        if(running){
            move();
            checkPoints();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    //limit to 90 deg turns
                    if(direction!= 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    //limit to 90 deg turns
                    if(direction!= 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    //limit to 90 deg turns
                    if(direction!= 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    //limit to 90 deg turns
                    if(direction!= 'U'){
                        direction = 'D';
                    }
                    break;

            }
        }
    }
}
