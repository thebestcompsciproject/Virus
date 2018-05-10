package Main;

import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {

	private ArrayList<GameObject> objects;
	
	private long time = 0;
	private int spawnTime = 500; //milliseconds to spawn a new triangle on the map;
	int totalTriangles = 0;
	
	public ObjectManager() {
		objects = new ArrayList<GameObject>();
	}
	
	public void addObject(GameObject o) {
		objects.add(o);
	}
	
	public void update() {
		for(int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.update();
		}
		purgeObjects();
	}
	
	public void purgeObjects() {
		for(int i = 0; i < objects.size(); i++) {
			if(!objects.get(i).isAlive()) {
				objects.remove(i);
				i--;
			}
		}
	}
	
	public void draw(Graphics g) {
		for(GameObject o: objects) {
			o.draw(g);
		}
	}
	
	public void manageMap() {
		if(System.currentTimeMillis() - time >= spawnTime) {
			addObject(new Triangle(0, 0, 0, 0, null, null, 0)); //edit
			totalTriangles++;
			time = System.currentTimeMillis();
		}
	}
	
	public void checkCollision() {
		for(int i = 0; i<objects.size(); i++) {
			for(int j = i+1; j<objects.size(); j++) {
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);
				double distance = Math.sqrt(Math.pow(o1.getX() + o2.getX(), 2) + Math.pow(o1.getY() + o2.getY(), 2));
				if(o1 instanceof Triangle && o2 instanceof Triangle) {
					
				}

			}
		}
	}
	
	public void reset() {
		objects.clear();
	}
}


