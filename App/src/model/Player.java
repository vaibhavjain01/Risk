package model;

import java.util.ArrayList;

/**
 * @author Team20
 *
 */
public class Player {

	private String name;
	private ArrayList<Territory> territories;
	private ArrayList<Card> playerCards;
	private int armies;
	private Integer numTerritories = new Integer(0);

	/**
	 * @param name
	 */
	Player(String name) {
		this.name = name;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return territories
	 */
	public ArrayList<Territory> getTerritories() {
		return territories;
	}

	/**
	 * @param territories
	 */
	public void setTerritories(ArrayList<Territory>  territories) {
		this.territories = territories;
	}
	
	/**
	 * @return numTerritories
	 */
	public int numOfTerritories() {
		return numTerritories;
	}

	/**
	 * after occupying each territories,add one to the numbers of them  
	 */
	public void occupyTerritory() {
		numTerritories++;
	}

	public void loseTerritory(Territory territory) {
		//
	}

	/**
	 * @param a
	 */
	public void addArmy(int a) {
		armies += a;
	}

	/**
	 * @param a
	 */
	public void loseArmy(int a) {
		armies -= a;
	}
	
	/**
	 * @param inArmies
	 */
	public void setArmies(int inArmies) {
		armies = inArmies;
	}
	
	/**
	 * @return armies
	 */
	public int getArmies() {
		return armies;
	}
	
	
	public void addCard(Card c) {

		playerCards.add(c);
	}
	
	public void removeCard(Card c) {
		playerCards.remove(c);
	}

}
