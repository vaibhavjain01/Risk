package src.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Cheater: computer player strategy whose reinforce() method doubles the number of armies on all its countries, whose attack() method automatically
 * conquers all the neighbors of all its countries, and whose fortify() method doubles the number of armies on its countries that have neighbors that belong to other players.
 */
public class Cheater implements Strategy, Serializable {
    private static final long serialVersionUID = -5417659417247726299L;
    GenericFunctions genfunObj = new GenericFunctions();

    private Integer playerType = 4;

    /**
     * @return Player type
     */
    @Override
    public Integer getPlayerType() {
        return playerType;
    }

    /**
     * @param playerTerritories Player territories
     * @param objPlayer Player object
     * @return 0
     */
    @Override
    public int getTerritoryForReinforcement(ArrayList<Territory> playerTerritories, Player objPlayer) {
        if (objPlayer.getGameConfig().getGamePhase() == genfunObj.GAMEPHASESTARTUP) {
            if (objPlayer.getArmies() > 0) {
                objPlayer.setArmies(objPlayer.getArmies() - 1);
                System.out.println("Armies " + objPlayer.getArmies());
                int tmp = genfunObj.genRandomNumber(0, playerTerritories.size() - 1);
                playerTerritories.get(tmp).increaseArmies();
            } else {
                objPlayer.getGameConfig().nextPlayerOrPhase();
            }
        } else {
            for (int ctr = 0; ctr < playerTerritories.size(); ctr++) {
                int num = playerTerritories.get(ctr).getArmies();
                playerTerritories.get(ctr).setArmies(num * 2);
                System.out.println("Cheater Doubled Armies");
            }
            objPlayer.setArmies(0);
            objPlayer.getGameConfig().nextPlayerOrPhase();
        }
        return 0;
    }

    /**
     * @param territory Territory
     * @param objPlayer PLayer object
     * @return -1
     */
    @Override
    public int getTerritoryForReinforcement(Territory territory, Player objPlayer) {
        return -1;
    }

    /**
     * @param srcTerritory Source territory
     * @param dstTerritory Destination territory
     * @param objPlayer PLayer object
     * @return -1
     */
    @Override
    public int getTerritoryForFortification(Territory srcTerritory, Territory dstTerritory, Player objPlayer) {
        return -1;
    }

    /**
     * @param map Map
     * @param playerTerritories Player territory
     * @param objPlayer Player object
     * @return 0
     */
    @Override
    public int getTerritoryForFortification(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer) {
        for (int ctr = 0; ctr < playerTerritories.size(); ctr++) {
            int num = playerTerritories.get(ctr).getArmies();
            ArrayList<String> adjacent = playerTerritories.get(ctr).getAdjacentCountries();

            for (int ctr2 = 0; ctr2 < adjacent.size(); ctr2++) {
                Territory temp = objPlayer.getGameConfig().getMapObj().getDictTerritory().get(adjacent.get(ctr2));
                if (temp.getOwner() != objPlayer.id) {
                    playerTerritories.get(ctr).setArmies(playerTerritories.get(ctr).getArmies() * 2);
                    System.out.println("Cheater cheated to place double armies on "
                            + "territories with neighbours belonging to other players.");
                    break;
                }
            }
        }
        return 0;
    }

    /**
     * @param srcTerritory Source territory
     * @param dstTerritory Destination territory
     * @param objPlayer PLayer object
     * @param attackerDice Attacker dice
     * @param defenderDice Defender dice
     * @return 01
     */
    @Override
    public int getTerritoryForAttack(Territory srcTerritory, Territory dstTerritory, Player objPlayer,
                                     Integer attackerDice, Integer defenderDice) {
        return -1;
    }

    /**
     * @param map Map
     * @param playerTerritories Player territories
     * @param objPlayer Player object
     * @return 0
     */
    @Override
    public int getTerritoryForAttack(Maps map, ArrayList<Territory> playerTerritories, Player objPlayer) {
        Map<String, Continent> dictContinents = objPlayer.getGameConfig().getMapObj().getDictContinents();
        for (int ctr = 0; ctr < playerTerritories.size(); ctr++) {
            Territory srcTerritory = playerTerritories.get(ctr);
            ArrayList<String> adjacent = playerTerritories.get(ctr).getAdjacentCountries();
            for (int ctr2 = 0; ctr2 < adjacent.size(); ctr2++) {
                Territory temp = objPlayer.getGameConfig().getMapObj().getDictTerritory().get(adjacent.get(ctr2));
                if (temp.getOwner() != objPlayer.id) {
                    System.out.println("Player " + srcTerritory.getOwner().toString() +
                            " captured player " + temp.getOwner().toString() + "'s Territory");
                    Player players[] = objPlayer.getGameConfig().getPlayers();
                    players[temp.getOwner()].removeTerritory(temp.getName());
                    temp.setOwner(objPlayer.id);
                    temp.increaseArmies();
                    srcTerritory.decreaseArmies();
                    objPlayer.getTerritories().add(temp);

                    for (String continent : dictContinents.keySet()) {
                        if (continent.equals(temp.getContinent())) {
                            if (dictContinents.get(continent).isContinentCaptured(srcTerritory.getOwner())) {
                                System.out.println("Player " + srcTerritory.getOwner().toString() + " captured continent " + continent);
                                System.out.println("Player will be awarded " + dictContinents.get(continent).getArmyReward() +
                                        " additional armies");
                            }
                        }
                    }
                }
            }
        }

        objPlayer.getGameConfig().nextPlayerOrPhase();
        return 0;
    }
}
