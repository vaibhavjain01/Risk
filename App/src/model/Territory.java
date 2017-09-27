package model;

import java.util.Vector;

public class Territory {
	/***************************/
	private Integer TERRITORYINITSIZE = 2;
	private Integer TERRITORYCAPACITYINCREMENT = 2;
	/***************************/
	public String Name = "";
	public Integer X = 0;
	public Integer Y = 0;
	private String Continent = "";
	private Vector<String> adjacentCountries = new Vector<String>(TERRITORYINITSIZE, TERRITORYCAPACITYINCREMENT);
	
	GenFun genFunObj = new GenFun();
	
	public Territory(String inInfo) {
		String[] tmp = genFunObj.genCommaSepStrToArray(inInfo);
		String tmpAdjacent = inInfo.substring(genFunObj.genOrdinalIndexOf(inInfo, ",", 4));
		SetInfo(tmp[0], tmp[1], tmp[2], tmp[3], tmpAdjacent);
	}
	
	public void SetInfo(String inName, String inX, String inY, String inContinent, String inAdjacent) {
		Name = inName;
		X = genFunObj.genStrToInt(inX);
		Y = genFunObj.genStrToInt(inY);
		Continent = inContinent;
		adjacentCountries = genFunObj.genCommaSepStrToVector(inAdjacent);
		return;
	}
}