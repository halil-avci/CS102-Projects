import java.util.Random;

/**
 * Quadruped
 */
public class Quadruped extends Vehicle 
{
    private final double ASPHALT_FACTOR = 0.5;
    private final double DIRT_FACTOR = 1.0;
    private final double STONE_FACTOR = 0.75;

    public Quadruped( String vehicleName) {
        super( vehicleName);
        
        Random random = new Random();
        setSpeed( random.nextInt( 20, 41));
        setFuel( random.nextInt( 10, 21));
    }

    /**
     * Takes roadType integer
     * Returns the road factor
     */
    public double getRoadFactor(int roadType) {
        if (roadType == 0) {
            return ASPHALT_FACTOR;
        }
        else if (roadType == 1) {
            return DIRT_FACTOR;
        }
        else {
            return STONE_FACTOR;
        }
    }

    /**
     * Takes roadType integer
     * Sets the new position of the vehicle
     * Consumes fuel
     */
    @Override
    void move(int roadType) {
        if (getFuel() > 0) {
            if (roadType == 0) {
                setPosition( getPosition() + getSpeed() * ASPHALT_FACTOR );
            }
            else if (roadType == 1) {
                setPosition( getPosition() + getSpeed() * DIRT_FACTOR );
            }
            else if (roadType == 2) {
                setPosition( getPosition() + getSpeed() * STONE_FACTOR );
            }

            consumeFuel();
        }
    }
}