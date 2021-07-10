package TPE.backend.model;

import javafx.scene.paint.Color;

public class Circle extends Figure {

    private final Point centerPoint;
    private final double radius;

    public Circle(Point centerPoint, double radius, double width, Color lineColor, Color fillColor) {
        super(width, lineColor, fillColor);
        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, radius);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        centerPoint.setX(centerPoint.getX() + diffX);
        centerPoint.setY(centerPoint.getY() + diffY);
    }

    @Override
    public Figure makeCopy() {
        return new Circle(centerPoint,radius,getWidth(),getLineColor(),getFillColor());
    }
}
