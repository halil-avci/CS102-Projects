import java.util.Random;

/**
 * Wheeled
 */
public class Wheeled extends Vehicle 
{
    private final double ASPHALT_FACTOR = 1.0;
    private final double DIRT_FACTOR = 0.75;
    private final double STONE_FACTOR = 0.75;

    public Wheeled( String vehicleName) {
        super( vehicleName);
        
        Random random = new Random();
        setSpeed( random.nextInt( 15, 26));
        setFuel( random.nextInt( 30, 41));
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
        else if (roadType == 2) {
            return STONE_FACTOR;
        }
        else {
            return 0;
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