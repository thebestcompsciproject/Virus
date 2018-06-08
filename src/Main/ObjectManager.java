//Muruhathasan-Fok-Gurram
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.UnsupportedAudioFileException;

import Main.PowerUps.AntidotePowerUp;
import Main.PowerUps.DartPowerUp;
import Main.PowerUps.MGPowerUp;
import Main.PowerUps.ParalyzePowerUp;
import Main.PowerUps.ReplenishPowerUp;

public class ObjectManager {

	private ArrayList<Player> players;
	private ArrayList<GameObject> map;
	private ArrayList<Bullet> bullets;
	
	private int width;
	private int height;
	private int frameX;
	private int frameY;
	
	private long timeMap = 0;
	private int spawnTimeMap = 1000;
	
	private long timeInf = 0;
	private double probInf = 0.04;
	private long timeRepl = 0;
	private double probRepl = 0.07;
	private long timeMG = 0;
	private double probMG = 0.05;
	private long timeP = 0;
	private double probP = 0.03;
	
	private PlayMusic musicUI;

	
	//CONSTRUCTOR
	
	public ObjectManager(int width, int height) {
		this.width = width;
		this.height = height;
		frameX = 0;
		frameY = 0;
		players = new ArrayList<Player>();
		map = new ArrayList<GameObject>();
		bullets = new ArrayList<Bullet>();
		addPlayer(new Player(width/4, height/4, 0, new Color(171, 60, 60), 0));
		addPlayer(new Player((3*width)/4, (3*height)/4, 0, new Color(241, 194, 50), 1));
		for(int i = 0; i <(width*height)/40000; i++) {
			map.add(new MapTriangle(Math.random()*width, Math.random()*height, Math.random()*360, 40, Color.gray));
		}
		
		musicUI = new PlayMusic();
		musicUI.loadInDaMusic();
	}
	
	public void updateInfo(int w, int h, int x, int y) {
		width = w;
		height = h;
		frameX = x;
		frameY = y;
	}
	
	//ADD METHODS
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void addObject(GameObject o) {
		map.add(o);
	}
	
	public void shootBullet(int index, int size) {
		/*PlayerTriangle t = players.get(index).removeLastTriangle();
		if(t!=null) {
			bullets.add(new Bullet(t.getX(), t.getY(), t.getDirection(), t.getSide(), players.get(index).getColor(), players.get(index).getDirection(), index, false));
		}*/
		
		if(size<40) {
			players.get(index).addTCount();
			if(players.get(index).removeLastTriangleRestricted()!=null) {
				bullets.add(new Bullet(players.get(index).getX(), players.get(index).getY(), players.get(index).getDirection(), size, players.get(index).getColor(), players.get(index).getDirection(), index, 0));
			}
		}
		else {
			if(players.get(index).removeLastTriangle()!=null) {
				bullets.add(new Bullet(players.get(index).getX(), players.get(index).getY(), players.get(index).getDirection(), size, players.get(index).getColor(), players.get(index).getDirection(), index, 0));
			}
		}
	}
	
	public void shootInfectedBullet(int index){
		PlayerTriangle t = players.get(index).removeLastTriangle();
		if(t!=null) {
			bullets.add(new Bullet(players.get(index).getX(), players.get(index).getY(), t.getDirection(), t.getSide(), new Color(106, 168, 79), players.get(index).getDirection(), index, 1));
			players.get(index).setIDart(false);
		}
	}
	
	public void shootParalyzedBullet(int index){
		PlayerTriangle t = players.get(index).removeLastTriangle();
		if(t!=null) {
			bullets.add(new Bullet(players.get(index).getX(), players.get(index).getY(), t.getDirection(), t.getSide(), Color.YELLOW, players.get(index).getDirection(), index, 2));
			players.get(index).setPDart(false);
		}
	}
	
	//UPDATES
	
	public void update() {
		for(Player p: players) {
			p.update();
		}
		for(GameObject o: map) {
			o.update();
		}
		for(MapTriangle b: bullets) {
			b.update();
		}
		
		manageMap();
		resistance();
		attract();
		checkCollision();
		purgeObjects();
	}
	
