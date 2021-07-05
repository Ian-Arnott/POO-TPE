package TPE.backend.model;

import javafx.scene.paint.Color;

public class Line extends Rectangle{

    public Line(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor, fillColor);
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", topLeft, bottomRight);
    }
}
