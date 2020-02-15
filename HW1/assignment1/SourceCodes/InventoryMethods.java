/* 
 * Maintainable interface contains methods to create, retrieve, update, and delete strings representing
 * items in a database. 
 */
public interface InventoryMethods {
	void getItem();
	void createItem(String createString);
	void updateItem(int num, String key, String updateString );
	void retrieveItem(String retrieveString);
	void deleteItem(String deleteString);
}