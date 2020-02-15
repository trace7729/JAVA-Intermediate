package ayhw2InventoryApp;
/* 
 * Maintainable interface contains methods to create, retrieve, update, and delete strings representing
 * items in a database. 
 */
public interface InventoryMethods {
	void getItem();
	String listItem();
	String createItem(String createString);
	String updateItem(int num, String key, String updateString );
	String retrieveItem(String retrieveString);
	String deleteItem(String deleteString);
}