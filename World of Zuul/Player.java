
/**
 * Class Player - the player in an adventure game.
 * 
 * This class is part of the "Kino Der Toten" application. 
 * "Kino Der Toten" is a very simple, text based adventure game.  
 *
 * An "Player" represents the player playing the game.
 *
 * @author Fareed Faisal
 * @version 2.0
 */
public class Player
{
    // declaration of all the fields:
    private int numberOfCommandsLeft; // the number of commands the player has
    private int points;               // the points the player has
    private Room currentRoom;         // the room the player is in 
    private String name;              // the name of the player
    
    /**
     * Create a player named "playerName". The player
     * has a limited number of commands and points to complete
     * the game. "playerName" is something like "Player1".
     * @param playerName The player's name.
     */
    public Player(String playerName)
    {
        // initialisations of all the fields:
        numberOfCommandsLeft = 21;
        points = 10000;
        playerName = name;
    }

    /**
     * get the name of the player.
     *
     * @return name the player name.
     */
    public String getName () 
    {
        return name;
    }
    
    /**
     * get the points the player has.
     *
     * @return points the player's points.
     */
    public int getPoints() 
    {
        return points;
    }
    
    /**
     * get the room the player is in.
     *
     * @return currentRoom the current room the player is in.
     */
    public Room getCurrentRoom () 
    {
        return currentRoom;
    }
    
    /**
     * get the number of commands the player has left.
     *
     * @return numberOfCommandsLeft the player's remaining commands.
     */
    public int getNumberOfCommandsLeft() 
    {
        return numberOfCommandsLeft;
    }
    
    /**
     * decrement the number of commands the player has left.
     */
    public void decrementNumberOfCommandsLeft() 
    {
        numberOfCommandsLeft = numberOfCommandsLeft - 1;
    }
    
    /**
     * Moves the player in a new room
     * and decrements the number of commands
     * remaining by one.
     *
     * @param  room  the room the player moves to.
     */
    public void enterRoom(Room room)
    {
        // put your code here
        decrementNumberOfCommandsLeft();
        currentRoom = room;
    }
}
