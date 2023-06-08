/**
 * Vehicle
 */
public abstract class Vehicle 
{
    private String name;
    private double speed;
    private double position;
    private int fuel;
    
    //Constructor
    public Vehicle( String name) {
        this.name = name;
        this.position = 0;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPosition() {
        return position;
    }

    public int getFuel() {
        return fuel;
    }

    public void setPosition( double newPosition) {
        position = newPosition;
    }

    public void setSpeed( double speed) {
        this.speed = speed;
    }

    public void setFuel( int fuel) {
        this.fuel = fuel;
    }

    /**
     * Consumes fuel
     * If fuel is zero, speed is zero
     */
    public void consumeFuel() {
        fuel --;
        if (fuel == 0) {
            speed = 0;
        }
    }

    abstract void move( int roadType);

    abstract double getRoadFactor( int roadType);
}