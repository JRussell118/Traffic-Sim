/*Light.java
 *12/10/2023
 *Jaden Russell
 *This file creates a Light class that creates a runnable traffic light component that changes
  the color of the traffic light after a certain amount of time. The class has variables to hold the color
  of the light, x position, and a boolean to check if the light has stopped. The class also includes methods
  to run the traffic light, change the color of the light, get the current color, stop the light,
  and paint the light as a component.
 */
package project.pkg3.jaden.russell;

import java.awt.*;
import java.util.Random;

//enumeration to define the traffic light's color
enum TrafficLightColor {
    RED, GREEN, YELLOW;
    
    public static TrafficLightColor getRandom(Random rand) {
            int i = rand.nextInt(values().length);
            return values()[i];
        }
}

// A computerized traffic light. 
class Light extends Component implements Runnable {

    private TrafficLightColor tlc; // holds the current traffic light color 
    private boolean stop = false; // set to true to stop the simulation 
    private final int x;  //Holds the x position of the light

    Light(TrafficLightColor init, int xPos) {
        tlc = init;
        x = xPos;
    }

    Light() {
        tlc = TrafficLightColor.RED;
        x = 0;
    }

    //pauses the light for a certain time when the light is a certain color 
    @Override
    public void run() {
        while (!stop) {
            try {
                switch (tlc) {
                    case GREEN:
                        Thread.sleep(10000); // green for 10 seconds 
                        break;
                    case YELLOW:
                        Thread.sleep(2000);  // yellow for 2 seconds 
                        break;
                    case RED:
                        Thread.sleep(12000); // red for 12 seconds 
                        break;
                }
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
            changeColor();
        }
    }

    // redraws and defines the light's tlc when changed. 
    synchronized void changeColor() {
        switch (tlc) {
            case RED:
                tlc = TrafficLightColor.GREEN;
                this.repaint();
                break;
            case YELLOW:
                tlc = TrafficLightColor.RED;
                this.repaint();
                break;
            case GREEN:
                tlc = TrafficLightColor.YELLOW;
                this.repaint();
        }
    }

    // Return current color. 
    synchronized TrafficLightColor getColor() {
        return tlc;
    }

    //returns the current x position
    public synchronized int getXPos() {
        return x;
    }
    
    // Stop the traffic light. 
    synchronized void cancel() {
        stop = true;
    }
    
    //Starts the traffic light.
    synchronized void start() {
        stop = false;
    }
    
    //draws a circle to depict the traffic light, and sets the color to the current tlc
    @Override
    public void paint(Graphics g) {
        Color c;
        switch (this.getColor()) {
            case RED:
                c = Color.RED;
                break;
            case YELLOW:
                c = Color.YELLOW;
                break;
            default:
                c = Color.GREEN;
                break;
        }
        g.setColor(c);
        g.fillOval(x, 170, 20, 20);
    }
}
