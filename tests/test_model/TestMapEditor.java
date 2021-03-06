package test_model;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import src.model.MapEditor;

/**
 * This class contains test cases for map editor
 * @author vaibh
 *
 */
public class TestMapEditor {
	private MapEditor mapEditor = null;
	private String newContinentOne = "ZM,3";
	private String newContinentTwo = "YM,4";
	private String newContinentThree = "XM,5";
	
	private String newCountryOne = "C1,100,100,ZM,A1,B1,D1";
	private String newCountryTwo = "D1,100,100,YM,C1";
	private String newCountryThree = "E1,100,100,YM,C1";
	private String newCountryFour = "F1,100,100,XM,C1";
	
	private String deleteCountryName = "F1";
	private String deleteContinentName = "YM";
	
	/**
	 * Overridden method runs once before all test cases
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// do nothing
	}

	/**
	 * Overridden method runs at the end of all test cases.
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// do nothing
	}

	/**
	 * Overridden method runs once before each test case.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		mapEditor = null;
	}

	/**
	 * Overridden method runs at end of each test case
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		// do nothing
	}
	/**
	 * Checks if the map is connected
	 */
	@Test
	public void testAddContinentMap() {
		mapEditor = new MapEditor(1, "MapEditorTest");
//		String path = String.format("Resources//Maps//%s.map", mapEditor.map);
		mapEditor.addContinent(this.newContinentOne);
		mapEditor.addContinent(this.newContinentTwo);
		mapEditor.addContinent(this.newContinentThree);
		
		mapEditor.newCountry(this.newCountryOne);
		mapEditor.newCountry(this.newCountryTwo);
		mapEditor.newCountry(this.newCountryThree);
		mapEditor.newCountry(this.newCountryFour);
		
		mapEditor.delCountry(deleteCountryName);
		mapEditor.delCountry("C1");
		mapEditor.deleteContinent(deleteContinentName);
		
		assertTrue("Continent Connection Error".equals(mapEditor.mapObj.validateMap()));
	}
}