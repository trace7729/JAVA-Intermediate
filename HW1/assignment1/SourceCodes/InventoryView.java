/*
 *InventoryView class uses Scanner class to read user input  
 */
import java.util.Scanner;

public class InventoryView {

private InventoryModel model;
private InventoryController controller;
//variables to store user inputs
private int selectNumber;
private int operationNumber;
private String viewString1;
private String viewString2;
private int columnToEdit;
//variables to prompt messages
private String selectionName;
private String columns;
	
//constructor
public InventoryView (InventoryController controller){
	this.controller = controller;
} 
//method viewMain() to set text-based console
public void viewMain() {
	Scanner scan = new Scanner(System.in);
	do{
		//prompt user to select multimedia type
		System.out.println("Please select from the list below"
	    		+ "\n\t1 - Books\n" + "\t2 - CDs\n" + "\t3 - DVDs\n" + "\t5 - Exit");
		selectNumber = scan.nextInt();
		if (selectNumber !=5){
			//operation menu
		    controller.setModelSelection(selectNumber);
		    System.out.println("Please select an operation from the list below:\n" +
					"\t1 - Create new inventory item\n" + 
		    		"\t2 - Retrieve item from inventory\n" + 
					"\t3 - Update item in inventory\n" + 
		   			"\t4 - Delete item in inventory");
		    operationNumber = scan.nextInt(); 
		    scan.nextLine();
		    //if user select create
 			if (operationNumber == 1){ 	        	
 				System.out.println("Enter the " + columns + " for the new " + selectionName
		        		+ " using a vertical bar ('|') to separate into columns.");
		        viewString1 = scan.nextLine();
		        controller.setOperation(viewString1, operationNumber);
 				} 
 			//if user select retrieve
		    else if (operationNumber == 2){
		        System.out.println("Enter the title of the " + selectionName + " to be retrieved");
		        viewString1 = scan.nextLine();
		        controller.setUserStringInput(viewString1);
		        controller.setOperation(viewString1, operationNumber);
		        }
 			//if user select update
		    else if (operationNumber == 3){
		        System.out.println("Enter the title of the " + selectionName + " to be updated");
		        viewString1 = scan.nextLine();
		        System.out.println("Enter the index of column to be updated (\'1\' for first column, "
		        			+ "\'2\' for second column...)");
		        columnToEdit = scan.nextInt();
			    scan.nextLine();
		        System.out.println("Update the string of column index " + columnToEdit);
		        viewString2 = scan.nextLine();
		        controller.setOperation(viewString1, viewString2, operationNumber, columnToEdit);
		        }
 			//if user select delete
		     else if (operationNumber == 4){
		        System.out.println("Enter the title of the " + selectionName + " to be deleted");
		        viewString1 = scan.nextLine();
		        controller.setOperation(viewString1, operationNumber);
		        }  
		    }else break; 
		}while (selectNumber !=5); //if user does not select exit
		    	System.out.println("Thank you for using Inventory");
		    	scan.close(); 
		}


public void setModel(InventoryModel model) {
	this.model = model;
	}
//method newView(String string) to load properties from model
public void newView(String string) {
	model.getItem();
	selectionName = model.getSelection();
	columns = model.getColumns();
	}

public void newView(int operationSelected) {
	model.performOperation();
	}		
}