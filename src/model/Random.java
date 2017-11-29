package src.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by nasrinizadivahedi on 2017-11-21.
 */
public class Random implements Strategy, Serializable {
    private static final long serialVersionUID = -5417659417247726299L;
    GenericFunctions genfunObj = new GenericFunctions();
    private Integer playerType = 3;
    
    @Override
    public Integer getPlayerType() {
    	return playerType;
    }

    @Override
    public int getTerritoryForReinforcement(ArrayList<Territory> playerTerritories, Player objPlayer) {
    	
    	int index = genfunObj.genRandomNumber(0, playerTerritories.size());
    	if((index >= 0) && (index < playerTerritories.size())) {
    		Territory reinforceTerritory = playerTerritories.get(index);
    		int armies = objPlayer.getArmies();
    		if(armies == 0) {
        		//objPlayer.getGameConfig().nextPlayerTurn();
        		objPlayer.getGameConfig().nextPlayerOrPhase();
        	}
            while (armies > 0) {
            	if(objPlayer.getGameConfig().getCurrentPlayer().id != objPlayer.id) {
        			break;
        		}
            	armies--;
            	reinforceTerritory.increaseArmies();
                objPlayer.setArmies(objPlayer.getArmies() - 1);
                if(objPlayer.getGameConfig().getGamePhase() == genfunObj.GAMEPHASESTARTUP) {
        			objPlayer.getGameConfig().nextPlayerOrPhase();
        		}
            }
    	}
        return 0;
    }

    @Override
    public int getTerritoryForReinforcement(Territory territory, Player objPlayer) {
    	return -1;
    }

    @Override
    public int getTerritoryForFortification(Territory srcTerritory, Territory dstTerritory, Player objPlayer) {
        return -1;
    }

    @Override
    public int getTerritoryForFortification(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer) {
    	int index = genfunObj.genRandomNumber(0, playerTerritories.size());
    	if((index >= 0) && (index < playerTerritories.size())) {
    		int ownerOfFirst = playerTerritories.get(index).getOwner();
    		ArrayList<String> adjacent = playerTerritories.get(index).getAdjacentCountries();
    		for(int ctr = 0; ctr < adjacent.size(); ctr++) {
    			int ownerOfSecond = map.getDictTerritory().get(adjacent.get(ctr)).getOwner();
    			if(ownerOfFirst == ownerOfSecond) {
    				int numMove = genfunObj.genRandomNumber(0, playerTerritories.get(ctr).getArmies());
    				for(int ctr2 = 0; ctr2 < numMove; ctr2++) {
    					Territory srcObjTerritory = playerTerritories.get(index);
    					Territory destObjTerritory = map.getDictTerritory().get(adjacent.get(ctr));
    					if(srcObjTerritory.getArmies() > 1) {
	    					srcObjTerritory.decreaseArmies();
	    		            destObjTerritory.increaseArmies();
	    		            System.out.printf("\n%s : %d left, %s : %d now", srcObjTerritory.getName(), srcObjTerritory.getArmies(),
	    		            		destObjTerritory.getName(), destObjTerritory.getArmies());
    					}
    					else {
    						System.out.println("Randomly chosen country has only 1 amy. Cannot Fortify");
    						return 0;
    					}
    				}
    			}
    		}
    		return 0;
    	}
        return -1;
    }

    @Override
    public int getTerritoryForAttack(Territory srcTerritory, Territory dstTerritory, Player objPlayer,
    		Integer attackerDice, Integer defenderDice) {
        return -1;
    }

    @Override
    public int getTerritoryForAttack(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer) {
    	int index = genfunObj.genRandomNumber(0, playerTerritories.size());
    	if((index >= 0) && (index < playerTerritories.size())) {
    		int ownerOfFirst = playerTerritories.get(index).getOwner();
    		ArrayList<String> adjacent = playerTerritories.get(index).getAdjacentCountries();
    		for(int ctr = 0; ctr < adjacent.size(); ctr++) {
    			int ownerOfSecond = map.getDictTerritory().get(adjacent.get(ctr)).getOwner();
    			if(ownerOfFirst != ownerOfSecond) {
    				Territory attackFrom = playerTerritories.get(index);
    				Territory attackTo = map.getDictTerritory().get(adjacent.get(ctr));
    				if(attackFrom.getArmies() <= 1) {
    					continue;
    				}
    				int attackerDice = genfunObj.genRandomNumber(1, attackFrom.getArmies() - 1);
    				int defendorDice = genfunObj.genRandomNumber(1, attackTo.getArmies());
    				int numTimesAttack = genfunObj.genRandomNumber(1, 100);
    				for(int attackTime = 0; attackTime < numTimesAttack; attackTime++) {
    					if(attackFrom.getOwner() == attackTo.getOwner()) {
    						break;
    					}
    					if(attackFrom.getArmies() == 1) {
        					break;
        				}
    					objPlayer.performAttack(attackerDice, defendorDice, 
    							objPlayer.getGameConfig().getMapObj().getDictContinents(), attackFrom, attackTo);
    				}
    			}
    		}
    		return 0;
    	}
        return -1;
    }
}
