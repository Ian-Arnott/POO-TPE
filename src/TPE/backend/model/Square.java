package TPE.backend.model;

import javafx.scene.paint.Color;

public class Square extends Rectangle{

    private final double sideLength;

    public Square(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor,fillColor);
        sideLength = Math.abs(bottomRight.getX() - topLeft.getX());
    }

    public double getSideLength() {
        return sideLength;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", topLeft, new Point(getTopLeft().getX() + sideLength, topLeft.getY() + sideLength));
    }

}
