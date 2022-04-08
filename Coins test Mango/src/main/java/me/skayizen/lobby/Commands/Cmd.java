package me.skayizen.lobby.Commands;

import me.skayizen.lobby.Main;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {


        if ((sender instanceof Player)) {
            Player p = (Player) sender;


            if(cmd.getName().equalsIgnoreCase("coins")) {
                if (sender.isOp()) {
                    if (args.length >= 2) {
                        if (Main.getInstance().isInt(args[1])) {
                            int num = Integer.parseInt(args[1]);
                            p.sendMessage(num +"");

                            if (args[0].equalsIgnoreCase("add")) {

                                Main.getInstance().coinsMongoDb(p.getPlayer(), num, "add");
                                p.sendMessage("§c" + num + "§6 Has been added ");


                            } else if (args[0].equalsIgnoreCase("remove")) {
                                Main.getInstance().coinsMongoDb(p.getPlayer(), num, "remove");
                                p.sendMessage("§c" + num + "§6 Has been remove ");


                            } else {
                                p.sendMessage("§cError §f:§c /coins add§f/§cadd§f/§creset §f+§c Integer");
                            }
                        } else {
                            p.sendMessage("§cError §F: §cYou need to put Integer ");
                        }


                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("reset")) {
                            Main.getInstance().coinsMongoDb(p.getPlayer(), 0, "reset");
                            p.sendMessage( "§6 Has been reset ");
                        } else {
                            p.sendMessage("§cError §f:§c /coins add§f/§cadd§f/§creset §f+§c Integer");
                        }
                    } else {
                        p.sendMessage("§cError §f:§c /coins add§f/§cadd§f/§creset §f+§c Integer");
                    }


                } else {
                    p.sendMessage("§cError §F: §cTu n'as pas la permission");
                }
            }

        }



        return false;
    }
}
