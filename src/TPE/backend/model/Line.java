package TPE.backend.model;

import javafx.scene.paint.Color;

public class Line extends Rectangle{

    private final double distance;

    public Line(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor, fillColor);
        distance = Math.sqrt(Math.pow((bottomRight.getX()-topLeft.getX()),2)+Math.pow((bottomRight.getY()-topLeft.getY()),2));
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public Figure makeCopy() {
        return new Line(topLeft,bottomRight,getWidth(),getLineColor(),getFillColor());
    }

    @Override
    public boolean containsPoint(Point point) {
        double crossProduct = (point.getY() - topLeft.getY()) * (bottomRight.getX() - topLeft.getX())
                - (point.getX() - topLeft.getX()) * (bottomRight.getY() - topLeft.getY());
        return Math.abs(crossProduct) < distance;
    }
}
