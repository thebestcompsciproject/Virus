//Muruhathasan

package Main;

public class TestCode {

	//for ObjectManager
	/*public void playerCollision() {
		double xVel = players.get(0).getVelocity()[0];
		double yVel = players.get(0).getVelocity()[1];
		double angle = getAngle();
		if(Math.abs(angle)<30||Math.abs(angle)>150) {
			players.get(0).setVelocity(players.get(0).getVelocity()[0], players.get(1).getVelocity()[1]);
			players.get(1).setVelocity(players.get(1).getVelocity()[0], yVel);
		}
		else {
			double u1 = 0;
			double v1 = 0;
			double u2 = 0;
			double v2 = 0;
			if(angle<0) {
				angle+=360;
			}
			angle = 360-angle;
			System.out.println(angle);
			if(angle<90) {
				u1+=players.get(0).getVelocity()[0]*Math.sin(Math.toRadians(30));
				v2+=-players.get(0).getVelocity()[0]*Math.cos(Math.toRadians(30));
				u1+=players.get(0).getVelocity()[1]*Math.cos(Math.toRadians(30));
				v2+=players.get(0).getVelocity()[1]*Math.sin(Math.toRadians(30));
				u2+=players.get(1).getVelocity()[0]*Math.sin(Math.toRadians(30));
				v1+=-players.get(1).getVelocity()[0]*Math.cos(Math.toRadians(30));
				u2+=players.get(1).getVelocity()[1]*Math.cos(Math.toRadians(30));
				v1+=players.get(1).getVelocity()[1]*Math.sin(Math.toRadians(30));
				players.get(0).setVelocity(u1*Math.sin(Math.toRadians(30)) - v1*Math.cos(Math.toRadians(30)), u1*Math.cos(Math.toRadians(30)) + v1*Math.sin(Math.toRadians(30)));
				players.get(1).setVelocity(u2*Math.sin(Math.toRadians(30)) - v2*Math.cos(Math.toRadians(30)), u2*Math.cos(Math.toRadians(30)) + v2*Math.sin(Math.toRadians(30)));
			}
			else if(angle<150) {
				u1+=players.get(0).getVelocity()[0]*Math.cos(Math.toRadians(30));
				v2+=players.get(0).getVelocity()[0]*Math.sin(Math.toRadians(30));
				u1+=-players.get(0).getVelocity()[1]*Math.sin(Math.toRadians(30));
				v2+=players.get(0).getVelocity()[1]*Math.cos(Math.toRadians(30));
				u2+=players.get(1).getVelocity()[0]*Math.cos(Math.toRadians(30));
				v1+=players.get(1).getVelocity()[0]*Math.sin(Math.toRadians(30));
				u2+=-players.get(1).getVelocity()[1]*Math.sin(Math.toRadians(30));
				v1+=players.get(1).getVelocity()[1]*Math.cos(Math.toRadians(30));
				players.get(0).setVelocity(u1*Math.sin(Math.toRadians(30)) + v1*Math.cos(Math.toRadians(30)), -u1*Math.cos(Math.toRadians(30)) + v1*Math.sin(Math.toRadians(30)));
				players.get(1).setVelocity(u2*Math.sin(Math.toRadians(30)) + v2*Math.cos(Math.toRadians(30)), -u2*Math.cos(Math.toRadians(30)) + v2*Math.sin(Math.toRadians(30)));
			}
			else if(angle<270) {
				u1+=players.get(0).getVelocity()[0]*Math.cos(Math.toRadians(30));
				v2+=-players.get(0).getVelocity()[0]*Math.sin(Math.toRadians(30));
				u1+=players.get(0).getVelocity()[1]*Math.sin(Math.toRadians(30));
				v2+=players.get(0).getVelocity()[1]*Math.cos(Math.toRadians(30));
				u2+=players.get(1).getVelocity()[0]*Math.cos(Math.toRadians(30));
				v1+=-players.get(1).getVelocity()[0]*Math.sin(Math.toRadians(30));
				u2+=players.get(1).getVelocity()[1]*Math.sin(Math.toRadians(30));
				v1+=players.get(1).getVelocity()[1]*Math.cos(Math.toRadians(30));
				players.get(0).setVelocity(u1*Math.sin(Math.toRadians(30)) - v1*Math.cos(Math.toRadians(30)), u1*Math.cos(Math.toRadians(30)) + v1*Math.sin(Math.toRadians(30)));
				players.get(1).setVelocity(u2*Math.sin(Math.toRadians(30)) - v2*Math.cos(Math.toRadians(30)), u2*Math.cos(Math.toRadians(30)) + v2*Math.sin(Math.toRadians(30)));
			}
			else {
				u1+=players.get(0).getVelocity()[0]*Math.cos(Math.toRadians(30));
				v2+=players.get(0).getVelocity()[0]*Math.sin(Math.toRadians(30));
				u1+=-players.get(0).getVelocity()[1]*Math.sin(Math.toRadians(30));
				v2+=players.get(0).getVelocity()[1]*Math.cos(Math.toRadians(30));
				u2+=players.get(1).getVelocity()[0]*Math.cos(Math.toRadians(30));
				v1+=players.get(1).getVelocity()[0]*Math.sin(Math.toRadians(30));
				u2+=-players.get(1).getVelocity()[1]*Math.sin(Math.toRadians(30));
				v1+=players.get(1).getVelocity()[1]*Math.cos(Math.toRadians(30));
				players.get(0).setVelocity(u1*Math.sin(Math.toRadians(30)) + v1*Math.cos(Math.toRadians(30)), -u1*Math.cos(Math.toRadians(30)) + v1*Math.sin(Math.toRadians(30)));
				players.get(1).setVelocity(u2*Math.sin(Math.toRadians(30)) + v2*Math.cos(Math.toRadians(30)), -u2*Math.cos(Math.toRadians(30)) + v2*Math.sin(Math.toRadians(30)));
			}
		}
	}*/
	
	//for ObjectManager
	/*public double getAngle() {
		double delX = players.get(0).getX() - players.get(1).getX();
		double delY = players.get(0).getY() - players.get(1).getY();
		double thetaR = Math.atan2(delX, delY);
		return thetaR*360/(2*Math.PI);
	}*/
}
