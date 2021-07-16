package TPE.backend;

import TPE.backend.model.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CanvasStateTest {

    private CanvasState canvasState;
    private Figure figure1, figure2, figure3;

    @BeforeEach
    void setUpTest(){
        canvasState = new CanvasState();
        figure1 = new Rectangle(new Point(1,2), new Point(2,3),1, Color.BLACK,Color.YELLOW);
        figure2 = new Ellipse(new Point(1,2), new Point(2,3),1, Color.BLACK,Color.YELLOW);
        figure3 = new Line(new Point(1,2), new Point(2,3),1, Color.BLACK,Color.YELLOW);
    }

    @Test
    void addAndRemoveFigure() {
        assertTrue(canvasState.addFigure(figure1));
        assertTrue(canvasState.containsFigure(figure1));
        assertTrue(canvasState.notEmpty());
        assertTrue(canvasState.removeFigure(figure1));
        assertFalse(canvasState.notEmpty());
    }

    @Test
    void figuresForwardsAndBackwards() {
        canvasState.addFigure(figure1);
        canvasState.addFigure(figure2);
        canvasState.addFigure(figure3);
        Figure[] figures = new Figure[]{figure1,figure2,figure3};
        testfigures(canvasState.figures(),figures);

        canvasState.moveFigureBackwards(figure2);
        figures = new Figure[]{figure2, figure1, figure3};
        testfigures(canvasState.figures(),figures);

        canvasState.moveFigureForwards(figure1);
        figures = new Figure[]{figure2, figure3, figure1};
        testfigures(canvasState.figures(),figures);

        CanvasState selectedFigures = new CanvasState();
        selectedFigures.addFigure(figure2);
        selectedFigures.addFigure(figure3);
        canvasState.moveFigureForwards(selectedFigures);
        figures = new Figure[]{figure1, figure2, figure3};
        testfigures(canvasState.figures(),figures);

        canvasState.moveFigureBackwards(selectedFigures);
        figures = new Figure[]{figure2, figure3, figure1};
        testfigures(canvasState.figures(),figures);
    }

    private void testfigures(Iterable<Figure> it, Figure[] figures) {
        int i = 0;
        for (Figure figure : it){
            assertEquals(figures[i++],figure);
        }
        assertEquals(figures.length,i);
    }


    @Test
    void removeAll() {
        canvasState.addFigure(figure1);
        canvasState.addFigure(figure2);
        canvasState.removeAll();
        assertFalse(canvasState.notEmpty());
    }


    @Test
    void copyAndSetState() {
        List<Figure> copy = canvasState.copyState();
        Iterator<Figure> copyIt = copy.listIterator();
        for (Figure figure : canvasState.figures()){
            assertEquals(figure,copyIt.next());
        }
        canvasState.setState(copy);
        copyIt = copy.listIterator();
        for (Figure figure : canvasState.figures()){
            assertEquals(copyIt.next(),figure);
        }
    }
}