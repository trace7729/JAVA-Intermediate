package ayhw2InventoryApp;
import java.util.List;
import java.util.ListIterator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
/*
 * Abstract class InventoryIte implements interface InventoryMethods 
 * createItem, updateItem and deleteItem
 */

public abstract class InventoryItem implements InventoryMethods{
//database will be maintained with Properties class
private Properties propTable;
//private variables to manipulate strings
private int countColumns;
private int endIndex;
private int startIndex;
private int firstDelimiter;
	
//private variables for store and load methods
private String nameDataFile;
private String headerDataFile;
	
//constructor
public InventoryItem(Properties propTable, String nameDataFile, String headerDataFile){
	this.propTable = propTable;
	this.nameDataFile = nameDataFile;
	this.headerDataFile = headerDataFile;
	}
//setters and getters
public void setPropTable(Properties propTable){
	this.propTable = propTable;
	}
	
public Properties getPropTable(){
	return propTable;
	}

public void setNameDataFile(String nameDataFile){
	this.nameDataFile = nameDataFile;
	}
	
public String getNameDataFile(){
	return nameDataFile;
	}
	
public void setHeaderDateFile(String headerDataFile){
	this.headerDataFile = headerDataFile;
	}
	
public String getHeaderDataFile(){
	return headerDataFile;
	}
public void setCountColumns(String stringEntry){
	int lastCount = 0;
	int count = 0;
	String findDelimiter = "|";
	while (lastCount != -1){
		   lastCount = stringEntry.indexOf(findDelimiter,lastCount);
		   if( lastCount != -1){
		       count ++;
		       lastCount+=findDelimiter.length();
		      }
		}
		   countColumns = count -1; 
	   }

public int getCountColumns(){
	return countColumns;
	}
// helper method to set the ending index of a column in a string	    
public void setEndIndex(int columnCountToEdit, String stringEntry){
	int forwardIndex = 0;
	String findDelimiter = "|";
	int columnLength;
	setCountColumns(stringEntry);
	if (columnCountToEdit == getCountColumns()){
		endIndex = stringEntry.length() -1;
		} else{
			  for (int index = 0; index <columnCountToEdit; index++ ){
				   {
					columnLength = stringEntry.indexOf(findDelimiter, forwardIndex+1 );
					forwardIndex = columnLength+1;
				   } 
			   endIndex= columnLength;
			   }
		   }
	   }
	   
public int getEndIndex(){
	return endIndex;
	}

// helper method to set the starting index for a column in a string
public void setStartIndex(int columnCountToEdit, String stringEntry){
	int forwardIndex = 0;
	String findDelimiter = "|";
	int columnLength;
	setCountColumns(stringEntry);
		if (columnCountToEdit ==1 ){
			startIndex = 1;
		   }else{
			   for (int index = 0; index <columnCountToEdit; index++ ){
				   {
					columnLength = stringEntry.indexOf(findDelimiter, forwardIndex );
					forwardIndex = columnLength+1;
				   } 
				   startIndex= columnLength +1;
			   }
		   }
	   }
	
public int getStartIndex(){
	return startIndex;
	}

// helper method loadPropTable() to load properties text file	   
public void loadPropTable( ){ 
	  try{
	      FileInputStream file = new FileInputStream( getNameDataFile() );
	      getPropTable().load( file ); 
	      file.close();
	      }catch ( IOException exception ){
	       exception.printStackTrace();
	      } 
	     } 

//helper method listPropTable() to print properties table
public String listPropTable( ){
	//System.out.printf("Inventory List: %s\n",getNameDataFile().toString());
	Set< Object > keys = propTable.keySet();
	List<String> invList=new ArrayList<String>();
	String printList="";
	for ( Object key : keys ){
		invList.add( "\n"+key+"\t"+ propTable.getProperty( ( String ) key ) );
	}
		
		ListIterator<String> iterator=invList.listIterator(invList.size());
		while (iterator.hasPrevious()){
			String string = (iterator.previous());
			printList=printList+string;
		}
		return printList;
}

//helper method storePropTable to write properties table into file string
public void storePropTable(){
	 try{
	    FileOutputStream file = new FileOutputStream( getNameDataFile() );
	    getPropTable().store( file, getHeaderDataFile() );
	    file.close();
	     }catch ( IOException exception ){
	       exception.printStackTrace();
	      } 
	     }
/*
 * helper method getPropertyKey(String givenString) to parse the first column
 * of input string, using vertical bar ('|') as delimeter
 */
public String getPropertyKey(String givenString){
	StringBuilder string = new StringBuilder(givenString); 
	firstDelimiter = string.indexOf("|", 1);
	String columnOne = string.substring(1, firstDelimiter);
	return columnOne;
	}

//implements interface InvetoryMethods methods

@Override 
public void getItem(){
		loadPropTable();
		listPropTable();
	}
public String listItem(){
	return listPropTable();
}
@Override
public String createItem(String createString){
	String nextString="";
	loadPropTable();
	if (createString!=null&&!createString.isEmpty()){
	String stringMarked = "|" + createString + "|";
	getPropTable().setProperty(getPropertyKey(stringMarked), stringMarked);
	storePropTable();
	nextString=("Item " + createString+ " added to inventory\n");
	}else nextString=("Nothing created");
	return nextString;
	}
	
@Override
public String updateItem(int num, String key, String updateString ){
		loadPropTable();
		//get value of the property key entered
	    String valueOld = getPropTable().getProperty(key);
	    StringBuilder build = new StringBuilder(valueOld); 
	    setStartIndex(num, valueOld);
	    setEndIndex(num, valueOld);
	    //delete old column
	    build.delete(getStartIndex(), getEndIndex());
	    build.insert(getStartIndex(), updateString);
	    String valueNew = build.toString();
	    //delete old column and insert new column
	    getPropTable().remove(key);
	    getPropTable().setProperty(getPropertyKey(valueNew), valueNew);
	    storePropTable();
	    String nextString=("Item "+key+" updated in inventory\n"+listPropTable());
	    //listPropTable();
	    return nextString;
	   }
@Override
public String deleteItem(String deleteString){
		retrieveItem(deleteString);
		getPropTable().remove(deleteString);
		storePropTable();
	  	String nextString=(deleteString + " is deleted from inventory\n");
	  	return nextString;
		}
}