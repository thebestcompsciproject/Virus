//Muruhathasan, Gurram
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

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
	private double probInf = 0.03;
	private long timeRepl = 0;
	private double probRepl = 0.2;
	private long timeMG = 0;
	private double probMG = 0.1;
	
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
	}
	
	public void updateInfo(int w, int h, int x, int y) {
		width = w;
		height = h;
		frameX = x;
		frameY = y;
	}
	
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
			players.get(index).removeLastTriangleRestricted();
			players.get(index).addTRemoved();
		}
		else
			players.get(index).removeLastTriangle();
		bullets.add(new Bullet(players.get(index).getX(), players.get(index).getY(), players.get(index).getDirection(), size, players.get(index).getColor(), players.get(index).getDirection(), index, false));
	}
	
	public void shootInfectedBullet(int index){
		PlayerTriangle t = players.get(index).removeLastTriangle();
		if(t!=null)
			bullets.add(new Bullet(players.get(index).getX(), players.get(index).getY(), t.getDirection(), t.getSide(), new Color(106, 168, 79), players.get(index).getDirection(), index, true));
	}
	
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
	}

	private void spawnInfectionDart() {
		if(System.currentTimeMillis()-1000>=timeInf){
			timeInf = System.currentTimeMillis();
			if (Math.random()<probInf) {
				addObject(new DartPowerUp(width*Math.random(), height*Math.random(), 360*Math.random(), 20.0, new Color(106, 168, 79)));
				addObject(new AntidotePowerUp(width*Math.random(), height*Math.random(), 360*Math.random(), 20.0, new Color(69, 126, 218)));
			}		
		}
		
	}
	
	private void spawnReplenish() {
		if (System.currentTimeMillis() - 1000 >= timeRepl) {
			timeRepl = System.currentTimeMillis();
			if(Math.random() < probRepl) {
				addObject(new ReplenishPowerUp(width*Math.random(), height*Math.random(), 360*Math.random(), 20.0, new Color(69, 69, 69)));
			}
		}
	}
	
	private void spawnMG() {
		if (System.currentTimeMillis() - 1000 >= timeMG) {
			timeMG = System.currentTimeMillis();
			if(Math.random() < probMG) {
				System.out.println("ok");
				addObject(new MGPowerUp(width*Math.random(), height*Math.random(), 360*Math.random(), 20.0, new Color(250, 250, 250)));
				//addObject(new MGPowerUp(100, 100, 360*Math.random(), 23.0, new Color(255, 255, 255)));
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
			o1.kill();
			if(o1.getInfection()){
				players.get(pIndex).setInfection(true);
			}
			else{
				if(o2.getIndex()>=0)
					players.get(pIndex).removeTriangle(o2.getIndex());
				else
					players.get(pIndex).getCore().removeTriangle(o2.getIndex()+6);
			}
		}
	}
	
	private void mapToPlayer(GameObject o1, PlayerTriangle o2, int pIndex) {
		if(o1 instanceof Bullet) {
			bulletToPlayer((Bullet)o1, o2, pIndex);
		}
		else if(o1 instanceof DartPowerUp){
			
			players.get(o2.getPlayer().getPIndex()).setDart(true);
			o1.kill();
		}
		else if(o1 instanceof AntidotePowerUp){
			players.get(o2.getPlayer().getPIndex()).setInfection(false);
			o1.kill();
		}
		else if(o1 instanceof MGPowerUp){	
			players.get(o2.getPlayer().getPIndex()).setMG(true);
			o1.kill();
		}
		else if(o1 instanceof ReplenishPowerUp){		
			players.get(o2.getPlayer().getPIndex()).setReplenish(true);
			o1.kill();
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
	
	public void draw(Graphics g) {
		for(GameObject o: map)
			o.draw(g);
		for(Player p: players)
			p.draw(g);
		for(MapTriangle b: bullets)
			b.draw(g);
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