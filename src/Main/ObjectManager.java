//Muruhathasan
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
	
	private long time = 0;
	private int spawnTime = 1000; //milliseconds to spawn a new triangle on the map
	
	public ObjectManager(int width, int height) {
		this.width = width;
		this.height = height;
		frameX = 0;
		frameY = 0;
		players = new ArrayList<Player>();
		map = new ArrayList<GameObject>();
		bullets = new ArrayList<Bullet>();
		addPlayer(new Player(400, 400, 0, Color.PINK, 0));
		addPlayer(new Player(400, 600, 0, Color.MAGENTA, 1));
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
	
	public void shootBullet(int index) {
		PlayerTriangle t = players.get(index).removeLastTriangle();
		if(t!=null) {
			bullets.add(new Bullet(players.get(index).getX(), players.get(index).getY(), t.getDirection(), t.getSide(), players.get(index).getColor(), players.get(index).getDirection(), index));
			//bullets.add(new Bullet(t.getX(), t.getY(), t.getDirection(), t.getSide(), players.get(index).getColor(), players.get(index).getDirection(), index));
		}
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
		if(map.size()<(width*height)/40000&&System.currentTimeMillis() - time >= spawnTime) {
			map.add(new MapTriangle(Math.random()*width, Math.random()*height, Math.random()*360, 40, Color.gray));
			time = System.currentTimeMillis();
		}
		
		for(int i = 0; i<bullets.size(); i++) {
			if(bullets.get(i).getX()>width||bullets.get(i).getY()>height) {
				bullets.get(i).kill();
			}
			else if(bullets.get(i).getX()<0||bullets.get(i).getY()<0) {
				bullets.get(i).kill();
			}
		}
	}
	
	private void resistance() {
		players.get(0).updateVelocity(-players.get(0).getVelocity()[0]*.01, -players.get(0).getVelocity()[1]*.01);
		players.get(1).updateVelocity(-players.get(1).getVelocity()[0]*.01, -players.get(1).getVelocity()[1]*.01);
	}
	
	private void attract() {
		for(int i = 0; i<bullets.size(); i++) {
			int index = (bullets.get(i).getPIndex()+1)%2;
			double velX = players.get(index).getX()-bullets.get(i).getX();
			velX/=Math.abs(3000);
			double velY = players.get(index).getY()-bullets.get(i).getY();
			velY/=Math.abs(3000);
			bullets.get(i).updateVelocity(velX, velY);
		}
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
		
		if(players.size()<2) {
			reset();
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
	}
	
	private void bulletToBullet(MapTriangle o1, MapTriangle o2) {
		o1.kill();
		o2.kill();
	}
	
	private void bulletToPlayer(Bullet o1, PlayerTriangle o2, int pIndex) {
		if(pIndex!=o1.getPIndex()){
			o1.kill();
			if(o2.getIndex()>=0)
				players.get(pIndex).removeTriangle(o2.getIndex());
			else
				players.get(pIndex).getCore().removeTriangle(o2.getIndex()+6);
		}
	}
	
	private void mapToPlayer(GameObject o1, PlayerTriangle o2, int pIndex) {
		if(o1 instanceof Bullet) {
			bulletToPlayer((Bullet)o1, o2, pIndex);
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
	
	private void edges() {
		for(int i = 0; i<players.size(); i++) {
			if(players.get(i).getX()<0||players.get(i).getX()>width)
				players.get(i).setVelocity(-players.get(i).getVelocity()[0], players.get(i).getVelocity()[1]);
			if(players.get(i).getY()<0||players.get(i).getY()>height)
				players.get(i).setVelocity(players.get(i).getVelocity()[0], -players.get(i).getVelocity()[1]);
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
