//Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {

	private ArrayList<Player> players;
	private ArrayList<GameObject> map;
	
	private long time = 0;
	private int spawnTime = 500; //milliseconds to spawn a new triangle on the map
	
	public ObjectManager() {
		players = new ArrayList<Player>();
		map = new ArrayList<GameObject>();
		addPlayer(new Player(400, 400, 0, Color.PINK));
		addPlayer(new Player(500, 500, 0, Color.MAGENTA));
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
		//purgeObjects();
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
	
	public void checkCollision() {
		for(int i = 0; i<map.size(); i++) {
			for(int j = i+1; j<map.size(); j++) {
				GameObject o1 = map.get(i);
				GameObject o2 = map.get(j);
				double distance = Math.sqrt(Math.pow(o1.getX() + o2.getX(), 2) + Math.pow(o1.getY() + o2.getY(), 2));
				if(o1 instanceof Triangle && o2 instanceof Triangle) {
					
				}

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
