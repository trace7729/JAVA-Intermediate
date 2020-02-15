import java.util.Properties;
/*
 * Class DVD represents DVDs in inventory
 * Extends InventoryItem and implements InventoryMethods method retrieveItem
 */
public class DVD extends InventoryItem{

public DVD (Properties propTable, String nameDataFile, String headerDataFile){
	super (propTable, nameDataFile, headerDataFile);
	}
	
	@Override 
public void retrieveItem(String retrieveString){
	loadPropTable();
	Object value = getPropTable().getProperty(retrieveString);
	if (value != null)
		System.out.printf("The title, year, rating, studio name and price of the dvd, '" + retrieveString 
					+ "' is %s\n", value + "\n");
	else
		System.out.println("The dvd " + retrieveString + " is not in inventory\n");
	}
}