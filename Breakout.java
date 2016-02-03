/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	private static final int NBricks=100;
	
	private GRect paddle;
	
	private double vX;
	private double vY=+3.0;	
	
	private RandomGenerator rgen= new RandomGenerator();
	
	private double DELAY=6;
	
	private GOval Ball;
	
	private GLabel LabelTurnsRemaining;
	
	private GLabel LabelBricksRemaining;
	
	private GObject collider;
	
	private int NTurnsLeft=NTURNS;
	
	private int NBricksLeft=NBricks;
	
	private GLabel Label_Win;
	
	private GLabel Label_Lose;
	
	
	

	public void run() {
		/* You fill this in, along with any subsidiary methods */
		Setup();
		CreatePaddle();
		addMouseListeners();
		BouncingBall();				
	}
	
	private void Setup(){
		
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=0;j<NBRICKS_PER_ROW;j++){
				GRect brick=new GRect((j*(BRICK_WIDTH+BRICK_SEP)),(BRICK_Y_OFFSET+i*(BRICK_HEIGHT+BRICK_SEP)),BRICK_WIDTH,BRICK_HEIGHT);
				brick.setFilled(true);
				switch(i){
				case 0: brick.setFillColor(Color.red );
						break;
				case 1: brick.setFillColor(Color.red );
						break;	
				case 2: brick.setFillColor(Color.orange );
						break;	
				case 3: brick.setFillColor(Color.orange );
						break;
				case 4: brick.setFillColor(Color.yellow);
						break;
				case 5: brick.setFillColor(Color.yellow);
						break;
				case 6: brick.setFillColor(Color.green);
						break;		
				case 7: brick.setFillColor(Color.green);
						break;	
				case 8: brick.setFillColor(Color.cyan);
						break;		
				case 9: brick.setFillColor(Color.cyan);
						break;		
				default:	
				}
				add(brick);
			}
		}
		
	}
	
	private void CreatePaddle(){
		paddle=new GRect((getWidth()-PADDLE_WIDTH)/2,(getHeight()-PADDLE_Y_OFFSET-PADDLE_HEIGHT),PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setFillColor(Color.black );
		add(paddle);		
	}
	
	 public void mouseMoved(MouseEvent e){
		 if(e.getX()>PADDLE_WIDTH/2 && (e.getX()+PADDLE_WIDTH/2)<getWidth()){
			 paddle.setLocation((e.getX()-PADDLE_WIDTH/2),(getHeight()-PADDLE_Y_OFFSET-PADDLE_HEIGHT));
		 }
	
	 }
	 
	 private void BouncingBall(){
		   createBall();
		   vX=rgen.nextDouble(1.0,3.0);
			 if(rgen.nextBoolean(0.5)) vX=-vX;
		   labelsSetting();
		   while(NTurnsLeft>0){
			   moveball();
			   checkCollision();
			   if(NBricksLeft==0) {
				   add(Label_Win);
				   break;
			   }
			   pause(DELAY);		   	   
		   }
		   if(NTurnsLeft<=0){
		   add(Label_Lose);
		   }
		 
	 }
	 
	 private void labelsSetting(){
		 LabelTurnsRemaining=new GLabel(NTurnsLeft+" ",50,50);
		 add(LabelTurnsRemaining);
		 LabelBricksRemaining=new GLabel(NBricksLeft+" ",100,50);
		 add(LabelBricksRemaining);
		 Label_Win=new GLabel ("You WINNNNN",getWidth()/2,getHeight()/2);
		 Label_Win.setFont("serif");
		 Label_Lose=new GLabel ("Suckerrrrrrr",getWidth()/2,getHeight()/2);
		 
	 }
	 private void createBall(){
		 Ball= new GOval(getWidth()/2-BALL_RADIUS,getHeight()/2-BALL_RADIUS,BALL_RADIUS*2,BALL_RADIUS*2);
		 Ball.setFilled(true);
		 Ball.setFillColor(Color.GRAY);
		 add(Ball);
		 
	 }
	 
	 private void moveball(){
		 Ball.move(vX, vY);
		 
	 }
	 
	 private void  re_init_ball(){
		 vX=rgen.nextDouble(1.0,3.0);
		 if(rgen.nextBoolean(0.5)) vX=-vX;	 
	 }
	 private void checkCollision(){
		 checkCollision_Bound();
		 checkCollision_Object(); 
		 
	 }
	 private void checkCollision_Bound(){
		 
		 double ball_R_Edge=Ball.getX()+2*BALL_RADIUS;
		 double ball_L_Edge=Ball.getX();
		 double ball_Bottom=Ball.getY()+2*BALL_RADIUS;
		 double ball_Top=Ball.getY();	 
		 if(ball_R_Edge>getWidth()){
			 vX=-vX;
		 }
		 else if (ball_R_Edge<0){
			 vX=-vX;
			 
		 }
		 else if ( ball_Bottom>getHeight()){
			
			 reServingBall();
			 NTurnsLeft--;
			 LabelTurnsRemaining.setLabel(NTurnsLeft+"");
			 
		 }
		 else if(ball_Top<0){
			 vY=-vY;
		 }
	 }
	 
	 private void reServingBall(){
		 
		 
		 waitForClick();
		 Ball.setLocation(getWidth()/2-BALL_RADIUS,getHeight()/2-BALL_RADIUS);
		 re_init_ball();
		 
	 }
	 private void checkCollision_Object(){
		 collider=getCollidingObject();
		 if(collider==paddle){
			 vY=-vY;
		 }
		 
		 else if (collider==null || collider==Label_Win || collider==Label_Lose
				 || collider==LabelTurnsRemaining || collider==LabelBricksRemaining){	 
		 }
		
		 
		 else{
			 vY=-vY;
			 remove(collider);
			 NBricksLeft--;
			 LabelBricksRemaining.setLabel(NBricksLeft+"");
			 
		 }
		
		 
	 }
	 
	 private GObject getCollidingObject(){
		 GPoint LeftTopCorner=new GPoint(Ball.getX(),Ball.getY());
		 GPoint RightTopCorner=new GPoint(Ball.getX()+2*BALL_RADIUS,Ball.getY());
		 GPoint LeftBottomCorner=new GPoint(Ball.getX(),Ball.getY()+2*BALL_RADIUS);
		 GPoint RightBottomCorner=new GPoint(Ball.getX()+2*BALL_RADIUS,Ball.getY()+2*BALL_RADIUS);
		 
		 if(getElementAt(LeftTopCorner)!=null){
			 return getElementAt(LeftTopCorner);
		 }
		 else if (getElementAt(RightTopCorner)!=null){
			 return getElementAt(RightTopCorner);
		 }
		 else if (getElementAt(LeftBottomCorner)!=null){
			 return getElementAt(LeftBottomCorner);
		 }
		 else if (getElementAt(RightBottomCorner)!=null){
			 return getElementAt(RightBottomCorner);
		 }
		 
		 else return null;	
	 }
	
}

