package TPE.backend.model;

import javafx.scene.paint.Color;

import java.util.Objects;

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

    @Override
    public Figure makeCopy() {
        return new Rectangle(topLeft,bottomRight,getWidth(),getLineColor(),getFillColor());
    }

    @Override
    public boolean containsPoint(Point point) {
        return point.getX() >= topLeft.getX() && point.getX() <= bottomRight.getX() &&
                point.getY() >= topLeft.getY() && point.getY() <= bottomRight.getY();
    }

    @Override
    public boolean isWithin(Point p1, Point p2) {
        return p1.getX() < topLeft.getX() && p2.getX() > bottomRight.getX() &&
                p1.getY() < topLeft.getY() && p2.getY() > bottomRight.getY();
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        topLeft.setX(topLeft.getX() + diffX);
        topLeft.setY(topLeft.getY() + diffY);
        bottomRight.setX(bottomRight.getX() + diffX);
        bottomRight.setY(bottomRight.getY() + diffY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle)) return false;
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return topLeft.equals(rectangle.topLeft) && bottomRight.equals(rectangle.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), topLeft, bottomRight);
    }
}
