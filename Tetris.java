import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


class Shape {
    protected Point[] shape;
	protected Color color;
	private Random rand = new Random();

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

	public GameMap(int width, int height) {
		map = new Color[width][height];
		
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
}


public class Tetris extends JPanel {

	private static final long serialVersionUID = -8715353373678321308L;
	
	private Shape fallingShape;
	private GameMap gameMap = new GameMap(12, 24);
	private Random rand = new Random();
	private Point centerPoint = new Point(5, 2); //Centro dos blocos
	private int score = 0 ;

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

	public void move(int side){

	}

	public void rotate(Boolean isLeftRotation) {

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
	
	
	// Draw the falling piece
	/*private void drawPiece(Graphics g) {		
		g.setColor(tetraminoColors[currentPiece]);
		for (Point p : Tetraminos[currentPiece].getShape()) {
			g.fillRect((p.x + pieceOrigin.x) * 26, 
					   (p.y + pieceOrigin.y) * 26, 
					   25, 25);
		}
	}*/
	
	@Override 
	public void paintComponent(Graphics g)
	{
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
		f.setSize(12*26+20, 26*23+100);
		final Tetris game = new Tetris();
		//game.init();
		
		f.add(game);
		f.setVisible(true);
		// Keyboard controls
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					game.rotate(true);
					break;
				case KeyEvent.VK_DOWN:
					game.rotate(false);
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
						//game.dropDown();
					} catch ( InterruptedException e ) {
						System.out.println(e.getMessage());


					}
				}
			}
		}.start();
	}
}