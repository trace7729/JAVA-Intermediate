/*
 *InventoryView2 class makes use of JAVA Swing class to read user input  
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;


public class InventoryView2 extends JFrame {

private InventoryModel model;
private InventoryController controller;
//variables to store user inputs
private int selectNumber;
private int operationNumber;
private String viewString1;
private String viewString2;
private int columnToEdit;
private String columnToEditString;

//variables to prompt messages
private String selectionName;
private String columns;

//GUI components
private JPanel welcomePanel; //CHANGED
private JPanel selectionPanel; //CHANGED
private JPanel operationPanel; //CHANGED
private JPanel contentPanel; //CHANGED

private JTextArea textAreaWelcome;//CHANGED
private JTextArea textAreaSelection; //CHANGED
private JTextArea textAreaOperation; //CHANGED
private JTextArea textAreaContent; //CHANGED

private JPanel buttonPanelO; //CHANGED
private JPanel radioButtonPanelS; //CHANGED

private JRadioButton[] radioButton;
private static String[] radioButtonName={"Book","CD","DVD"};
private ButtonGroup radioButtonGroup; //CHANGED

private JButton[] button;//CHANGED
private static String[] buttonName={"Create new inventory item","Retrieve item from inventory","Update item in inventory","Delete item in inventory"};

private JPanel buttonPanelI;
private String selectionType;
private String nextString;
private String columnArray[];
	
//constructor
public InventoryView2 (InventoryController controller){
	super("Inventory Application for Multimedia Store");
	this.controller=controller; 
	setLayout(new GridBagLayout());
	
	//Create font objects
	Font smallFont=new Font("Times New Roman",Font.PLAIN,14);
	Font largeFont=new Font("Times New Roman",Font.BOLD,16);
	
	
	//Selection Panel
	selectionPanel=new JPanel();
	selectionPanel.setLayout(new GridLayout(2,2));
	TitledBorder selectionTitle=BorderFactory.createTitledBorder("Step 1 : Select Multimedia Type");
	selectionPanel.setBorder(selectionTitle);
	
	GridBagConstraints selectionJPConstraints=new GridBagConstraints();
	setGBC(selectionPanel,selectionJPConstraints);
	
	textAreaSelection=new JTextArea("\nSelect a multimedia type from the list below:");
	formateTextArea(selectionPanel,textAreaSelection,smallFont,false,false);
	
	radioButton=new JRadioButton[radioButtonName.length];
	radioButtonGroup=new ButtonGroup();
	radioButtonPanelS=new JPanel();
	radioButtonPanelS.setLayout(new GridLayout(1,3));
	RadioButtonItem radioButtonEventS=new RadioButtonItem();//S for selection
	for (int i=0; i<radioButtonName.length;i++){
		radioButton[i]=new JRadioButton(radioButtonName[i],false);
		radioButtonGroup.add(radioButton[i]);
		radioButtonPanelS.add(radioButton[i]);
		radioButton[i].addItemListener(radioButtonEventS);
	}
	selectionPanel.add(radioButtonPanelS);
	
	//Operation
	operationPanel=new JPanel();
	operationPanel.setLayout(new GridLayout(3,1));
	TitledBorder operationTitle=BorderFactory.createTitledBorder("Step 2 : Select Operation");
	operationPanel.setBorder(operationTitle);
	operationPanel.setVisible(true);
	
	GridBagConstraints operationJPConstraints=new GridBagConstraints();
	setGBC(operationPanel,operationJPConstraints);
	
	textAreaOperation=new JTextArea("\nPlease select an operation from the list below,"+"or click to view inventory:\n");
	formateTextArea(operationPanel,textAreaOperation,smallFont,false,false);
	
	button = new JButton[buttonName.length];
	buttonAction buttonOperation=new buttonAction(); //O for operation
	buttonPanelO=new JPanel(new GridBagLayout());
	GridBagConstraints buttonGBC=new GridBagConstraints();
	//buttonPanelS.setLayout(new GridLayout(2,2));
	for(int i=0; i<buttonName.length;i++){
		buttonGBC.fill=GridBagConstraints.VERTICAL;
		buttonGBC.gridx=i;
		buttonGBC.gridy=1;
		buttonGBC.insets=new Insets(4,5,0,0);
		button[i]=new JButton(buttonName[i]);
		//button[i].setMargin(new Insets(2,2,2,2));
		button[i].addActionListener(buttonOperation);
		//button[i].setPreferredSize();
		buttonPanelO.add(button[i],buttonGBC);
	}
	operationPanel.add(buttonPanelO);
	
	//Inventory Current
	buttonPanelI=new JPanel(new GridBagLayout());
	GridBagConstraints buttonGBCI=new GridBagConstraints();
	//buttonPanelI.setLayout(new BorderLayout());
	JButton currentInv=new JButton("Current Inventory");
	currentInv.setFont(smallFont);
	buttonGBCI.fill=GridBagConstraints.VERTICAL;
	
	InvButtonAction invButtonAction=new InvButtonAction();
	currentInv.addActionListener(invButtonAction);
	
	buttonPanelI.add(currentInv,buttonGBCI);
	operationPanel.add(buttonPanelI);
	
	//Content
	contentPanel=new JPanel();
	TitledBorder contentTitle=BorderFactory.createTitledBorder("Step 3 : Contents of Inventory");
	contentPanel.setBorder(contentTitle);
	contentPanel.setBackground(Color.WHITE);
	contentPanel.setLayout(new GridLayout(1,2));
	contentPanel.setVisible(false);
	
	GridBagConstraints contentJPConstraints = new GridBagConstraints();
	contentJPConstraints.gridwidth = GridBagConstraints.REMAINDER;
	contentJPConstraints.weighty = .7;
	contentJPConstraints.weightx = 1.0;
	contentJPConstraints.fill = GridBagConstraints.BOTH;
	add(contentPanel, contentJPConstraints);
	
	textAreaContent = new JTextArea();
	formateTextArea(contentPanel, textAreaContent, smallFont, false, true);
	JScrollPane displayScrollPane = new JScrollPane(textAreaContent);
	displayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	contentPanel.add(displayScrollPane);
} 

public void start(){
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(800,500);
	setLocation(300,300);
	setVisible(true);
}
public void setModel(InventoryModel model) {
	this.model = model;
	}
//method newView(String string) to load properties from model
public void newView(String string) {
	model.getItem();
	selectionName = model.getSelection();
	columnArray = model.getMediaColumnArray();
	}

public void newView(int operationSelected) {
	model.performOperation();
	nextString=model.getNextString();
	}	
public String getColumns(){
	String columns="";
	int length;
	if(selectionType=="CD"||selectionType=="DVD")
		length=6;
	else length=5;
	for(int i=0; i < length;i++){
		if(i<length-1)
			columns=columns+columnArray[i]+", ";
		if(i==length-1)
			columns=columns+columnArray[i];
	}
	return columns;
}

public void formateTextArea(JPanel jPanel, JTextArea text, Font font, boolean editable, boolean opaque){
	text.setFont(font);
	text.setEditable(editable);
	text.setOpaque(opaque);
	jPanel.add(text);
}
public void setGBC(JPanel panel,GridBagConstraints c){
	c.gridwidth=GridBagConstraints.REMAINDER;
	c.fill=GridBagConstraints.HORIZONTAL;
	c.weighty=0.2;
	add(panel,c);
}
//inner class for radio button events
//enabling the selection of Multimedia Type
private class RadioButtonItem implements ItemListener{
	public void itemStateChanged(ItemEvent evet){
		for (int i=0; i<radioButton.length;i++){
			if (radioButton[i].isSelected())
				controller.setModelSelection(i +1);
		}
		operationPanel.setVisible(true);
	}
}
//inner class for operation buttons
private class buttonAction implements ActionListener{
	public void actionPerformed(ActionEvent event){
		Object operation=event.getSource();
		contentPanel.setVisible(true);
		String createString=("Create inventory item for the multimedia type "+selectionName+"\nEnter the "+getColumns()+"\nUse a vertical line '|' to separate each property value into column");
		String retrieveString=("Enter the title of the "+selectionName+" to retrieve from the inventory");
		String updateString1=("Enter the title of the "+selectionName+" for updating");
		String updateString2=("Enter the number of the column (\'1\' for first column, "+"\'2\' for second column...)");
		String deleteString=("Enter the title of the "+selectionName+" to be deleted");
		
		if(operation==button[0]){
			operationNumber=1;
			viewString1=JOptionPane.showInputDialog(createString);
			controller.setOperation(viewString1,operationNumber);
			JOptionPane.showMessageDialog(null, nextString);
		}else if (operation==button[1]){
			operationNumber=2;
			viewString1=JOptionPane.showInputDialog(retrieveString);
			controller.setOperation(viewString1,operationNumber);
			JOptionPane.showMessageDialog(null, nextString);
		}else if(operation==button[2]){
			operationNumber=3;
			viewString1=JOptionPane.showInputDialog(updateString1);
			int columnToEdit=Integer.parseInt(JOptionPane.showInputDialog(updateString2));
			viewString2=JOptionPane.showInputDialog("Enter the new property for the column number " +columnToEdit);
			controller.setOperation(viewString1,viewString2,3,columnToEdit);
			textAreaContent.setText(nextString);
		}else if(operation==button[3]){
			operationNumber=4;
			viewString1=JOptionPane.showInputDialog(deleteString);
			controller.setOperation(viewString1,operationNumber);
			JOptionPane.showMessageDialog(null, nextString);
		}
	}
}
//inner class for inventory list button
private class InvButtonAction implements ActionListener{
	public void actionPerformed(ActionEvent event){
		operationNumber=5;
		controller.setOperation(operationNumber);
		textAreaContent.setText(nextString);
	}
}
}