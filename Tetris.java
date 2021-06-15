import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

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
		int maxWidth = 0, minWidth = 0;
		int maxHeight = 0, minHeight = 0;

		for (Point point : shape) {
			if(maxWidth < point.x){
				maxWidth = point.x;
			}
			else if(minWidth > point.x){
				minWidth = point.x;
			}
			else if(maxHeight < point.y){
				maxHeight = point.y;
			}
			else if(minHeight > point.y){
				minHeight = point.y;
			}
		}

		widthLeft = minWidth;
		widthRight = maxWidth;
		
		heightTop = minHeight;
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
		for (Point point : shape) {
			if(point.x != 0 && point.y != 0){
				rotateCorners(point);
			}
			else {
				rotateCross(point, isLeftRotation);
			}
		}
	}

	private void rotateCorners(Point point){
		if(point.x + point.y == 0){
			point.x *= -1;
		}
		else{
			point.y *= -1;
		}
	}

	private void rotateCross(Point point, Boolean isLeftRotation){
		if(point.x == 0){
			point.y = 0;
			point.x = isLeftRotation? -1: 1;
		}
		else {
			point.x = 0;
			point.y = isLeftRotation? -1: 1;
		}
	}

	protected void invertPoint(Point point) {
		int aux = point.x;
		point.x = point.y;
		point.y = aux;
	}

	public int getWidthLeft(){
		return widthLeft;
	}

	public int getWidthRight(){
		return widthRight;
	}

	public int getHeight(){
		return heightTop + heightBottom;
	}

	void posRotate(){

		int auxWidth = widthLeft;
		int auxRight = widthRight;
		widthLeft = 
		widthRight = 

		heightTop = widthLeft;
		widthLeft = heightTop;
		heightBottom  = 0; 
		
	}
}

class StripShape extends Shape {

    StripShape() {
		super();
		Point[] shape = {
			new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)
		};
		setShape(shape);
    }


	@Override
	protected void rotate(Boolean isLeftRotation) {
		int xSum = 0;
		
		for (Point point : shape) {
			xSum += point.x;
		}

		Boolean isXStrip = xSum == 0 || xSum == 4;

		for (Point point : shape) {
			invertPoint(point);

			if(isXStrip){
				if(!isLeftRotation){
					point.y = 0;
				}
			}
			else {
				if(isLeftRotation){
					point.x = 1;
				}
			}
		}
		posRotate();
	}

}


class JShape extends Shape {

    JShape() {
		super();
		Point[] shape = {
			new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)
		};
		setShape(shape);
    }
}

class LShape extends Shape {

    LShape() {
		super();
		Point[] shape = {
			new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)
		};
		setShape(shape);
    }

	
}

class OShape extends Shape {

    OShape() {
		super();
		Point[] shape = {
			new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)
		};
		setShape(shape);
    }
}

class SShape extends Shape {

    SShape() {
		super();
		Point[] shape = {
			new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)
		};
		setShape(shape);
    }
}

class TShape extends Shape {

    TShape() {
		super();
		Point[] shape = {
			new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)
		};
		setShape(shape);
    }
}

class ZShape extends Shape {

    ZShape() {
		super();
		Point[] shape = {
			new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)
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
        
				gameMapLine[lineIndex] = Color.BLACK;
			}
		}
	}

	//Davi (Resolver as bordas)
	private void PrintBorders(){
		for (int colIndex = 0; colIndex < map[0].length -1; colIndex++) {
			map[0][colIndex] = Color.GRAY;
			map[map.length -1][colIndex] = Color.GRAY;
		}

		for (int lineIndex = 0; lineIndex < map.length; lineIndex++) {
			map[lineIndex][map[0].length -1] = Color.GRAY;
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
		map[colIndex][lineIndex] = Color.black;
	}

	public int getHeight(){
		return height;
	}

	public int getWidth(){
		return width - 1;
	}
}


public class Tetris extends JPanel {

	private static final long serialVersionUID = -8715353373678321308L;
	
	private GameMap gameMap = new GameMap(12, 24);
	private Random rand = new Random();

	private Shape fallingShape;
	private Point centerPoint = new Point(5, 2);
	private int score = 0;

	Tetris(){
		newFallingShape();
	}

	public void newFallingShape(){
		
		int randomNumber = rand.nextInt();
		Shape newShape;

		switch (randomNumber) {
			case 0:
				newShape = new StripShape();
				break;

			case 1:
				newShape = new ZShape();
				break;

			case 2:
				newShape = new TShape();
				break;

			case 3:
				newShape = new SShape();
				break;
				
			case 4:
				newShape = new OShape();
				break;

			case 5:
				newShape = new LShape();
				break;

			case 6:
				newShape = new LShape();
				break;
			default:
				newShape = new LShape();
		}

		fallingShape = newShape;
	}

	public void fixToMap() {
		
	}

	public void fallBlock(){
		clearLastShapeRender();
		
		if(centerPoint.y < gameMap.getMap()[0].length){
			centerPoint.y += 1;
		}
	}

	public void move(int command){
		clearLastShapeRender();
		int newXPlace = centerPoint.x + command;

		if(canMoveXAxle(newXPlace)) {
			centerPoint.x += command;
		}
	}

	private Boolean canMoveXAxle(int newXPlace){
		Boolean outOfBoxRight = gameMap.getWidth() > newXPlace + fallingShape.getWidthRight();
		Boolean outOfBoxLeft = 0 < newXPlace - fallingShape.getWidthLeft();
		
		return outOfBoxRight && outOfBoxLeft;
	}

	private void clearLastShapeRender(){
		for (Point point : fallingShape.getShape()) {
			int newX = point.x + centerPoint.x;
			int newY = point.y + centerPoint.y;
	
			gameMap.setMapBlock(newX, newY, Color.black);
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
		fallingShape.rotate(isLeftRotation);
	}

	// switch (numClears) {
	// case 1:
	// 	score += 100;
	// 	break;
	// case 2:
	// 	score += 300;
	// 	break;
	// case 3:
	// 	score += 500;
	// 	break;
	// case 4:
	// 	score += 800;
	// 	break;
	// }
	
	@Override 
	public void paintComponent(Graphics g)
	{
		clearLastShapeRender();
		renderShapes();
		
		System.out.println("Paint");
		
		g.fillRect(0, 0, 26*12, 26*24);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 24; j++) {
				g.setColor(gameMap.getMap()[i][j]);
				g.fillRect(26*i, 26*j, 25, 25);
			}
		}
		// Display the score
		// g.setColor(Color.WHITE);
		// g.drawString("" + score, 19*12, 25);
		
		// Draw the currently falling piece
		// drawPiece(g);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(12*26+20, 26*24+100);
		final Tetris game = new Tetris();
		
		f.add(game);
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
						Thread.sleep(1000);
						game.repaint();
						game.fallBlock();
					} catch ( InterruptedException e ) {
						System.out.println(e.getMessage());
					}
				}
			}
		}.start();
	}
}