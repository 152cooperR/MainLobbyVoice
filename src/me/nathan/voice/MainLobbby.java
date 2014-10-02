package me.nathan.voice;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Logger;

public class MainLobbby extends JavaPlugin  implements Listener{
    public final Logger logger = Logger.getLogger("Minecraft");
    public static MainLobbby plugin;
    public int parpos = 0;
    public void onEnable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(ChatColor.GREEN +(pdfFile.getName() + " Version " +  pdfFile.getVersion() +" :Has Been Enabled"));
        getServer().getPluginManager().registerEvents(this, this);
    }


    public void sendToServer(Player player, String targetServer){

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try{
            out.writeUTF("Connect");
            out.writeUTF(targetServer);
        }catch (Exception e){
            e.printStackTrace();
        }
        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
    }

    }