	private void manageMap() {
		if(map.size()<(width*height)/40000&&System.currentTimeMillis() - timeMap >= spawnTimeMap) {
			map.add(new MapTriangle(Math.random()*width, Math.random()*height, Math.random()*360, 40, Color.gray));
			timeMap = System.currentTimeMillis();
		}
		
		for(int i = 0; i<bullets.size(); i++) {
			if(bullets.get(i).getX()>width||bullets.get(i).getY()>height) {
				bullets.get(i).kill();
			}
			else if(bullets.get(i).getX()<0||bullets.get(i).getY()<0) {
				bullets.get(i).kill();
			}
		}
		spawnInfectionDart();
		spawnReplenish();
		spawnMG();
		spawnParalyzeDart();
	}

	private void spawnInfectionDart() {
		if(System.currentTimeMillis()-1000>=timeInf){
			timeInf = System.currentTimeMillis();
			if (Math.random()<probInf) {
				addObject(new DartPowerUp(width*Math.random(), height*Math.random(), 25.0));
			}		
		}
		
	}
	
	private void spawnReplenish() {
		if (System.currentTimeMillis() - 1000 >= timeRepl) {
			timeRepl = System.currentTimeMillis();
			if(Math.random() < probRepl) {
				addObject(new ReplenishPowerUp(width*Math.random(), height*Math.random(), 25.0));
			}
		}
	}
	
	private void spawnMG() {
		if (System.currentTimeMillis() - 1000 >= timeMG) {
			timeMG = System.currentTimeMillis();
			if(Math.random() < probMG) {
				addObject(new MGPowerUp(width*Math.random(), height*Math.random(), 25.0));
			}
		}
	}
	
	private void spawnParalyzeDart() {
		if (System.currentTimeMillis() - 1000 >= timeP) {
			timeP = System.currentTimeMillis();
			if(Math.random() < probP) {
				addObject(new ParalyzePowerUp(width*Math.random(), height*Math.random(), 25.0));
			}
		}
	}
	
	private void resistance() {
		players.get(0).updateVelocity(-players.get(0).getVelocity()[0]*.010, -players.get(0).getVelocity()[1]*.010);
		players.get(1).updateVelocity(-players.get(1).getVelocity()[0]*.010, -players.get(1).getVelocity()[1]*.010);
	}
	
	
	private void attract() {
		for(int i = 0; i<bullets.size(); i++) {
			int index = bullets.get(i).getPIndex();
			if(checkAngleContained(bullets.get(i).getFinalD(), getAngle(bullets.get(i).getX(), bullets.get(i).getY(), players.get((index+1)%2).getX(), players.get((index+1)%2).getY()), 90)) {
				bullets.get(i).updateFinalD((angleDiff(bullets.get(i).getFinalD(), getAngle(bullets.get(i).getX(), bullets.get(i).getY(), players.get((index+1)%2).getX(), players.get((index+1)%2).getY())))/75);
			}
		}
	}
	
	public double angleDiff(double d1, double d2) {
		return direction(d1, d2)*Math.min(Math.abs(d1-d2), 360-Math.abs(d1-d2));
	}
	
	public int direction(double d1, double d2) {
		if(d1<180) {
			if(d2>d1&&d2<d1+180) {
				return 1;
			}
		}
		else {
			if(d2>d1||d2<d1-180) {
				return 1;
			}
		}
		return -1;
	}
	
	private boolean checkAngleContained(double d1, double d2, double diff) {
		if(Math.abs(angleDiff(d1, d2))<diff)
			return true;
		return false;
	}
	
	private double getAngle(double x1, double y1, double x2, double y2) {
		double delX = x2 - x1;
		double delY = y2 - y1;
		double thetaR = Math.atan2(delX, delY);
		return ((thetaR*360/(2*Math.PI))+360)%360;
	}
	
