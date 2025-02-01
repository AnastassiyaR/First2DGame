package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable{
	
	// Screen settings
	final int originalTileSize = 16; // 16x16 tile, like character, water and etc
	final int scale = 3; // so the character is not too small 16x3=48
	

	public final int tileSize = originalTileSize * scale; 	// we do public so it is available for other packages too
	
	final int maxScreenCol = 16; // in total 16 48x48 tiles in col
	final int maxScreenRow = 12; // in total 16 48x48 tiles in row
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// FPS
	int FPS = 60;
			
	

	KeyHandler keyH = new KeyHandler();
	Thread gameThread; // keep the game working until the stop
	Player player = new Player(this,keyH);
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of the class JPanel
		this.setBackground(new Color(173, 216, 230));
		this.setDoubleBuffered(true); // so that the screen refreshes smoothly, without artifacts and flickering
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel can be focused to receive the key input
	}
	
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	
 	@Override
//	public void run() {
//		// running tasks in a background thread
// 		
// 		double drawInterval = 1000000000/FPS; // 0.01666 seconds
// 		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		while(gameThread != null) {
//			
//			// The time between update and repaint
//			// long currentTime = System.nanoTime();
//			// System.out.println("Time is"+currentTime);
//			
//			update();
//			
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//				// System.out.println("GAME LOOP"); for checking
//			
//			} catch (InterruptedException e) {
//			    e.printStackTrace();
//			}
//		}
//				
//	}
 	
 	public void run() {
 		
 		double drawInterval = 1000000000/FPS;
 		double delta = 0;
 		long lastTime = System.nanoTime();
 		long currentTime;
 		long timer = 0;
 		int drawCount = 0;
 		
 		while(gameThread != null) {
 			currentTime = System.nanoTime();
 			
 			delta += (currentTime - lastTime) / drawInterval;
 			timer += (currentTime - lastTime);
 			lastTime = currentTime;
 			
 			if(delta >= 1) {
 				update();
 				repaint();
 				delta--;
 				drawCount++;
 			}
 			
 			if(timer >= 1000000000) {
 				// System.out.println("FPS:"+drawCount); to check FPS
 				drawCount = 0;
 				timer = 0;
 			}
 			
 		}
 	}
 	
 	public void update() {
 		
 		player.update();
 	}
 	
 	
 	public void paintComponent(Graphics g) {
 		// Graphics has many functions to draw objects on the screen
 		
 		super.paintComponent(g);
 		Graphics2D g2 = (Graphics2D)g; // for 2D game
 		
 		
 		player.draw(g2);
 		
 		g2.dispose();
 	}
 
}

