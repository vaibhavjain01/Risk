package src.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Aggressive: Computer player strategy that focuses on attack (reinforces its
 * strongest country, then always attack with it until it cannot attack anymore, then
 * fortifies in order to maximize aggregation of forces in one country).
 */
public class Aggressive implements Strategy, Serializable {
    private static final long serialVersionUID = -5417659417247726299L;
    GenericFunctions genfunObj = new GenericFunctions();
    private Integer playerType = 1;

    /**
     * @param playerTerritories PLayer territories
     * @param objPlayer         Player object
     * @return check
     */
    @Override
    public int getTerritoryForReinforcement(ArrayList<Territory> playerTerritories, Player objPlayer) {
        int max = 0;
        Territory temp = null;
        for (int ctr = 0; ctr < playerTerritories.size(); ctr++) {
            if (playerTerritories.get(ctr).getArmies() > max) {
                max = playerTerritories.get(ctr).getArmies();
                temp = playerTerritories.get(ctr);
            }
        }

        int num = objPlayer.getArmies();
        if (num == 0) {
            objPlayer.getGameConfig().nextPlayerOrPhase();
        }
        for (int ctr2 = 0; ctr2 < num; ctr2++) {
            if (objPlayer.getGameConfig().getCurrentPlayer().id != objPlayer.id) {
                break;
            }
            temp.increaseArmies();
            if (objPlayer.getGameConfig().getGamePhase() == genfunObj.GAMEPHASESTARTUP) {
                objPlayer.getGameConfig().nextPlayerOrPhase();
            }
            objPlayer.setArmies(objPlayer.getArmies() - 1);
        }

        return 0;
    }

    /**
     * Get territory for fortification
     *
     * @param srcTerritory Source territory
     * @param dstTerritory Destination territory
     * @param objPlayer    Player object
     * @return -1
     */
    @Override
    public int getTerritoryForFortification(Territory srcTerritory, Territory dstTerritory, Player objPlayer) {
        return -1;
    }

    /**
     * Get Territory for fortification
     *
     * @param map               Map
     * @param playerTerritories Player territories
     * @param objPlayer         Player object
     * @return 0
     */
    @Override
    public int getTerritoryForFortification(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer) {
        return 0;
    }

    /**
     * @param srcTerritory Source Territory
     * @param dstTerritory Destination Territory
     * @param objPlayer    Player object
     * @param attackerDice attacker dice
     * @param defenderDice Defender dice
     * @return -1
     */
    @Override
    public int getTerritoryForAttack(Territory srcTerritory, Territory dstTerritory, Player objPlayer,
                                     Integer attackerDice, Integer defenderDice) {
        return -1;
    }

    /**
     * @param map               Map
     * @param playerTerritories Player territories
     * @param objPlayer         Player object
     * @return check
     */
    @Override
    public int getTerritoryForAttack(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer) {
        int max = 0;
        Territory temp = null;
        Integer ableToAttack = -1;

        for (int ctr = 0; ctr < playerTerritories.size(); ctr++) {
            if (playerTerritories.get(ctr).getArmies() > max) {
                max = playerTerritories.get(ctr).getArmies();
                temp = playerTerritories.get(ctr);
            }
        }

        Territory target = null;
        if(temp == null) {
        	return ableToAttack;
        }
        ArrayList<String> adjacent = temp.getAdjacentCountries();

        for (int ctr = 0; ctr < adjacent.size(); ctr++) {
            Territory adja = objPlayer.getGameConfig().getMapObj().getDictTerritory().get(adjacent.get(ctr));
            if (adja.getOwner() != temp.getOwner()) {
                target = adja;
                while ((adja.getOwner() != temp.getOwner()) && (temp.getArmies() > 1)) {
                    Integer attackerDice = 0;
                    Integer defenderDice = 0;

                    if (temp.getArmies() >= 4) {
                        attackerDice = 3;
                    } else if (temp.getArmies() == 3) {
                        attackerDice = 2;
                    } else if (temp.getArmies() == 2) {
                        attackerDice = 1;
                    } else {
                        break;
                    }


                    if (adja.getArmies() >= 2) {
                        defenderDice = 2;
                    } else if (adja.getArmies() == 1) {
                        defenderDice = 1;
                    } else {
                        break;
                    }

                    objPlayer.performAttack(attackerDice, defenderDice,
                            objPlayer.getGameConfig().getMapObj().getDictContinents(), temp, target);
                    ableToAttack = 0;
                }
            }
        }

        return ableToAttack;
    }

    /**
     * @param territory Territory
     * @param objPlayer Player object
     * @return -1
     */
    @Override
    public int getTerritoryForReinforcement(Territory territory, Player objPlayer) {
        return -1;
    }

    /**
     * @return Player Type
     */
    @Override
    public Integer getPlayerType() {
        return playerType;
    }
}
