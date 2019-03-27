import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Kino Der Toten" application. 
 * "Kino Der Toten" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Fareed Faisal
 * @version 2.0
 */

public class Room 
{
    // declaration of all the fields:
    private String description; // stores the description of a room 
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items = new ArrayList<>(); // stores items of a room.
    private ArrayList<Character> characters = new ArrayList<>(); // stores characters of a room.
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        // initialisation of all the fields:
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are here: Lobby.
     *     Exits: north west
     *     Item: Olympia M14
     *     Characters in the room: Monkey Bomb
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        // append the exit, item and character strings returned by
        // their respective methods to the room description.
        return "You are here: " + description + ".\n" + getExitString() + 
        ".\n" + getRoomItems() + ".\n" + getRoomCharacters();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return returnString Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Return a string describing the room's items, for example
     * Items: Olympia M14".
     * @return output Details of the room's items.
     */
    public String getRoomItems() 
    {
        String output = "Items in the room: ";
        // iterate through the ArrayList of items
        // to search for the items in a room.
        for (int i = 0; i < items.size(); i++) {
            output += items.get(i).getDescription() + " ";
        }
        return output;
    }
    
    /**
     * Return a string describing the room's character, for example
     * Characters: Monkey Bomb Mystery Box".
     * @return output Details of the room's characters.
     */
    public String getRoomCharacters() 
    {
        String output = "Characters in this room: ";
        // iterate through the ArrayList of characters
        // to search for the characters in a room.
        for (int i = 0; i < characters.size(); i++) {
            output += characters.get(i).getCharacterName() + " ";
        }
        return output;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Get items from the room via index
     * Alternative method to accessing items by name.
     * @param index of the items ArrayList.
     * @return The item at the given index.
     */
    public Item getItem(int index)
    {
        return items.get(index);
    }
    
    /**
     * Get items from the room via name. 
     * Alternative method to accessing items by index.
     * @param itemName the name of the item.
     * @return The item at the index of the given name or null if the name is not found.
     */
    public Item getItem(String itemName)
    {
        // iterate through the ArrayList of items
        // to search for the given item name.
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                return items.get(i);   
            }
        }  
        return null;
    }
    
    /**
     * Get characters from the room via name. 
     * @param itemName the name of the item.
     * @return The item at the index of the given name or null if the name is not found.
     */
    public Character getCharacter(String characterName)
    {
        // iterate through the ArrayList of characters
        // to search for the given character name.
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getCharacterName().equals(characterName)) {
                return characters.get(i);   
            }
        }  
        return null;
    }
    
    /**
     * Remove a given item from the room.
     * 
     * @param itemName item to be removed.
     */
    public void removeItem(String itemName)
    {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                items.remove(i);   
            }
        }
    }
    
    /**
     * Remove a given character from the room.
     * 
     * @param the character to be removed.
     */
    public void removeCharacter(String characterName)
    {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getCharacterName().equals(characterName)) {
                characters.remove(i);   
            }
        }
    }
    
    /**
     * Set a given item in the room.
     * 
     * @param newItem the item to be added.
     */
    public void setItem(Item newItem) 
    {
        items.add(newItem);
    }
    
    /**
     * Set a given character in the room.
     * 
     * @param newCharacter the character to be added.
     */
    public void setCharacter(Character newCharacter) 
    {
        characters.add(newCharacter);
        newCharacter.setRoom(this); // add a character to the given room.
    }
}

