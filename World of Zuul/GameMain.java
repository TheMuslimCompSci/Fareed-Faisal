/**
 * This class is part of the "Kino Der Toten" application. 
 * "Kino Der Toten" is a very simple, text based adventure game. 
 * 
 * This class defines a main method within it to play the game.
 *
 * @author Fareed Faisal
 * @version 2.0
 */
public class GameMain
{
    /**
     * The starting point for the zuul game.
     * @param args Program arguments.
     */
    public static void main(String[] args)
    {
        // if the length of the array is one,
        // assume this argument is the player's name
        // and print a welcome statement
        if(args.length == 1) {
            System.out.println("Hello there: " + args[0]);
        }
        Game game = new Game(); // create a Game object
        game.play(); // call the play method in the Game class
    }
}