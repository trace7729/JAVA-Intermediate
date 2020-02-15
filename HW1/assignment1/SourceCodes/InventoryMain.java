public class InventoryMain {
	/*
	 * main method of application
	 */
public static void main(String[] args) {
	InventoryModel model = new InventoryModel();
	InventoryController controller = new InventoryController(model);
	InventoryView view = new InventoryView(controller);
		
	model.setView(view);
	view.setModel(model);
		
	view.viewMain();
	}
}