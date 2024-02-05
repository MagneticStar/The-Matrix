import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;

public class Database implements Cloneable, Serializable{
     
     
     // Parameters
          // Simulation 
          public int generationSize = 1000; // How many creatures should there be at the start of a new generation
          public int startingFoodCount = 200; // how many foods should be initially created
          public int minimumFoodEaten = 2; // The minimum number of food a creature must eat to reproduce at the end of a generation
          
          public int generationLength = 100; // How many ticks each generation is
          public int simulationLength = 100; // How many generations there should be

          public int worldSize = 128; // The size of the square world

          public int repoductionPerCreature = 1; // the amount of creatures a creature makes when reproducing
          public double mutationChance = 0.05; // The chance of mutation, must be between 0 and 1 (inclusive)
          public double bitMutationAverage = (1.08665/Math.pow(mutationChance,0.531384)-0.0435476);
          public int genomeLength = 8; // length of a creatures DNA, controls the number of neurons

          // Trackers
          public int currentGenerationTick; // How many ticks have passed this generation
          public int currentGeneration; // How many generations have passed this simulation
          public int currentFoodCount = startingFoodCount;
          public int[][] creatureLocations = new int[worldSize][worldSize];
          public int[][] foodLocations = new int[worldSize][worldSize]; // x,y
          public Color[][][] creatureColorsForAllTicks;
          public int[][][] foodLocationsForAllTicks;
          public Creature[] creaturesList = new Creature[generationSize];
          public int reproducedLastGeneration; // How many creatures reproduced last generation
          public int foodEatenLastGeneration; // How many foods were eaten last generation

          public boolean doVisuals = true; // show visuals while runnning simulation or not
          public boolean startNextGeneration = false; // should the program start this generation
          public boolean autoStartGeneration = true; // start each generation at the end of the last
          public boolean generationFinished = false; // is the generation finished
          public boolean saveAndExit = false; // should the program save and exit at the end of this generation
     
          // Brain Screen
          public int brainScreenSizeX = 30;
          public int brainScreenSizeY = genomeLength*3;

          // Visuals
          public Color simulationScreenColor = Color.white; // background color for the simuation
          public JPanel visualPanel = null;
          public boolean showDataFrame = true;
          public boolean showSources = false;
          public boolean showSinks = true;
          // Random
          public Random random = new Random();
          
          // Serial
          public String fileName = "x.tmp"; // filename for loading or saving
          public boolean LOADFILE = false; // whether to load from file or generate new
}