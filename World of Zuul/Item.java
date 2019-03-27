
/**
 * Class Item - an item in an adventure game.
 * 
 * This class is part of the "Kino Der Toten" application. 
 * "Kino Der Toten" is a very simple, text based adventure game.  
 *
 * An "Item" represents one item in the scenery of the game.  It is 
 * It is placed in a room.  For each item, there is a cost to pick up 
 * the item.
 *
 * @author Fareed Faisal
 * @version 2.0
 */

public class Item
{
    // declaration of all the fields:
    private String description; // the description of the item
    private int cost;           // the cost of the item

    /**
     * Create an item described "itemDescription". It has a cost.
     * "itemDescription" is something like "Olympia" or "M14".
     * @param itemdDescription The item's description.
     * @param itemCost The item's cost.
     */
    public Item(String itemDescription, int itemCost)
    {
        // initialisation of all the fields:
        description = itemDescription;
        cost = itemCost;
    }

    /**
     * get the description of the item.
     *
     * @return description the item description.
     */
    public String getDescription() 
    {
        return description;
    }
    
    /**
     * get the cost of the item.
     *
     * @return cost the item cost.
     */
    public int getCost() 
    {
        return cost;
    }
}
