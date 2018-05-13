//Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {

	private ArrayList<Player> players;
	private ArrayList<GameObject> map;
	private ArrayList<MapTriangle> bullets;
	
	private long time = 0;
	private int spawnTime = 500; //milliseconds to spawn a new triangle on the map
	
	public ObjectManager() {
		players = new ArrayList<Player>();
		map = new ArrayList<GameObject>();
		bullets = new ArrayList<MapTriangle>();
		addPlayer(new Player(400, 400, 0, Color.PINK, 0));
		addPlayer(new Player(400, 600, 0, Color.MAGENTA, 1));
	}
	
	public void addBullet(int x, int y, int direction) {
		
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void addObject(GameObject o) {
		map.add(o);
	}
	
	public void update() {
		for(Player p: players) {
			p.update();
		}
		for(GameObject o: map) {
			o.update();
		}
		manageMap();
		checkCollision();
		purgeObjects();
	}
	
	public void purgeObjects() {
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
	}
	
	public void draw(Graphics g) {
		for(GameObject o: map) {
			o.draw(g);
		}
		for(Player p: players) {
			p.draw(g);
		}
	}
	
	public void manageMap() {
		if(System.currentTimeMillis() - time >= spawnTime) {
			//addObject(new Triangle());
			time = System.currentTimeMillis();
		}
	}
	
	public void bulletToBullet(MapTriangle o1, MapTriangle o2) {
		o1.kill();
		o2.kill();
	}
	
	public void bulletToPlayer(MapTriangle o1, PlayerTriangle o2) {
		o1.kill();
		if(o2.getType().equalsIgnoreCase("layer"))
			players.get(o2.getPlayer().getPIndex()).removeTriangle(o2.getIndex());
		else
			players.get(o2.getPlayer().getPIndex()).getCore().removeTriangle(o2.getIndex());
	}
	
	public void mapToPlayer(GameObject o1, PlayerTriangle o2) {
		o1.kill();
		players.get(o2.getPlayer().getPIndex()).addTriangle();
	}
	
	public void playerCollision() {
		players.get(0).setVelocity(-players.get(0).getVelocity()[0], -players.get(0).getVelocity()[1]);
		players.get(1).setVelocity(-players.get(1).getVelocity()[0], -players.get(1).getVelocity()[1]);
	}
	
	public void checkMap(int i, int j) {
		GameObject o1;
		GameObject o2;
		double distance;
		
		for(int k = 0; k<map.size(); k++) {
			o1 = players.get(i).getReserve().get(j);
			o2 = map.get(k);
			distance = Math.sqrt(Math.pow(o1.getX() - o2.getX(), 2) + Math.pow(o1.getY() - o2.getY(), 2));
			if(distance<(0.8*players.get(i).getHeight())) {
				mapToPlayer(o2, (PlayerTriangle) o1);
				break;
			}
		}
	}
	
	public boolean checkLayer(int i, int j) {
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
	
	public void checkCollision() {
		for(int j = 0; j<210; j++) {
			if(players.get(0).getDrawn().get(j)) {
				if(checkLayer(0,j)) {
					break;
				}
				checkMap(0,j);
			}
		}
		for(int j = 0; j<6; j++) {
			if(players.get(0).getCore().getDrawn().get(j)) {
				if(checkCore(0,j)) {
					break;
				}
				checkMap(0,j);
			}
		}
		for(int j = 0; j<210; j++) {
			if(players.get(1).getDrawn().get(j)) {
				checkMap(1,j);
			}
		}
		for(int j = 0; j<6; j++) {
			if(players.get(1).getCore().getDrawn().get(j)) {
				checkMap(1,j);
			}
		}
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void reset() {
		map.clear();
		players.clear();
	}

}
