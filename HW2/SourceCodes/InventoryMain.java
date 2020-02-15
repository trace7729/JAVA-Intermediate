package ayhw2InventoryApp;
public class InventoryMain {
	/*
	 * main method of application
	 */
public static void main(String[] args) {
	InventoryModel model = new InventoryModel();
	InventoryController controller = new InventoryController(model);
	InventoryView2 view = new InventoryView2(controller);
		
	model.setView(view);
	view.setModel(model);
		
	view.start();
	}
}