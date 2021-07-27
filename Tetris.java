import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;




public class Tetris extends JPanel {

	private static final long serialVersionUID = -8715353373678321308L;

	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(900, 900);
		
		
		final Screen game = new Screen();
		final Screen game2 = new Screen();

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
                        game.setScore(game.getScore() + 1);
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
						game2.fallBlock();
						game2.repaint();
						game.repaint();
					} catch ( InterruptedException e ) {
						System.out.println(e.getMessage());
					}
				}
			}
		}.start();
	}
}