import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;



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


class GameMap {
	private Color[][] map;
	private int width;
	private int height;

	public GameMap(int width, int height) {
		map = new Color[width][height];

		this.width = width;
		this.height = height;
		SetBackgroundColor();
		PrintBorders();
	}

	private void SetBackgroundColor(){
    
		for (int columnIndex = 0; columnIndex < map.length -0; columnIndex++) {
			Color[] gameMapLine = map[columnIndex];

			for (int lineIndex = 0; lineIndex < gameMapLine.length -0; lineIndex++) {
        
				gameMapLine[lineIndex] = getMapBackgroundColor();
			}
		}
	}

	private void PrintBorders(){
		for (int colIndex = 0; colIndex < map[0].length -1; colIndex++) {
			map[0][colIndex] = Color.GRAY;
			map[map.length -1][colIndex] = Color.GRAY;
		}

		for (int lineIndex = 0; lineIndex < map.length; lineIndex++) {
			map[lineIndex][map[0].length -1] = Color.GRAY;
		}
	}

	public void clearRow(int rowIndex){
		int mapOffset = 1;

		for(int colIndex = mapOffset; colIndex < getWidth(); colIndex++){
			setMapBlock(colIndex, rowIndex, getMapBackgroundColor());
		}
	}

	public Boolean checkIfRowIsFull(int rowIndex){
		Boolean isRowFull = false;

		for(int colIndex = 0; colIndex < getWidth(); colIndex++){
			isRowFull = getMapBlock(colIndex, rowIndex) != getMapBackgroundColor();
	
			if(isRowFull == false) {
				break;
			}
		}

		return isRowFull;
	}

	public int getMapOffSet(){
		return 1;
	}

	public void applyGravity(int rowIndex){
		for(int rowIndex2 = rowIndex; rowIndex2 > 0; rowIndex2--) {
			for(int colIndex = getMapOffSet(); colIndex < getWidth(); colIndex++){
				Color topColor = getMapBlock(colIndex, rowIndex2-1);
				Color bottomColor = getMapBlock(colIndex, rowIndex2);

				if(bottomColor == getMapBackgroundColor()) {
					setMapBlock(colIndex, rowIndex2, topColor);
					setMapBlock(colIndex, rowIndex2-1, getMapBackgroundColor());
				}
			}
		}
	}


	public Color[][] getMap(){
		return map;
	}

	public Color getMapBlock(int colIndex, int lineIndex){
		return map[colIndex][lineIndex];
	}

	public void setMapBlock(int colIndex, int lineIndex, Color color){
		map[colIndex][lineIndex] = color;
	}

	public void clearMapBlock(int colIndex, int lineIndex){
		map[colIndex][lineIndex] = getMapBackgroundColor();
	}

	public int getHeight(){
		return height;
	}

	public int getWidth(){
		return width - 1;
	}

	public Color getMapBackgroundColor(){
		return Color.BLACK;
	}
}


public class Tetris extends JPanel {

	private static final long serialVersionUID = -8715353373678321308L;
	
	private GameMap gameMap = new GameMap(12, 24);
	private Random rand = new Random();

	private Shape fallingShape;
	private Point centerPoint = new Point(5, 2);
	private int score = 100;

	Tetris(){
		newFallingShape();
	}

	public void newFallingShape(){
		
		int randomNumber = rand.nextInt(6);
		Shape newShape;
		centerPoint = new Point(5, 2);
		System.out.println(randomNumber);
		switch (randomNumber) {
			case 0:
				newShape = new StripShape();
				break;

			case 1:
				newShape = new JShape();
				break;

			case 2:
				newShape = new LShape();
				break;

			case 3:
				newShape = new OShape();
				break;
				
			case 4:
				newShape = new SShape();
				break;

			case 5:
				newShape = new TShape();
				break;

			case 6:
				newShape = new ZShape();
				break;
			default:
				newShape = new LShape();
		}

		
		fallingShape = newShape;

		if(willCollide(0, 1)) {
			System.out.printf("Finish him");
		}
	}

	public void fallBlock(){

		int fallMovementValue = 1;

		if(willCollide(0, fallMovementValue)) {
			fixToMap();
		}
		else {
			centerPoint.y += fallMovementValue;
		}
	}

	
	public void move(int command){
		
		if(!willCollide(command, 0)) {
			centerPoint.x += command;
		}
	}
	
