package ayhw2InventoryApp;
import java.util.Properties;
/*
 * Class CD represents CDs in inventory
 * Extends InventoryItem and implements InventoryMethods method retrieveItem
 */
public class CD extends InventoryItem {

	//constructor 
public CD (Properties props, String nameOfDatabaseFile, String headerForDatabase){
	super (props, nameOfDatabaseFile, headerForDatabase);
	}

@Override
public String retrieveItem(String retrieveString){
	loadPropTable();
	String retrieveItem;
	Object value = getPropTable().getProperty(retrieveString);
	if (value != null)
			retrieveItem=("The album title, artist, year, label, and price of the CD, " + retrieveString 
					+ ": "+value+"\n");
	else
			retrieveItem=("The CD " + retrieveString + " is not in inventory\n");
	return retrieveItem;
}
}