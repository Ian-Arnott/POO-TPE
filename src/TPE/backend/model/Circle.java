package TPE.backend.model;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Circle extends Figure {

    private final Point centerPoint, endPoint;
    private final double radius;

    public Circle(Point startPoint, Point endPoint, double width, Color lineColor, Color fillColor) {
        super(width, lineColor, fillColor);
        this.centerPoint = startPoint;
        this.endPoint = endPoint;
        this.radius = Math.abs(endPoint.getX() - startPoint.getX());
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
    public boolean containsPoint(Point point) {
        return Math.sqrt(Math.pow(centerPoint.getX() - point.getX(), 2) +
                Math.pow(centerPoint.getY() - point.getY(), 2)) < radius;
    }

    @Override
    public boolean isWithin(Point p1, Point p2) {
        return p1.getX() <= centerPoint.getX() - radius
                && p1.getY() <= centerPoint.getY() - radius
                && p2.getX() >= centerPoint.getX() + radius
                && p2.getY() >= centerPoint.getY() + radius;
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        centerPoint.setX(centerPoint.getX() + diffX);
        centerPoint.setY(centerPoint.getY() + diffY);
    }

    @Override
    public Figure makeCopy() {
        return new Circle(centerPoint,endPoint,getWidth(),getLineColor(),getFillColor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0 && centerPoint.equals(circle.centerPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), centerPoint, radius);
    }
}
