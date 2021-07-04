package TPE.backend.model;

public class Line extends Rectangle{

    public Line(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", topLeft, bottomRight);
    }
}
