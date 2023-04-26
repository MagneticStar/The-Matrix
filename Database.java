import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Database {
     
     public static  ArrayList<Food> foodsList = new ArrayList<Food>();
     public static  ArrayList<Water> watersList = new ArrayList<Water>();
     public static ArrayList<Creature> creaturesList = new ArrayList<Creature>();

     // Params
     public final static int creatureCount = 1000;
     public final static int generationLength = 21;
     public static int currentGenerationStep = 0;
     public final static int worldSize = 100;
     public final static int brainScreenSizeX = 30;
     public final static int brainScreenSizeY = 500;
     public final static Color simulationScreenColor = Color.black;
     public final static Random random = new Random();
     public final static int hungerCounter = 20;
     
}