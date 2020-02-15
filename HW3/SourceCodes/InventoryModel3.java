//package java3HW3folder;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class InventoryModel3 extends AbstractTableModel ///table model object provides the the ResultSet data to the JTable
{
	InventoryModel3 model;
	
	static final String DATABASE_URL = "jdbc:mysql://localhost/books";
	static final String USERNAME = "deitel";
	static final String PASSWORD = "deitel";
	
   private Connection connection;
   private Statement statement;
   private ResultSet resultSet;
   private ResultSetMetaData metaData;
   private int rowCount;
   private String[] columnsArray;
   private boolean connectToDB = false;
   
   public InventoryModel3(){
	   
   }
   
   public InventoryModel3( String url, String username,
      String password, String query ) throws SQLException, ClassNotFoundException
   {         
      connection = DriverManager.getConnection( url, username, password );
      // create Statement to query database
      statement = connection.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
      connectToDB = true;
      setQuery( query ); 
   } 

   public Class getColumnClass( int column ) throws IllegalStateException
   {
      //check database connection
      if ( !connectToDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      //find class name
      try 
      {
         String className = metaData.getColumnClassName( column + 1 );         
        
         return Class.forName( className );
      }
      catch ( Exception exception ) 
      {
         exception.printStackTrace();
      } 
      return Object.class; //if try block fails, return class Object
   } 

   public int getColumnCount() throws IllegalStateException
   {   
      //check database connection
      if ( !connectToDB ) 
         throw new IllegalStateException( "Not Connected to Database" );
      //find column count
      try 
      {
         return metaData.getColumnCount(); 
      }  
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      }   
      return 0; // if try block fails, return 0 for column count
   } 

   public String getColumnName( int column ) throws IllegalStateException
   {    
      //check database connection
      if ( !connectToDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      //find column name
      try 
      {
         return metaData.getColumnName( column + 1 );  
      }  
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      }   
      return ""; //if try block fails, return empty string for column name
   } 

   
   public int getRowCount() throws IllegalStateException
   {      
      //check database connection
      if ( !connectToDB ) 
         throw new IllegalStateException( "Not Connected to Database" );
      return rowCount;
   } 

   public Object getValueAt( int row, int column ) 
      throws IllegalStateException
   {
      //check database connection
      if ( !connectToDB ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // find value at specified row and column
      try 
      {
         resultSet.absolute( row + 1 );
         return resultSet.getObject( column + 1 );
      }  
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      }       
      return ""; //if try block fails, return empty string 
   } 
   
   public void setQuery( String query ) 
      throws SQLException, IllegalStateException 
   {
      //check database connection
      if ( !connectToDB ) 
         throw new IllegalStateException( "Not Connected to Database" );
      resultSet = statement.executeQuery( query );
      metaData = resultSet.getMetaData();
      //find row count
      resultSet.last();             
      rowCount = resultSet.getRow();          
      //notify that model has changed
      fireTableStructureChanged();
   } 
     
   public boolean isCellEditable(int row, int col){
	   if (col < 1) {
           return false;
       } else {
           return true;
       }
   }
   
   
   public void setValueAt(Object value, int row, int col )           
		   throws IllegalStateException{
       //check database connection
       if ( !connectToDB ) 
          throw new IllegalStateException( "Not Connected to Database" );
       		try
       		{
    	   resultSet.absolute(row);
    	   resultSet.updateObject(col, value);
                
                 fireTableCellUpdated(row, col);
              }  
              catch ( SQLException sqlException ) 
              {
                 sqlException.printStackTrace();
              }       
   }
   
   public void setCoumnsArray(){
	   String[] columnsArray = new String[getColumnCount()];
	   String add;
	   for(int i = 1; i< getColumnCount(); i++){
		   add = getColumnName(i);
	   		columnsArray[i] = add;
   }
   }
   
   public String[] getColumnsArray(){
	   return columnsArray;
   }
     
	//helper method returns list of columns for a given media item
	public String getItemColumns(){
		String itemColumns = "";
		int length = getColumnCount();
		for(int i =1; i < length; i++ ){
			//insert comma after column name, unless it is the last field	
			String appendString = (i != length-1) ? (getColumnName(i) + ", ") : (getColumnName(i) );
			itemColumns += appendString;
		}
		return itemColumns;
	}
   
   //method to add row to database 
   public void createItem(String stringSecond, String stringThird, String stringFourth, String stringFifth)
		   throws SQLException , IllegalStateException
   {
		resultSet.moveToInsertRow();
		resultSet.updateString(2, stringSecond);
		resultSet.updateString(3, stringThird);	
		resultSet.updateString(4, stringFourth);		
		resultSet.updateString(5, stringFifth);		

		resultSet.insertRow();
		
	    metaData = resultSet.getMetaData();
	    //find row count
	    resultSet.last();               
	    rowCount = resultSet.getRow();      
	    //notify that model has changed
	    fireTableStructureChanged();
	}

   public void updateItem(int itemID, int column, String newValue)
	   throws SQLException , IllegalStateException{
	   resultSet.updateString( column+1, newValue);
       resultSet.updateRow(); 
	   metaData = resultSet.getMetaData();
	   //find row count
	   resultSet.last();                   
	   rowCount = resultSet.getRow();        
	   // notify that model has changed
	   fireTableStructureChanged();
   }
   
   public void deleteItem()
		   throws SQLException , IllegalStateException{
	   resultSet.deleteRow();
	   metaData = resultSet.getMetaData();
	   //find row count
	   resultSet.last();                   
	   rowCount = resultSet.getRow();      
	   //notify that model has changed
	   fireTableStructureChanged();
   }
                 
   public void disconnectFromDatabase()            
   {              
      if ( connectToDB )                  
      {
         //close Statement and Connection            
         try                                          
         {                                            
            resultSet.close();                        
            statement.close();                        
            connection.close();                       
         }                                   
         catch ( SQLException sqlException )          
         {                                            
            sqlException.printStackTrace();           
         }                                 
         finally  // update database connection status
         {                                            
            connectToDB = false;              
         }                              
      } 
   }          
} 