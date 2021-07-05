package TPE.backend.model;

import javafx.scene.paint.Color;

public class Rectangle extends Figure {

    protected final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(width, lineColor, fillColor);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

}
