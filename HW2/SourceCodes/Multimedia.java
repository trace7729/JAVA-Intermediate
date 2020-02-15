package ayhw2InventoryApp;
/*
 * Enum MediaType contains the name of the media type and its corresponding fields,
 * so DemoModel can communicate such data to DemoView, for use in the latter's 
 * message prompts.
 */
public enum  Multimedia {
	//declare constants of enum type
	BOOK("Book", "title", "author", "last 4 digits of ISBN", "price"," "),
	CD("CD", "album title", "artist", "year","production", "price"),
	DVD("DVD", "title", "year", "rating", "studio","price");

	
	//private instance variables
	private final String mediaTypeName;

	private final String fieldOne;  
	private  final String fieldTwo;
	private final String fieldThree;
	private final String fieldFour;
	private final String fieldFive;
	
	Multimedia(String mediaTypeName, String fieldOne, String fieldTwo, String fieldThree, String fieldFour, String fieldFive){
		this.mediaTypeName = mediaTypeName;
		this.fieldOne = fieldOne;
		this.fieldTwo = fieldTwo;
		this.fieldThree = fieldThree;
		this.fieldFour = fieldFour;
		this.fieldFive=fieldFive;
	}
	
	//accessors for fields
	public String getMultimediaType() {
		return mediaTypeName;
	}
	
	public String[] getColumnArray(){
		String[] fieldArray  = {fieldOne, fieldTwo, fieldThree, fieldFour, fieldFive}; 
		return fieldArray;
	}
	
/*	public String getField(int i){
		return fieldArray[i];
	}
	public String getFieldOne(){
		return fieldOne;
	}
	public String getFieldTwo(){
		return fieldTwo;
	}
	public String getFieldThree(){
		return fieldThree;
	}
	public String getFieldFour(){
		return fieldFour;
	}*/
}