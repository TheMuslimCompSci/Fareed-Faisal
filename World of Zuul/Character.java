
/**
 * Class Character - a character in an adventure game.
 * 
 * This class is part of the "Kino Der Toten" application. 
 * "Kino Der Toten" is a very simple, text based adventure game.  
 *
 * A "Character" represents one character in the scenery of the game.  The 
 * character is placed in a room.  For each command entered by the user, 
 * the character moves by itself to the neighboring room.
 *
 * @author Fareed Faisal
 * @version 2.0
 */

public class Character
{
    // declaration of all the fields:
    private String name;        // the name of the character
    private int requiredPoints;
    private Room currentRoom;   // the room the character is currently in
    
    /**
     * Create a character named "characterName".
     * "characterName" is something like "Monkey Bomb" or "Mystery Box".
     * @param characterName The character's name.
     */
    public Character(String characterName, int requiredPoints)
    {
        // initialisation of all the fields:
        name = characterName;
        this.requiredPoints = requiredPoints;
    }

    /**
     * get the current room the character is in.
     *
     * @return currentRoom the room the character is in.
     */
    public Room getRoom()
    {
        return currentRoom;
    }
    
    /**
     * get the name of the character.
     *
     * @return name the character name.
     */
    public String getCharacterName()
    {
        return name;
    }
    
    /**
     * move the character to another room.
     *
     * @param newRoom the new room the character is moved to.
     */
    public void setRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }
    
    public int getRequiredPoints()
    {
        return requiredPoints;
    }
}
