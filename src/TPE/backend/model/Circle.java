package TPE.backend.model;

import javafx.scene.paint.Color;

public class Circle extends Figure {

    protected final Point centerPoint;
    protected final double radius;

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

}
