package TPE.backend.model;

import javafx.scene.paint.Color;

public class Ellipse extends Rectangle{

    private Point center;
    private final double sminor ,smajor;

    public Ellipse(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor, fillColor);
        center = new Point((topLeft.getX()+bottomRight.getX())/2,(topLeft.getY()+bottomRight.getY())/2);
        sminor = topLeft.getY() - center.getY();
        smajor = bottomRight.getX() - center.getX();
    }

    public double getSmajor() {
        return smajor;
    }

    public double getSminor() {
        return sminor;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s]", center);
    }

}