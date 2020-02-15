import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

public class InventoryView3 extends JFrame //VIEW
{
		private InventoryModel3 resultSetTableModel;
	
		static final String DATABASE_URL = "jdbc:mysql://localhost/inventory";
		static final String USERNAME = "deitel";
		static final String PASSWORD = "deitel";
  
   static final String BOOK_QUERY = "SELECT * FROM book";
   static final String CD_QUERY = "SELECT * FROM cd";
   static final String DVD_QUERY = "SELECT * FROM dvd";
   
   private InventoryModel3 tableModel;
   private JList selectionList;
   private static final String[] selectionNames = {"Book", "CD", "DVD"};
   private static final String[] queries = {BOOK_QUERY, CD_QUERY, DVD_QUERY};
   
   private String viewString;
  
   
  
   public InventoryView3(InventoryModel3 resultSetTableModel) 
   {   
      super( "An inventory application for multimedia store" );
      
        this.resultSetTableModel =  resultSetTableModel;
        
      try 
      {
         // create Table Model for results
         tableModel = new InventoryModel3( DATABASE_URL,
            USERNAME, PASSWORD, BOOK_QUERY );
         
 		//selection panel
        JPanel selectionPanel = new JPanel();
 		selectionPanel.setLayout(new GridLayout (2, 1));
 		
 		JTextArea textSelectionPrompt = new JTextArea("\nPlease select a selection from the list below:");
 		textSelectionPrompt.setOpaque(false);
 		textSelectionPrompt.setEditable(false); 		
 
		
        // JList for selections
 		selectionList = new JList(selectionNames);
 		selectionList.setVisibleRowCount(3);
 		selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		add(new JScrollPane(selectionList));
 		
		selectionPanel.add(textSelectionPrompt);
		selectionPanel.add(selectionList);
		add(selectionPanel, BorderLayout.NORTH );
		
		JPanel operationText = new JPanel();
		operationText.setLayout(new FlowLayout());
 		JTextArea textOperation = new JTextArea("\n Select an operation button, or enter the title of item to be retrieved from inventory: ");
 		textOperation.setOpaque(false);
 		textOperation.setEditable(false);
 		operationText.add(textOperation, FlowLayout.LEFT);
 		
		//operation Panel
		JPanel operationButtonPanel = new JPanel();
		
         // set up JButton for operations
         JButton createButton = new JButton( "Create Item" );
         JButton updateButton = new JButton( "Update Item" );
         JButton deleteButton = new JButton( "Delete Item" );
         operationButtonPanel.add(createButton);
         operationButtonPanel.add(deleteButton);
         operationButtonPanel.add(updateButton);
         
         JPanel operationPanel = new JPanel();
         operationPanel.setLayout(new BorderLayout());
         operationPanel.add(operationText, BorderLayout.NORTH);
         
         operationPanel.add(operationButtonPanel, BorderLayout.SOUTH);
         add(operationPanel, BorderLayout.SOUTH);

         // JTable for table model
         final JTable resultTable = new JTable( tableModel );
         resultTable.setCellSelectionEnabled(true);
         add( new JScrollPane( resultTable ));

         
         JLabel retrieveLabel = new JLabel( "Retrieve:" );
         final JTextField retrieveText = new JTextField();
         JButton retrieveButton = new JButton( "Apply" );
         Box boxRetrieve = Box.createHorizontalBox(); 
         boxRetrieve.add( retrieveLabel );
         boxRetrieve.add( retrieveText );
         boxRetrieve.add( retrieveButton );
         
         operationPanel.add(boxRetrieve, BorderLayout.CENTER);

         //event listener for selection
         selectionList.addListSelectionListener(
    		 new ListSelectionListener()
    		 {
    			 public void valueChanged(ListSelectionEvent event)
    			 {
    				 //display new table
    				 try {
    					 tableModel.setQuery(queries[selectionList.getSelectedIndex()]);
					} catch (SQLException e) {
						e.printStackTrace();
					}
    			 }
    		 });
         
         //event listener for create Button
         createButton.addActionListener(     
            new ActionListener() 
            {        	
               public void actionPerformed( ActionEvent event )
               {
           		viewString = JOptionPane.showInputDialog("Enter the " + tableModel.getItemColumns() 
    					+ " , using a vertical line ('|') to separate fields.");
           		
           		StringBuilder builder = new StringBuilder(viewString.toString());           		
           		//get values for the fields user entered
           		String line = "|";
           		
           		String columnOne = builder.substring(0, builder.indexOf(line));
           		builder.delete(0, (columnOne.length()+1));
           		
           		String columnTwo = builder.substring(0, builder.indexOf(line));
           		builder.delete(0, columnTwo.length()+1);
           		
           		String columnThree = builder.substring(0, builder.indexOf(line));
           		builder.delete(0, columnThree.length()+1);
           		
           		String columnFour = builder.substring(0, builder.length()); 	
           		//pass values to model's insert row method
            	  try
            	  {
            		  tableModel.createItem(columnOne, columnTwo, columnThree, columnFour);
            	  }
    		 	
                  catch ( SQLException sqlException1 ) 
                  {
                     JOptionPane.showMessageDialog( null, sqlException1.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
                     try 
                     {
                        tableModel.setQuery( BOOK_QUERY );
                     }
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );        
                        tableModel.disconnectFromDatabase();        
                        System.exit( 1 ); 
                     }                   
                  }
               } 
            }); 
         
         // create event listener for update Button
         updateButton.addActionListener(     
            new ActionListener() 
            {
               public void actionPerformed( ActionEvent event )
               {   
              	  int row = resultTable.convertRowIndexToModel(resultTable.getSelectedRow());
              	  int col = resultTable.convertColumnIndexToModel(resultTable.getSelectedColumn());
             	  if (tableModel.isCellEditable(row, col) == false){
             		   JOptionPane.showMessageDialog(null, "To update, first select a table cell. " +
             				   tableModel.getColumnName(0) + " can't be modified.");
             		   return;
             	   }
                  try 
                  {             	  
                	  String newString = JOptionPane.showInputDialog("Entry the new data ");
                	  tableModel.updateItem(row, col, newString);                	   
                  }
                  catch ( SQLException sqlException1 ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException1.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     //recover from invalid input, execute book query
                     try 
                     {
                        tableModel.setQuery( BOOK_QUERY );
                     } 
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );         
                        tableModel.disconnectFromDatabase();
                        System.exit( 1 );
                     }                   
                  }
               } 
            }); 
         
         //event listener for Delete Button
         deleteButton.addActionListener(     
            new ActionListener() 
            {        	
               public void actionPerformed( ActionEvent event )
               {
               try 
                  {
            	   //delete a row by pressing delete button
            		  if (resultTable.getSelectedRow() > -1) 
            		  tableModel.deleteItem();
            		  else{
            			  JOptionPane.showMessageDialog(null, "Select a row to delete an entry.");
            			  return;
            		  }
                  }
                  catch ( SQLException sqlException1 ) 
                  {
                     JOptionPane.showMessageDialog( null,sqlException1.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
                  
                     try 
                     {
                        tableModel.setQuery( BOOK_QUERY );
                     } 
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
                        tableModel.disconnectFromDatabase();      
                        System.exit( 1 ); 
                     }                  
                  } 
               } 
            });
         
         final TableRowSorter< TableModel > sorter = 
            new TableRowSorter< TableModel >( tableModel );
         resultTable.setRowSorter( sorter );
         setSize( 500, 250 ); 
         setVisible( true ); 
         
         //listener for retrieve button
         retrieveButton.addActionListener(            
            new ActionListener() 
            {
               public void actionPerformed( ActionEvent e ) 
               {
                  String text = retrieveText.getText();
                  if ( text.length() == 0 )
                     sorter.setRowFilter( null );
                  else
                  {
                     try
                     {
                        sorter.setRowFilter( 
                           RowFilter.regexFilter( text ) );
                     } 
                     catch ( PatternSyntaxException psExpection ) 
                     {
                        JOptionPane.showMessageDialog( null,
                           "Bad regex pattern", "Bad regex pattern",
                           JOptionPane.ERROR_MESSAGE );
                     } 
                  } 
               }
            }
         );} 
      catch (ClassNotFoundException classNotFount)
      {
    	  JOptionPane.showMessageDialog( null, "Unable to find Connector/J Driver class"); 
      }
      catch ( SQLException sqlException ) 
      {
         JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
            "Database error", JOptionPane.ERROR_MESSAGE );              
         tableModel.disconnectFromDatabase();        
         System.exit( 1 ); 
      } 
      
      setDefaultCloseOperation( DISPOSE_ON_CLOSE );
      
      
      addWindowListener(     
         new WindowAdapter() 
         {
            public void windowClosed( WindowEvent event )
            {
               tableModel.disconnectFromDatabase();
               System.exit( 0 );
            } 
         }
      );
     } 

   public void start(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 450);
		setLocation(350, 50);
		setVisible(true);
   }
} 
