package me.skayizen.lobby.Events;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.skayizen.lobby.Main;

import org.bson.Document;

import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.*;


public class Join implements Listener {

    @EventHandler public void joinDbInfo(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Document found = (Document) Main.collection.find(new org.bson.Document("UUID", p.getUniqueId().toString())).first();

        Main.getInstance().coins.putIfAbsent(p.getPlayer(), 0);

        if(found == null){
            Document info = new Document("UUID", p.getUniqueId().toString());
            info.append("Pseudo", p.getName());
            info.append("IP", p.getAddress().toString());
            Main.getInstance().coins.putIfAbsent(p.getPlayer(), 0);
            info.append("Coins", Main.getInstance().coins.get(p.getPlayer()));

            Main.collection.insertOne(info);

            p.sendMessage("You have been added to database");

        }
        Main.getInstance().coins.put(p.getPlayer(), (Integer) found.get("Coins"));

    }

    @EventHandler public void chat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
       e.setCancelled(true);
        Bukkit.broadcastMessage("§c§l巴 §c" + p.getName() + " §F: §c" + e.getMessage().toString());
    }

    @EventHandler public void joinServer(PlayerJoinEvent e){


    }

}
