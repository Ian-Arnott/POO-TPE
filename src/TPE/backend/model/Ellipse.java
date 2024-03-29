package TPE.backend.model;

import javafx.scene.paint.Color;

public class Ellipse extends Rectangle{

    private final Point center;
    private final double sminor ,smajor;

    public Ellipse(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor, fillColor);
        center = new Point((topLeft.getX()+bottomRight.getX())/2,(topLeft.getY()+bottomRight.getY())/2);
        sminor = center.getY() - topLeft.getY();
        smajor = bottomRight.getX() - center.getX();
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Longitud Xaxis: %s, Longitud Yaxis: %s]", center,smajor,sminor);
    }

    @Override
    public Figure makeCopy() {
        return new Ellipse(topLeft,bottomRight,getWidth(),getLineColor(),getFillColor());
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        super.moveFigure(diffX, diffY);
        center.setX(center.getX() + diffX);
        center.setY(center.getY() + diffY);
    }

    @Override
    public boolean containsPoint(Point point) {
        return ((Math.pow((point.getX()-center.getX()),2))/Math.pow(smajor,2) +
                (Math.pow((point.getY()-center.getY()),2))/Math.pow(sminor,2))<= 1;
    }
}
