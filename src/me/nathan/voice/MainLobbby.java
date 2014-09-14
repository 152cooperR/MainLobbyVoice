package me.nathan.voice;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Logger;

public class MainLobbby extends JavaPlugin  implements Listener{
    public final Logger logger = Logger.getLogger("Minecraft");
    public static MainLobbby plugin;
    public void onEnable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(ChatColor.GREEN +(pdfFile.getName() + " Version " +  pdfFile.getVersion() +" :Has Been Enabled"));
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        player.getInventory().clear();

        int x;
        int y;
        int z;
        x  = 0;
        y=67;
        z=2;
        player.sendMessage(starter() + "Welcome To Voice!");
        Location spawn = new Location(player.getWorld(), x, y, z);
        player.teleport(spawn);


    }

    public static String starter() {
        return ChatColor.DARK_PURPLE + "[" + ChatColor.RED + "V" + ChatColor.DARK_PURPLE + "]: " + ChatColor.GRAY;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = (Player) sender;
       if(commandLabel.equalsIgnoreCase("nav")){
        openNav(player);

       }
        return false;
    }

    public static void openNav(Player player){
        Inventory nav = Bukkit.createInventory(null,54,starter() + "Nav");
        ItemStack edg = new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)14);
        ItemMeta edgm = edg.getItemMeta();
        edgm.setDisplayName(starter() + "Nav");
        edg.setItemMeta(edgm);
        nav.setItem(0, edg);
        nav.setItem(1, edg);
        nav.setItem(2,edg);
        nav.setItem(3,edg);
        nav.setItem(4,edg);
        nav.setItem(5,edg);
        nav.setItem(6,edg);
        nav.setItem(7,edg);
        nav.setItem(8,edg);
        nav.setItem(9,edg);
        nav.setItem(18,edg);
        nav.setItem(36,edg);
        nav.setItem(45,edg);
        nav.setItem(17,edg);
        nav.setItem(27,edg);
        nav.setItem(26,edg);
        nav.setItem(35,edg);
        nav.setItem(44,edg);
        nav.setItem(52,edg);
        nav.setItem(46,edg);
        nav.setItem(47,edg);
        nav.setItem(48,edg);
        nav.setItem(49,edg);
        nav.setItem(50,edg);
        nav.setItem(51,edg);
        nav.setItem(53,edg);

        ItemStack v = new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)10);
        ItemMeta vm = v.getItemMeta();
        vm.setDisplayName(ChatColor.DARK_PURPLE +"VOICE");
        v.setItemMeta(vm);
        nav.setItem(11, v);
        nav.setItem(20, v);
        nav.setItem(30, v);
        nav.setItem(40, v);
        nav.setItem(15, v);
        nav.setItem(24, v);
        nav.setItem(32,v);


        ItemStack info = new ItemStack(Material.SKULL_ITEM);
        ItemMeta infom = info.getItemMeta();
        infom.setDisplayName(ChatColor.GOLD + "Player Info");
        info.setItemMeta(infom);
        nav.setItem(37,info);

        ItemStack factions = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta factionsm = factions.getItemMeta();
        factionsm.setDisplayName(starter() + ChatColor.RED + "Factions!");
        factions.setItemMeta(factionsm);
        nav.setItem(22, factions);

        player.openInventory(nav);
    }

    @EventHandler
    public void invClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(starter() + "Nav")){
            event.setCancelled(true);
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE +"VOICE")){
            event.setCancelled(true);
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Player Info")){
            event.setCancelled(true);
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(starter() + ChatColor.RED + "Factions!")){
            player.closeInventory();
            sendToServer(player, "factions");
        }
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
@EventHandler
    public void Move(PlayerMoveEvent event){
    Player player = event.getPlayer();

    if(player.getLocation().getY()==30 || player.getLocation().getY() < 30){
        if(!player.isOp()){
            int x;
            int y;
            int z;
            x =0;
            y=67;
            z=2;
            Location spawn = new Location(player.getWorld(), x, y, z);
            player.teleport(spawn);
        }
    }
}

    }


