package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import view.ErrorInfoView;
import view.MainWindow;

/**
 * @author Team20
 *
 */
public class Maps {
	/*************************/
	private Integer MAP_INIT_SIZE = 2;
	private Integer MAP_CAPACITY_INCREMENT = 2;

	private Integer TERRITORY_INIT_SIZE = 2;
	private Integer TERRITORY_CAPACITY_INCREMENT = 2;
	/*************************/

	private String mapLocation;
	private String mapName;
	/* Basic Map Properties */
	/* Remove */
	private String mapImage = ""; // with extension
	private String mapWrap = ""; // yes or no
	private String mapScroll = ""; // horizontal, vertical, none
	/* */

	private String mapAuthor = null; // first and last name
	private String mapWarning = null; // yes or no

	/* Map Continents and Army Bonuses for each */
	// Map<String, Integer> dictContinents = new HashMap<String,
	// Integer>(MAP_INIT_SIZE, MAP_CAPACITY_INCREMENT);
	private Map<String, Continent> dictContinents = new HashMap<String, Continent>(MAP_INIT_SIZE,
			MAP_CAPACITY_INCREMENT);
	private Map<String, Territory> dictTerritory = new HashMap<String, Territory>(TERRITORY_INIT_SIZE,
			TERRITORY_CAPACITY_INCREMENT);

	GenFun genFunObj = new GenFun();
	private ErrorInfoView errorInfoView;
	/**
	 * @param inMapLocation
	 */
	public Maps(String inMapLocation) {
		mapLocation = inMapLocation;
		errorInfoView = new ErrorInfoView();
		String readMapMsg = readMap();
		String validateMapMsg = validateMap();
		
		if (readMapMsg != "true") {
			// TBD error
			System.out.println(readMapMsg);
			errorInfoView.showErrorInfo(readMapMsg);
		}
		if (validateMapMsg != "true") {
			// TBD error
			System.out.println(validateMapMsg);
			errorInfoView.showErrorInfo(validateMapMsg);
		}
	}

	public void setMapName(String inMapName) {
		mapName = inMapName;
	}

	/**
	 * @return mapName
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * @return size of dictContinents
	 */
	public Integer getNumContinents() {
		return dictContinents.size();
	}

	/**
	 * @return size of dictContinents
	 */
	public Integer getNumTerritories() {
		return dictTerritory.size();
	}

	public void setDictContinents(Map<String, Continent> dictContinents) {
		this.dictContinents = dictContinents;
	}

	public void setDictTerritory(Map<String, Territory> dictTerritory) {
		this.dictTerritory = dictTerritory;
	}

	/**
	 * @return the dictContinents
	 */
	public Map<String, Continent> getDictContinents() {
		return dictContinents;
	}

	/**
	 * @return the dictTerritory
	 */
	public Map<String, Territory> getDictTerritory() {
		return dictTerritory;
	}

	/**
	 * @return the mapAuthor
	 */
	public String getMapAuthor() {
		return mapAuthor;
	}

	/**
	 * @param mapAuthor
	 *            the mapAuthor to set
	 */
	public void setMapAuthor(String mapAuthor) {
		this.mapAuthor = mapAuthor;
	}

	/**
	 * @param inVal
	 * @return rtVal
	 */
	private Integer checkPropertyMap(String inVal) {
		Integer rtVal = 0; // 0 = Success, -1 = Fail

		if (inVal.contains("author=")) {
			mapAuthor = genFunObj.genStringGetValueAfterEquals(inVal);
			if(mapAuthor != null)
				return rtVal;
		}
		
		if (inVal.contains("image=")) {
			/* vj, we dont load images */
			//mapImage = genFunObj.genStringGetValueAfterEquals(inVal);
			//if(mapImage != null)
				return rtVal;
		}
		
		if (inVal.contains("wrap=")) {
			mapWrap = genFunObj.genStringGetValueAfterEquals(inVal);
			if(mapWrap != null)
				return rtVal;
		}
		if (inVal.contains("scroll=")) {
			mapScroll = genFunObj.genStringGetValueAfterEquals(inVal);
			if(mapScroll != null)
				return rtVal;
		}
		if (inVal.contains("warn=")) {
			mapWarning = genFunObj.genStringGetValueAfterEquals(inVal);
			if(mapWarning != null)
				return rtVal;
		}
		rtVal = -1;
		return rtVal;
	}

