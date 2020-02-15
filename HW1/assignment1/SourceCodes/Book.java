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
public void retrieveItem(String retrieveString){
	loadPropTable();
	Object value = getPropTable().getProperty(retrieveString);
	if (value != null){
		System.out.println("The title, author, last 4 digits of ISBN and price of the book, " + retrieveString 
					+ ":");
		System.out.println("\t"+value+"\n");
	}
	else
		System.out.println("The book " + retrieveString + " is not in inventory\n");
	}		
}