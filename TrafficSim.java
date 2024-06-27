/*TrafficSim.java
 *12/10/2023
 *Jaden Russell
 *This file creates a runnable TrafficSim class that creates an array of traffic lights and cars to paint onto a Jpanel.
  The Traffic sim creates three lights and cars by default, with methods to add more lights and cars to the 
  lists. The TrafficSim's run method continuously draws the sim to show the lights change and cars move through the
  panel. The TrafficSim also has start, stop, getCars, and getLights to return the lists of ights and cars, and start 
  or cancel the TrafficSim.
 */
package project.pkg3.jaden.russell;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class TrafficSim extends JPanel implements Runnable {

    private ArrayList<Light> lights = new ArrayList<>(); //All of the traffic lights in the sim
    private ArrayList<Car> cars = new ArrayList<>(); //All of the cars in the sim
    private Random rand = new Random(); //Random value holder
    private boolean stop = false; // set to true to stop the simulation 

    //Default constructor adds three cars and three lights to the arraylists
    public TrafficSim() {
        lights.add(new Light(TrafficLightColor.GREEN, 100));
        lights.add(new Light(TrafficLightColor.RED, 200));
        lights.add(new Light(TrafficLightColor.YELLOW, 300));
        cars.add(new Car(CarColor.BLUE, 0, 10, lights));
        cars.add(new Car(CarColor.GRAY, 100, 17.21, lights));
        cars.add(new Car(CarColor.BLACK, 200, 7.45, lights));
    }

    public void run() {
        while (!stop) {
            this.repaint();
        }
    }

    public void addLight() {
        int newX = lights.get(lights.size() - 1).getXPos() + 100;
        lights.add(new Light(TrafficLightColor.getRandom(rand), newX));
        for (Car c : cars) {
            c.setLights(lights);
        }
        this.repaint();
    }

    public void addCar() {
        cars.add(new Car(CarColor.getRandom(rand), rand.nextDouble(0, 300), rand.nextDouble(5, 30), lights));
        this.repaint();
    }

    synchronized ArrayList<Light> getLights() {
        return lights;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    // Stop the sim. 
    synchronized void cancel() {
        stop = true;
    }

    //Starts the sim.
    synchronized void start() {
        stop = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Light l : lights) {
            l.paint(g);
        }

        for (Car c : cars) {
            c.paint(g);
        }
    }
}
