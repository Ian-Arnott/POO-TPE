package TPE.backend;

import TPE.backend.model.Figure;

import java.util.ArrayList;
import java.util.Collections;
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

    public boolean notEmpty() {
        return !list.isEmpty();
    }

    public void removeAll() {
        list.removeAll(list);
    }

    public void moveFigureBackwards(Figure selectedFigure) {
        int i = list.indexOf(selectedFigure);
        if (list.indexOf(selectedFigure)!=0){
            Collections.swap(list,i,i-1);
        }
    }
    public void moveFigureBackwards(CanvasState selectedFigures) {
        int i = 1;
        for (Figure figure : selectedFigures.figures()){
            if (selectedFigures.getSize()!=list.size()){
                if (list.indexOf(figure)!=0 && list.indexOf(figure)-i>=0){
                    Collections.swap(list,list.indexOf(figure),list.indexOf(figure)-1);
                }
                i++;
            }
        }
    }

    private int getSize() {
        return list.size();
    }

    public void moveFigureForwards(Figure selectedFigure) {
        int i = list.indexOf(selectedFigure);
        if (list.indexOf(selectedFigure)!= list.size()-1){
            Collections.swap(list,i,i+1);
        }
    }
    public void moveFigureForwards(CanvasState selectedFigures) {
        int i = 1;
        List<Figure> aux2 = new ArrayList<>(selectedFigures.list);
        Collections.reverse(aux2);
        for (Figure element : aux2){
            if (aux2.size()!=list.size()){
                if (list.indexOf(element)!=list.size()-1 && list.indexOf(element)+i<=list.size()-1){
                    Collections.swap(list,list.indexOf(element),list.indexOf(element)+1);
                }
                i++;
            }
        }
    }
}
