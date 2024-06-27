/*Main.java
 *12/10/2023
 *Jaden Russell
 *This file creates a GUI that creates a simulation of a traffic intersection with buttons that
  allow the user to start, stop, pause, or add more intersections and cars to the simulation
 */
package project.pkg3.jaden.russell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Main {

    //static variable for time's seconds
    static int seconds = 0;

    public static void main(String[] args) {

        //creates a default traffic sim to put into the frame
        TrafficSim sim = new TrafficSim();

        //Creates the frame of the GUI called Traffic Sim with buttons to control the sim
        JFrame f = new JFrame("Traffic Sim");
        JPanel panel = new JPanel();
        JPanel btns = new JPanel();
        JPanel timer = new JPanel();
        JLabel tLabel = new JLabel("Timer:");
        JLabel iLabel = new JLabel("Intersections: " + sim.getLights().size());
        JLabel cLabel = new JLabel("Cars: " + sim.getCars().size());
        JLabel time = new JLabel("" + seconds);

        JButton start = new JButton("Start");
        JButton pause = new JButton("Pause");
        JButton addCar = new JButton("Add Car");
        JButton addLight = new JButton("Add Intersection");

        panel.setLayout(new BorderLayout());
        btns.setLayout(new FlowLayout());
        timer.setLayout(new FlowLayout());

        panel.add(btns, BorderLayout.SOUTH);
        panel.add(timer, BorderLayout.NORTH);
        panel.add(sim, BorderLayout.CENTER);

        //Timer to time the simulation
        Timer t = new Timer(1000, (ActionEvent e) -> {
            seconds++;
            time.setText("" + seconds);
        });

        //adds all components to the panels in the GUI    
        btns.add(start);
        btns.add(pause);
        btns.add(addCar);
        btns.add(addLight);

        timer.add(cLabel);
        timer.add(iLabel);
        timer.add(tLabel);
        timer.add(time);

        f.add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 500);
        f.setVisible(true);

        //When the Start button is pressed
        start.addActionListener((ActionEvent e) -> {

            //Creates two lists of threads for the cars and lights while creating a single thread for the sim
            ArrayList<Thread> lThreads = new ArrayList<>();
            ArrayList<Thread> cThreads = new ArrayList<>();
            Thread simTh = new Thread(sim);

            //Checks the text of the button to change it to a stop button if it is Start, vice versa.
            if (start.getText().contentEquals("Start")) {

                //starts each light in the sims list of lights
                for (Light l : sim.getLights()) {
                    lThreads.add(new Thread(l));
                    l.start();
                }

                //starts each car in the sims list of cars
                for (Car c : sim.getCars()) {
                    lThreads.add(new Thread(c));
                    c.start();
                }

                //starts all threads for the lights and cars
                for (Thread thl : lThreads) {
                    thl.start();
                }
                for (Thread thc : cThreads) {
                    thc.start();
                }

                //starts the sim and timer and creates the stop button
                simTh.start();
                sim.start();
                t.start();
                start.setText("Stop");

            } else {
                //cancels each light in the sims list of lights
                for (Light l : sim.getLights()) {
                    lThreads.add(new Thread(l));
                    l.cancel();
                }

                //cancels each car in the sims list of cars
                for (Car c : sim.getCars()) {
                    lThreads.add(new Thread(c));
                    c.cancel();
                }

                //interrupts all threads for the lights and cars
                for (Thread thl : lThreads) {
                    thl.interrupt();
                }
                for (Thread thc : cThreads) {
                    thc.interrupt();
                }

                //interrupts the sim, cancels the timer and creates the start button
                simTh.interrupt();
                sim.cancel();
                seconds = 0;
                t.stop();
                start.setText("Start");
            }
        });

        //When the Pause button is pressed
        pause.addActionListener((ActionEvent e) -> {

            //Creates two lists of threads for the cars and lights while creating a single thread for the sim
            ArrayList<Thread> lThreads = new ArrayList<>();
            ArrayList<Thread> cThreads = new ArrayList<>();
            Thread simTh = new Thread(sim);
            if (start.getText().contentEquals("Start") && pause.getText().equals("Pause")) {

            } else if (pause.getText().equals("Pause")) {
                //cancels each light in the sims list of lights
                for (Light l : sim.getLights()) {
                    lThreads.add(new Thread(l));
                    l.cancel();
                }

                //cancels each car in the sims list of cars
                for (Car c : sim.getCars()) {
                    lThreads.add(new Thread(c));
                    c.pause();
                }

                //interrupts all threads for the lights and cars
                for (Thread thl : lThreads) {
                    thl.interrupt();
                }
                for (Thread thc : cThreads) {
                    thc.interrupt();
                }

                //interrupts the sim, pauses the timer and creates the continue button
                simTh.interrupt();
                sim.cancel();
                t.stop();
                pause.setText("Continue");

            } else {
                //starts each light in the sims list of lights
                for (Light l : sim.getLights()) {
                    lThreads.add(new Thread(l));
                    l.start();
                }

                //starts each car in the sims list of cars
                for (Car c : sim.getCars()) {
                    lThreads.add(new Thread(c));
                    c.start();
                }

                //starts all threads for the lights and cars
                for (Thread thl : lThreads) {
                    thl.start();
                }
                for (Thread thc : cThreads) {
                    thc.start();
                }

                //starts the sim and timer and creates the pause button
                simTh.start();
                sim.start();
                t.start();
                pause.setText("Pause");
            }
        });

        //When the addCar button is pressed
        addCar.addActionListener((ActionEvent e) -> {
            //Checks if the sim is stopped before adding another car to the sim
            if (start.getText().equals("Start")) {
                sim.addCar();
                cLabel.setText("Cars: " + sim.getCars().size());
            }
        });

        //When the addLight button is pressed
        addLight.addActionListener((ActionEvent e) -> {
            //Checks if the sim is stopped before adding another light to the intersection
            if (start.getText().equals("Start")) {
                sim.addLight();
                iLabel.setText("Intersections: " + sim.getLights().size());
            }
        });

    }

}
