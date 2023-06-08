import java.util.Random;
import java.util.Scanner;

/**
 * Simulation
 */
public class Simulation 
{
    private int[] roadType;
    private double[] roadLength;
    private Vehicle[] vehicles;

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);

        Simulation simulation = new Simulation();
        boolean finished = false;
        while (!finished) {
            simulation.simulate(input);

            System.out.print("Your answer: ");
            int answer = input.nextInt();
            if (answer == 2) {
                finished = true;
            }
        }
        System.out.println("\nBye!");
        input.close();
    }

    //Constructor
    public Simulation() {
        //Empty Constructor
    }

    /**
     * Simulation Method
     * @param input Scanner object
     */
    public void simulate(Scanner input) {

        //Creates road
        System.out.print("Please enter the road length: ");
        int totalRoadLength = input.nextInt();
        createRoad(totalRoadLength);
        
        System.out.println("\nThe following road is generated:");
        for (int i = 0; i < roadType.length; i++) {
            int length = (int) roadLength[i];
            
            String road = "";
            if (roadType[i] == 0) {
                road = "Apshalt";
            }
            else if (roadType[i] == 1) {
                road = "Dirt";
            }
            else if (roadType[i] == 2) {
                road = "Stone";
            }
            
            System.out.printf("|-%s %d-", road, length);
        }
        System.out.println("|\n");

        //Creates vehicles
        System.out.print("Please enter vehicle count: ");
        int numberOfVehicles = input.nextInt();
        createVehicles(numberOfVehicles);

        System.out.println("\nThe following vehicles are generated:");
        for (int i = 0; i < numberOfVehicles; i++) {
            String name = vehicles[i].getName();
            int speed = (int) vehicles[i].getSpeed();
            int fuel = vehicles[i].getFuel();

            System.out.printf("%s - Speed: %d - Fuel: %d\n", name, speed, fuel);
        }

        //Race Loop
        boolean finished = false;
        boolean noFuelLeft = false;
        int turn = 1;
        while (!finished && !noFuelLeft) {
            System.out.println("\nTurn " + turn + ":");

            //Turns
            for (int i = 0; i < numberOfVehicles; i++) {
                String name = vehicles[i].getName();
                int speed = (int) vehicles[i].getSpeed();
                int fuel = vehicles[i].getFuel();
                double position = vehicles[i].getPosition();
                String positionString = String.valueOf(position);
                if (position % 1 == 0) {positionString = String.valueOf((int) position);}
    
                System.out.printf("%s - Position: %s - Speed: %d - Fuel: %d\n", name, positionString, speed, fuel);
            }
            
            //Movements
            System.out.println("\nMovements:");
            int counter = 0;
            while (!finished && counter < numberOfVehicles) {
                if (vehicles[counter].getFuel() > 0) {          
                    String name = vehicles[counter].getName();
                    
                    int roadIndex = getType( vehicles[counter].getPosition());
                    String road = "";
                    if (roadIndex == 0) {
                        road = "asphalt";
                    }
                    else if (roadIndex == 1) {
                        road = "dirt";
                    }
                    else if (roadIndex == 2) {
                        road = "stone";
                    }

                    int speed = (int) vehicles[counter].getSpeed();
                    double roadFactor = vehicles[counter].getRoadFactor(roadIndex);
                    String roadFactorString = String.valueOf(roadFactor);
                    if (roadFactor % 1 == 0) {roadFactorString = String.valueOf((int) roadFactor);}
                    double units = speed * roadFactor;
                    String unitsString = String.valueOf(units);
                    if (units % 1 == 0) {unitsString = String.valueOf((int) units);}

                    System.out.printf( "%s moves from %s, for %d * %s = %s units\n", 
                            name, road, speed, roadFactorString, unitsString);

                    //Moves the vehicles
                    vehicles[counter].move(roadIndex);

                    if (vehicles[counter].getPosition() >= totalRoadLength) {
                        finished = true;
                    }
                    counter ++;
                }
                else {
                    String name = vehicles[counter].getName();
                    System.out.println( name + " cannot move! Out of fuel!");
                    counter ++;
                }
            }

            noFuelLeft = true;
            for (int i = 0; i < numberOfVehicles; i++) {
                if (vehicles[i].getFuel() > 0) {
                    noFuelLeft = false;
                }
            }
            
            turn ++;
        }

        //Finds the winner
        Vehicle winner = vehicles[0];
        for (int i = 1; i < numberOfVehicles; i++) {
            if (winner.getPosition() < vehicles[i].getPosition()) {
                winner = vehicles[i];
            }
        }

        String winnerName = winner.getName();
        double winnerPosition = winner.getPosition();
        String winnerPositionString = String.valueOf(winnerPosition);
        int winnerSpeed = (int) winner.getSpeed();
        int winnerFuel = winner.getFuel();
        if (winnerPosition % 1 == 0) {winnerPositionString = String.valueOf((int) winnerPosition);}

        if (noFuelLeft) {
            System.out.printf("\n%s could not finish the race but won by going the furthest! " +
                    "Position: %s - Speed: %d - Fuel: %d\n", winnerName, winnerPositionString, winnerSpeed, winnerFuel);
        }
        else {
            System.out.printf("\n%s finishes the race! " + 
                    "Position: %d - Speed: %d - Fuel: %d\n", winnerName, totalRoadLength, winnerSpeed, winnerFuel);
        }

        System.out.println("\nEnd of simulation. Do you want to play again?\n1-Yes\n2-No");
    }

    /**
     * @param position the position of the vehicle
     * @return the roadType that the vehicle is on
     */
    public int getType( double position) {
        int intPosition = (int) position;
        int totalRoad = 0;
        int currentRoadType = -1;

        for (int i = 0; i < roadLength.length; i++) {
            if (totalRoad + roadLength[i] > intPosition && intPosition >= totalRoad) {
                currentRoadType = roadType[i];
                totalRoad += roadLength[i];
            }
            else {
                totalRoad += roadLength[i];
            }
        }

        return currentRoadType;
    }

    /**
     * Creates random vehicles
     * @param numberOfVehicles
     */
    public void createVehicles( int numberOfVehicles) {
        vehicles = new Vehicle[numberOfVehicles];
        int numberOfWheeled = 0;
        int numberOfFlying = 0;
        int numberOfQuadruped = 0;
        
        Random random = new Random();
        for (int i = 0; i < numberOfVehicles; i++) {
            int randomNumber = random.nextInt( 100);

            if (randomNumber < 40) {
                numberOfWheeled ++;
                vehicles[i] = new Wheeled( "W" + (numberOfWheeled));
            }
            else if (randomNumber >= 65) {
                numberOfQuadruped ++;
                vehicles[i] = new Quadruped( "Q" + (numberOfQuadruped));
            }
            else {
                numberOfFlying ++;
                vehicles[i] = new Flying( "F" + (numberOfFlying));
            }

        }
    }

    /**
     * Creates random road
     * @param totalRoadLength
     */
    public void createRoad( int totalRoadLength) {
        Random random = new Random();
        final int MAX_NUMBER_OF_PARTS = 10;
        final double BALANCER = 3.0 / 5;
        
        int numberOfParts = random.nextInt( 1, MAX_NUMBER_OF_PARTS + 1);
        if (totalRoadLength < numberOfParts) {
            numberOfParts = random.nextInt( 1, totalRoadLength + 1);
        }
        
        this.roadType = new int[numberOfParts];
        for (int i = 0; i < numberOfParts; i++) {
            int randomRoadType = random.nextInt(3);

            if (i != 0) {
                while (roadType[i-1] == randomRoadType) {
                    randomRoadType = random.nextInt(3);
                }
            }

            roadType[i] = randomRoadType;
        }

        this.roadLength = new double[numberOfParts];
        for (int i = 1; i < numberOfParts + 1; i++) {
            double maxLength = totalRoadLength - (numberOfParts - i);

            if (numberOfParts == i) {
                roadLength[i-1] = maxLength;
            }
            else {
                roadLength[i-1] = random.nextInt( 1, (int) (maxLength * BALANCER + 1.5));
                totalRoadLength -= roadLength[i-1];
            }
        }
    }
}