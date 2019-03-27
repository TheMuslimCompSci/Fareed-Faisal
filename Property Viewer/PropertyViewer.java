import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed. 
 *  
 * 
 * @author Michael Kölling and Josh Murphy
 * @version 1.0
 * 
 * 
 * PropertyViewer is a class that implements the logic of the property viewer
 * 
 * 
 * This class is instantiated to start and run the application
 */
public class PropertyViewer
{    
    // declaration of all the fields:
    private PropertyViewerGUI gui;                     // the Graphical User Interface
    private Portfolio portfolio;                       // the collection of properties
    
    private int index;                                 // numbering of the properties in the collection
    private int numberOfPropertiesViewed;              // counter for each property viewed
    private int totalPriceOfPropertiesViewed;          // counter for the sum of each property viewed
    
    public static final int FIRST_PROPERTY_VIEW = 1;   // constant to count the view of the first property automatically displayed
    public static final int FIRST_PROPERTY_PRICE = 23; // constant for the price of the first property as it is automatically displayed
    
    /**
     * Create a PropertyViewer and display its GUI on screen.
     * 
     * Constructor for objects of class PropertyViewer
     */
    public PropertyViewer()
    {
      // initialisation of all the fields:
      int index = 0;                        // set index to 0 for the first property in the portfolio          
      int numberOfPropertiesViewed = 0;     // set view counter to a starting value of  0        
      int totalPriceOfPropertiesViewed = 0; // set price counter to a starting value of  0
        
      gui = new PropertyViewerGUI(this);              // instantiate the object gui of class PropertyViewerGUI
      portfolio = new Portfolio("airbnb-london.csv"); // instantiate the object portfolio of class Portfolio
        
      // automatically displays the ﬁrst property in the portfolio at index 0 by calling the showProperty method in the GUI class 
      gui.showProperty(portfolio.getProperty(0));
        
      // displays the ID of the first property near the top of the window via the showID method in the GUI class
      gui.showID(portfolio.getProperty(0));           
        
      // shows if the first property has been marked as a favourite at the bottom of the window via the showFavourite method
      gui.showFavourite(portfolio.getProperty(0));
    }

    /*
     * ---- public view functions ----
     * 
     * ----- methods for base tasks -----
     */ 
    
    /**
     * Displays the next property in the portfolio and it's data when the Next button is pressed
     */
    public void nextProperty()
    {
      index = index + 1;                               // increment the index to show the next property in the portfolio
        
      // displays the updated data of each property
      gui.showProperty(portfolio.getProperty(index));  // shows the property at the given index in the portfolio
      gui.showID(portfolio.getProperty(index));        // shows the ID of the property at the top of the window 
      gui.showFavourite(portfolio.getProperty(index)); // shows whether the property is a favourite at the bottom of the window
        
      // increment the counter for the number of properties viewed
      numberOfPropertiesViewed = numberOfPropertiesViewed + 1;
      // increment the counter for the sum of the prices of properties viewed 
      totalPriceOfPropertiesViewed = totalPriceOfPropertiesViewed + portfolio.getProperty(index).getPrice();
    }

    /**
     * Displays the previous property in the portfolio and it's data when the Previous button is pressed
     */
    public void previousProperty()
    {
      index = index - 1;                               // decrement the index to show the previous property in the portfolio
        
      // displays the updated data of each property
      gui.showProperty(portfolio.getProperty(index));  // shows the property at the given index in the portfolio
      gui.showID(portfolio.getProperty(index));        // shows the ID of the property at the top of the window  
      gui.showFavourite(portfolio.getProperty(index)); // shows whether the property is a favourite at the bottom of the window
        
      // increment the counter for the number of properties viewed
      numberOfPropertiesViewed = numberOfPropertiesViewed + 1;
      // increment the counter for the sum of the prices of properties viewed 
      totalPriceOfPropertiesViewed = totalPriceOfPropertiesViewed + portfolio.getProperty(index).getPrice();
    }

    /**
     * Updates and shows the isFavourite ﬁeld of a property when the Toggle Favourite button is pressed
     */
    public void toggleFavourite()
    {
      portfolio.getProperty(index).toggleFavourite();  // calls the toggleFavourite method in the Property class to update the field 
      gui.showFavourite(portfolio.getProperty(index)); // shows the updated isFavourite field on the bar at the bottom of the window
    }
    

    //----- methods for challenge tasks -----
    
    /**
     * This method opens the system's default internet browser
     * The Google maps page should show the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
      // set latitude to the value returned from the getLatitude method in the Property class 
      double latitude = portfolio.getProperty(index).getLatitude();   
       
      // set longitude to the value returned from the getLongitude method in the Property class
      double longitude = portfolio.getProperty(index).getLongitude();
       
      // displays the location of a property on the map given the latitude and longitude values
      URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
      java.awt.Desktop.getDesktop().browse(uri); 
    }
    
    /**
     * Returns the number of properties that have been viewed so far since the application was started.
     */
    public int getNumberOfPropertiesViewed()
    {    
      return numberOfPropertiesViewed + FIRST_PROPERTY_VIEW; // return the grand total of properties viewed
    }
    
    /**
     * Returns the average price of the properties viewed so far since the application was started. 
     */
    public int averagePropertyPrice()
    {
      int averagePropertyPrice;                                                          // variable for storing the calculated average
      int grandTotalPropertyPrice = totalPriceOfPropertiesViewed + FIRST_PROPERTY_PRICE; // grand total for prices of properties viewed
      int grandTotalPropertyViews = numberOfPropertiesViewed + FIRST_PROPERTY_VIEW;      // grand total for number of properties viewed
        
      averagePropertyPrice = grandTotalPropertyPrice / grandTotalPropertyViews; // calculate the average price of properites viewed
      return averagePropertyPrice;                                              // return the average (mean) price of properties viewed
    }
}
