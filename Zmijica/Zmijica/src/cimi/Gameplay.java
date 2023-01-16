package cimi;
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean play = true;
	
	private int lengthofsnake = 3;
	private int moves = 0;
	private int score = 0;
	private int highScore = 0;
	
	private ImageIcon leftmouth;
	private ImageIcon rightmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	
	private Timer timer;
	private  int delay = 100;
	
	Zmijica zmijica = new Zmijica(); 
	Loptica loptica = new Loptica();
	
	private Random random = new Random();
	
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);
	
	private ImageIcon snakeImage;
	private ImageIcon titleImage;
	private ImageIcon enemyImage;
	
	public static void main(String[] args) {
		JFrame obj = new JFrame();
		Gameplay gameplay = new Gameplay();
		obj.setBounds(10, 10, 910, 700);
		obj.setBackground(Color.DARK_GRAY);
		obj.setVisible(true);
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);

	}

	//Osluskuje
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paint(Graphics g) {
		
		if(moves == 0) {
		    zmijica.getSnakexlength()[2] = 50;
			zmijica.getSnakexlength()[1] = 75;
			zmijica.getSnakexlength()[0] = 100;
			
			zmijica.getSnakeylength()[2] = 100;
			zmijica.getSnakeylength()[1] = 100;
			zmijica.getSnakeylength()[0] = 100;
		}
		
		//draw title image borders
		g.setColor(Color.white);
		g.fillRect(24, 10, 852, 56);
		
		//draw the title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//draw border for gameplay
		g.setColor(Color.white);
		g.drawRect(24, 74, 852, 577);
		
		//draw background for gameplay
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		//draw scores
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Score: "+score, 750, 30);
		g.drawString("High score:"+highScore, 50, 30);
		
		//draw length of snake
			
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));		
		g.drawString("Length of snake: "+lengthofsnake, 750, 50);
		
		//Glava zmijice
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, zmijica.getSnakexlength()[0], zmijica.getSnakeylength()[0]);
		
		for(int a = 0; a < lengthofsnake; a++) {
			if(a == 0 && right) {
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g, zmijica.getSnakexlength()[0], zmijica.getSnakeylength()[0]);
			}
			if(a == 0 && left) {
				leftmouth = new ImageIcon("lefttmouth.png");
				leftmouth.paintIcon(this, g, zmijica.getSnakexlength()[0], zmijica.getSnakeylength()[0]);
			}
			if(a == 0 && down) {
				downmouth = new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, g, zmijica.getSnakexlength()[0], zmijica.getSnakeylength()[0]);
			}
			if(a == 0 && up) {
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g, zmijica.getSnakexlength()[0], zmijica.getSnakeylength()[0]);
			}
			//Telo zmijice
			/*if(a != 0) {
				snakeImage = new ImageIcon("snakeimage.png");
				snakeImage.paintIcon(this, g, zmijica.getSnakexlength()[a], zmijica.getSnakeylength()[a]);
			}*/
		}
		//Loptica
		enemyImage = new ImageIcon("enemy.png");
		
		if(loptica.getEnemyxpos()[xpos] == zmijica.getSnakexlength()[0] && loptica.getEnemyypos()[ypos] == zmijica.getSnakeylength()[0]) {
			score++;
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
		}
		//Vidljivost loptice	
		enemyImage.paintIcon(this, g, loptica.getEnemyxpos()[xpos], loptica.getEnemyypos()[ypos]);
		//Game over
		for(int i = 1; i < lengthofsnake; i++) {
			if(zmijica.getSnakexlength()[i] == zmijica.getSnakexlength()[0] && zmijica.getSnakeylength()[i] == zmijica.getSnakeylength()[0]) {
				
				right = false;
				left = false;
				up = false;
				down = false;
				play = false;
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.PLAIN, 50));
				g.drawString("GAME OVER", 300, 300);
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.PLAIN, 20));
				g.drawString("space to RESTART", 350, 340);
			}
		}
		//High score
		if(score > highScore)
			highScore = score;
		
		g.dispose();
		
	}
	//Ocrtavanje zmijice
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(right && play) 
		{
			for(int i = lengthofsnake-1; i >= 0; i--) {
				zmijica.getSnakeylength()[i+1] = zmijica.getSnakeylength()[i];
			}
			for (int i = lengthofsnake; i >= 0; i--) {
				if(i == 0) {
					zmijica.getSnakexlength()[i] = zmijica.getSnakexlength()[i] + 25;
				} else {
					zmijica.getSnakexlength()[i] = zmijica.getSnakexlength()[i-1];
				}
				if(zmijica.getSnakexlength()[i] > 850) {
					zmijica.getSnakexlength()[i] = 25;
				}
			}
			repaint();
		}
		if(left && play) 
		{
			for(int i = lengthofsnake-1; i >= 0; i--) {
				zmijica.getSnakeylength()[i+1] = zmijica.getSnakeylength()[i];
			}
			for (int i = lengthofsnake; i >= 0; i--) {
				if(i == 0) {
					zmijica.getSnakexlength()[i] = zmijica.getSnakexlength()[i] - 25;
				} else {
					zmijica.getSnakexlength()[i] = zmijica.getSnakexlength()[i-1];
				}
				if(zmijica.getSnakexlength()[i] < 25) {
					zmijica.getSnakexlength()[i] = 850;
				}
			}
			repaint();
		}
		if(up && play) 
		{
			for(int i = lengthofsnake-1; i >= 0; i--) {
				zmijica.getSnakexlength()[i+1] = zmijica.getSnakexlength()[i];
			}
			for (int i = lengthofsnake; i >= 0; i--) {
				if(i == 0) {
					zmijica.getSnakeylength()[i] = zmijica.getSnakeylength()[i] - 25;
				} else {
					zmijica.getSnakeylength()[i] = zmijica.getSnakeylength()[i-1];
				}
				if(zmijica.getSnakeylength()[i] < 75) {
					zmijica.getSnakeylength()[i] = 625;
				}
			}
			repaint();
		}
		if(down && play) 
		{
			for(int i = lengthofsnake-1; i >= 0; i--) {
				zmijica.getSnakexlength()[i+1] = zmijica.getSnakexlength()[i];
			}
			for (int i = lengthofsnake; i >= 0; i--) {
				if(i == 0) {
					zmijica.getSnakeylength()[i] = zmijica.getSnakeylength()[i] + 25;
				} else {
					zmijica.getSnakeylength()[i] = zmijica.getSnakeylength()[i-1];
				}
				if(zmijica.getSnakeylength()[i] > 625) {
					zmijica.getSnakeylength()[i] = 75;
				}
			}
			repaint();
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			play = true;
			lengthofsnake = 3;
			moves = 0;
			score = 0;
			repaint();
		}
		if( e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moves++;
			right = true;
			if(!left) {
				right = true;
			}
			else {
				left = true;
				right = false;
			}
			up = false;
			down = false;
		}
		if( e.getKeyCode() == KeyEvent.VK_LEFT) {
			moves++;
			left = true;
			if(!right) {
				left = true;
			}
			else {
				right = true;
				left = false;
			}
			up = false;
			down = false;
		}
		if( e.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			up = true;
			if(!down) {
				up = true;
			}
			else {
				down = true;
				up = false;
			}
			left = false;
			right = false;
		}
		if( e.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			down = true;
			if(!up) {
				down = true;
			}
			else {
				up = true;
				down = false;
			}
			left = false;
			right = false;
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

