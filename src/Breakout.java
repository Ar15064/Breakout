
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;
import acm.graphics.GLabel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {



    /*
    1) What do I do about lives
        // I need somewhere to store the number of lives ||
        // Every time I lose the ball, I need to decrease lives ||
    2) All of the bricks take the same number of hits

    3) How many lives I have left ||
    4) how many points do I have
    5) what happens if I run out of lives
        // you die ||
    6) how can I tell if a brick hit ||

    7) power ups in some bricks?
    8) multiple levels?
    9) An animation of broken brick?
     */

    private Ball ball;
    private Paddle paddle;
    private int lives = 3;
    private GLabel playersLabel;






    private int numBricksInRow;
    public Color[] rowColors = {Color.BLACK, Color.DARK_GRAY,CustomColors.myColor2,CustomColors.myColor3,CustomColors.myColor4,CustomColors.myColor5,CustomColors.myColor6,CustomColors.myColor7,CustomColors.myColor8,CustomColors.myColor9};

    @Override
    public void init(){
        numBricksInRow = (int) (getWidth() / (Brick.WIDTH + 5.0));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {

                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY =  Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX, brickY, rowColors[row], row);
                add(brick);
                playersLabel = new GLabel("You have " + lives + " lives");
                playersLabel.setFont("Arial-20");
                add(playersLabel,0,20);
                playersLabel.setLabel("You have " + lives + " lives");


            }

        }

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50 ,10);
        add(paddle);







    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            // handle collisions
            handleCollisions();

            // handle losing the ball
            if(ball.lost){
                handleLoss();
            }

            pause(5);
        }
    }

    private void handleCollisions(){
        // obj can store what we hit
        GObject obj = null;

        // check to see if the ball is about to hit something

        if(obj == null){
            // check the top right corner
            obj = this.getElementAt(ball.getX()+ball.getWidth(), ball.getY());
        }

        if(obj == null){
            // check the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        //check the bottom right corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }
        //check the bottom left corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        // see if we hit something
        if(obj != null){

            // lets see what we hit!
            if(obj instanceof Paddle){


                if(ball.getX() < (paddle.getX() + (paddle.getWidth() * .2))){
                    // did I hit the left side of the paddle?
                    ball.bounceLeft();
                } else if(ball.getX() > (paddle.getX() + (paddle.getWidth() * .8))) {
                    // did I hit the right side of the paddle?
                    ball.bounceRight();
                } else {
                    // did I hit the middle of the paddle?
                    ball.bounce();


                }

            }


            if(obj instanceof Brick){ // we can use Brick in here duhhh
                ((Brick) obj).brickPoint -= 1;
                if(((Brick) obj).brickPoint == 0){
                    this.remove(obj);
                }


                // bounce the ball
                ball.bounce();
                // destroy the brick


            }

        }

        // if by the end of the method obj is still null, we hit nothing
    }

    private void handleLoss(){
        playersLabel.setLabel("You have " + lives + " lives");
        ball.lost = false;
        lives -= 1;
        Dialog.showMessage("You have " + lives);
        if(lives == 0){
            Dialog.showMessage("You lose");

            System.exit(0);
        }
        reset();
    }

    private void reset(){
        ball.setLocation(getWidth()/2, 350);
        paddle.setLocation(230, 430);
        waitForClick();
    }

    public static void main(String[] args) {
        new Breakout().start();
    }

}