package TPE.backend.model;

import javafx.scene.paint.Color;

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

    public abstract void moveFigure(double diffX, double diffY);

    public abstract Figure makeCopy();
}
