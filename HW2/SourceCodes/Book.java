package ayhw2InventoryApp;
import java.util.Properties;
/*
 * Class Book represents books in inventory
 * Extends InventoryItem and implements InventoryMethods method retrieveItem
 */
public class Book extends InventoryItem{
	
	//constructor 
public Book (Properties props, String nameOfDatabaseFile, String headerForDatabase){
	super (props, nameOfDatabaseFile, headerForDatabase);
	}
	
@Override  
public String retrieveItem(String retrieveString){
	loadPropTable();
	String retrieveItem;
	Object value = getPropTable().getProperty(retrieveString);
	if (value != null)
		retrieveItem=("The title, author, last 4 digits of ISBN, and price of the book, " + retrieveString 
					+ ": "+value+"\n");
	else
		retrieveItem=("The book " + retrieveString + " is not in inventory\n");
	return retrieveItem;
	}		
}