	private void purgeObjects() {
		for(int i = 0; i < map.size(); i++) {
			if(!map.get(i).isAlive()) {
				map.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < players.size(); i++) {
			if(!players.get(i).isAlive()) {
				players.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			if(!bullets.get(i).isAlive()) {
				bullets.remove(i);
				i--;
			}
		}
	}
	
	//COLLISIONS
	
	private void checkCollision() {
		for(int j = 0; j<210; j++) {
			if(players.get(0).getDrawn().get(j)) {
				if(checkLayer(0,j)) {
					break;
				}
				checkMapLayer(0,j);
			}
		}
		
		for(int j = 0; j<6; j++) {
			if(players.get(0).getCore().getDrawn().get(j)) {
				if(checkCore(0,j)) {
					break;
				}
				checkMapCore(0,j);
			}
		}
		
		for(int j = 0; j<210; j++) {
			if(players.get(1).getDrawn().get(j)) {
				checkMapLayer(1,j);
			}
		}
		
		for(int j = 0; j<6; j++) {
			if(players.get(1).getCore().getDrawn().get(j)) {
				checkMapCore(1,j);
			}
		}
		
		edges();
		checkBullets();
	}
	
	private void bulletCollision(MapTriangle o1, MapTriangle o2) { 
		if(((Bullet)o1).getPIndex() != ((Bullet)o2).getPIndex()) {
			o1.kill();
			o2.kill();
		}
	}
	
	private void bulletToPlayer(Bullet o1, PlayerTriangle o2, int pIndex) {
		if(pIndex!=o1.getPIndex()){
			o1.addHits();
			if(o1.getType() == 1){
				if(!players.get(pIndex).hasInfection())
					addObject(new AntidotePowerUp(width*Math.random(), height*Math.random(), 25.0));
				players.get(pIndex).setInfection(true);
				o1.kill();
			}
			else if(o1.getType() == 2) {
				players.get(pIndex).setParalyze(true);
				o1.kill();
			}
			else{
				if(o2.getIndex()>=0)
					players.get(pIndex).removeTriangle(o2.getIndex());
				else {
					if(o1.getHits()==1)
						players.get(pIndex).getCore().removeTriangle(o2.getIndex()+6);
					o1.kill();
				}
			}
		}
	}
	
	private void mapToPlayer(GameObject o1, PlayerTriangle o2, int pIndex) {
		if(o1 instanceof Bullet) {
			bulletToPlayer((Bullet)o1, o2, pIndex);
		}
		else if(o1 instanceof AntidotePowerUp){
			players.get(o2.getPlayer().getPIndex()).setInfection(false);
			players.get(o2.getPlayer().getPIndex()).setColor(players.get(o2.getPlayer().getPIndex()).getColor());
			o1.kill();
			
			try {
				musicUI.playCollectPU();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(o1 instanceof DartPowerUp){
			if(players.get(o2.getPlayer().getPIndex()).setIDart(true)) {
				o1.kill();
				try {
					musicUI.playCollectPU();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(o1 instanceof MGPowerUp){	
			if(players.get(o2.getPlayer().getPIndex()).setMG(true)) {
				o1.kill();
				try {
					musicUI.playCollectPU();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(o1 instanceof ReplenishPowerUp){		
			if(players.get(o2.getPlayer().getPIndex()).setReplenish(true)) {
				o1.kill();
				try {
					musicUI.playReplenishPU();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(o1 instanceof ParalyzePowerUp){		
			if(players.get(o2.getPlayer().getPIndex()).setPDart(true)) {
				o1.kill();
				try {
					musicUI.playParalyzePU();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			o1.kill();
			players.get(o2.getPlayer().getPIndex()).addTriangle();
		}
	}
	
	private void playerCollision() {
		double xVel = players.get(0).getVelocity()[0];
		double yVel = players.get(0).getVelocity()[1];
		players.get(0).setVelocity(players.get(1).getVelocity()[0], players.get(1).getVelocity()[1]);
		players.get(1).setVelocity(xVel, yVel);
	}
	
	private void checkMapLayer(int i, int j) {
		GameObject o1;
		GameObject o2;
		double distance;
		
		for(int k = 0; k<map.size(); k++) {
			o1 = players.get(i).getReserve().get(j);
			o2 = map.get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(distance<(0.8*players.get(i).getHeight())) {
				mapToPlayer(o2, (PlayerTriangle) o1, i);
				break;
			}
		}
		
		for(int k = 0; k<bullets.size(); k++) {
			o1 = players.get(i).getReserve().get(j);
			o2 = bullets.get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(distance<(0.8*players.get(i).getHeight())) {
				mapToPlayer(o2, (PlayerTriangle) o1, i);
				break;
			}
		}
	}
	
	private void checkMapCore(int i, int j) {
		GameObject o1;
		GameObject o2;
		double distance;
		
		for(int k = 0; k<map.size(); k++) {
			o1 = players.get(i).getCore().getReserve().get(j);
			o2 = map.get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(distance<(players.get(i).getHeight())) {
				mapToPlayer(o2, (PlayerTriangle) o1, i);
				break;
			}
		}
		
		for(int k = 0; k<bullets.size(); k++) {
			o1 = players.get(i).getCore().getReserve().get(j);
			o2 = bullets.get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(distance<(0.8*players.get(i).getHeight())) {
				mapToPlayer(o2, (PlayerTriangle) o1, i);
				break;
			}
		}
	}
	
	private boolean checkLayer(int i, int j) {
		GameObject o1;
		GameObject o2;
		double distance;
		
		for(int k = 0; k<players.get((i+1)%2).getReserve().size(); k++) {
			o1 = players.get(i).getReserve().get(j);
			o2 = players.get((i+1)%2).getReserve().get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(players.get((i+1)%2).getDrawn().get(k) && distance<(0.8*players.get(i).getHeight())) {
				playerCollision();
				return true;
			}
		}
		
		for(int k = 0; k<6; k++) {
			o1 = players.get(i).getReserve().get(j);
			o2 = players.get((i+1)%2).getCore().getReserve().get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(players.get((i+1)%2).getCore().getDrawn().get(k) && distance<(0.8*players.get(i).getHeight())) {
				playerCollision();
				return true;
			}
		}
		return false;
	}
	
	private boolean checkCore(int i, int j) {
		GameObject o1;
		GameObject o2;
		double distance;
		
		for(int k = 0; k<players.get((i+1)%2).getReserve().size(); k++) {
			o1 = players.get(i).getCore().getReserve().get(j);
			o2 = players.get((i+1)%2).getReserve().get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(players.get((i+1)%2).getDrawn().get(k) && distance<(0.8*players.get(i).getHeight())) {
				playerCollision();
				return true;
			}
		}
		
		for(int k = 0; k<6; k++) {
			o1 = players.get(i).getCore().getReserve().get(j);
			o2 = players.get((i+1)%2).getCore().getReserve().get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(players.get((i+1)%2).getCore().getDrawn().get(k) && distance<(0.8*players.get(i).getHeight())) {
				playerCollision();
				return true;
			}
		}
		return false;
	}
	
	public void checkBullets() {
		double distance;
		for(int i = 0; i<bullets.size(); i++) {
			for(int j = i+1; j<bullets.size(); j++) {
				GameObject o1 = bullets.get(i);
				GameObject o2 = bullets.get(j);
				if(o1 instanceof Bullet && o2 instanceof Bullet) {
					distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
					if(distance<(0.8*players.get(0).getHeight())) {
						bulletCollision((Bullet)o1, (Bullet)o2);
					}
				}
			}
		}
	}
	
	private void edges() {
		for(int i = 0; i<players.size(); i++) {
			if(players.get(i).getX()<0)
				players.get(i).setVelocity(Math.abs(players.get(i).getVelocity()[0]), players.get(i).getVelocity()[1]);
			if(players.get(i).getX()>width)
				players.get(i).setVelocity(-Math.abs(players.get(i).getVelocity()[0]), players.get(i).getVelocity()[1]);
			if(players.get(i).getY()<0)
				players.get(i).setVelocity(players.get(i).getVelocity()[0], Math.abs(players.get(i).getVelocity()[1]));
			if(players.get(i).getY()>height)
				players.get(i).setVelocity(players.get(i).getVelocity()[0], -Math.abs(players.get(i).getVelocity()[1]));
		}
	}
	
	//GRAPHICS
	
	public void draw(Graphics g) {
		if(players.size()==2)
			powerUpsGUI(g);
		for(GameObject o: map)
			o.draw(g);
		for(Player p: players)
			p.draw(g);
		for(MapTriangle b: bullets)
			b.draw(g);
	}
	
	private void powerUpsGUI(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		
		if(players.get(0).hasIDart()) {
			g.drawImage(GamePanel.arcReactVirus, 2*width/100, height-height/10, height/15, height/15, null);
		}
		else {
			g.drawImage(GamePanel.arcReactDefault, 2*width/100, height-height/10, height/15, height/15, null);
		}
		if(players.get(0).hasPDart()) {
			g.drawImage(GamePanel.arcReactParalyze, 2*width/100+ height/15+width/50, height-height/10, height/15, height/15, null);
		}
		else {
			g.drawImage(GamePanel.arcReactDefault, 2*width/100+ height/15+width/50, height-height/10, height/15, height/15, null);
		}
		if(players.get(1).hasIDart()) {
			g.drawImage(GamePanel.arcReactVirus, width-(2*width/100+ 2*height/15+width/50), height-height/10, height/15, height/15, null);
		}
		else{
			g.drawImage(GamePanel.arcReactDefault, width-(2*width/100+ 2*height/15+width/50), height-height/10, height/15, height/15, null);
		}
		if(players.get(1).hasPDart()) {
			g.drawImage(GamePanel.arcReactParalyze, width-(2*width/100+height/15), height-height/10, height/15, height/15, null);
		}
		else {
			g.drawImage(GamePanel.arcReactDefault, width-(2*width/100+height/15), height-height/10, height/15, height/15, null);
		}
		
		int y1 = height-height/15;
		
		if(players.get(0).hasInfection()) {
			g.setColor(new Color(106, 168, 79));
			
			try {
				musicUI.infectedSound();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.fillRect(2*width/100+ 2*(height/15+width/50), y1, (int) (width*(10000-(System.currentTimeMillis()-players.get(0).getInfectionStartTime()))/(10000*6)), height/30);
			y1-=height/20;
		}
		if(players.get(0).isParalyzed()) {
			g.setColor(Color.YELLOW);
			g.fillRect(2*width/100+ 2*(height/15+width/50), y1, (int) (width*(3200-(System.currentTimeMillis()-players.get(0).getPStartTime()))/(3200*6)), height/30);
			y1-=height/20;
		}
		if(players.get(0).getMG()) {
			g.setColor(new Color(69, 69, 69));
			g.fillRect(2*width/100+ 2*(height/15+width/50), y1, (int) (width*(5000-(System.currentTimeMillis()-players.get(0).getMGStartTime()))/(5000*6)), height/30);
			y1-=height/20;
		}
		if(players.get(0).getReplenish()) {
			g.setColor(players.get(0).getColor());
			g.fillRect(2*width/100+ 2*(height/15+width/50), y1, (int) (width*(1000-(System.currentTimeMillis()-players.get(0).getReplenishStartTime()))/(1000*6)), height/30);
			y1-=height/20;
		}
		
		int y2 = height-height/15;
		
		if(players.get(1).hasInfection()) {
			g.setColor(new Color(106, 168, 79));
			g.fillRect(width-(2*width/100+ 2*(height/15+width/50) + width/6), y2, (int) (width*(10000-(System.currentTimeMillis()-players.get(1).getInfectionStartTime()))/(10000*6)), height/30);
			y2-=height/20;
		}
		if(players.get(1).isParalyzed()) {
			g.setColor(Color.YELLOW);
			g.fillRect(width-(2*width/100+ 2*(height/15+width/50) + width/6), y2, (int) (width*(3200-(System.currentTimeMillis()-players.get(1).getPStartTime()))/(3200*6)), height/30);
			y2-=height/20;
		}
		if(players.get(1).getMG()) {
			g.setColor(new Color(69, 69, 69));
			g.fillRect(width-(2*width/100+ 2*(height/15+width/50) + width/6), y2, (int) (width*(5000-(System.currentTimeMillis()-players.get(1).getMGStartTime()))/(5000*6)), height/30);
			y2-=height/20;
		}
		if(players.get(1).getReplenish()) {
			g.setColor(players.get(0).getColor());
			g.fillRect(width-(2*width/100+ 2*(height/15+width/50) + width/6), y2, (int) (width*(1000-(System.currentTimeMillis()-players.get(1).getReplenishStartTime()))/(1000*6)), height/30);
			y2-=height/20;
		}
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void reset() {
		map.clear();
		players.clear();
		bullets.clear();
	}
}