package TPE.backend.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

    private Figure rectangle,circle,ellipse,square,line;

    @BeforeEach
    void setUpTest(){
        rectangle = new Rectangle(new Point(1,2), new Point(4,3),1, Color.BLACK,Color.YELLOW);
        circle = new Circle(new Point(2,2), new Point(4,4),1,Color.BLACK,Color.YELLOW);
        ellipse = new Ellipse(new Point(1,2), new Point(4,3),1, Color.BLACK,Color.YELLOW);
        square = new Square(new Point(1,2), new Point(4,3),1, Color.BLACK,Color.YELLOW);
        line = new Line(new Point(1,2), new Point(4,3),1, Color.BLACK,Color.YELLOW);
    }
    @Test
    void containsPoint() {
        assertTrue(rectangle.containsPoint(new Point(1,2.5)));
        assertFalse(rectangle.containsPoint(new Point(10,10)));
        assertTrue(circle.containsPoint(new Point(2,2)));
        assertFalse(circle.containsPoint(new Point(10,10)));
        assertTrue(ellipse.containsPoint(new Point(1,2.5)));
        assertFalse(ellipse.containsPoint(new Point(10,10)));
        assertTrue(line.containsPoint(new Point(1,2)));
        assertFalse(line.containsPoint(new Point(10,10)));
    }

    @Test
    void isWithin() {
        Point startTrue = new Point(0,0);
        Point endTrue = new Point(5,5);
        assertTrue(rectangle.isWithin(startTrue,endTrue));
        assertTrue(circle.isWithin(startTrue,endTrue));
        assertTrue(ellipse.isWithin(startTrue,endTrue));
        assertTrue(square.isWithin(startTrue,endTrue));
        assertTrue(line.isWithin(startTrue,endTrue));

        Point endFalse = new Point(10,10);
        assertFalse(rectangle.isWithin(endTrue,endFalse));
        assertFalse(circle.isWithin(endTrue,endFalse));
        assertFalse(ellipse.isWithin(endTrue,endFalse));
        assertFalse(square.isWithin(endTrue,endFalse));
        assertFalse(line.isWithin(endTrue,endFalse));

    }

    @Test
    void moveFigure() {
        rectangle.moveFigure(1,1);
        ellipse.moveFigure(1,1);
        circle.moveFigure(1,1);
        square.moveFigure(1,1);
        line.moveFigure(1,1);

        assertEquals(rectangle, new Rectangle(new Point(2, 3), new Point(5, 4), 1, Color.BLACK, Color.YELLOW));
        assertEquals(ellipse, new Ellipse(new Point(2, 3), new Point(5, 4), 1, Color.BLACK, Color.YELLOW));
        assertEquals(circle, new Circle(new Point(3, 3), new Point(5, 5), 1, Color.BLACK, Color.YELLOW));
        assertEquals(square, new Square(new Point(2, 3), new Point(5, 4), 1, Color.BLACK, Color.YELLOW));
        assertEquals(line, new Line(new Point(2, 3), new Point(5, 4), 1, Color.BLACK, Color.YELLOW));

    }

    @Test
    void makeCopy() {
        assertEquals(rectangle,rectangle.makeCopy());
        assertEquals(circle,circle.makeCopy());
        assertEquals(ellipse,ellipse.makeCopy());
        assertEquals(square,square.makeCopy());
        assertEquals(line,line.makeCopy());
    }
}