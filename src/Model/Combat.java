package Model;

import java.util.Scanner;
import Model.Monster;
import Model.Player;
import Model.Artifact;

/**
 * The Combat class handles all combat-related interactions between the player and monsters.
 * It provides methods for engaging in fights, calculating damage, determining combat outcomes,
 * and handling escape attempts. This class is central to the game's combat mechanics.
 *
 * @author Justice League Team
 * @version Fall 2025
 */
public class Combat
{
    /**
     * The player character involved in the combat.
     */
    private Player player;

    /**
     * The monster that the player is fighting against.
     */
    private Monster monster;

    /**
     * Constructs a new Combat instance with the specified player and monster.
     *
     * @param player The player character that will participate in combat
     * @param monster The monster that the player will fight against
     */
    public Combat(Player player, Monster monster)
    {
        this.player = player;
        this.monster = monster;
    }

    /**
     * Engages in a fight between the player and monster with an optional artifact.
     * Determines the outcome based on win chances, handles damage calculation, and manages
     * item drops when the player wins.
     *
     * @param player The player participating in combat
     * @param monster The monster being fought
     * @param artifact An optional artifact that may affect combat (can be null)
     * @return true if the player wins the fight, false if the player is defeated
     */
    public static boolean engageFight(Player player, Monster monster, Artifact artifact)
    {
        boolean hasWeapon = artifact != null;
        boolean isStunned = hasWeapon && artifact.getStun() > 0;

        int winChance = hasWeapon ? monster.getWinChanceWithItem() : monster.getWinChanceWithoutItem();
        boolean playerWin = roll(winChance);

        if (playerWin)
        {
            System.out.println("You stunned " + monster.getMonsterName() + "!");
            if (monster.getItemDrop() != null && !monster.getItemDrop().equalsIgnoreCase("Nothing"))
            {
                System.out.println("Monster dropped: " + monster.getItemDrop());
            }
            return true;
        }
        else
        {
            int damage = isStunned ? monster.getDamagedStunned() : monster.getDamagedUnstunned();

            player.takeDamage(damage);
            System.out.println("You were defeated by " + monster.getMonsterName() + "!");
            return false;
        }
    }

    /**
     * Simulates a dice roll to determine if an event occurs based on a percentage chance.
     *
     * @param percentage The percentage chance (0-100) for success
     * @return true if the roll is successful, false otherwise
     */
    public static boolean roll(int percentage)
    {
        return Math.random() * 100 < percentage;
    }

    /**
     * Handles the player's attempt to run away from combat with a monster.
     * Displays the monster's escape message and inflicts damage on the player.
     *
     * @param player The player attempting to escape
     * @param monster The monster the player is escaping from
     */
    public static void runAway(Player player, Monster monster)
    {
        String runMessage = monster.getRunstatement();
        System.out.println(runMessage);

        player.takeDamage(monster.getDamagedUnstunned());
    }

    /**
     * Extracts the damage value from a combat message string.
     * The damage should be in the format "(-X hp)" where X is the damage amount.
     *
     * @param message The combat message containing damage information
     * @return The extracted damage value, or 0 if no damage could be extracted
     */
    public static int extractDamage(String message)
    {
        int start = message.indexOf("(-");
        int end = message.indexOf("hp)");

        if (start != -1 && end != -1 && end > start + 2)
        {
            String num = message.substring(start + 2, end).trim();
            try
            {
                return Integer.parseInt(num);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Failed to parse damage from: " + message);
            }
        }
        return 0;
    }

}
