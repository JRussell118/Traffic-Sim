/*Car.java
 *12/10/2023
 *Jaden Russell
 *This file creates a Car class that creates a runnable car component that moves through the sim. 
  The car stops for red traffic lights and moves through yellow and green lights. The class has variables to 
  hold the color, x position, velocity, the closest traffic light, and all of the lights in the sim.
  The class also includes methods to start, stop, and run the car, get the car's current velocity, set the
  nearest traffic light and loop around the sim when the car reaches the edge.
 */
package project.pkg3.jaden.russell;

import java.awt.Component;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

//enumeration to define the car's color
enum CarColor {
    BLACK, GRAY, BLUE, CYAN, MAGENTA;

    public static CarColor getRandom(Random rand) {
        int i = rand.nextInt(values().length);
        return values()[i];
    }
}

public class Car extends Component implements Runnable {

    private CarColor c; // holds the current car color 
    private double x;  //Holds the x position of the car
    private double velocity; //Holds the velocity of the car
    private boolean stop = false; // set to true to stop the simulation 
    private Light near; //Traffic light closest to car to signal the car to stop
    private ArrayList<Light> lights; //All of the traffic lights in the sim
    private int textY; //y position of the car's drawn text
    private Random rand = new Random(); //random value holder

    public Car() {
        c = CarColor.BLACK;
        x = 0;
        velocity = 0;
        near = null;
    }

    public Car(CarColor color, double xPos, double v, ArrayList<Light> l) {
        c = color;
        x = xPos;
        velocity = v;
        lights = l;
        near = null;
        textY = rand.nextInt(220, 300);
    }

    //gets the car's nearest light, then adds the velocity to x to move the car.
    public void run() {
        while (!stop) {
            try {
                near = setNearest(lights);
                x += getVelocity() * 0.1;
                loop();
                this.repaint();
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Return the car's color. 
    synchronized CarColor getColor() {
        return c;
    }

    // Return the car's velocity. 
    synchronized double getVelocity() {
        //if the closest light is null or not red, the car moves
        if (near == null || near.getColor() != TrafficLightColor.RED) {
            return velocity;
        }

        return 0;
    }

    synchronized void setLights(ArrayList<Light> l) {
        lights = l;
    }

    //returns the current x position
    public synchronized double getXPos() {
        return x;
    }

    //Sets the light closest to the car
    synchronized Light setNearest(ArrayList<Light> lList) {
        for (Light light : lList) {
            double diff = light.getXPos() - this.getXPos();
            if (diff < 5 && diff > -1) {
                return light;
            }
        }
        return null;
    }

    //loops around the sim if the car reaches the end of the frame
    synchronized void loop() {
        if (x >= 1000) {
            x = 0;
        }
    }

    // Stop the car. 
    synchronized void cancel() {
        stop = true;
        x = 0;
    }

    //pause the car
    synchronized void pause() {
        stop = true;
    }

    //Starts the car.
    synchronized void start() {
        stop = false;
    }

    //draws a rectangle to act as a car, with a string to show the position and velocity of the car
    //as it moves
    @Override
    public void paint(Graphics g) {
        Color c;
        switch (this.getColor()) {
            case BLACK:
                c = Color.BLACK;
                break;
            case GRAY:
                c = Color.GRAY;
                break;
            case MAGENTA:
                c = Color.MAGENTA;
                break;
            default:
                c = Color.BLUE;
                break;
        }
        g.setColor(c);
        g.fillRect((int) x, 190, 20, 20);

        String desc = "(%.0f, 0) %.2f km/h".formatted(getXPos(), getVelocity());
        g.setColor(Color.black);
        g.drawString(desc, (int) x, textY);
    }
}
