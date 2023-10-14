package com.gamedevop;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	static final int screen_Width=600;
	static final int screen_Height=600;
	static final int unitSize=20;
	static final int game_Unit=(screen_Width*screen_Height)/unitSize;
	static final int delay=150;
	final int []x=new int[game_Unit];
	final int []y=new int[game_Unit];
	int body_Parts=6;
	int appleEaten;
	int applex;
	int appley;
	char direction='D';
	boolean running=false;
	Random random;
	Timer timer;
	GamePanel(){
		random= new Random();
		this.setPreferredSize(new Dimension(screen_Width,screen_Height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		gameStart();
	}
	public void gameStart() {
		newApple();
		running=true;
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
//			for(int i=0;i<=screen_Height/unitSize;i++) {
//				g.drawLine(i*unitSize, 0, i*unitSize, screen_Height);
//				g.drawLine(0, i*unitSize,screen_Width,i*unitSize);
//			}
			g.setColor(Color.red);
			g.fillOval(applex, appley, unitSize, unitSize);
			//g.fillRect(applex, appley, unitSize,unitSize);
			for(int i=0;i<body_Parts;i++) {
				if(i==0) {
					g.setColor(Color.ORANGE);
					g.fillOval(x[i], y[i], unitSize, unitSize);
				}
				else {
					g.setColor(Color.green);
					g.fillOval(x[i], y[i], unitSize, unitSize);
				}
			}
			g.setColor(Color.RED);
			g.setFont(new Font("Ink Free",Font.CENTER_BASELINE,30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+appleEaten, (screen_Width-metrics.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
	}
	public void move() {
		for(int i=body_Parts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		switch(direction)
		{
		case 'U':
			y[0]=y[0]-unitSize;
			break;
		case 'D':
			y[0]=y[0]+unitSize;
			break;
		case 'L':
			x[0]=x[0]-unitSize;
			break;
		case 'R':
			x[0]=x[0]+unitSize;
			break;
		}
	}
	public void newApple() {
		applex=random.nextInt((int)(screen_Width/unitSize))*unitSize;
		appley=random.nextInt((int)(screen_Height/unitSize))*unitSize;
		
	}
	public void checkApple() {
		if(x[0]==applex && y[0]==appley) {
			body_Parts++;
			appleEaten++;
			newApple();
		}
		
	}
	
	public void checkCollisions() {
		for(int i=body_Parts;i>0;i--) {
			if(x[0]==x[i] && y[0]==y[i]) running =false;
		}
		if(x[0]<0) running=false;
		if(x[0]>screen_Width) running=false;
		if(y[0]<0) running=false;
		if(y[0]>screen_Height) running=false;
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.ITALIC,80));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+appleEaten, (screen_Width-metrics1.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.ITALIC,80));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Khatam", (screen_Width-metrics2.stringWidth("Game Khatam"))/2, screen_Height/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode())
			{
			  case KeyEvent.VK_LEFT:
			  {
				  if(direction !='R') direction = 'L';
			  }
			  break;
			  case KeyEvent.VK_RIGHT:
			  {
				  if(direction !='L') direction = 'R';
			  }
			  break;
			  case KeyEvent.VK_UP:
			  {
				  if(direction !='D') direction = 'U';
			  }
			  break;
			  case KeyEvent.VK_DOWN:
			  {
				  if(direction !='U') direction = 'D';
			  }
			  break;
			}
			
		}
	}

}
