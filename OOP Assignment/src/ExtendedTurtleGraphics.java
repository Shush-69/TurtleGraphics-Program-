import java.awt.Graphics;
import uk.ac.leedsbeckett.oop.TurtleGraphics;


public class ExtendedTurtleGraphics extends TurtleGraphics{

    public void circle(int diameter)
    {
        Graphics g = this.getGraphicsConext();
        g.drawOval(this.getxPos()-diameter/2, this.getyPos()-diameter/2, diameter, diameter);
    }
}