	private Boolean willCollide(Point[] shape) {
		return willCollide(shape, 0, 0);
	}

	private Boolean willCollide(int xModifier, int yModifier) {
		return willCollide(fallingShape.getShape(), xModifier, yModifier);
	}

	private Boolean willCollide(Point[] shape, int xModifier, int yModifier){
		clearLastShapeRender();

		for (Point point : shape) {
			int x = point.x + centerPoint.x + xModifier;
			int y = point.y + centerPoint.y + yModifier;

			Color gameColor = gameMap.getMapBlock(x, y);

			Boolean collide = gameColor != gameMap.getMapBackgroundColor();

			if(collide) {
				return true;
			}
		}
		
		return false;
	}

	private void fixToMap() {
		
		for (Point point : fallingShape.getShape()) {
			gameMap.setMapBlock(point.x + centerPoint.x, point.y + centerPoint.y, fallingShape.color);
		}

		isFull();
		newFallingShape();
	}
	
	private void clearLastShapeRender(){
		for (Point point : fallingShape.getShape()) {
			int newX = point.x + centerPoint.x;
			int newY = point.y + centerPoint.y;
	
			gameMap.setMapBlock(newX, newY, gameMap.getMapBackgroundColor());
		}
	}

	public void renderShapes(){
		for (Point point : fallingShape.getShape()) {
			int newX = point.x + centerPoint.x;
			int newY = point.y + centerPoint.y;

			gameMap.setMapBlock(newX, newY, fallingShape.color);
		}
	}

	public void rotate(Boolean isLeftRotation) {
		clearLastShapeRender();
		
		Point[] newPosition = fallingShape.preRotate(fallingShape.getShape(), isLeftRotation);
		
		if(!willCollide(newPosition)) {
			fallingShape.rotate(isLeftRotation);
		}
	}

	public void isFull(){
		int rowStrick = 0;
		int mapOffset = 1;

		for(int rowIndex = mapOffset; rowIndex < gameMap.getHeight() -mapOffset; rowIndex++) {
			Boolean isRowFull = gameMap.checkIfRowIsFull(rowIndex);
			
			
			if(isRowFull){
				rowStrick++;
				gameMap.clearRow(rowIndex);
				gameMap.applyGravity(rowIndex);
			}
		}

		calculatePoints(rowStrick);
	}

	public void calculatePoints(int rowStrick){

		switch (rowStrick) {
			case 1:
				score += 100;
				break;
			case 2:
				score += 300;
				break;
			case 3:
				score += 500;
				break;
			case 4:
				score += 800;
				break;
		}
	}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		clearLastShapeRender();
		renderShapes();
		
		g.fillRect(0, 0, 26*12, 26*24);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 24; j++) {
				g.setColor(gameMap.getMap()[i][j]);
				g.fillRect(26*i, 26*j, 25, 25);
			}
		}
		
		// Display the score
		g.setColor(Color.WHITE);
		g.drawString("" + score, 19*12, 25);
		
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(900, 900);
		
		
		final Tetris game = new Tetris();
		final Tetris game2 = new Tetris();

		GridLayout gridLayout = new GridLayout(0, 2);
        f.setLayout(gridLayout);
        gridLayout.layoutContainer(f);

		f.add(game);
		f.add(game2);
		f.setVisible(true);
		
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
				
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						game.rotate(true);
						break;
					case KeyEvent.VK_DOWN:
						//game.rotate(true);
						game.fallBlock();
						break;
					case KeyEvent.VK_LEFT:
						game.move(-1);
						break;
					case KeyEvent.VK_RIGHT:
						game.move(+1);
						break;
					case KeyEvent.VK_SPACE:
						//game.dropDown();
						game.score += 1;
						break;
				} 

				game.repaint();
			}
			
			public void keyReleased(KeyEvent e) {
			}
		});
		
		// Make the falling piece drop every second
		new Thread() {
			@Override public void run() {
				while (true) {
					
					try {
						Thread.sleep(600);

						game.fallBlock();
						game.repaint();
					} catch ( InterruptedException e ) {
						System.out.println(e.getMessage());
					}
				}
			}
		}.start();
	}
}