package TPE.frontend;

import TPE.backend.CanvasState;
import TPE.backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;
	// Listas para rehacer y deshacer
	private final List<List<Figure>> undoCanvas = new ArrayList<>();
	private final List<List<Figure>> redoCanvas = new ArrayList<>();

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");
	private final ToggleButton lineButton = new ToggleButton("Línea");

	// Dibujar una figura
	private Point startPoint, endPoint, eventPoint;

	// Seleccionar una figura
	private Figure selectedFigure;
	private final CanvasState selectedFigures = new CanvasState();

	// Borde
	private final Slider slider = new Slider(1, 50, 10);
	private final ColorPicker borderColor = new ColorPicker();

	// Relleno
	private final ColorPicker fillColor = new ColorPicker();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		ToggleButton deleteButton = new ToggleButton("Borrar");
		ToggleButton backButton = new ToggleButton("Al Fondo");
		ToggleButton frontButton = new ToggleButton("Al Frente");
		ToggleButton undoButton = new ToggleButton("Deshacer");
		ToggleButton redoButton = new ToggleButton("Rehacer");
		ToggleButton[] toolsArr = {selectionButton, rectangleButton,
				circleButton, squareButton, ellipseButton, lineButton,
				deleteButton, backButton, frontButton, undoButton, redoButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(95);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		// Slider de Borde, Colorpicker de Borde y Relleno Default
		// Tambien listeners para los que estan seleccionados
		slider.valueProperty().addListener((observableValue, number, t1) -> {
			if (selectionButton.isSelected()) {
				if(selectedFigure != null){
					selectedFigure.setWidth(slider.getValue());
				}
				if (selectedFigures.notEmpty()){
					for (Figure figure : selectedFigures.figures()){
						figure.setWidth(slider.getValue());
					}
				}
			}
			redrawCanvas();
		});
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(25);
		slider.setBlockIncrement(1);
		slider.adjustValue(1);
		borderColor.setValue(Color.BLACK);
		borderColor.valueProperty().addListener((observableValue, color, t1) -> {
			if (selectionButton.isSelected()) {
				if(selectedFigure != null){
					undoCanvas.add(canvasState.copyState());
					redoCanvas.removeAll(redoCanvas);
					selectedFigure.setLineColor(borderColor.getValue());
				}
				if (selectedFigures.notEmpty()){
					undoCanvas.add(canvasState.copyState());
					redoCanvas.removeAll(redoCanvas);
					for (Figure figure : selectedFigures.figures()){
						figure.setLineColor(borderColor.getValue());
					}
				}
			}
			redrawCanvas();
		});
		fillColor.setValue(Color.YELLOW);
		fillColor.valueProperty().addListener((observableValue, color, t1) -> {
			if (selectionButton.isSelected()) {
				if(selectedFigure != null){
					undoCanvas.add(canvasState.copyState());
					redoCanvas.removeAll(redoCanvas);
					selectedFigure.setFillColor(fillColor.getValue());
				}
				if (selectedFigures.notEmpty()){
					undoCanvas.add(canvasState.copyState());
					redoCanvas.removeAll(redoCanvas);
					for (Figure figure : selectedFigures.figures()){
						figure.setFillColor(fillColor.getValue());
					}
				}
			}
			redrawCanvas();
		});

		// Borrar
		deleteButton.selectedProperty().addListener(observable -> {
			if (selectedFigure != null){
				undoCanvas.add(canvasState.copyState());
				redoCanvas.removeAll(redoCanvas);
				canvasState.removeFigure(selectedFigure);
				redrawCanvas();
			}
			if (selectedFigures.notEmpty()){
				undoCanvas.add(canvasState.copyState());
				redoCanvas.removeAll(redoCanvas);
				for (Figure figure : selectedFigures.figures()){
					canvasState.removeFigure(figure);
				}
				redrawCanvas();
				selectedFigures.removeAll();
			}
		});

		// Llevar al fondo y al frente
		backButton.setOnAction(actionEvent -> {
			if (selectedFigure != null){
				undoCanvas.add(canvasState.copyState());
				redoCanvas.removeAll(redoCanvas);
				canvasState.moveFigureBackwards(selectedFigure);
				redrawCanvas();
			}
			if (selectedFigures.notEmpty()){
				undoCanvas.add(canvasState.copyState());
				redoCanvas.removeAll(redoCanvas);
				canvasState.moveFigureBackwards(selectedFigures);
				redrawCanvas();
			}
		});
		frontButton.setOnAction(actionEvent -> {
			if (selectedFigure != null){
				undoCanvas.add(canvasState.copyState());
				redoCanvas.removeAll(redoCanvas);
				canvasState.moveFigureForwards(selectedFigure);
				redrawCanvas();
			}
			if (selectedFigures.notEmpty()){
				undoCanvas.add(canvasState.copyState());
				redoCanvas.removeAll(redoCanvas);
				canvasState.moveFigureForwards(selectedFigures);
				redrawCanvas();
			}
		});

		// Deshacer y rehacer
		undoButton.setOnAction(actionEvent -> {
			if (!undoCanvas.isEmpty()){
				redoCanvas.add(canvasState.copyState());
				canvasState.setState(undoCanvas.remove(undoCanvas.size()-1));
				redrawCanvas();
			}
		});
		redoButton.setOnAction(actionEvent -> {
			if (!redoCanvas.isEmpty()){
				undoCanvas.add(canvasState.copyState());
				canvasState.setState(redoCanvas.remove(redoCanvas.size()-1));
				redrawCanvas();
			}
		});


		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		Label sliderLabel = new Label("Borde");
		buttonsBox.getChildren().add(sliderLabel);
		buttonsBox.getChildren().add(slider);
		buttonsBox.getChildren().add(borderColor);
		Label fillLabel = new Label("Relleno");
		buttonsBox.getChildren().add(fillLabel);
		buttonsBox.getChildren().add(fillColor);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);
		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));
		canvas.setOnMouseReleased(event -> {
			endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return;
			}
			if (selectionButton.isSelected() && validPoints(startPoint, endPoint)){
				if (selectedFigures.notEmpty()){
					selectedFigures.removeAll();
					redrawCanvas();
				}
				for (Figure figure : canvasState.figures()){
					if (isWithin(figure)){
						selectedFigures.addFigure(figure);
					}
				}
			}
			else {
				selectedFigures.removeAll();
				redrawCanvas();
			}
			Figure newFigure;
			if(rectangleButton.isSelected() && validPoints(startPoint, endPoint)) {
				newFigure = new Rectangle(startPoint, endPoint, slider.getValue(), borderColor.getValue(), fillColor.getValue());
			}
			else if(circleButton.isSelected() && validPoints(startPoint, endPoint)) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(startPoint, circleRadius, slider.getValue(),borderColor.getValue(), fillColor.getValue());
			}
			else if(lineButton.isSelected()){
				if(validPoints(startPoint, endPoint)) {
					newFigure = new Line(startPoint,endPoint, slider.getValue(), borderColor.getValue(), fillColor.getValue());
				}
				else {
					newFigure = new Line(endPoint,startPoint, slider.getValue(), borderColor.getValue(), fillColor.getValue());
				}
			}
			else if(squareButton.isSelected() && validPoints(startPoint, endPoint)) {
				newFigure = new Square(startPoint, endPoint, slider.getValue(),borderColor.getValue(), fillColor.getValue());
			}
			else if(ellipseButton.isSelected() && validPoints(startPoint, endPoint)) {
				newFigure = new Ellipse(startPoint, endPoint, slider.getValue(),borderColor.getValue(), fillColor.getValue());
			}
			else {
				return;
			}
			undoCanvas.add(canvasState.copyState());
			redoCanvas.removeAll(redoCanvas);
			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});
		canvas.setOnMouseMoved(event -> {
			eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			Figure aux = null;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					aux = figure;
				}
			}
			label.append(aux);
			if(found) {
				statusPane.updateStatus(label.toString());
			}
			else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						selectedFigures.removeAll();
					}
				}
				label.append(selectedFigure);
				if (found) {
					statusPane.updateStatus(label.toString());
				}
				else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});
		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if (selectedFigure != null){
					selectedFigure.moveFigure(diffX, diffY);
				}
				if (selectedFigures.notEmpty()){
					for (Figure figure : selectedFigures.figures() ){
						figure.moveFigure(diffX, diffY);
					}
				}
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private boolean isWithin(Figure figure) {
		boolean within = false;
		if (figure.getClass() == Rectangle.class){
			Rectangle rectangle = (Rectangle) figure;
			within = startPoint.getX() < rectangle.getTopLeft().getX() && endPoint.getX() > rectangle.getBottomRight().getX() &&
					startPoint.getY() < rectangle.getTopLeft().getY() && endPoint.getY() > rectangle.getBottomRight().getY();
		}
		else if(figure.getClass() == Circle.class) {
			Circle circle = (Circle) figure;
			within = startPoint.getX() < circle.getCenterPoint().getX() - circle.getRadius()
					&& startPoint.getY() < circle.getCenterPoint().getY() - circle.getRadius()
					&& endPoint.getX() > circle.getCenterPoint().getX() + circle.getRadius()
					&& endPoint.getY() > circle.getCenterPoint().getY() + circle.getRadius();
		}
		else if (figure.getClass() == Ellipse.class){
			Ellipse ellipse = (Ellipse) figure;
			within = startPoint.getX() < ellipse.getTopLeft().getX() && endPoint.getX() > ellipse.getBottomRight().getX() &&
					startPoint.getY() < ellipse.getTopLeft().getY() && endPoint.getY() > ellipse.getBottomRight().getY();
		}
		else if (figure.getClass() == Square.class){
			Square square = (Square) figure;
			within = startPoint.getX() < square.getTopLeft().getX() && endPoint.getX() > square.getTopLeft().getX() + square.getSideLength() &&
					startPoint.getY() < square.getTopLeft().getY() && endPoint.getY() > square.getTopLeft().getY() + square.getSideLength();
		}
		else if (figure.getClass() == Line.class){
			Line line = (Line) figure;
			within = startPoint.getX() < line.getTopLeft().getX() && endPoint.getX() > line.getBottomRight().getX() &&
					startPoint.getY() < line.getTopLeft().getY() && endPoint.getY() > line.getBottomRight().getY();
		}
		return within;
	}

	private static boolean validPoints(Point startPoint, Point endPoint){
		return !(endPoint.getX() < startPoint.getX()) && !(endPoint.getY() < startPoint.getY());
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure || selectedFigures.containsFigure(figure)) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(figure.getLineColor());
			}
			gc.setFill(figure.getFillColor());
			gc.setLineWidth(figure.getWidth());
			if(figure.getClass() == Rectangle.class) {
				Rectangle rectangle = (Rectangle) figure;
				gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
				gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
			}
			else if(figure.getClass() == Circle.class) {
				Circle circle = (Circle) figure;
				double diameter = circle.getRadius() * 2;
				gc.fillOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
				gc.strokeOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
			}
			else if (figure.getClass() == Line.class) {
				Line line = (Line) figure;
				gc.strokeLine(line.getBottomRight().getX(),line.getBottomRight().getY(),line.getTopLeft().getX(),line.getTopLeft().getY());
			}
			else if (figure.getClass() == Square.class) {
				Square square = (Square) figure;
				gc.fillRect(square.getTopLeft().getX(),square.getTopLeft().getY(),
						square.getSideLength(), square.getSideLength());
				gc.strokeRect(square.getTopLeft().getX(),square.getTopLeft().getY(),
						square.getSideLength(), square.getSideLength());
			}
			else if (figure.getClass() == Ellipse.class) {
				Ellipse ellipse = (Ellipse) figure;
				gc.fillOval(ellipse.getTopLeft().getX(),ellipse.getTopLeft().getY(),(ellipse.getBottomRight().getX()-ellipse.getTopLeft().getX()),(ellipse.getBottomRight().getY()-ellipse.getTopLeft().getY()));
				gc.strokeOval(ellipse.getTopLeft().getX(),ellipse.getTopLeft().getY(),(ellipse.getBottomRight().getX()-ellipse.getTopLeft().getX()),(ellipse.getBottomRight().getY()-ellipse.getTopLeft().getY()));
			}
		}
	}

	private boolean figureBelongs(Figure figure, Point eventPoint) {
		boolean found = false;
		if(figure.getClass() == Rectangle.class) {
			Rectangle rectangle = (Rectangle) figure;
			found = eventPoint.getX() > rectangle.getTopLeft().getX() && eventPoint.getX() < rectangle.getBottomRight().getX() &&
					eventPoint.getY() > rectangle.getTopLeft().getY() && eventPoint.getY() < rectangle.getBottomRight().getY();
		}
		else if(figure.getClass() == Circle.class) {
			Circle circle = (Circle) figure;
			found = Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
					Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
		}
		else if (figure.getClass() == Line.class) {
			Line line = (Line) figure;
			found = (Line.distance(line.getTopLeft(),eventPoint)+Line.distance(line.getBottomRight(),eventPoint)) < 1+line.getDistance()
					&& (Line.distance(line.getTopLeft(),eventPoint)+Line.distance(line.getBottomRight(),eventPoint)) > -1+line.getDistance();
		}
		else if (figure.getClass() == Square.class) {
			Square square = (Square) figure;
			found = eventPoint.getX() > square.getTopLeft().getX() && eventPoint.getX() < square.getTopLeft().getX() + square.getSideLength() &&
					eventPoint.getY() > square.getTopLeft().getY() && eventPoint.getY() < square.getTopLeft().getY() + square.getSideLength();
		}
		else if (figure.getClass() == Ellipse.class) {
			Ellipse ellipse = (Ellipse) figure;
			found = ((Math.pow((eventPoint.getX()-ellipse.getCenter().getX()),2))/Math.pow(ellipse.getSmajor(),2) +
					(Math.pow((eventPoint.getY()-ellipse.getCenter().getY()),2))/Math.pow(ellipse.getSminor(),2))<= 1;
		}
		return found;
	}

}
