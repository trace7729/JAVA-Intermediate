/*
 *Enum Multimedia contains the media type and its properties
 */
public enum  Multimedia {

BOOK("Book", "title, author, last 4 digits of ISBN, price"),
CD("CD", "album title, artist, year, label, price"),
DVD("DVD", "title, year, rating, studio, price");
	
private String multimediaType;
private String multimediaColumn;

Multimedia(String multimediaType, String multimediaColumn){
	this.multimediaType = multimediaType;
	this.multimediaColumn = multimediaColumn;
	}
	
public String getMultimediaType() {
	return multimediaType;
	}
public String getMultimediaColumn() {
	return multimediaColumn;
	}
}