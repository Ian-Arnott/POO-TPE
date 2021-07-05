package TPE.backend.model;

import javafx.scene.paint.Color;

public class Ellipse extends Rectangle{
    public Ellipse(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor, fillColor);
    }

    @Override
    public String toString() {
        return String.format("Elipse [ %s , %s ]", topLeft, bottomRight);
    }

}
