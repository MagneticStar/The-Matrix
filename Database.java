import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;

public class Database implements Cloneable, Serializable{
     
     
     // Parameters
          // Simulation 
          public int generationSize = 100; // How many creatures should there be at the start of a new generation
          public int startingFoodCount = 20; // how many foods should be initially created
          public int minimumFoodEaten = 1; // The minimum number of food a creature must eat to reproduce at the end of a generation
          
          public int generationLength = 100; // How many ticks each generation is
          public int simulationLength = 100; // How many generations there should be

          public int worldSize = 128; // The size of the square world

          public int repoductionPerCreature = 1; // the amount of creatures a creature makes when reproducing
          public double mutationChance = 0.05; // The chance of mutation, must be between 0 and 1 (inclusive)
          public double bitMutationAverage = (1.08665/Math.pow(mutationChance,0.531384)-0.0435476);
          public int genomeLength = 16;

          // Trackers
          public int currentGenerationTick; // How many ticks have passed this generation
          public int currentGeneration; // How many generations have passed this simulation
          public int currentFoodCount = startingFoodCount;
          public int[][] creatureLocations = new int[worldSize][worldSize];
          public int[][] foodLocations = new int[worldSize][worldSize]; // x,y
          public Color[][][] creatureColorsForAllTicks;
          public int[][][] foodLocationsForAllTicks;
          public Creature[] creaturesList = new Creature[generationSize];
          public int reproducedLastGeneration;
          public int foodEatenLastGeneration;

          public boolean doVisuals = true;
          public boolean startNextGeneration = false;
          public boolean autoStartGeneration = true;
          public boolean generationFinished = false;
          public boolean saveAndExit = false;
     
          // Brain Screen
          public int brainScreenSizeX = 30;
          public int brainScreenSizeY = genomeLength*2;

          // Visuals
          public Color simulationScreenColor = Color.white;
          public JPanel visualPanel = null;

          // Random
          public Random random = new Random();
          
          // Serial
          public String fileName = "x.tmp";
          public boolean LOADFILE = true;

     public Database() {
          
     }
     
     @Override
     public Object clone() throws CloneNotSupportedException {
          return super.clone();  
     }
}