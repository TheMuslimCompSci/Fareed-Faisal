
import java.util.ArrayList;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;

/**
 *  This class is the main class of the "Kino Der Toten" application. 
 *  "Kino Der Toten" is a very simple, text based adventure game.  
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method or call the main method directly from the GameMain class.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 *  This class implements the logic of the game.
 * 
 * @author  Fareed Faisal
 * @version 2.0
 */

public class Game 
{
    private Parser parser; // parser object to read and interpret user input
    
    // the various rooms of the map as well as the
    // starting, charge, current and previous rooms
    // the player is in/has been
    private Room startingRoom;
    private Room chargeRoom;
    private Room currentRoom;
    private Room previousRoom;
    private Room lobby, lowerHall, alley, backRoom, upperHall, foyer, dressingRoom, theater, stage, teleporter, projectorRoom;
    
    // the collections of the: 
    private ArrayList<Room> characterRoomList = new ArrayList<>(); // the rooms the characters can be in.
    private ArrayList<Room> randomRoomList = new ArrayList<>(); // the rooms the random generator can select from.
    private ArrayList<Item> inventory = new ArrayList<>(); // the items the player has in his inventory.
    private ArrayList<Character> charactersList = new ArrayList<>(); // the characters in the game.
    
    private Scanner userInput; // the user input
    private Player player; // the player playing the game
    private Character character; // the character in the game
    private int numberOfPointsLeft; // the points the player has left
    private Random randomiser = new Random(); // the random room generator
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms(); // set up all the rooms in the game
        createItems(); // set up all the items in the game
        createCharacters(); // set up all the characters in the game
        parser = new Parser();
        userInput = new Scanner(System.in);
        player = new Player("Player1");
        player.enterRoom(startingRoom); // enter the Lobby at the start of the game
        numberOfPointsLeft = player.getPoints(); // get the player's points
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
         // create the rooms
         lobby = new Room("Lobby (starting room)");
         lowerHall = new Room("Lower Hall");
         alley = new Room("Alley");
         backRoom = new Room("Back Room");
         upperHall = new Room("Upper Hall");
         foyer = new Room("Foyer");
         dressingRoom = new Room("Dressing Room");
         theater = new Room("Theater");
         stage = new Room("Stage"); 
         teleporter = new Room("Teleporter");
         projectorRoom = new Room("Projector Room");
         
         // add the specified rooms to the random room list
         Collections.addAll(randomRoomList, lobby, lowerHall, alley, backRoom, 
         upperHall, foyer, dressingRoom, theater, stage, projectorRoom); 
        
         // initialise room exits
         lobby.setExit("east", upperHall);
         lobby.setExit("west", lowerHall);
         lobby.setExit("north", projectorRoom);

         lowerHall.setExit("north", alley);
         lowerHall.setExit("east", lobby);
        
         alley.setExit("north", backRoom);
         alley.setExit("south", lowerHall);
        
         backRoom.setExit("east", stage);
         backRoom.setExit("south", alley);
        
         upperHall.setExit("north", foyer);
         upperHall.setExit("west", lobby);
        
         foyer.setExit("north", dressingRoom);
         foyer.setExit("south", upperHall);
        
         dressingRoom.setExit("west", stage);
         dressingRoom.setExit("south", foyer);
        
         theater.setExit("north", stage);
         theater.setExit("south", teleporter);
        
         stage.setExit("south", theater);
         stage.setExit("east", dressingRoom);
         stage.setExit("west", backRoom);
         
         teleporter.setExit("north", theater);
         teleporter.setExit("south", projectorRoom);
        
         projectorRoom.setExit("north", teleporter);
         projectorRoom.setExit("south", lobby);

