//Muruhathasan-Fok
package package1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Player extends GameObject{

	private ArrayList<PlayerTriangle> reserve;
	private ArrayList<Boolean> drawn;
	private int[] layerEnds;
	private Core core;
	private double[] velocity;
	private int pIndex;
	
	private long infectionStartTime = -1;
	private long infectionTimer = -1;
	private int infectionBuffer = 10000;
	
	private long MGStartTime = -1;
	private long MGTimer = -1;
	private int MGBuffer = 5000;
	
	private long replenishStartTime = -1;
	private long replenishTimer = -1;
	private int replenishBuffer = 100;
	 
	private long pStartTime = -1;
	private long pTimer = -1;
	private int pBuffer = 80;
	private int pLayer = 1;
	private int pRuns = 0;
	
	double side = 40;
	double height = (Math.sqrt(3)*side)/2;
	
	boolean[] powerUps;
	boolean infection;
	int tCount = 0;
	int colorChangeStall = 0;
	boolean paralyzed;
	
	private PlayMusic musicUI;
	
	public Player(double x, double y, double direction, Color color, int pIndex) {
		super();
		reserve = new ArrayList<PlayerTriangle>();
		drawn = new ArrayList<Boolean>();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.pIndex = pIndex;
		velocity = new double[2];
		core = new Core(this);
		constructTriangles();
		constructDrawn();
		initiatePowerUps();
		
		musicUI = new PlayMusic();
		musicUI.loadInDaMusic();
		musicUI.checkDaMusic();
		
		
	}
	
	private void initiatePowerUps(){
		powerUps = new boolean[4];
		for(int i = 0; i<powerUps.length; i++){
			powerUps[i] = false;
		}
		infection = false;
	}
	
	private void constructTriangles() {
		layerEnds = new int[6];
		layerEnds[0] = 0;
		layerEnds[1] = 18;
		layerEnds[2] = 48;
		layerEnds[3] = 90;
		layerEnds[4] = 144;
		layerEnds[5] = 210;
		
		double dir = 180;
		int index = 0;
		
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<6; j++) {
				double xref = Math.sin(Math.toRadians(j*60-30))*(side/2+side*(i+1));
				double yref = Math.cos(Math.toRadians(j*60-30))*(side/2+side*(i+1));
				double x = xref + Math.sin(Math.toRadians(j*60+60))*(height/3);
				double y = yref + Math.cos(Math.toRadians(j*60+60))*(height/3);
				for(int k = 0 ; k<(i+1)*2; k++) {
					reserve.add(new PlayerTriangle(x, y, dir, side, this, index, color));
					index++;
					x += Math.sin(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					y += Math.cos(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					dir = (dir+180)%360;
				}
				reserve.add(new PlayerTriangle(x, y, dir, side, this, index, color));
				index++;
				dir = (dir+180)%360;
			}
		}
	}
	
	private void constructDrawn() {
		for(int i = 0; i<18; i++) {
			drawn.add(true);
		}
		for(int i = 0; i<210-18; i++) {
			drawn.add(false);
		}
	}
	
	public void update() {
		x+=velocity[0];
		y+=velocity[1];
		for(int i = 0; i<210; i++) {
			reserve.get(i).setCenter(x, y);
		}
		for(int i = 0; i<6; i++) {
			core.getReserve().get(i).setCenter(x, y);
		}
		boolean b = false;
		for(int i = 0; i<6; i++) {
			if(core.getDrawn().get(i))
				b = true;
		}
		isAlive = b;
		
		if(infection)
			infection();
		if(paralyzed)
			paralyze();
		if(powerUps[1])
			MG();
		if(powerUps[2])
			replenish();
	}
	
	public void draw(Graphics g) {
		core.draw(g);
		
		for(int i = 0; i<210; i++) {
			if(drawn.get(i))
				reserve.get(i).draw(g);
		}
		
		g.setColor(color);
		int x2 = (int)(x+Math.sin(Math.toRadians(direction))*height*5/6);
		int y2 = (int)(y+Math.cos(Math.toRadians(direction))*height*5/6);
		g.drawLine((int)x, (int)y, x2, y2);
		drawTriangle(g, x2, y2);
	}
	
	public void drawTriangle(Graphics g, double x2, double y2) {
		int[] xcord = new int[3];
		int[] ycord = new int[3];
		
		for(int i = 0 ; i<3; i++) {
			xcord[i] = (int)(x2+2.0/3*8*Math.sin(Math.toRadians(direction+i*120)));
			ycord[i] = (int)(y2+2.0/3*8*Math.cos(Math.toRadians(direction+i*120)));
		}
		
		Polygon p = new Polygon(xcord, ycord, 3);
		g.setColor(color);
		g.fillPolygon(p);
	}
	
	public void addTriangle() {
		for(int i = 0; i<210; i++) {
			if(!drawn.get(i)) {
				try {
					musicUI.pickingUpTriangles();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				drawn.set(i, true);
				break;
			}
		}
	}
	
	public void removeTriangle(int index) {
		drawn.set(index, false);
	}
	
	public PlayerTriangle removeLastTriangle() {
		for(int i = 210-1; i>=0; i--) {
			if(drawn.get(i)) {
				drawn.set(i, false);
				return reserve.get(i);
			}
		}
		return null;
	}
	
	public PlayerTriangle removeLastTriangleRestricted() {
		if(tCount%2 == 0) {
			return removeLastTriangle();
		}
		return new PlayerTriangle();
	}
	
	public void updateVelocity(double x, double y) {
		velocity[0]+=x;
		velocity[1]+=y;
	}
	
	public void setVelocity(double x, double y) {
		velocity[0] = x;
		velocity[1] = y;
	}
	
	public void updateDirection(double change) {
		direction = (direction+change+360)%360;
	}
	
	private void infection()
	{
		if(infectionTimer<0) {
			infectionTimer = System.currentTimeMillis();
			infectionStartTime = infectionTimer;
		}
		if(System.currentTimeMillis() - infectionBuffer/20 > infectionTimer){
			tCount++;
			infectionTimer = System.currentTimeMillis();
			removeLastTriangle();
			if(tCount>=20) {
				infection = false;
				infectionTimer = -1;
				tCount = 0;
			}
		}
		
		if(colorChangeStall<3&&tCount%2==1) {
			setColor(new Color(106, 168, 79));
			colorChangeStall++;
		}
		else if(tCount%2 == 0){
			colorChangeStall = 0;
		}
		else {
			setColor(color);
		}
	}
	
	private void replenish() {
		if (replenishTimer < 0) {
			replenishTimer = System.currentTimeMillis();
			replenishStartTime = replenishTimer;
		}
		if(System.currentTimeMillis() - replenishBuffer > replenishTimer) {
			addTriangle();
			replenishTimer = System.currentTimeMillis();
			tCount++;
		}
		if(tCount>=10) {
			powerUps[2] = false;
			tCount = 0;
			replenishTimer = -1;
		}
	}
	
	public void MG() {
		if(MGTimer<0) {
			MGTimer = System.currentTimeMillis();
			MGStartTime = MGTimer;
		}
		if(System.currentTimeMillis() - MGBuffer > MGTimer) {
			MGTimer = -1;
			powerUps[1] = false;
			tCount = 0;
		}
	}
	
	public void paralyze() {
		if(pTimer<0) {
			pTimer = System.currentTimeMillis();
			pStartTime = pTimer;
		}
		if(System.currentTimeMillis()-pBuffer > pTimer) {
			pTimer = System.currentTimeMillis();
			pLayer++;
			if(pLayer>5) {
				pLayer = 1;
				pRuns++;
			}
			setColor(color);
			for(int i = layerEnds[pLayer-1]; i<layerEnds[pLayer]; i++) {
				reserve.get(i).setColor(Color.YELLOW);
			}
		}
		if(pRuns>=8) {
			setColor(color);
			pTimer = -1;
			paralyzed = false;
			pRuns = 0;
			pLayer = 0;
		}
	}
	
	public void addTCount() {
		tCount++;
	}
	
	public void setColor(Color c) {
		for(int i = 0; i<reserve.size(); i++) {
			reserve.get(i).setColor(c);
		}
	}
		
	public ArrayList<PlayerTriangle> getReserve(){
		return reserve;
	}
	
	public ArrayList<Boolean> getDrawn(){
		return drawn;
	}
	
	public Core getCore() {
		return core;
	}

	public double getHeight() {
		return height;
	}
	
	public double[] getVelocity() {
		return velocity;
	}
	
	public int getPIndex() {
		return pIndex;
	}
	
	public boolean hasPowerUp(){
		for(int i = 0; i<powerUps.length; i++){
			if(powerUps[i]){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasInfection() {
		return infection;
	}
	
	public boolean isParalyzed() {
		return paralyzed;
	}
	
	public boolean hasIDart(){
		return powerUps[0];
	}
	
	public boolean hasPDart() {
		return powerUps[3];
	}
	
	public boolean getReplenish() {
		return powerUps[2];
	}
	
	public boolean getMG() {
		return powerUps[1];
	}
	
	public void setInfection(boolean b) {
		infection = b;
	}
	
	public void setParalyze(boolean b) {
		paralyzed = b;
	}
	
	public boolean setIDart(boolean b) {
		if(!hasPowerUp()||!b) { 
			powerUps[0] = b;
			return true;
		}
		return false;
	}
	
	public boolean setMG(boolean b) {
		if(!hasPowerUp()||!b) { 
			powerUps[1] = b;
			return true;
		}
		return false;
	}
	
	public boolean setReplenish(boolean b) {
		if(!hasPowerUp()||!b) { 

			powerUps[2] = b;
			return true;
		}
		return false;
	}
	
	public boolean setPDart(boolean b) {
		if(!hasPowerUp()||!b) { 
			powerUps[3] = b;
			return true;
		}
		return false;
	}
	
	public long getPStartTime() {
		return pStartTime;
	}
	
	public long getInfectionStartTime() {
		return infectionStartTime;
	}
	
	public long getReplenishStartTime() {
		return replenishStartTime;
	}
	
	public long getMGStartTime() {
		return MGStartTime;
	}
}