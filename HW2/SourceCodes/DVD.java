package ayhw2InventoryApp;
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
public String retrieveItem(String retrieveString){
	loadPropTable();
	String retrieveItem;
	Object value = getPropTable().getProperty(retrieveString);
	if (value != null)
		retrieveItem=("The title, year, rating, studio, and price of the DVD, " + retrieveString 
					+ ": "+value+"\n");
	else
		retrieveItem=("The DVD " + retrieveString + " is not in inventory\n");
	return retrieveItem;
	}
}