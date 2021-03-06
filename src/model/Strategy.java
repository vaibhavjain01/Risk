package src.model;

import java.util.ArrayList;

/**
 * Strategy class to implement different strategies
 */
public interface Strategy {
    public int getTerritoryForReinforcement(ArrayList<Territory> playerTerritories, Player objPlayer);
    public int getTerritoryForReinforcement(Territory territory, Player objPlayer);
    
    public int getTerritoryForFortification(Territory srcTerritory, Territory dstTerritory, Player objPlayer);
    public int getTerritoryForFortification(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer);
    
    public int getTerritoryForAttack(Territory srcTerritory, Territory dstTerritory, Player objPlayer, 
    		Integer attackerDice, Integer defenderDice);
    public int getTerritoryForAttack(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer);
    
    public Integer getPlayerType();
}
