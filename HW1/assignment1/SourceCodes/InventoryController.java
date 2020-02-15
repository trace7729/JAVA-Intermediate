/*
 * InventoryController class contains methods to be used by
 * InventoryView to pass user input to InventoryModel
 * To process user string input
 * To set selection and operation
 */
public class InventoryController {
	
private InventoryModel model;

//constructor
public InventoryController(InventoryModel model){
	this.model = model;
	}
public void setModelSelection(int number){
	model.setSelection(number);
	}

//method setOperation to pass user input
public void setOperation(int number) {
	model.setSelectedOperation(number);
	}
public void setOperation(String viewString, int operationNumber) {
	model.setInputString1(viewString);
	model.setSelectedOperation(operationNumber);
	}
public void setOperation(String viewString, String viewString2, int operationNumber, int fieldToEdit) {
	model.setInputString1(viewString);
	model.setInputString2(viewString2);
	model.setColumnIndex(fieldToEdit);
	model.setSelectedOperation(operationNumber);
	}
public void setUserStringInput(String string) {
	if (model.getInputString1() != string){
		model.setInputString1(string);
		}
	}
}