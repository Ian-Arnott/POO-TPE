package TPE.backend;

import TPE.backend.model.Figure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    private final List<Figure> list = new ArrayList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void removeFigure(Figure figure){
        list.remove(figure);
    }

    public boolean containsFigure(Figure figure){
        return list.contains(figure);
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void removeAll() {
        list.removeAll(list);
    }
}