	public String readMap() {
		String mapTxtLoc = String.format("Resources//Maps//%s.map", mapLocation);
		String mapImgLoc = String.format("Resources//Maps//%s.bmp", mapLocation);

		BufferedReader mapData = genFunObj.genOpenFileToBufferedReader(mapTxtLoc);
		String line;
		String[] tmpArr;
		Integer count = new Integer(0);

		/* Property Flags 	- 1 = Found, 2 = Completed, 0 = Not Found */
		Integer mapProperty = new Integer(0);
		Integer continentsProperty = new Integer(0);
		Integer territoriesProperty = new Integer(0);

		try {
			while ((line = mapData.readLine()) != null) {

				if ((line.equalsIgnoreCase("[MAP]") == true)) {
					if (continentsProperty == 1) {
						continentsProperty = 2;
					}
					if (territoriesProperty == 1) {
						territoriesProperty = 2;
					}
					mapProperty = 1;
					continue;
				}

				if ((line.equalsIgnoreCase("[Continents]") == true)) {
					if ((mapProperty != 2) && (mapProperty != 0)) {
						break;
					}
					if (territoriesProperty == 1) {
						territoriesProperty = 2;
					}
					continentsProperty = 1;
					continue;
				}

				if ((line.equalsIgnoreCase("[Territories]") == true)) {
					if ((mapProperty != 2) && (mapProperty != 0)) {
						break;
					}
					if (continentsProperty == 1) {
						continentsProperty = 2;
					}
					territoriesProperty = 1;
					continue;
				}

				if (mapProperty == 1) {
					if (checkPropertyMap(line) == 0) {
						count++;
					} else {
						if (count < 5)
							continue;
						else
							mapProperty = 2;
					}
				}

				if (continentsProperty == 1) {
					tmpArr = genFunObj.genStringGetSplitArrayEquals(line);
					if (tmpArr.length < 2) {
						continue;
					}
					Continent tmpContinentObj = new Continent(tmpArr[0], genFunObj.genStrToInt(tmpArr[1]));
					dictContinents.put(tmpArr[0], tmpContinentObj);
				}

				if (territoriesProperty == 1) {
					if (line.equalsIgnoreCase("")) {
						continue;
					}
					Territory obj = new Territory(line);
					String terrName = obj.getName();
					dictTerritory.put(terrName, obj);
				}
			}

			if (continentsProperty == 2) {
				territoriesProperty = 2;
			}
			if (territoriesProperty == 2) {
				continentsProperty = 2;
			}

			if (mapProperty != 2) {
				return "Map is missing [MAP] section";
			}
			if (continentsProperty != 2) {
				return "Map is missing [Continents] section";
			}
			if (territoriesProperty != 2) {
				return "Map is missing [Territories] section";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "true";
	}

	public String validateMap() {
		if ((mapAuthor == null) && (mapWarning == null)) {
		      return "The map author or map warning property is not valid"; 
		}

		for (String territory : this.dictTerritory.keySet()) 
		{
		    Territory tmpTerritoryObj = this.dictTerritory.get(territory); 
		    if(tmpTerritoryObj.getName() == null) 
		    { 
		    	return "\nThe territory %s does not have a name" + territory; 
		    } 
		    if(tmpTerritoryObj.getContinent() == null) 
		    { 
			    return "\nThe territory %s does not have a continent" +  territory; 
		    } 
		    if(tmpTerritoryObj.getX() == 0) 
		    { 
			    return "\nThe territory %s does not have defined X Axis" + territory; 
		    } 
		    else if(tmpTerritoryObj.getY() == 0) 
		    { 
			    return "\nThe territory %s does not have defined Y Axis" + territory; 
		    } 
		   
		    if(dictContinents.get((tmpTerritoryObj.getContinent())) == null) 
		    { 
			    return "\nThe territory %s is in continent %s. This continent either does not exist or isnt defined properly" + territory + tmpTerritoryObj.getContinent();
	 	    } 
		   
		    ArrayList<String> adjacent = tmpTerritoryObj.getAdjacentCountries(); 
		    for(int i = 0; i<adjacent.size(); i++) 
		    { 
			    Integer matchFlag = 0; 
			    String adjacentCountry = adjacent.get(i); 
			    if(this.dictTerritory.get(adjacentCountry) != null) 
			    { 
			    	ArrayList<String> checkList = (this.dictTerritory.get(adjacentCountry)).getAdjacentCountries(); 
			        for(int j = 0; j<checkList.size(); j++) 
			        { 
				        if(this.dictTerritory.get(territory).getName().equals(checkList.get(j))) 
				        { 
					        matchFlag = 1; 
					        break; 
				        } 
			        } 
			        if(matchFlag == 0) 
			        { 
				       return "\nPlease check adjacency for territory %s" + adjacentCountry;  
			        } 
			    } 
				else 
				{ 
				    return "\nPlease mention adjacent territories for %s" + adjacentCountry; 
				} 
			} 
		
		}
		
		if(mapConnectivity() != "true")
		{
			return "The map is not a connected graph";
		}

		return "true";
	}
	
	public String mapConnectivity()
	{
		HashMap<Territory, Integer> TerritoryVisitFlags = new HashMap<Territory, Integer>();
		Territory tmp = null;
		for(String territory : this.dictTerritory.keySet())
		{
			Territory tmpTerritoryObj = this.dictTerritory.get(territory);
			if(tmp == null)
			{
				tmp = tmpTerritoryObj;
			}
		    if(tmpTerritoryObj.getName() != null)
		    {
		    	TerritoryVisitFlags.put(tmpTerritoryObj, 0);
		    }
		}
		
		TerritoryVisitFlags = validateConnectivity(TerritoryVisitFlags, tmp);
		
		for(String territory : this.dictTerritory.keySet())
		{
			if(TerritoryVisitFlags.get(this.dictTerritory.get(territory)) != 1)
			{
				return "The map is not a connected graph";
			}
		}
		
		
		return "true";
	}

	private HashMap<Territory, Integer> validateConnectivity(HashMap<Territory, Integer> TerritoryVisitFlags, Territory territory)
	{
		try
		{
			if((TerritoryVisitFlags == null) || (territory == null) || (TerritoryVisitFlags.size() == 0))
			{
				return null;
			}
			
			int val = TerritoryVisitFlags.get(territory); 
			
			if(val == 0)
			{
				TerritoryVisitFlags.put(territory, 1);
			}
			
			for(String adjTerritory : territory.getAdjacentCountries())
			{
				if(TerritoryVisitFlags.get(dictTerritory.get(adjTerritory)) == 0)
				{
					TerritoryVisitFlags = validateConnectivity(TerritoryVisitFlags, dictTerritory.get(adjTerritory));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(TerritoryVisitFlags);
			System.out.println(territory);
			//System.out.println(TerritoryVisitFlags.get(territory));
		}
		return TerritoryVisitFlags;
	}
}