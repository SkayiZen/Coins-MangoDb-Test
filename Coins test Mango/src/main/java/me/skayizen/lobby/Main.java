package me.skayizen.lobby;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.skayizen.lobby.Commands.Cmd;
import me.skayizen.lobby.Events.Join;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    public static Main instance;


    public static Main getInstance() {

        return instance;
    }
    public HashMap<Player, Integer> coins = new HashMap<Player, Integer>();





    // public static JedisPool jedisPool;


    @Override public void onEnable() {
        instance = this;
        registerEvents();
        mongoConnect();
        cmd();
        Bukkit.broadcastMessage("§8[§cTomoe§8] §cLoad");
    }

    public void cmd(){
        getCommand("coins").setExecutor(new Cmd());
    }
    public void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Join(), this);

    }




    @Override public void onDisable() {}


    public static MongoCollection<Document> collection;
    public static MongoDatabase mongoDatabase;

    public static void mongoConnect(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://SkayiZen:kalou@mongodb.nivfl.mongodb.net/MongoDB?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase("Tomoe");
        collection = mongoDatabase.getCollection("information");

        System.out.println("Database Connected");
        Bukkit.broadcastMessage("§aDatabase connect");
    }

    public void coinsMongoDb(Player p, int coins, String string){
        Main.getInstance().coins.putIfAbsent(p.getPlayer(), 0);
        int coinss = Main.getInstance().coins.get(p.getPlayer());

        if(string.equalsIgnoreCase("add")){
            Main.getInstance().coins.put(p.getPlayer(), coinss + coins);
        } else if(string.equalsIgnoreCase("remove")){
            Main.getInstance().coins.put(p.getPlayer(), coinss - coins);
        } else if(string.equalsIgnoreCase("reset")){
            Main.getInstance().coins.put(p.getPlayer(), 0);
        }

        Document found = (Document) Main.collection.find(new org.bson.Document("UUID", p.getUniqueId().toString())).first();
        Document foundCoins = new Document("Coins", Main.getInstance().coins.get(p.getPlayer()));
        Document updateCoins = new Document("$set", foundCoins);

        Main.collection.updateOne(found, updateCoins);
    }


    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
