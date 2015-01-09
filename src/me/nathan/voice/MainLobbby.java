package me.nathan.voice;



import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bukkit.ChatColor.LIGHT_PURPLE;
import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class MainLobbby extends JavaPlugin  implements Listener{
    public final Logger logger = Logger.getLogger("Minecraft");
    private Scoreboard board;
    private Objective o;
    private Score s;


    public void onEnable(){
        PluginDescriptionFile pdfFile = this.getDescription();

        this.logger.info(pdfFile.getName() + " Version " +  pdfFile.getVersion() +" : has Been Enabled");

        getServer().getPluginManager().registerEvents(this, this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        saveConfig();




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


    public void nav(Player player){
       Inventory nav =  Bukkit.createInventory(null, 27, starter());


        player.openInventory(nav);
    }



    /* EventHandlers
    * onJoin(PlayerJoinEvent)
    * onClick(InventoryClickEvent)
    * itmeDrop (PlayerDropItemEvent)
    * onBreak (BlockBreakEvent)
    * onClick (PlayerInteractEvent)
    * onServerPing (ServerListPingEvent)
    * */

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();

        p.getInventory().clear();
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassm = compass.getItemMeta();
        compassm.setDisplayName(ChatColor.RED + "Navigation " + ChatColor.GRAY + "(Right Click)");
        compass.setItemMeta(compassm);


        ItemStack pinfo = new ItemStack(Material.SKULL_ITEM ,1, (short) SkullType.PLAYER.ordinal());
        SkullMeta pinfom = (SkullMeta) pinfo.getItemMeta();
        pinfom.setOwner(p.getName());
        pinfom.setDisplayName(ChatColor.RED + "Player Settings " + ChatColor.GRAY + "(Right Click)");
        pinfo.setItemMeta(pinfom);

        ItemStack particles = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta particlesM = particles.getItemMeta();
        particlesM.setDisplayName(ChatColor.RED + "Particles Menu " + ChatColor.GRAY + "(Right Click)");
        particles.setItemMeta(particlesM);


        p.getInventory().setItem(8, particles);
        p.getInventory().setItem(0, compass);
        p.getInventory().setItem(1, pinfo);
        p.setExp(-10);
        p.setLevel(-10);
        p.setFoodLevel(20);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 1, false));

        if (!getConfig().isSet("players." + p.getName()) ){
            getConfig().addDefault("players." + p.getName(), "");
            getConfig().addDefault("players." + p.getName() + ".hasSnowEnabled", true);
            getConfig().set("players." + p.getName() + ".hasSnowEnabled", true);
            saveConfig();

        }


        if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(true)){
            p.setPlayerWeather(WeatherType.DOWNFALL);
            getConfig().set("players." + p.getName() + ".hasSnowEnabled", true);
            saveConfig();
        }else if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(false)){
            p.setPlayerWeather(WeatherType.CLEAR);
            getConfig().set("players." + p.getName() + ".hasSnowEnabled", false);
            saveConfig();

        }




    }


    @EventHandler
    public void onFood(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        /* Snow / Weather Toggle >> Start */
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA  + "Toggle snow" + ChatColor.RED + " off" + ChatColor.AQUA + ".") ||
            e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA  + "Toggle snow" + ChatColor.GREEN + " on" + ChatColor.AQUA + ".")){
            if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(false)){
                p.closeInventory();
                p.setPlayerWeather(WeatherType.DOWNFALL);
                getConfig().set("players." + p.getName() + ".hasSnowEnabled", true);
                saveConfig();
            }else if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(true)){
                p.setPlayerWeather(WeatherType.CLEAR);
                getConfig().set("players." + p.getName() + ".hasSnowEnabled", false);
                saveConfig();
                p.closeInventory();
            }

        }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Close Menu")){
            p.closeInventory();
        }
    }
    @EventHandler
    public void itemDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();



    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(!p.isOp()){
            e.setCancelled(true);
        }
    }


    Logger log = Logger.getLogger("Minecraft");

    @EventHandler
    public void click(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
            if(a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_AIR)){
                if(e.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Player Settings " + ChatColor.GRAY + "(Right Click)")){
                    openPlayerSettingsMenu(p);



                }else if (e.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Particles Menu " + ChatColor.GRAY + "(Right Click)")){
                    openParticlesMenu(p);
                }
            }
     }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.SKULL_ITEM){
            e.setCancelled(true);
        }
        }
    public void openPlayerSettingsMenu(Player p){
        Inventory playerSettings = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.AQUA + "Your settings:");

        ItemStack snow = new ItemStack(Material.SNOW_BALL);
        ItemMeta snowmeta = snow.getItemMeta();
        ArrayList snowL = new ArrayList();
        if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(false)){
            snowmeta.setDisplayName(ChatColor.AQUA  + "Toggle snow" + ChatColor.GREEN + " on" + ChatColor.AQUA + ".");
            snowL.add(ChatColor.AQUA + "Snow is currently " + ChatColor.RED +  "off" + ChatColor.AQUA + ".");
        }else if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(true)) {
            snowmeta.setDisplayName(ChatColor.AQUA  + "Toggle snow" + ChatColor.RED + " off" + ChatColor.AQUA + ".");
            snowL.add(ChatColor.AQUA + "Snow is currently " + ChatColor.GREEN +  "on" + ChatColor.AQUA + ".");
        }
        snowmeta.setLore(snowL);
        snow.setItemMeta(snowmeta);

        ItemStack close = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)14);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName(ChatColor.RED + "Close Menu");
        close.setItemMeta(closem);

        ItemStack pinfo = new ItemStack(Material.SIGN);
        ItemMeta pinfom = pinfo.getItemMeta();
        pinfom.setDisplayName(ChatColor.AQUA + p.getName() + ":"); //TODO Add username with prefix and info from DB to this.
        ArrayList pinfoL = new ArrayList();
        pinfoL.add(ChatColor.AQUA + "Current Server: " + p.getServer().getName());
        Entity e = p;
        pinfom.setLore(pinfoL);
        pinfo.setItemMeta(pinfom);

        playerSettings.setItem(22, close);
        playerSettings.setItem(12, pinfo);
        playerSettings.setItem(10, snow);

        p.openInventory(playerSettings);
    }


    public void openParticlesMenu(Player p){
        Inventory particlesMenu = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.AQUA + "Particles Menu");



        p.openInventory(particlesMenu);

    }

    @EventHandler
    public void onExp(PlayerExpChangeEvent e){
        Player p = e.getPlayer();
        p.damage(4);
        p.setExp(-10);
        p.setLevel(-10);
    }


    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onDeath(EntityDamageEvent e){
        e.setCancelled(true);
    }



    /*Methods*/


    public boolean hasSnow(Player p){
       if(getConfig().get("players." + p.getName() + "hasSnowEnabled").equals(true)){
           return true;
       } else{
           return false;
       }
    }

    private void shootFireworks(Player player){


            Firework fw = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);

            FireworkMeta fm = fw.getFireworkMeta();

            Random r = new Random();
            int ftype = r.nextInt(5) + 1;
            FireworkEffect.Type type = null;
            switch (ftype){
                case 1:
                    type = FireworkEffect.Type.STAR;
                    break;
                case 2:
                    type = FireworkEffect.Type.BALL_LARGE;
                    break;
                case 3:
                    type = FireworkEffect.Type.BURST;
                    break;
                case 4:
                    type = FireworkEffect.Type.CREEPER;
                    break;
                case 5:
                    type = FireworkEffect.Type.BALL;
                    break;

            }
            int c1i = r.nextInt(16) + 1;
            int c2i = r.nextInt(16) + 1;
            Color c1 = getColour(c1i);
            Color c2 = getColour(c2i);
            FireworkEffect effect = FireworkEffect.builder()
                    .flicker(r.nextBoolean()).withColor(c1).withFade(c2)
                    .with(type).trail(r.nextBoolean()).build();
            fm.addEffect(effect);
            int power = r.nextInt(2)+1;
            fm.setPower(power);
            fw.setFireworkMeta(fm);

    }


    public Color getColour(int c){
        switch(c){
            default:
            case 1:return Color.AQUA;
            case 2:return Color.BLACK;
            case 3:return Color.BLUE;
            case 4:return Color.FUCHSIA;
            case 5:return Color.GRAY;
            case 6:return Color.GREEN;
            case 7:return Color.LIME;
            case 8:return Color.MAROON;
            case 9:return Color.NAVY;
            case 10:return Color.OLIVE;
            case 11:return Color.PURPLE;
            case 12:return Color.RED;
            case 13:return Color.SILVER;
            case 14:return Color.TEAL;
            case 15:return Color.WHITE;
            case 16:return Color.YELLOW;



        }
    }






    public static String starter(){
        return ChatColor.RED + "[ " + ChatColor.DARK_PURPLE + "VOICE" + ChatColor.RED + "]";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(commandLabel.equalsIgnoreCase("snow")){
                if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(false)){
                    getConfig().set("players." + p.getName() + ".hasSnowEnabled" , true);
                    p.setPlayerWeather(WeatherType.DOWNFALL);
                    saveConfig();
                    p.sendMessage(ChatColor.AQUA + "Snow is now toggled " + ChatColor.GREEN + "on" + ChatColor.AQUA + ".");
                }else if(getConfig().get("players." + p.getName() + ".hasSnowEnabled").equals(true)) {
                    getConfig().set("players." + p.getName() + ".hasSnowEnabled" ,false);
                    p.sendMessage(ChatColor.AQUA + "Snow is now toggled " + ChatColor.RED + "off" + ChatColor.AQUA + ".");
                    p.setPlayerWeather(WeatherType.CLEAR);
                    saveConfig();
                }
            }
        }else{
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        }

        return false;
    }
}





