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

    public void moveFigure(double diffX, double diffY){
        if(getClass() == Rectangle.class) {
            Rectangle rectangle = (Rectangle) this;
            rectangle.getTopLeft().setX(rectangle.getTopLeft().getX() + diffX);
            rectangle.getBottomRight().setX(rectangle.getBottomRight().getX() + diffX);
            rectangle.getTopLeft().setY(rectangle.getTopLeft().getY() + diffY);
            rectangle.getBottomRight().setY(rectangle.getBottomRight().getY() + diffY);
        }
        else if(getClass() == Circle.class) {
            Circle circle = (Circle) this;
            circle.getCenterPoint().setX(circle.getCenterPoint().getX() + diffX);
            circle.getCenterPoint().setY(circle.getCenterPoint().getY() + diffY);
        }
        else if (getClass() == Line.class) {
            Line line = (Line) this;
            line.getTopLeft().setX(line.getTopLeft().getX() + diffX);
            line.getTopLeft().setY(line.getTopLeft().getY() + diffY);
            line.getBottomRight().setX(line.getBottomRight().getX() + diffX);
            line.getBottomRight().setY(line.getBottomRight().getY() + diffY);
        }
        else if (getClass() == Square.class) {
            Square square = (Square) this;
            square.getTopLeft().setX(square.getTopLeft().getX() + diffX);
            square.getTopLeft().setY(square.getTopLeft().getY() + diffY);
            square.getBottomRight().setX(square.getBottomRight().getX() + diffX);
            square.getBottomRight().setY(square.getBottomRight().getY() + diffY);
        }
        else if (getClass() == Ellipse.class) {
            Ellipse ellipse = (Ellipse) this;
            ellipse.getTopLeft().setX(ellipse.getTopLeft().getX() + diffX);
            ellipse.getTopLeft().setY(ellipse.getTopLeft().getY() + diffY);
            ellipse.getBottomRight().setX(ellipse.getBottomRight().getX() + diffX);
            ellipse.getBottomRight().setY(ellipse.getBottomRight().getY() + diffY);
            ellipse.getCenter().setX(ellipse.getCenter().getX() + diffX);
            ellipse.getCenter().setY(ellipse.getCenter().getY() + diffY);
        }
    }
}