         startingRoom = lobby;  // the starting room of the game
         currentRoom = startingRoom; 
         previousRoom = currentRoom;
    }
    
    /**
     * Create all the items and set their rooms.
     * Add the starting items to the player's inventory.
     */
    private void createItems() 
    {
         // the inventory of the player at the start of the game
         Collections.addAll(inventory, new Item("M1911", 0), new Item("Frag-Grenade", 0), new Item("Knife", 0));
        
         // intialise room items
         lobby.setItem(new Item("Olympia", 500));
         lobby.setItem(new Item("M14", 500));
         lobby.setItem(new Item("Quick-Revive", 1500));
        
         lowerHall.setItem(new Item("MPL", 1000));

         alley.setItem(new Item("AK74u", 1200));
         alley.setItem(new Item("Double-Tap-Root-Beer", 2000));
        
         upperHall.setItem(new Item("PM63", 1000));
         upperHall.setItem(new Item("Mule-Kick", 4000));
        
         foyer.setItem(new Item("MP40", 1000));
         foyer.setItem(new Item("Stakeout", 1500));
         foyer.setItem(new Item("Speed-Cola", 3000));
        
         dressingRoom.setItem(new Item("MP5k", 1000));
        
         theater.setItem(new Item("Bowie-Knife", 3000));
         theater.setItem(new Item("Juggernog", 2500));
        
         stage.setItem(new Item("M16", 1200));
         stage.setItem(new Item("Claymore", 1000));
    }
    
    /**
     * Create all the characters and set their rooms.
     * Add the characters to a list and add the 
     * rooms the characters go to in a list. 
     */
    private void createCharacters() 
    {
         //initialise room characters
         backRoom.setCharacter(new Character("Monkey Bomb", 1000));
         dressingRoom.setCharacter(new Character("Mystery Box", 1000));
        
         // add all the specified characters in the list of characters
         Collections.addAll(charactersList, new Character("Monkey Bomb", 1000), new Character("Mystery Box", 1000));
        
         // add the specified rooms to list of rooms the characters can go to
         Collections.addAll(characterRoomList, lobby, lowerHall, alley, backRoom, stage, dressingRoom, foyer, upperHall);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Danke for playing.  Auf Wiedersehen.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Kino Der Toten!");
        System.out.println("Escape the undead in this theatrical installment of Kino Der Toten");
        System.out.println();
        System.out.println("You must complete the game before your commands run out");
        System.out.println("Make sure your points don't run out either or you'll lose!");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("Some words in this game are in German!");
        System.out.println("Type 'translate if you need to translate them");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        // some commands are in German for the user to translate
        // before knowing what they do
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            if(!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Go where? (type 'back' to go back to the previous room)");
            }
            else if (command.getSecondWord().equals("back")) {
                // return to the previously visited room
                goBack();
            }
            else {
                wantToQuit = goRoom(command);
            }
            // move the charactes everytime the player moves
            updateCharacterRooms();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("inventar")) {
            printInventory();
        }
        else if (commandWord.equals("get")) {
            getItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("look")) {
            printLook();
        }
        else if (commandWord.equals("translate")) {
            try {
                viewTranslation(command);
            }
            catch (Exception e) {
                System.out.println("INVALID URL");
            }
        }
        else if (commandWord.equals("pay")) {
            //payCharacter(command);
        }
        else if (commandWord.equals("karte")) {
            try {
                viewMap(command);
            }
            catch (Exception e) {
                System.out.println("INVALID URL");
            } 
        }
        else if (commandWord.equals("fire")) {
            fireBeamer();
        }
        else if (commandWord.equals("charge")) {
            chargeBeamer();
        }
        
        // decrement the player's commmands remaining
        int commandsLeft = showNumberOfCommandsLeft();
        
        // the player loses if they run out of commands
        // or they run out of points
        if ((commandsLeft <= -1) || (numberOfPointsLeft <= 0)) {
            System.out.println("Nein! You failed. The undead will eat you now...");
            wantToQuit = true;
        }
        
        // else command not recognised.
        return wantToQuit;
    }
    
    /**
     * This method moves the characters around the map
     * depending on the character. One character moves
     * around the map in a clockwise direction and the
     * other in an anti clockwise direction.
     */
    private void updateCharacterRooms() 
    {
        for (int i = 0; i < characterRoomList.size(); i++) {
            // if the specified character exists in a room in the character room list
            // remove them from their current room and add them to the next room
            if (characterRoomList.get(i).getCharacter("Monkey Bomb") != null) {
                Character characterWeWantToAdd = characterRoomList.get(i).getCharacter("Monkey Bomb");
                characterRoomList.get(i).removeCharacter("Monkey Bomb");
                // make sure the index is within bounds of the character room list
                characterRoomList.get((i + 1)%(characterRoomList.size())).setCharacter(characterWeWantToAdd);
            }
            if (characterRoomList.get(i).getCharacter("Mystery Box") != null) {
                Character characterWeWantToAdd = characterRoomList.get(i).getCharacter("Mystery Box");
                characterRoomList.get(i).removeCharacter("Mystery Box");
                // make sure the index is within bounds of the character room list
                characterRoomList.get((i + 1)%(characterRoomList.size())).setCharacter(characterWeWantToAdd);
            }
        }
    }
    
    /**
     * This method moves the player to the room last visited
     * before the player entered the current room.
     */
    private void goBack() 
    {
        // if there is no previous room
        // i.e. the player is at the starting room
        // then the player stays in the same room
        if (previousRoom == null) {
            System.out.println("there is no room to go back to");
        }
        else {
            enterRoom(previousRoom);
        }
    }
    
    /**
     * This method moves the player to the next room
     * after the player leaves the current room
     */
    private void enterRoom (Room nextRoom) 
    {
        // set the previous room as the current room
        // and the current room as the next room
        previousRoom = currentRoom; 
        currentRoom = nextRoom;
        
        // move the player into the next room
        player.enterRoom(currentRoom);
        System.out.println(currentRoom.getLongDescription()); 
    }
    
    /**
     * This method prints out the description of the room 
     * along with it's items, exits and characters.
     */
    private void printLook()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * This method removes the item  
     * the player drops and removes it from 
     * their inventory.
     * 
     * @return command the drop command.
     */
    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        // the item the player want to drop
        String item = command.getSecondWord();
        
        int cost = 0;

        // the item in the current room that is to be dropped
        Item newItem = null;
        
        int index = 0;
        
        // check if the item is in the player's inventory
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getDescription().equals(item)) {
                newItem = inventory.get(i);
                index = i;
            }
        }

        if (newItem == null) {
            System.out.println("That item is not in your inventory!");
        }
        else {
            // remove the item from the player's inventory
            inventory.remove(index);
            currentRoom.setItem(new Item(item, cost));
            System.out.println("Dropped: " + item);
        }
    }
    
    /**
     * This method gets the item  
     * the player wants and adds it to 
     * their inventory.
     * 
     * @return command the get command.
     */
    private void getItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Get what?");
            return;
        }

        // the item player wants to get
        String item = command.getSecondWord();

        // the item in the current room 
        // that is to be picked up
        Item newItem = currentRoom.getItem(item);
        
        // check if the item is in the room
        if (newItem == null) {
            System.out.println("That item is not here!");
        }
        else if (canPickItem (item)) {
            // if them item can be picked up add it to the player's inventory
            // and deduct the player's points by the item's cost
            inventory.add(newItem);
            currentRoom.removeItem(item);
            numberOfPointsLeft = numberOfPointsLeft - newItem.getCost();
            System.out.println("Picked up: " + item);
            System.out.println("Points left: " + numberOfPointsLeft);
        }
        else {
            // if the player doesn't have enough points to add it to their inventory
            System.out.println("Not enough points to pick up");
        }
    }
    
    /**
     * This method checks if the player can get an
     * item by checking the player's points.
     * 
     * @return true if the player can get the item, 
     * false if the item is not there or the
     * player doesn't have enough points.
     */
    private boolean canPickItem (String itemName) 
    {
        boolean canPick = true;
        Item item = currentRoom.getItem(itemName);
        // if the item is not there to pick up
        if (item == null) {
            canPick = false;
        }
        int totalCost = getTotalCost() + item.getCost();
        // if the player doesn't have enough points to get the item
        if (totalCost > player.getPoints()) {
            canPick = false;
        }
        return canPick;
    }   
    
    /**
     * This method gets the number of
     * commands the player has left to 
     * complete the game and decrements
     * it with every command entered.
     * 
     * @return the number of commands left.
     */
    private int showNumberOfCommandsLeft()
    {
        System.out.println("You have " + player.getNumberOfCommandsLeft() + " commands left.");
        player.decrementNumberOfCommandsLeft();
        return player.getNumberOfCommandsLeft();
    }

    /**
     * This method prints
     * all the items in the inventory
     * and their total cost.
     */
    private void printInventory() 
    {
        String output = "";
        // iterate through the inventory
        // and increment the output with
        // the description of each item
        for (int i = 0; i < inventory.size(); i++) {
            output += inventory.get(i).getDescription() + " ";
        }
        System.out.println("You are carrying:");
        System.out.println(output);
        System.out.println("Your items cost: " + getTotalCost());
    }
    
    /**
     * This method gets the total cost of
     * all the items in the inventory
     * 
     * @return itemsCost the total cost of the player's inventory.
     */
    private int getTotalCost() 
    {
        int itemsCost = 0;
        // iterate through the inventory
        // and increment the cost of each
        // item to the total
        for (int i = 0; i < inventory.size(); i++) {
            itemsCost = itemsCost + inventory.get(i).getCost();
        }
        return itemsCost;
    }
        
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You must escape limitless hordes of the undead");
        System.out.println("as you wander through the theater of the damned!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
   
    /**
     * This method generates a random room
     * from the given list of rooms of size 10. 
     * 
     * @return the randomly generated room.
     */
    private Room randomRoomGenerator() 
    {
        return randomRoomList.get(randomiser.nextInt(9));
    }
    
    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * If the player enters the teleporter , they
     * are teleported to a random room.
     * 
     * If the player enters the projector room with the required items,
     * they win.
     * 
     * @param command the go command.
     * @return true if the player is in the winning room with the winning item
     * else, return false.
     */
    private boolean goRoom(Command command) 
    {
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            if (nextRoom.getShortDescription().equals("Teleporter")) {
                System.out.println("Teleporting to random room...");
                // set the player in a randomly generated room
                nextRoom = randomRoomGenerator(); 
                enterRoom(nextRoom);
            }
            else if ((currentRoom.getShortDescription().equals("Projector Room"))) {
                // check the player's inventory for the item needed to win
                for (int i = 0; i < inventory.size(); i++) {
                    if (inventory.get(i).getDescription().equals("Bowie-Knife")) {
                        System.out.println("You win! You escaped the undead!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
     
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * This method opens the system's default internet browser.
     * The web page shows the plan of map of the game.
     * 
     * @param command the map command.
     */
    private void viewMap(Command command) throws Exception
    {
        URI uri = new URI(
        "https://1.bp.blogspot.com/-lr3gDDZbjtc/XAGefEre51I/AAAAAAAAS7Y/1SUBAWXiDZoTpSoJoxm8G8LIH5IAUnuSgCLcBGAs/s1600/Picture1.png");
        java.awt.Desktop.getDesktop().browse(uri); 
    }
    
    /**
     * This method "chargess" the beamer 
     * in the room the player is in.
     */
    private void chargeBeamer()
    {
        // set the charge room as the room the player is in
        System.out.println("Charging beamer... done");
        chargeRoom = currentRoom;
    }
    
    /**
     * This method "fires" the beamer and 
     * teleports the player to the room 
     * where it was charged.
     */
    private void fireBeamer()
    {
        // set the current room the player is in
        // as the room where it was charged
        System.out.println("Firing beamer... done");
        currentRoom = chargeRoom;
        enterRoom(currentRoom);
    }
    
    /**
     * This method opens the system's default internet browser
     * The Google translate page shows the translation of the given word/s from German to English.
     * 
     * @param command the translate command.
     */
    private void viewTranslation(Command command) throws Exception
    {
        String firstTranslateWord = command.getSecondWord();
        String secondTranslateWord = command.getThirdWord();
        if(!command.hasSecondWord())  {
              // if there is no second word, we don't know what to translate...
              System.out.println("Translate what?");
              firstTranslateWord = "";
        }
      
        if(!command.hasThirdWord())  {
              // if there is no third word, the second translate word is an empty string
           secondTranslateWord = "";
        }

        // displays the translation of a given word on Google Translate    
        if (firstTranslateWord != "") {
            URI uri = new URI("https://translate.google.com/#de/en/" + firstTranslateWord + "%20" + secondTranslateWord);
            java.awt.Desktop.getDesktop().browse(uri); 
        }
    }  
} 
