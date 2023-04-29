import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Database {
     
     public static  ArrayList<Food> foodsList = new ArrayList<Food>();
     public static ArrayList<Creature> creaturesList = new ArrayList<Creature>();

     // Parameters
          // Simulation 
          public final static int generationSize = 1000; // How many creatures should there be at the start of a new generation
          public final static int generationLength = 100; // How many ticks each generation is
          public final static int simulationLength = 100; // How many generations there should be
          public final static int worldSize = 128; // The size of the square world
          public final static double mutationChance = 0.001; // The chance of mutation, must be between 0 and 1 (inclusive)
          public final static int minimumFoodEaten = 1; // The minimum number of food a creature must eat to reproduce at the end of a generation
          public final static int startingFoodCount = 100;
          public final static int startingWaterCount = 100;

          // Trackers
          public static int currentGenerationTick; // How many ticks have passed this generation
          public static int currentGeneration; // How many generations have passed this simulation
          public static ArrayList<Coor> creatureCoordinates;
          public static ArrayList<Coor> foodCoordinates;
          public static ArrayList<Coor> waterCoordinates;
     
          // Brain Screen
          public static int brainScreenSizeX = 30;
          public static int brainScreenSizeY = 500;

          // Visuals
          public final static Color simulationScreenColor = Color.white;

          // Random
          public final static Random random = new Random();
}