package TPE.backend.model;

public class Ellipse extends Rectangle{
    public Ellipse(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return String.format("Elipse [ %s , %s ]", topLeft, bottomRight);
    }

}
