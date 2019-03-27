import java.util.List;
import java.util.Random;

/**
 * A simple model of a plant.
 * Plants age, move, breed, and die.
 * 
 * @author Fareed Faisal and Salim Dridi
 * @version 2016.02.29 (2)
 */
public class Plant extends Organism
{
    // Characteristics shared by all plants (class variables).

    // The age at which a plant can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a plant can live.
    private static final int MAX_AGE = 9;
    // The likelihood of a plant breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The plant's age.
    private int age;
    // The plant's gender.
    private int gender;
    
    /**
     * Create a new plant. A plant may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the plant will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param randomAge If true, the cat will have random gender. 0 for females and 1 for males.
     */
    public Plant(boolean randomAge, Field field, Location location, boolean randomGender)
    {
        super(field, location);
        if (randomGender) {
            setGender(rand.nextInt(2)); //assign each cat a gender
        }
        else {
            gender = 0; //default gender female
        }
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }
    
    /**
     * This is what the plant does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newPlants A list to return newly born plants.
     */
    public void act(List<Organism> newPlants)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newPlants);
        }
    }
    
    /**
    * Create a new organism. An organism may be created with age
    * zero (a new born) or with a random age.
    *
    * @param randomAge If true, the organism will have a random age.
    * @param field The field currently occupied.
    * @param location The location within the field.
    * @param randomGender If true, the cat will have random gender. 0 for females and 1 for males.
    */
    protected Organism createOrganism(boolean randomAge, Field field, Location location, boolean randomGender)
    {
        return new Plant(randomAge, field, location, randomGender);
    }
    
    /**
     * Return the age to which a plant can live.
     * @return The plant's max age.
     */
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the age at which a plant can breed.
     * @return The plant's breeding age.
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the likelihood of a plant breeding.
     * @return The plant's breeding probability.
     */
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum number of births for a plant.
     * @return The plant's maximum number of births.
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
}
