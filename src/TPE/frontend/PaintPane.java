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

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

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

	// Borde
	private final Slider slider = new Slider(1, 50, 10);
	private final ColorPicker borderColor = new ColorPicker();

	// Relleno
	private final ColorPicker fillColor = new ColorPicker();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton,
				circleButton, squareButton, ellipseButton, lineButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		// Slider de Borde, Colorpicker de Borde y Relleno Default
		// Tambien listeners para los que estan seleccionados
		slider.valueProperty().addListener((observableValue, number, t1) -> {
			if (selectionButton.isSelected() && selectedFigure != null) {
				selectedFigure.setWidth(slider.getValue());
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
			if (selectionButton.isSelected() && selectedFigure != null) {
				selectedFigure.setLineColor(borderColor.getValue());
			}
			redrawCanvas();
		});
		fillColor.setValue(Color.YELLOW);
		fillColor.valueProperty().addListener((observableValue, color, t1) -> {
			if (selectionButton.isSelected() && selectedFigure != null) {
				selectedFigure.setFillColor(fillColor.getValue());
			}
			redrawCanvas();
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
			Figure newFigure;
			if(rectangleButton.isSelected() && validPoints(startPoint, endPoint)) {
				newFigure = new Rectangle(startPoint, endPoint, slider.getValue(), borderColor.getValue(), fillColor.getValue());
			}
			else if(circleButton.isSelected() && validPoints(startPoint, endPoint)) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(startPoint, circleRadius, slider.getValue(),borderColor.getValue(), fillColor.getValue());
			}
			else if (lineButton.isSelected()){
				newFigure = new Line(startPoint,endPoint, slider.getValue(), borderColor.getValue(), fillColor.getValue());
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
			} else {
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
					}
				}
				label.append(selectedFigure);

				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
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
				if(selectedFigure != null && selectedFigure.getClass() == Rectangle.class) {
					Rectangle rectangle = (Rectangle) selectedFigure;
					rectangle.getTopLeft().setX(rectangle.getTopLeft().getX() + diffX);
					rectangle.getBottomRight().setX(rectangle.getBottomRight().getX() + diffX);
					rectangle.getTopLeft().setY(rectangle.getTopLeft().getY() + diffY);
					rectangle.getBottomRight().setY(rectangle.getBottomRight().getY() + diffY);
				}
				else if(selectedFigure != null && selectedFigure.getClass() == Circle.class) {
					Circle circle = (Circle) selectedFigure;
					circle.getCenterPoint().setX(circle.getCenterPoint().getX() + diffX);
					circle.getCenterPoint().setY(circle.getCenterPoint().getY() + diffY);
				}
				else if (selectedFigure != null && selectedFigure.getClass() == Line.class) {
					Line line = (Line) selectedFigure;
					line.getTopLeft().setX(line.getTopLeft().getX() + diffX);
					line.getTopLeft().setY(line.getTopLeft().getY() + diffY);
					line.getBottomRight().setX(line.getBottomRight().getX() + diffX);
					line.getBottomRight().setY(line.getBottomRight().getY() + diffY);
				}
				else if (selectedFigure != null && selectedFigure.getClass() == Square.class) {
					Square square = (Square) selectedFigure;
					square.getTopLeft().setX(square.getTopLeft().getX() + diffX);
					square.getTopLeft().setY(square.getTopLeft().getY() + diffY);
					square.getBottomRight().setX(square.getBottomRight().getX() + diffX);
					square.getBottomRight().setY(square.getBottomRight().getY() + diffY);
				}
				else if (selectedFigure != null && selectedFigure.getClass() == Ellipse.class) {
					Ellipse ellipse = (Ellipse) selectedFigure;
					ellipse.getTopLeft().setX(ellipse.getTopLeft().getX() + diffX);
					ellipse.getTopLeft().setY(ellipse.getTopLeft().getY() + diffY);
					ellipse.getBottomRight().setX(ellipse.getBottomRight().getX() + diffX);
					ellipse.getBottomRight().setY(ellipse.getBottomRight().getY() + diffY);
				}
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private boolean validPoints(Point startPoint, Point endPoint){
		return !(endPoint.getX() < startPoint.getX()) && !(endPoint.getY() < startPoint.getY());
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
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
						Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()));
				gc.strokeRect(square.getTopLeft().getX(),square.getTopLeft().getY(),
						Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()));
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
		} else if(figure.getClass() == Circle.class) {
			Circle circle = (Circle) figure;
			found = Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
					Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
		} else if (figure.getClass() == Line.class) {
			Line line = (Line) figure;
			found = eventPoint.getX() > line.getTopLeft().getX() && eventPoint.getX() < line.getBottomRight().getX() &&
					eventPoint.getY() > line.getTopLeft().getY() && eventPoint.getY() < line.getBottomRight().getY();
		}
		else if (figure.getClass() == Square.class) {
			Square square = (Square) figure;
			found = eventPoint.getX() > square.getTopLeft().getX() && eventPoint.getX() < square.getBottomRight().getX() &&
					eventPoint.getY() > square.getTopLeft().getY() && eventPoint.getY() < square.getBottomRight().getY();
		}
		else if (figure.getClass() == Ellipse.class) {
			Ellipse ellipse = (Ellipse) figure;
			found = eventPoint.getX() > ellipse.getTopLeft().getX() && eventPoint.getX() < ellipse.getBottomRight().getX() &&
					eventPoint.getY() > ellipse.getTopLeft().getY() && eventPoint.getY() < ellipse.getBottomRight().getY();
		}
		return found;
	}

}
