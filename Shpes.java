import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;

class Shape {
    protected Point[] shape;
	protected Color color;
	private Random rand = new Random();
	private int widthLeft, widthRight;
	private int heightTop, heightBottom;

	public Shape(){
		generateRandomColor();
	}

	private final Color[] availableColors = {
		Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
	};

	private void generateRandomColor(){
		color = availableColors[rand.nextInt(availableColors.length -1)];
	}
	
    protected void setShape(Point[] shape){
		int maxWidth = 0, minWidth = 10;
		int maxHeight = 0, minHeight = 10;

		for (Point point : shape) {
			if(maxWidth < point.x) {
				maxWidth = point.x;
			}
			
			if(minWidth > point.x) {
				minWidth = point.x;
			}
			
			if(maxHeight < point.y) {
				maxHeight = point.y;
			}

			if(minHeight > point.y) {
				minHeight = point.y;
			}
		}

		widthLeft = minWidth;
		widthRight = maxWidth;
		
		heightTop = maxHeight;
		heightBottom = minHeight;

        this.shape = shape;
    }

	public Point[] getShape(){
        return shape;
    }

	public void rotateLeft(){
		rotate(true);
	}

	public void rotateRight(){
		rotate(false);
	}


	protected void rotate(Boolean isLeftRotation) {
		shape = preRotate(shape, isLeftRotation);

		posRotate(isLeftRotation);
	}

	public Point[] preRotate(Point[] mockedShape, Boolean isLeftRotation) {
		
		List<Point> newPointsList = new ArrayList<Point>();
		Point newPoint;

		for (Point point : mockedShape) {

			if(isLeftRotation) {
				newPoint = new Point(point.y, -point.x);
			}
			else {
				newPoint = new Point(-point.y, point.x);
			}

			newPointsList.add(newPoint);
		}
		Point[] newPoints = new Point[newPointsList.size()];

		for (int index = 0; index < newPointsList.size(); index++)
			newPoints[index] = newPointsList.get(index);

		return newPoints;
	}

	protected void invertPoint(Point point) {
		int aux = point.x;
		point.x = point.y;
		point.y = aux;
	}

	public int getWidthLeft(){
		return Math.abs(widthLeft);
	}

	public int getWidthRight(){
		return Math.abs(widthRight);
	}

	public int getHeight(){
		return Math.abs(heightTop) + Math.abs(heightBottom);
	}

	public int getHeightBottom(){
		return Math.abs(heightBottom);
	}

	void posRotate(Boolean isLeftRotation){
		int aux = heightBottom;
		
		if(isLeftRotation){
			heightBottom = widthLeft;
			widthLeft = heightTop;
			heightTop = widthRight;
			widthRight = aux;
		}
		else {
			heightBottom = widthRight;
			widthRight = heightTop;
			heightTop = widthLeft;
			widthLeft = aux;
		}
	}
}

class StripShape extends Shape {

    StripShape() {
		super();
		Point[] shape = {
			new Point(-1, 0), new Point(0, 0), new Point(1, 0), new Point(2, 0)
		};
		setShape(shape);
    }
}

class JShape extends Shape {

    JShape() {
		super();
		Point[] shape = {
			new Point(-1, 0), new Point(0, 0), new Point(1, 0), new Point(1, -1)
		};
		
		setShape(shape);
    }
}

class LShape extends Shape {

    LShape() {
		super();
		Point[] shape = {
			new Point(-1, 0), new Point(0, 0), new Point(1, 0), new Point(1, 1)
		};
		setShape(shape);
    }
}

class OShape extends Shape {

    OShape() {
		super();
		Point[] shape = {
			new Point(-1, -1), new Point(-1, 0), new Point(0, -1), new Point(0, 0)
		};
		setShape(shape);
    }
}

class SShape extends Shape {

    SShape() {
		super();
		Point[] shape = {
			new Point(0, -1), new Point(1, -1), new Point(-1, 0), new Point(0, 0)
		};
		setShape(shape);
    }
}

class TShape extends Shape {

    TShape() {
		super();
		Point[] shape = {
			new Point(0, -1), new Point(-1, 0), new Point(0, 0), new Point(1, 0)
		};
		setShape(shape);
    }
}

class ZShape extends Shape {

    ZShape() {
		super();
		Point[] shape = {
			new Point(-1, -1), new Point(0, -1), new Point(0, 0), new Point(-1, 0)
		};
		setShape(shape);
    }
}
