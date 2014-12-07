package me.nathan.voice;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.*;
import java.util.logging.Logger;

import static org.bukkit.ChatColor.LIGHT_PURPLE;

public class MainLobbby extends JavaPlugin  implements Listener{
    public final Logger logger = Logger.getLogger("Minecraft");
    private Scoreboard board;
    private Objective o;
    private Score s;
    /**
     * Added gitignore.
     * */

    public void onEnable(){
        board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

        o = board.registerNewObjective(ChatColor.GREEN + "Skywars Tokens: ","dummy");
        o.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "VOIC3");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        s = o.getScore(ChatColor.GREEN +  "Skywars Tokens: ");

        s.setScore(1);


        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(ChatColor.GREEN +(pdfFile.getName() + " Version " +  pdfFile.getVersion() +" :Has Been Enabled"));
        getServer().getPluginManager().registerEvents(this, this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        saveDefaultConfig();


        getConfig();


        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

            public void run() {
                double x1 = 14.5;
                double y1 = 66;
                double z1 = -0.5;

                double x2 = 14.5;
                double y2 = 66;
                double z2 = -30.5;

                double x3 = -15.5;
                double y3 = 66;
                double z3 = -30.5;

                double x4 = -15.5;
                double y4 = 66;
                double z4 = -0.5;


                Location spot1 = new Location(Bukkit.getWorld("world"), x1, y1, z1);
                Location spot2 = new Location(Bukkit.getWorld("world"), x2, y2, z2);
                Location spot3 = new Location(Bukkit.getWorld("world"), x3, y3, z3);
                Location spot4 = new Location(Bukkit.getWorld("world"), x4, y4, z4);



                Byte blockData = 0x0;


                FallingBlock fallingBlock1 = Bukkit.getWorld("world").spawnFallingBlock(spot1, Material.LAVA, blockData);
                FallingBlock fallingBlock2 = Bukkit.getWorld("world").spawnFallingBlock(spot2, Material.LAVA, blockData);
                FallingBlock fallingBlock3 = Bukkit.getWorld("world").spawnFallingBlock(spot3,  Material.LAVA, blockData);
                FallingBlock fallingBlock4 = Bukkit.getWorld("world").spawnFallingBlock(spot4,  Material.LAVA, blockData);


                float block1x = 0;
                double block1y = 1.5;
                float block1z = 0;

                fallingBlock1.setDropItem(false);
                fallingBlock2.setDropItem(false);
                fallingBlock3.setDropItem(false);
                fallingBlock4.setDropItem(false);

                fallingBlock1.setVelocity(new Vector(block1x,block1y,block1z));
                fallingBlock2.setVelocity(new Vector(block1x,block1y,block1z));
                fallingBlock3.setVelocity(new Vector(block1x, block1y, block1z));
                fallingBlock4.setVelocity(new Vector(block1x,block1y,block1z));

                fallingBlock1.setFallDistance(5);
                fallingBlock2.setFallDistance(5);
                fallingBlock3.setFallDistance(5);
                fallingBlock4.setFallDistance(5);

            }
        },10L, 60l);

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

    public void hideMenu(Player player){
        Inventory vis = Bukkit.createInventory(null, 9,ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Click to select visibility");
        ItemStack show = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)5);
        ItemMeta showm = show.getItemMeta();
        showm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Show Players");
        show.setItemMeta(showm);

        ItemStack hide = new ItemStack(Material.STAINED_GLASS_PANE,1 ,(byte)14);
        ItemMeta hidem = hide.getItemMeta();
        hidem.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Hide Players");
        hide.setItemMeta(hidem);

        vis.setItem(3, show);
        vis.setItem(5,hide);
        player.openInventory(vis);

    }

    public void nav(Player player){
       Inventory nav =  Bukkit.createInventory(null, 27, starter());
       ItemStack skywars = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta skywarsmeta = skywars.getItemMeta();
        skywarsmeta.setDisplayName(ChatColor.RED+ "" + ChatColor.BOLD + "SKYWARS");

        ItemStack survival = new ItemStack(Material.GRASS);
        ItemMeta survivalm = skywars.getItemMeta();
        survivalm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SURVIVAL");
        survival.setItemMeta(survivalm);

        ItemStack staff = new ItemStack(Material.SKULL_ITEM);
        ItemMeta staffm = staff.getItemMeta();
        staffm.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "[STAFF]");
        staff.setItemMeta(staffm);

        ItemStack rules = new ItemStack(Material.BOOK);
        ItemMeta rulesm = rules.getItemMeta();
        rulesm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "RULES");
        rules.setItemMeta(rulesm);

        ItemStack corner = new ItemStack(Material.BLAZE_ROD);
        ItemMeta cornerm = corner.getItemMeta();
        cornerm.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "VOICE");
        corner.setItemMeta(cornerm);

        ItemStack bars = new ItemStack(Material.IRON_FENCE);
        ItemMeta barsm = bars.getItemMeta();
        barsm.setDisplayName(starter());
        bars.setItemMeta(barsm);

        ItemStack soon = new ItemStack(Material.BEACON);
        ItemMeta soonm = soon.getItemMeta();
        soonm.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Future games:");
        ArrayList soonl = new ArrayList();
        soonl.add(ChatColor.RED +  "  • SkyBlock!");
        soonl.add(ChatColor.RED + "  • Survival!");
        soonm.setLore(soonl);
        soon.setItemMeta(soonm);

        ItemStack help = new ItemStack(Material.COMMAND);
        ItemMeta helpm = help.getItemMeta();
        ArrayList helpl = new ArrayList();
        helpm.setDisplayName(ChatColor.DARK_AQUA + "Need help? Found a bug?");
        helpl.add(ChatColor.AQUA + "Tweet @NRprogarmming to report a bug");
        helpl.add(ChatColor.AQUA + "Messgae one of our modertors for help");
        helpm.setLore(helpl);
        help.setItemMeta(helpm);


        skywars.setItemMeta(skywarsmeta);

        nav.setItem(9, staff);
        nav.setItem(11, skywars);
        nav.setItem(15, survival);
        nav.setItem(17, rules);
        nav.setItem(22, soon);
        nav.setItem(4, help);


        nav.setItem(0, corner);
        nav.setItem(8, corner);
        nav.setItem(18, corner);
        nav.setItem(26, corner);

        player.openInventory(nav);
    }

    public void openParGUI(Player p){
        Inventory par = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "PARTICLES");

    }



    private static String starter(){
        return LIGHT_PURPLE + "Voice";

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.setLevel(0);
        ItemStack ss =  new ItemStack(Material.COMPASS);
        ItemMeta ssm = ss.getItemMeta();
        ssm.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SERVER SELECTOR" + ChatColor.GRAY + " (Right Click)");
        ss.setItemMeta(ssm);

        ItemStack hp = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta hpm = hp.getItemMeta();
        hpm.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "HIDE PLAYERS" + ChatColor.GRAY + " (Right Click)");
        hp.setItemMeta(hpm);

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)2);
        ItemMeta fillerm = filler.getItemMeta();
        fillerm.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "VOICE");
        filler.setItemMeta(fillerm);



        if(!p.isOp()){
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().setItem(0,filler);
            p.getInventory().setItem(3, ss);
            p.getInventory().setItem(5, hp);
            p.getInventory().setItem(8,filler);

        }else{
            p.setGameMode(GameMode.CREATIVE);
            ItemStack particles = new ItemStack(Material.NETHER_STAR);
            ItemMeta particlesM = particles.getItemMeta();
            particlesM.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "PARTICLES"+ ChatColor.GRAY + " (Right Click)");
            particles.setItemMeta(particlesM);
            p.getInventory().setItem(0,filler);
            p.getInventory().setItem(2, particles);
            p.getInventory().setItem(6, hp);
            p.getInventory().setItem(8,filler);
            p.getInventory().setItem(4, ss);

        }
        double x = -0.5;
        double y = 61;
        double z = -15.5;
        Location spawn = new Location(p.getWorld(), x, y, z);
        p.setScoreboard(board);


        p.teleport(spawn);

    }
    public void hideAllPlayers(Player player){
        for(Player p : Bukkit.getOnlinePlayers()){

            player.hidePlayer(p);

            p.closeInventory();

        }
    }

    public void showAllPlayers(Player player){
        for(Player p : Bukkit.getOnlinePlayers()){
            player.showPlayer(p);
            p.closeInventory();

        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();


        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Show Players")) {
            p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU CAN NOW SEE ALL PLAYERS!");
            showAllPlayers(p);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Hide Players")) {
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ALL PLAYERS HIDDEN!");
            hideAllPlayers(p);

        }

            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "SKYWARS")){
                p.closeInventory();
               sendToServer(p ,"skywars_lobby_1");

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "[STAFF]")) {
                p.closeInventory();
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Owners:");
                p.sendMessage(ChatColor.GOLD + " • voice1987");
                p.sendMessage(ChatColor.GOLD + " • voice97");
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Head Developer:");
                p.sendMessage(ChatColor.GOLD + " • NathanSDK (152cooperR)");
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Developers:");
                p.sendMessage(ChatColor.GOLD + " • kylewong178849");
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Admins:");
                p.sendMessage(ChatColor.GOLD + " • None");
            }

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "" + ChatColor.BOLD + "Future games:")){
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Need help? Found a bug?")){
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "" + ChatColor.BOLD + "RULES")){

            p.closeInventory();
            p.sendMessage(ChatColor.RED + "Rules:");
            p.sendMessage(ChatColor.GOLD + "1)");
            p.sendMessage(ChatColor.DARK_AQUA + "Use appropriate language.");
            p.sendMessage(ChatColor.DARK_GREEN + "This will result in (depending on severity) a mute, then if it continues a ban.");
            p.sendMessage(ChatColor.GOLD + "2)");
            p.sendMessage(ChatColor.DARK_AQUA + "Respect all players and staff");
            p.sendMessage(ChatColor.DARK_GREEN + "This will result in (depending on severity) a mute, then if it continues a ban.");
            p.sendMessage(ChatColor.GOLD + "3)");
            p.sendMessage(ChatColor.DARK_AQUA + "Do not hack, cheat, or exploit.");
            p.sendMessage(ChatColor.DARK_GREEN + "This will result in (depending on severity) a temp ban, then if it continues a perm ban.");
            p.sendMessage(ChatColor.GOLD + "4)");
            p.sendMessage(ChatColor.DARK_AQUA + "Finding a bug, and failing to report it and taking advantage of it.");
            p.sendMessage(ChatColor.DARK_GREEN + "This will result in (depending on severity) a 1 day ban, then if it continues a 3 day ban.");
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "VOICE")) {
            p.closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "SURVIVAL")){
            p.closeInventory();
            sendToServer(p, "survival");
        }
    }
    @EventHandler
    public void itemDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(!p.hasPermission("voic3.admin")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(!p.isOp()){
            e.setCancelled(true);
        }
    }
    public int hurtlvl = 0;

    public Map<Player, Location> playerLocation = new HashMap();
    public Map<Player, Entity> chairList = new HashMap();
    public Map<Player, Location> chairLocation = new HashMap();
    Logger log = Logger.getLogger("Minecraft");

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                    (!player.isInsideVehicle()) &&
                    (player.getItemInHand().getType() == Material.AIR)) {
                String blockMaterial = block.getType().name();
                for (String chairBlock : this.getConfig().getStringList("Chairs")) {
                    if (blockMaterial.equalsIgnoreCase(chairBlock)) {
                        if (!this.getConfig().getBoolean("UpsideDown")) {
                            if (block.getData() > 3) {
                                return;
                            }
                        }
                        int range = this.getConfig().getInt("Range");
                        if ((range > 0) &&
                                (player.getLocation().distance(block.getLocation()) - 1.0D > range)) {
                            return;
                        }
                        World world = player.getWorld();
                        this.playerLocation.put(player, player.getLocation());
                        Entity chair = world.spawnEntity(player.getLocation(), EntityType.ARROW);
                        this.chairList.put(player, chair);
                        chair.teleport(block.getLocation().add(0.5D, 0.2D, 0.5D));
                        this.chairLocation.put(player, chair.getLocation());
                        chair.setPassenger(player);
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }


    }
    @EventHandler
    public void click(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        if(e.getItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SERVER SELECTOR" + ChatColor.GRAY + " (Right Click)")){
            if(a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_AIR)){
                nav(p);
                e.getPlayer().playSound(p.getLocation(), Sound.CHICKEN_EGG_POP,1,1);
            }
        }else if(e.getItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "HIDE PLAYERS" + ChatColor.GRAY + " (Right Click)")){
            hideMenu(p);
        }

        if(p.getItemInHand().getType() == Material.EYE_OF_ENDER){
            e.setCancelled(true);
        }

        if(p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "PARTICLES"+ ChatColor.GRAY + " (Right Click)")){
            openParGUI(p);
        }







    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        if (this.playerLocation.containsKey(event.getPlayer()))
        {
            if (event.getPlayer().isSneaking())
            {
                Player player = event.getPlayer();
                final Player sit_player = player;
                final Location stand_location = (Location)this.playerLocation.get(player);
                Entity sit_chair = (Entity)this.chairList.get(player);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
                {
                    public void run()
                    {
                        sit_player.teleport(stand_location);
                        sit_player.setSneaking(false);
                    }
                }, 1L);
                event.setCancelled(true);
                this.playerLocation.remove(player);
                sit_chair.remove();
                this.chairList.remove(player);
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
        }
    }



    @EventHandler
    public void onjoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        e.setJoinMessage("Welcome to the server" + e.getPlayer().getName());
    }



    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if (this.playerLocation.containsKey(event.getPlayer()))
        {
            Player player = event.getPlayer();
            if ((player.getLocation() != this.playerLocation.get(player)) &&
                    (!player.isInsideVehicle()))
            {
                World world = player.getWorld();
                if (this.chairLocation.containsKey(player))
                {
                    Entity chair = world.spawnEntity((Location)this.chairLocation.get(player), EntityType.ARROW);
                    chair.setPassenger(player);
                }
            }
        }
    }



    @EventHandler
    public void move(PlayerMoveEvent e){
        Player p = e.getPlayer();
        ItemStack jump = new ItemStack(Material.WOOL,1 ,(byte)14);
        if(p.getLocation().subtract(0,1,0).getBlock().getType() == Material.SOUL_SAND){
            p.setVelocity(p.getLocation().getDirection().multiply(3).setY(2));
            p.playSound(p.getLocation(), Sound.PISTON_EXTEND ,1, 1);
            }
        if(p.getLocation().subtract(0,1,0).getBlock().getType() == Material.SPONGE){
            p.setVelocity(p.getLocation().getDirection().multiply(0).setY(2));
            p.playSound(p.getLocation(), Sound.SLIME_WALK, 1,-2);
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

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player p = (Player) sender;
        if(commandLabel.equalsIgnoreCase("f")){
            shootFireworks(p);
            p.sendMessage(ChatColor.AQUA + "You launched a firework!");
        }else if(commandLabel.equalsIgnoreCase("lobby")){
            if(args.length ==0){
                p.sendMessage(ChatColor.RED + "You are already connected to the main lobby!");
            }else if(args.length ==1){

                if(args[0].equalsIgnoreCase("skywars" )|| args[0].equalsIgnoreCase("sky")){
                    sendToServer(p, "skywars_lobby_1");
                }else if(args[0].equalsIgnoreCase("survival" )|| args[0].equalsIgnoreCase("sur")){
                    sendToServer(p, "survival");
                }else{
                    p.sendMessage(ChatColor.RED + args[0]  + " is not a server!");
                }
            }else if(args.length >= 2){
                p.sendMessage(ChatColor.RED + "The correct usage is /lobby <lobby>");
            }


        }else if(commandLabel.equalsIgnoreCase("test")){

        }else if(commandLabel.equalsIgnoreCase("test2")){
            if(p.hasPermission("voice.admin")){
                ///Here if where you put the code that will execute when the player enters the command
            }else{
                p.sendMessage(ChatColor.RED + "You do not have permission to use this command nub!");
            }
        }
        return false;
    }


    @EventHandler
    public void leave(PlayerQuitEvent e){
        e.setQuitMessage(null);
    }

    @EventHandler
    public void snowball(PlayerEggThrowEvent event){
        Player p = (Player) event.getPlayer();
        Egg egg = event.getEgg();
        event.setHatching(false);
        event.getEgg().setVelocity(p.getLocation().getDirection().normalize().add(new Vector(0,2.5,10)));

        event.getEgg().getWorld().createExplosion(event.getEgg().getLocation(), 20,true);



    }

    @EventHandler
    public void oncraft(CraftItemEvent e){
        Player p = (Player) e.getWhoClicked();
    }



}





