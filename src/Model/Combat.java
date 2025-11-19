package Model;

import java.util.Scanner;
import Model.Monster;
import Model.Player;
import Model.Artifact;

public class Combat
{
    private Player player;
    private Monster monster;
    public Combat(Player player, Monster monster)
    {
        this.player = player;
        this.monster = monster;
    }
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

    public static boolean roll(int percentage)
    {
        return Math.random() * 100 < percentage;
    }

    public static void runAway(Player player, Monster monster)
    {
        String runMessage = monster.getRunstatement();
        System.out.println(runMessage);

        player.takeDamage(monster.getDamagedUnstunned());
    }

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