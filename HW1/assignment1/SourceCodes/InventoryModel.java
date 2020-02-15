import java.util.Properties;
/*
 * InventoryModel class instantiates properties objects
 * Contains methods to set operation, update view, and perform operations
 */
public class InventoryModel {
  
private InventoryView view;
private String inputString1;
private String inputString2;
private int columnIndex;
private int inventoryObjectsIndex;
private Multimedia mediaType;
			
private toPerform currentOperation;
private enum toPerform {CREATE, RETRIEVE, UPDATE, DELETE};

private Properties propTableBook = new Properties();
private Properties propTableCD = new Properties();
private Properties propTableDVD = new Properties();	

//instantiate multimedia types, book, CD and DVD
private Book book = new Book(propTableBook, "Book.txt", "Book Property File");
private CD cd = new CD(propTableCD, "CD.txt", "CD Property File");
private DVD dvd = new DVD(propTableDVD, "DVD.txt", "DVD Property File");

//initialize book, CD and DVD to implement interface Inventoryethods 
private InventoryMethods[] inventoryObjects = new InventoryMethods[]{book, cd, dvd};
	
public InventoryModel() {
	}
public void setView(InventoryView view) {
	this.view = view;
	}

//setters and getters

//method setSelection(int number) selects enum representing multi media type
//and update View component with string
public void setSelection(int number) {
	setInventoryObjectsIndex(number-1);
	if (number == 1){
		mediaType = Multimedia.BOOK;
		}
	else if (number == 2){
		mediaType = Multimedia.CD;
		}
	else if (number == 3){
		mediaType = Multimedia.DVD;
		}
		view.newView(" " + mediaType);
	}
public String getSelection(){
	return mediaType.getMultimediaType().toString();
	}
public String getColumns(){
	return mediaType.getMultimediaColumn().toString();
	}
public void setInventoryObjectsIndex(int number){
	inventoryObjectsIndex = number;
	} 
public int getInventoryObjectsIndex() {
	return inventoryObjectsIndex;
	}

public void setInputString1(String string1) {
	inputString1 = string1;
	}
	
public String getInputString1() {
	return inputString1;
	}

public void setInputString2(String string2) {
	inputString2 = string2;
	}
	
public String getInputString2() {
	return inputString2;
	}
	
public void setColumnIndex(int index){
	columnIndex = index;
	}
	
public int getColumnIndex(){
	return columnIndex;
	}
	
public void getItem(){
	inventoryObjects[getInventoryObjectsIndex()].getItem();
	}

public void createItem(String createString){
	inventoryObjects[getInventoryObjectsIndex()].createItem(createString);
	}
	
public void retrieveItem(String retrieveString){
	inventoryObjects[getInventoryObjectsIndex()].retrieveItem(retrieveString);
	}

public void updateItem(int num, String key, String updateString){
	inventoryObjects[getInventoryObjectsIndex()].updateItem(num, key, updateString);  
	}
	
public void deleteItem(String deleteString){
	inventoryObjects[getInventoryObjectsIndex()].deleteItem(deleteString);
	}
//method setSelectedOperation(int operation) sets operation in model
// and updates view wiht an integer
public void setSelectedOperation(int operation) {
	if (operation == 1)
		currentOperation = toPerform.CREATE;
	else if (operation == 2)
		currentOperation = toPerform.RETRIEVE;
	else if (operation == 3)
		currentOperation = toPerform.UPDATE;
	else if (operation == 4)
		currentOperation = toPerform.DELETE;
	view.newView(operation);
	}
//method performOperation() performs CRUD operations
public void performOperation(){
	if (currentOperation == toPerform.CREATE)
		this.createItem(getInputString1());
	else if (currentOperation == toPerform.RETRIEVE)
		this.retrieveItem(getInputString1());
	else if (currentOperation == toPerform.UPDATE){
		this.updateItem(getColumnIndex(), getInputString1(), getInputString2());
		}
	else if (currentOperation == toPerform.DELETE)
		this.deleteItem(getInputString1());
	}
}