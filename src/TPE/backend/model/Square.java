package TPE.backend.model;

import javafx.scene.paint.Color;

public class Square extends Rectangle{

    public Square(Point topLeft, Point bottomRight, double width, Color lineColor, Color fillColor) {
        super(topLeft, bottomRight,width, lineColor,fillColor);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", topLeft, bottomRight);
    }

}
