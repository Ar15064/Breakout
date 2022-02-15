import acm.graphics.GRect;
import java.awt.Color;


public class Brick extends GRect {


    public int brickPoint;
    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;


    public Brick(double x, double y, Color color,int row){

        super(x,y,WIDTH, HEIGHT);
        this.setFillColor(color);
        this.setFilled(true);



        switch (row){
            // 7 is the row we need to add the brickPoint to 2
            case 8:
            case 9:
                brickPoint = 1;
                break;
            case 6:
            case 7:
                brickPoint = 2;

                break;

            case 4:
            case 5:
                brickPoint = 4;
                break;

            case 2:
            case 3:
                brickPoint = 6;
                break;

            case 0:
            case 1:
                brickPoint = 8;




        }
    }


}