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
public void retrieveItem(String retrieveString){
	loadPropTable();
	Object value = getPropTable().getProperty(retrieveString);
	if (value != null)
			System.out.printf("The album title, artist, year, production, price of the CD, '" + retrieveString 
						+ "' is %s\n", value + "\n");
	else
			System.out.println("The cd " + retrieveString + " is not in inventory\n");
		}
}