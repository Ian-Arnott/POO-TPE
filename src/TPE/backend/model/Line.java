package TPE.backend.model;

import javafx.scene.paint.Color;

public class Line extends Rectangle{

    private final double distance;

    public Line(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor, fillColor);
        distance = Math.sqrt(Math.pow((bottomRight.getX()-topLeft.getX()),2)-Math.pow((bottomRight.getY()-topLeft.getY()),2));
    }

    public static double distance(Point a, Point b){
        return Math.sqrt(Math.pow((b.getX()-a.getX()),2)-Math.pow((b.getY()-a.getY()),2));
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public Figure makeCopy() {
        return new Line(topLeft,bottomRight,getWidth(),getLineColor(),getFillColor());
    }
}
