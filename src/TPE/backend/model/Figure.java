package TPE.backend.model;

import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class Figure {

    private Color lineColor;
    private double width;
    private Color fillColor;

    public Figure(double width, Color lineColor, Color fillColor){
        this.width = width;
        this.fillColor = fillColor;
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public abstract boolean containsPoint(Point point);

    public abstract boolean isWithin(Point p1, Point p2);

    public abstract void moveFigure(double diffX, double diffY);

    public abstract Figure makeCopy();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return Double.compare(figure.width, width) == 0 && lineColor.equals(figure.lineColor) && fillColor.equals(figure.fillColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineColor, width, fillColor);
    }
}
