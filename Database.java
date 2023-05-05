import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Database {
     
     public static  ArrayList<Food> foodsList = new ArrayList<Food>();
     public static ArrayList<Creature> creaturesList = new ArrayList<Creature>();

     // Parameters
          // Simulation 
<<<<<<< Updated upstream
          public final static int generationSize = 1; // How many creatures should there be at the start of a new generation
          public final static int amountOfFood = 100; // how many foods should be initially created
          public final static int amountOfWater = 100; // how many Waters should be initially created
=======
          public final static int generationSize = 1000; // How many creatures should there be at the start of a new generation
          public final static int amountOfFood = 1000; // how many foods should be initially created
>>>>>>> Stashed changes
          
          public final static int generationLength = 31; // How many ticks each generation is
          public final static int simulationLength = 1; // How many generations there should be
          public final static int worldSize = 128; // The size of the square world
          public final static double mutationChance = 0.5; // The chance of mutation, must be between 0 and 1 (inclusive)
          public final static double bitMutationAverage = (1.08665/Math.pow(mutationChance,0.531384)-0.0435476);
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
          public static int brainScreenSizeY = Genome.genomeLength*2;

          // Visuals
          public final static Color simulationScreenColor = Color.black;

          // Random
          public final static Random random = new Random();
          // public final static int hungerCounter = 30;
     
     
}